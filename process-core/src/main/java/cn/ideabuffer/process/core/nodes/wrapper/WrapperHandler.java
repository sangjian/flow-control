package cn.ideabuffer.process.core.nodes.wrapper;

import cn.ideabuffer.process.core.context.Context;
import cn.ideabuffer.process.core.status.ProcessStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author sangjian.sj
 * @date 2021/06/12
 */
public interface WrapperHandler<R> {

    void before(@NotNull Context context);

    void afterReturning(@NotNull Context context,  @Nullable R result, @NotNull ProcessStatus status);

    void afterThrowing(@NotNull Context context, @NotNull Throwable t);

}