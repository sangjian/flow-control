package cn.ideabuffer.process.nodes.branch;

import cn.ideabuffer.process.nodes.ExecutableNode;
import cn.ideabuffer.process.rule.Rule;
import org.jetbrains.annotations.NotNull;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class Branches {

    public static BranchNode newBranch(@NotNull ExecutableNode... nodes) {
        return newBranch(null, nodes);
    }

    public static BranchNode newBranch(Rule rule, @NotNull ExecutableNode... nodes) {
        return new DefaultBranch(rule, nodes);
    }
}
