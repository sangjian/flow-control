package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.nodes.ExecutableNode;
import cn.ideabuffer.process.core.nodes.Nodes;
import cn.ideabuffer.process.core.nodes.ParallelBranchNode;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.Branches;
import cn.ideabuffer.process.core.processors.ParallelBranchProcessor;
import cn.ideabuffer.process.core.rule.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;
import cn.ideabuffer.process.core.strategy.ProceedStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class ParallelBranchNodeBuilder extends AbstractExecutableNodeBuilder<ProcessStatus, ParallelBranchProcessor, ParallelBranchNode> {

    private List<BranchNode> branches;

    private ProceedStrategy strategy;

    private ParallelBranchNodeBuilder() {
        super(Nodes.newParallelBranchNode());
        this.branches = new ArrayList<>();
    }

    public static ParallelBranchNodeBuilder newBuilder() {
        return new ParallelBranchNodeBuilder();
    }

    @Override
    public ParallelBranchNodeBuilder parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder addListeners(ProcessListener... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public ParallelBranchNodeBuilder by(ParallelBranchProcessor processor) {
        super.by(processor);
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        this.branches.add(Branches.newBranch(nodes));
        return this;
    }

    public ParallelBranchNodeBuilder addBranch(@NotNull BranchNode branch) {
        this.branches.add(branch);
        return this;
    }

    public ParallelBranchNodeBuilder proceedWhen(@NotNull ProceedStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public ParallelBranchNode build() {
        ParallelBranchNode node = super.build();
        this.branches.forEach(processor::addBranch);
        processor.proceedWhen(strategy);
        return node;
    }
}