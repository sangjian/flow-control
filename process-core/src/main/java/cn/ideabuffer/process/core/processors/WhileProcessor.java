package cn.ideabuffer.process.core.processors;

import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.context.KeyMapper;
import cn.ideabuffer.process.core.nodes.branch.BranchNode;
import cn.ideabuffer.process.core.rules.Rule;
import cn.ideabuffer.process.core.status.ProcessStatus;

import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2020/05/02
 */
public interface WhileProcessor extends ComplexProcessor<ProcessStatus> {

    Rule getRule();

    void setRule(Rule rule);

    BranchNode getBranch();

    void setBranch(BranchNode branch);

    KeyMapper getKeyMapper();

    void setKeyMapper(KeyMapper keyMapper);

    Set<Key<?>> getReadableKeys();

    void setReadableKeys(Set<Key<?>> readableKeys);

    Set<Key<?>> getWritableKeys();

    void setWritableKeys(Set<Key<?>> writableKeys);
}
