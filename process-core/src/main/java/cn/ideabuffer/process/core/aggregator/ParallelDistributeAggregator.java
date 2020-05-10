package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.processors.DistributeProcessor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author sangjian.sj
 * @date 2020/03/08
 */
public class ParallelDistributeAggregator<O> implements DistributeAggregator<O> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Executor executor;
    private Class<O> resultClass;

    public ParallelDistributeAggregator(@NotNull Executor executor, @NotNull Class<O> resultClass) {
        this.executor = executor;
        this.resultClass = resultClass;
    }

    @Override
    public O aggregate(Context context, List<DistributeMergeableNode<?, O>> nodes) throws Exception {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        O result;
        try {
            result = resultClass.newInstance();
        } catch (Exception e) {
            logger.error("aggregate error, resultClass:{}, nodes:{}", resultClass, nodes, e);
            throw new RuntimeException(e);
        }
        long maxTimeout = Aggregators.getMaxTimeout(nodes);
        BlockingQueue<MergerNode<?, O>> mergerNodes = new LinkedBlockingQueue<>(nodes.size());
        List<CompletableFuture<?>> timeouts = new LinkedList<>();
        List<CompletableFuture<?>> normals = new LinkedList<>();
        nodes.forEach(node -> {
            CompletableFuture<?> f = getFuture(context, node);
            // 因为结果对象只有一个，为了避免并发可能引起的问题，这里不希望并行地去merge；
            // 将返回结果封装成对象后放入队列，所有节点执行完毕后再进行统一merge
            f.thenAccept(v -> {
                //noinspection unchecked
                MergerNode<?, O> m = new MergerNode(node, v, result);
                mergerNodes.offer(m);
            });
            if (node.getTimeout() > 0) {
                timeouts.add(f);
            } else {
                normals.add(f);
            }
        });

        CompletableFuture<Void> allTimeouts = CompletableFuture.allOf(timeouts.toArray(new CompletableFuture[0]));
        CompletableFuture<Void> allNormals = CompletableFuture.allOf(normals.toArray(new CompletableFuture[0]));

        try {
            // 如果有超时控制，则等待最大的超时时间
            if (maxTimeout > 0) {
                allTimeouts.get(maxTimeout, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException e) {
            logger.error("timeout!", e);
        } finally {
            // 确保没有超时控制的节点执行结束
            allNormals.join();
            // 统一merge结果
            mergerNodes.forEach(MergerNode::merge);
        }

        return result;
    }

    private CompletableFuture<?> getFuture(Context context, DistributeMergeableNode<?, O> node) {
        Supplier<?> supplier = new InvokeSupplier<>(node, context);
        CompletableFuture<?> future;
        if (executor == null) {
            future = CompletableFuture.supplyAsync(supplier);
        } else {
            future = CompletableFuture.supplyAsync(supplier, executor);
        }
        return future;
    }

    private class MergerNode<V, R> {
        private DistributeMergeableNode<V, R> node;
        private V value;
        private R result;

        MergerNode(DistributeMergeableNode<V, R> node, V value, R result) {
            this.node = node;
            this.value = value;
            this.result = result;
        }

        public void merge() {
            if (node.getProcessor() != null) {
                node.getProcessor().merge(value, result);
            }
        }
    }

    private class InvokeSupplier<V> implements Supplier<V> {

        private DistributeMergeableNode<V, O> node;

        private Context context;

        InvokeSupplier(DistributeMergeableNode<V, O> node, Context context) {
            this.node = node;
            this.context = context;
        }

        @Override
        public V get() {
            try {
                if (node.getProcessor() == null) {
                    return null;
                }
                return node.getProcessor().process(context);
            } catch (Exception e) {
                logger.error("InvokeSupplier invoke error, node:{}", node, e);
                throw new RuntimeException(e);
            }
        }
    }
}
