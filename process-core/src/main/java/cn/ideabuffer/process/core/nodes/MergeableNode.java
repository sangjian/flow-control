package cn.ideabuffer.process.core.nodes;

import cn.ideabuffer.process.core.Matchable;
import cn.ideabuffer.process.core.Mergeable;
import cn.ideabuffer.process.core.Node;

/**
 * 可合并结果的节点
 *
 * @author sangjian.sj
 * @date 2020/03/07
 */
public interface MergeableNode extends Node, Mergeable, Matchable {

}
