package cn.ideabuffer.process.core.nodes.aggregate;

import cn.ideabuffer.process.core.processors.UnitAggregateProcessorImpl;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class DefaultUnitAggregatableNode<R> extends AbstractAggregatableNode<R, R>
    implements UnitAggregatableNode<R> {

    public DefaultUnitAggregatableNode() {
        this(new UnitAggregateProcessorImpl<>());
    }

    public DefaultUnitAggregatableNode(UnitAggregateProcessorImpl<R> processor) {
        super(processor);
    }

}
