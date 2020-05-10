package cn.ideabuffer.process.core.aggregator;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.nodes.DistributeMergeableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/03/27
 */
public interface DistributeAggregator<O> {
    /**
     * 对可聚合的节点进行结果聚合，并返回聚合后的结果
     *
     * @param context 流程上下文
     * @param nodes   可合并节点
     * @return 聚合结果
     * @throws Exception
     */
    O aggregate(Context context, List<DistributeMergeableNode<?, O>> nodes) throws Exception;
}
