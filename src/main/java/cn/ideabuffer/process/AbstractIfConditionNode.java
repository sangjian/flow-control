package cn.ideabuffer.process;

import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.block.BlockFacade;
import cn.ideabuffer.process.block.BlockWrapper;
import cn.ideabuffer.process.block.DefaultBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sangjian.sj
 * @date 2020/01/18
 */
public abstract class AbstractIfConditionNode<E> extends AbstractExecutableNode implements IfConditionNode<E> {

    private List<ExecutableNode> trueNodeList;

    private List<ExecutableNode> falseNodeList;

    public AbstractIfConditionNode(String id) {
        super(id);
        trueNodeList = new ArrayList<>();
        falseNodeList = new ArrayList<>();
    }

    @Override
    public IfConditionNode<E> addTrueNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        trueNodeList.add(node);
        return this;
    }

    @Override
    public IfConditionNode<E> addFalseNode(ExecutableNode node) {
        if(node == null) {
            throw new NullPointerException();
        }
        falseNodeList.add(node);
        return this;
    }

    @Override
    public List<ExecutableNode> getTrueNodes() {
        return trueNodeList;
    }

    @Override
    public List<ExecutableNode> getFalseNodes() {
        return falseNodeList;
    }

    @Override
    public boolean execute(Context context) throws Exception {

        Boolean judgement = judge(context);
        List<ExecutableNode> nodeList;
        if (Boolean.TRUE.equals(judgement)) {
            nodeList = trueNodeList;
        } else {
            nodeList = falseNodeList;
        }
        Block ifBlock = new DefaultBlock(context.getBlock());
        BlockWrapper blockWrapper = new BlockWrapper(ifBlock);
        ContextWrapper contextWrapper = new ContextWrapper(context, new BlockFacade(blockWrapper));
        for (ExecutableNode node : nodeList) {
            boolean stop = node.execute(contextWrapper);
            if(stop) {
                return true;
            }
            if(blockWrapper.hasBroken() || blockWrapper.hasContinued()) {
                break;
            }
        }
        return false;

    }
}
