package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.nodes.AggregatableNode;
import cn.ideabuffer.process.core.nodes.MergeableNode;
import cn.ideabuffer.process.core.nodes.transmitter.AbstractTransmittableNode;
import cn.ideabuffer.process.core.processors.AggregateProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/04/13
 */
public abstract class AbstractAggregatableNode<I, O>
    extends AbstractTransmittableNode<O, AggregateProcessor<I, O>> implements
    AggregatableNode<I, O> {

    public AbstractAggregatableNode(@NotNull AggregateProcessor<I, O> processor) {
        super.registerProcessor(processor);
    }

    @Override
    protected void onDestroy() {
        List<MergeableNode<I>> mergeableNodes = getProcessor().getMergeableNodes();
        if (mergeableNodes == null || mergeableNodes.isEmpty()) {
            return;
        }
        mergeableNodes.forEach((node) -> {
            try {
                node.destroy();
            } catch (Exception e) {
                logger.error("destroy encountered problem, node:{}", node, e);
            }
        });
    }
}
