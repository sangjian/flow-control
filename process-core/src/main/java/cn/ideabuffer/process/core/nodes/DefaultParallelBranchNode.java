package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.executor.NodeExecutors;
import cn.ideabuffer.process.core.handler.ExceptionHandler;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategies;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class DefaultParallelBranchNode extends AbstractExecutableNode<Void> implements ParallelBranchNode {

    private List<BranchNode> branches;

    private ProceedStrategy strategy = ProceedStrategies.AT_LEAST_ONE_FINISHED;

    public DefaultParallelBranchNode() {
        this(null);
    }

    public DefaultParallelBranchNode(List<BranchNode> branches) {
        this(null, null, null, branches);
    }

    public DefaultParallelBranchNode(Rule rule,
        Executor executor, ExceptionHandler handler) {
        this(rule, executor, handler, null);
    }

    public DefaultParallelBranchNode(Rule rule,
        Executor executor, ExceptionHandler handler, List<BranchNode> branches) {
        super(false, rule, executor);
        this.branches = branches == null ? new ArrayList<>() : branches;
    }

    public void setBranches(@NotNull List<BranchNode> branches) {
        this.branches = branches;
    }

    public void setStrategy(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
    }

    @NotNull
    @Override
    public ProcessStatus execute(Context context) throws Exception {
        if (branches == null || !ruleCheck(context)) {
            return ProcessStatus.PROCEED;
        }
        return NodeExecutors.PARALLEL_EXECUTOR.execute(getExecutor(), strategy, context,
            branches.toArray(new ExecutableNode[0]));
    }

    @Override
    protected final Void doExecute(Context context) throws Exception {
        return null;
    }

    @Override
    public void addBranch(@NotNull ExecutableNode<?>... nodes) {
        if (nodes.length > 0) {
            branches.add(new DefaultBranchNode(nodes));
        }
    }

    @Override
    public void addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
    }
}
