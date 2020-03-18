package cn.ideabuffer.process.test.nodes.aggregate;

import cn.ideabuffer.process.Context;
import cn.ideabuffer.process.nodes.AbstractMergeableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class IntArrayMergeableNode2 extends AbstractMergeableNode<int[]> {

    @Override
    protected int[] doInvoke(Context context) throws Exception {
        return new int[] {3, 5, 8};
    }
}