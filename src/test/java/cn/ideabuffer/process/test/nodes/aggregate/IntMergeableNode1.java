package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntMergeableNode1 extends AbstractMergeableNode<Integer> {

    @Override
    protected Integer doInvoke(Context context) throws Exception {
        return 12;
    }
}
