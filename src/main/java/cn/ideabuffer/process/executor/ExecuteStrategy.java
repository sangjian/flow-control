package cn.ideabuffer.process.executor;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.ExecutableNode;
import cn.ideabuffer.process.branch.Branch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @author sangjian.sj
 * @date 2020/02/24
 */
public interface ExecuteStrategy {

    /**
     * 在执行的线程池中执行节点
     *
     * @param executor 执行线程池执行
     * @param context 流程上下文
     * @param nodes 可执行节点列表
     * @return
     * @throws Exception
     */
    boolean execute(ExecutorService executor, Context context, ExecutableNode... nodes) throws Exception;

    default boolean execute(ExecutorService executor, Context context, Branch branch) throws Exception {
        if(branch == null || branch.getNodes() == null) {
            return false;
        }
        return execute(executor, context, branch.getNodes().toArray(new ExecutableNode[0]));
    }
}
