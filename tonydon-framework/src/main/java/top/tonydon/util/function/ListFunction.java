package top.tonydon.util.function;

import java.util.List;

@FunctionalInterface
public interface ListFunction<T> {
    List<T> apply();
}
