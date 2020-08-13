package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.ReturnCondition;
import cn.ideabuffer.process.core.aggregator.DistributeAggregator;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.DistributeAggregatableNode;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.processors.DistributeAggregateProcessor;
import cn.ideabuffer.process.core.processors.impl.DistributeAggregateProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class DistributeAggregatableNodeBuilder<R>
    extends AbstractExecutableNodeBuilder<R, DistributeAggregateProcessor<R>, DistributeAggregatableNode<R>> {

    private List<DistributeMergeableNode<?, R>> mergeableNodes;

    private DistributeAggregator<R> aggregator;

    private DistributeAggregatableNodeBuilder(DistributeAggregatableNode<R> node) {
        super(node);
        mergeableNodes = new ArrayList<>();
    }

    public static <R> DistributeAggregatableNodeBuilder<R> newBuilder() {
        DistributeAggregatableNode<R> node = Nodes.newDistributeAggregatableNode();
        return new DistributeAggregatableNodeBuilder<>(node);
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> addListeners(ProcessListener<R>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> by(DistributeAggregateProcessor<R> processor) {
        super.by(processor);
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregate(@NotNull DistributeMergeableNode<?, R>... nodes) {
        this.mergeableNodes.addAll(Arrays.asList(nodes));
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregate(@NotNull List<DistributeMergeableNode<?, R>> nodes) {
        this.mergeableNodes.addAll(nodes);
        return this;
    }

    public DistributeAggregatableNodeBuilder<R> aggregator(@NotNull DistributeAggregator<R> aggregator) {
        this.aggregator = aggregator;
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> resultKey(Key<R> resultKey) {
        super.resultKey(resultKey);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> returnOn(ReturnCondition<R> condition) {
        super.returnOn(condition);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> require(@NotNull Key<?> key) {
        super.require(key);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> require(@NotNull Key<?>... keys) {
        super.require(keys);
        return this;
    }

    @Override
    public DistributeAggregatableNodeBuilder<R> require(@NotNull Set<Key<?>> keys) {
        super.require(keys);
        return this;
    }

    @Override
    public DistributeAggregatableNode<R> build() {
        if (processor == null) {
            processor = new DistributeAggregateProcessorImpl<>();
        }
        DistributeAggregatableNode<R> node = super.build();
        processor.aggregate(mergeableNodes);
        processor.aggregator(aggregator);
        return node;
    }
}
