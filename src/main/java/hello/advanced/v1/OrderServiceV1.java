package hello.advanced.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 안쪽의 @Component 어노테이션으로 인해 컴포넌트 스캔의 대상이 되고, 자동으로 스프링 빈으로 등록됨
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 trace;

////    @Autowired // 생성자가 하나면, 자동으로 Autowired가 됨.
//    public OrderService(OrderRepositoryV1 orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야 한다.(해당 기능이 없을 때와 동일하게)
        }
    }
}
