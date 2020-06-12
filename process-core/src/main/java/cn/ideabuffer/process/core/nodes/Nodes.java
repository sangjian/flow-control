package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.aggregate.*;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.nodes.condition.DoWhileConditionNode;
import cn.ideabuffer.process.core.nodes.condition.IfConditionNode;
import cn.ideabuffer.process.core.nodes.condition.WhileConditionNode;
import cn.ideabuffer.process.core.processors.ResultProcessor;
import cn.ideabuffer.process.core.processors.impl.GenericAggregateProcessorImpl;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Nodes {

    private Nodes() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> ProcessNode<R> newProcessNode() {
        return new ProcessNode<>();
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor) {
        return new ProcessNode<>(processor);
    }

    public static <R> ProcessNode<R> newProcessNode(Processor<R> processor, KeyMapper mapper) {
        return new ProcessNode<>(processor, mapper);
    }

    public static <R> BaseNode<R> newBaseNode(@NotNull ResultProcessor<R> processor) {
        return new DefaultBaseNode<>(processor);
    }

    public static NodeGroup newGroup() {
        return new NodeGroup();
    }

    public static BranchNode newBranch() {
        return new DefaultBranchNode();
    }

    public static BranchNode newBranch(Rule rule, @NotNull Processor<?>... processors) {
        List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
            Collectors.toList());
        return newBranch(rule, nodes);
    }

    public static BranchNode newBranch(@NotNull Processor<?>... processors) {
        return newBranch(null, processors);
    }

    public static BranchNode newBranch(@NotNull List<ExecutableNode<?, ?>> nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull List<ExecutableNode<?, ?>> nodes) {
        return new DefaultBranchNode(rule, nodes);
    }

    public static BranchNode newBranch(@NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(null, Arrays.asList(nodes));
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode<?, ?>... nodes) {
        return newBranch(rule, Arrays.asList(nodes));
    }

    public static ParallelBranchNode newParallelBranchNode() {
        return newParallelBranchNode(null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule) {
        return newParallelBranchNode(rule, null);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, List<BranchNode> branches) {
        return newParallelBranchNode(rule, null, branches);
    }

    public static ParallelBranchNode newParallelBranchNode(Rule rule, Executor executor, List<BranchNode> branches) {
        return new DefaultParallelBranchNode(rule, executor, branches);
    }

    public static <R> UnitAggregatableNode<R> newUnitAggregatableNode() {
        return new DefaultUnitAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode() {
        return new DefaultGenericAggregatableNode<>();
    }

    public static <P, R> GenericAggregatableNode<P, R> newGenericAggregatableNode(
        GenericAggregateProcessorImpl<P, R> processor) {
        return new DefaultGenericAggregatableNode<>(processor);
    }

    public static <R> DistributeAggregatableNode<R> newDistributeAggregatableNode() {
        return new DefaultDistributeAggregatableNode<>();
    }

    public static IfWhen newIf(Rule rule) {
        return new IfWhen(rule);
    }

    public static WhileWhen newWhile(Rule rule) {
        return new WhileWhen(rule);
    }

    public static DoWhileWhen newDoWhile(Rule rule) {
        return new DoWhileWhen(rule);
    }

    public static TryCatchFinally newTry(BranchNode branch) {
        return new TryCatchFinally(branch);
    }

    public static TryCatchFinally newTry(ExecutableNode<?, ?>... nodes) {
        return new TryCatchFinally(new DefaultBranchNode(nodes));
    }

    public static TryCatchFinally newTry(Processor<?>... processors) {
        if (processors == null || processors.length == 0) {
            return new TryCatchFinally(new DefaultBranchNode());
        }
        List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
            Collectors.toList());
        return newTry(nodes);
    }

    public static TryCatchFinally newTry(List<ExecutableNode<?, ?>> nodes) {
        return new TryCatchFinally(new DefaultBranchNode(nodes));
    }

    public static class IfWhen {

        private Rule rule;

        IfWhen(Rule rule) {
            this.rule = rule;
        }

        public IfWhenBuilder then(@NotNull BranchNode branch) {
            return new IfWhenBuilder(rule, branch);
        }

        public IfWhenBuilder then(@NotNull ExecutableNode<?, ?>... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public IfWhenBuilder then(@NotNull List<ExecutableNode<?, ?>> nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public IfWhenBuilder then(@NotNull Processor<?>... processors) {
            return then(Arrays.stream(processors).map(Nodes::newProcessNode).collect(Collectors.toList()));
        }

        public class IfWhenBuilder {

            private Rule rule;

            private BranchNode thenBranch;

            IfWhenBuilder(Rule rule, BranchNode thenBranch) {
                this.rule = rule;
                this.thenBranch = thenBranch;
            }

            public IfConditionNode otherwise(BranchNode branch) {
                return new IfConditionNode(rule, thenBranch, branch);
            }

            public IfConditionNode otherwise(ExecutableNode<?, ?>... nodes) {
                return otherwise(new DefaultBranchNode(nodes));
            }

            public IfConditionNode otherwise(Processor<?>... processors) {
                return otherwise(new DefaultBranchNode(processors));
            }

            public IfConditionNode end() {
                return new IfConditionNode(rule, thenBranch);
            }

        }

    }

    public static class WhileWhen {

        protected Rule rule;

        WhileWhen(Rule rule) {
            this.rule = rule;
        }

        public WhileConditionNode then(BranchNode branch) {
            return new WhileConditionNode(rule, branch);
        }

        public WhileConditionNode then(ExecutableNode<?, ?>... nodes) {
            return then(new DefaultBranchNode(nodes));
        }

        public WhileConditionNode then(List<ExecutableNode<?, ?>> nodes) {
            return then(new DefaultBranchNode(null, nodes));
        }

        public WhileConditionNode then(@NotNull Processor<?>... processors) {
            List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
                Collectors.toList());
            return then(nodes);
        }

    }

    public static class DoWhileWhen extends WhileWhen {

        DoWhileWhen(Rule rule) {
            super(rule);
        }

        @Override
        public WhileConditionNode then(BranchNode branch) {
            return new DoWhileConditionNode(rule, branch);
        }

    }

    public static class TryCatchFinally {

        private BranchNode tryBranch;

        private List<TryCatchFinallyNode.CatchMapper> catchMapperList;

        TryCatchFinally(BranchNode tryBranch) {
            this.tryBranch = tryBranch;
            this.catchMapperList = new LinkedList<>();
        }

        public TryCatchFinally catchOn(Class<? extends Throwable> expClass, BranchNode branch) {
            if (expClass == null) {
                throw new NullPointerException();
            }
            this.catchMapperList.add(new TryCatchFinallyNode.CatchMapper(expClass, branch));
            return this;
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, ExecutableNode<?, ?>... nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, List<ExecutableNode<?, ?>> nodes) {
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinally catchOn(@NotNull Class<? extends Throwable> expClass, Processor<?>... processors) {
            if (processors == null || processors.length == 0) {
                return catchOn(expClass, new DefaultBranchNode());
            }
            List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
                Collectors.toList());
            return catchOn(expClass, new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode doFinally(BranchNode branch) {
            return new TryCatchFinallyNode(tryBranch, catchMapperList, branch);
        }

        public TryCatchFinallyNode doFinally(ExecutableNode<?, ?>... nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }

        public TryCatchFinallyNode doFinally(Processor<?>... processors) {
            if (processors == null || processors.length == 0) {
                return new TryCatchFinallyNode(tryBranch, catchMapperList, null);
            }
            List<ExecutableNode<?, ?>> nodes = Arrays.stream(processors).map(Nodes::newProcessNode).collect(
                Collectors.toList());
            return doFinally(nodes);
        }

        public TryCatchFinallyNode doFinally(List<ExecutableNode<?, ?>> nodes) {
            return doFinally(new DefaultBranchNode(nodes));
        }
    }
}
