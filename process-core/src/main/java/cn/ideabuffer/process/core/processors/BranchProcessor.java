package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface BranchProcessor extends Processor<Void> {

    void addNodes(ExecutableNode<?, ?>... nodes);

    List<ExecutableNode<?, ?>> getNodes();

}