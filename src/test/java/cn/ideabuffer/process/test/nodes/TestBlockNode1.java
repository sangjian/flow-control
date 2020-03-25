package cn.ideabuffer.process.test.nodes;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.block.Block;
import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestBlockNode1 extends AbstractExecutableNode {

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        Block block = context.getBlock();
        int k = block.get("k", 0);
        logger.info("before put, k = {}", k);
        block.put("k", 100);
        logger.info("after put, k = {}", block.get("k"));
        return ProcessStatus.PROCEED;
    }
}
