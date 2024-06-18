package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // 반환 타입이 사용처마다 다르므로, 제네릭으로
    // 제네릭은 일단, 타입 지정 시점을 객체 생성 시점으로 미룬다고 생각하면 된다.
    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            // 로직 호출
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외는 그대로 던져주기로 했었다.
        }
    }

    protected abstract T call();
}
