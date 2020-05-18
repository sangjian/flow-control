package cn.ideabuffer.process.core.nodes.merger;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

/**
 * @author sangjian.sj
 * @date 2020/03/11
 */
public class DoubleSumMerger implements UnitMerger<Double> {

    @Override
    public Double merge(@NotNull Collection<Double> results) {
        if (results == null) {
            return 0d;
        }

        return results.stream().filter(Objects::nonNull).mapToDouble(value -> value).sum();
    }
}
