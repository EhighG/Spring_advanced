package hello.advanced.trace.strategy.code.strategy;

@FunctionalInterface
public interface Strategy {

    void call(); // 변하는 부분(알고리즘)
}
