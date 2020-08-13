package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Processor;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sangjian.sj
 * @date 2020/05/03
 */
public class ProcessNode<R> extends AbstractExecutableNode<R, Processor<R>> {
    public ProcessNode() {
        super();
    }

    public ProcessNode(Processor<R> processor) {
        super.registerProcessor(processor);
    }

    public ProcessNode(Processor<R> processor, KeyMapper mapper) {
        super.registerProcessor(processor);
        super.setKeyMapper(mapper);
    }

    public ProcessNode(Processor<R> processor, KeyMapper mapper, Set<Key<?>> requiredKeys) {
        super.registerProcessor(processor);
        super.setKeyMapper(mapper);
        super.setRequiredKeys(requiredKeys);
    }

    public ProcessNode(Processor<R> processor, KeyMapper mapper, Key<?>... requiredKeys) {
        super.registerProcessor(processor);
        super.setKeyMapper(mapper);
        if (requiredKeys != null) {
            super.setRequiredKeys(Arrays.stream(requiredKeys).collect(Collectors.toSet()));
        }
    }

}
