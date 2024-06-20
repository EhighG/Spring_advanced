package hello.advanced.app.v4_template_method_pattern;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 안쪽의 @Component 어노테이션으로 인해 컴포넌트 스캔의 대상이 되고, 자동으로 스프링 빈으로 등록됨
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null; // Void일 땐 return문을 빼는 것까진 불가능하다.
            }
        };
        template.execute("OrderService.orderItem()");
    }
}
