package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.block.Block;
import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.ContextWrapper;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.nodes.branch.DefaultBranchNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/01
 */
public class NodeGroup extends DefaultBranchNode {

    @Override
    public ProcessStatus execute(Context context) throws Exception {
        Block block = new Block(context.getBlock());
        ContextWrapper contextWrapper = Contexts.wrap(context, block);
        return super.execute(contextWrapper);
    }

}
