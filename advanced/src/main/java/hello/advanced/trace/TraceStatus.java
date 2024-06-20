package hello.advanced.trace;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그의 상태정보 저장
 * 로그를 종료할 때 표기할 것들이 필요해서, 관련 정보들도 저장
 */
@Getter @Setter
public class TraceStatus {

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    private TraceId traceId;
    private Long startTimeMs; // 전체 수행시간을 얻기 위함
    private String message;
}
