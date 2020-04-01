package cn.ideabuffer.process.core.executor;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

/**
 * 串行执行器
 *
 * @author sangjian.sj
 * @date 2020/02/25
 */
public interface SerialExecutor {

    /**
     * 执行节点
     *
     * @param context 流程上下文
     * @param nodes   可执行节点列表
     * @return
     * @throws Exception
     */
    ProcessStatus execute(Context context, ExecutableNode... nodes) throws Exception;
}