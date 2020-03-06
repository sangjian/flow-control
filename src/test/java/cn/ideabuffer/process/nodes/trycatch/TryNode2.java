package cn.ideabuffer.process.nodes.trycatch;

import cn.ideabuffer.process.nodes.AbstractExecutableNode;
import cn.ideabuffer.process.Context;

/**
 * @author sangjian.sj
 * @date 2020/02/28
 */
public class TryNode2 extends AbstractExecutableNode {

    @Override
    public boolean doExecute(Context context) throws Exception {
        System.out.println("in TryNode2");

        throw new NullPointerException();
    }
}
