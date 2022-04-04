package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceImplTest {

    /*
        생성자 주입을 선택하는 이유

        1. 불변 - 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없어 불변하게 설계할 수 있음
        2. 누락 - 프레임워크 없이 순수한 자바로 단위 테스트를 하는 경우 의존관계 주입이 누락될 경우 NPE 이 발생할 수 있음
        3. final 키워드 - 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에서 막아줌
     */

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}