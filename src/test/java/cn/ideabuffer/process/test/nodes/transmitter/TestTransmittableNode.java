package cn.ideabuffer.process.test.nodes.transmitter;

import cn.ideabuffer.process.context.Context;
import cn.ideabuffer.process.nodes.transmitter.AbstractTransmittableNode;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TestTransmittableNode extends AbstractTransmittableNode<String> {
    @Override
    protected String doInvoke(Context context) throws Exception {
        return "hello";
    }
}
