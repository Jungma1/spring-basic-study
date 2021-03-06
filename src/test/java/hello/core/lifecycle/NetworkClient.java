package hello.core.lifecycle;

/*
    InitializingBean(초기화), DisposableBean(소멸) 인터페이스)
    - 스프링 전용 인터페이스에 의존함
    - 초기화, 소멸 메서드의 이름을 변경할 수 없음
    - 코드를 고칠 수 없는 외부 라이브러리를 적용할 수 없음
    = 요즘에는 거의 사용하지 않음


    @Bean(initMethod = "init", destroyMethod = "close") - 빈 등록 설정 정보 사용)
    - 메서드 이름을 자유롭게 줄 수 있음
    - 스프링 빈이 스프링 코드에 의존하지 않음
    - *코드를 고칠 수 없는 외부 라이브러리에도 초기화 종료 메서드를 적용할 수 있음
    - destroyMethod = "(inferred)" 가 기본값으로 종료 메서드를 추론해서 호출하는 기능이 있음 (close, shutdown 등)


    애노테이션 @PostConstruct, @PreDestroy)
    - 최신 스프링에서 가장 권장하는 방법임
    - 스프링에 종속적인 기술이 아니라 JSR-250 라는 자바 표준임
    - *단점은 외부 라이브러리에는 적용하지 못함. 외부 라이브러리를 초기화, 종료 해야 하면 @Bean 의 기능을 사용


    *결론, @PostConstruct, @PreDestroy 애노테이션을 사용
 */

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;
    
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    @PostConstruct
    public void init() { // 의존관계 주입이 끝나고 호출
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
