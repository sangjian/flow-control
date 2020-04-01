package cn.ideabuffer.process.core.test.nodes;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.context.Contexts;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.core.status.ProcessStatus;

/**
 * @author sangjian.sj
 * @date 2020/03/05
 */
public class TestNode1 extends AbstractExecutableNode {

    @Override
    protected ProcessStatus doExecute(Context context) throws Exception {
        Key<Integer> key = Contexts.newKey("k", int.class);
        logger.info("in testNode1, k:{}", context.get(key));
        Thread.sleep(1000);
        return ProcessStatus.PROCEED;
    }
}