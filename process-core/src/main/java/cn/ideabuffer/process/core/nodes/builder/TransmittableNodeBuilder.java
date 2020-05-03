package cn.ideabuffer.process.core.nodes.builder;

import cn.ideabuffer.process.core.ProcessListener;
import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.nodes.TransmittableNode;
import cn.ideabuffer.process.core.rule.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * @author sangjian.sj
 * @date 2020/04/24
 */
public class TransmittableNodeBuilder<R, P extends Processor<R>> extends AbstractExecutableNodeBuilder<R, P, TransmittableNode<R, P>> {

    private TransmittableNodeBuilder(TransmittableNode<R, P> node) {
        super(node);
    }

    public static <R, P extends Processor<R>> TransmittableNodeBuilder newBuilder(@NotNull TransmittableNode<R, P> node) {
        return new TransmittableNodeBuilder<>(node);
    }

    @Override
    public TransmittableNodeBuilder<R, P> parallel() {
        super.parallel();
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> parallel(Executor executor) {
        super.parallel(executor);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> processOn(Rule rule) {
        super.processOn(rule);
        return this;
    }

    @Override
    public TransmittableNodeBuilder<R, P> addListeners(ProcessListener... listeners) {
        super.addListeners(listeners);
        return this;
    }

}