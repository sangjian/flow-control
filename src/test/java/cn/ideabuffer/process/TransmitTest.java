package cn.ideabuffer.process;

import cn.ideabuffer.process.nodes.transmitter.TestTransmittableNode;
import org.junit.Test;

/**
 * @author sangjian.sj
 * @date 2020/03/10
 */
public class TransmitTest {

    @Test
    public void testTransmitNode () throws Exception {
        Chain chain = new DefaultChain();
        Context context = new DefaultContext();
        TestTransmittableNode node = new TestTransmittableNode();
        node.thenApply((ctx, r) -> {
            System.out.println(r);
            return r + " world";
        }).thenApply((ctx, r) -> {
            System.out.println(r);
            return r.length();
        }).thenAccept((ctx, r) -> System.out.println(r));
        chain.addProcessNode(node);
        chain.execute(context);
    }

}