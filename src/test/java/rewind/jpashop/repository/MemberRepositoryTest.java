package rewind.jpashop.repository;

import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.OrderItem;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.service.MemberService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class MemberRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    MemberService memberService;
    @Test
    @Commit
    public void memberReporsitorytest() throws Exception {
        //given
        Member memberA = new Member("userA");
        Member savedMember = memberRepository.save(memberA);
        List<Member> userA = memberRepository.findByusername("userA");
        for (Member member : userA) {
            System.out.println("userA = " + userA.stream().map( m -> (
                    m.getUsername()
                    )));
        }
        //when
        Item item = new Book("autor", "123", "kim", 10000, 5);

        OrderItem orderItem1 = OrderItem.createOrderItem(item, 10000, 2);
        OrderItem saved = orderItemRepository.save(orderItem1);

        System.out.println("orderPrice = " + orderItem1.getOrderPrice());
        System.out.println("orderPrice = " + item.getStockQuantity());
        //then
        assertThat(savedMember.getUsername()).isEqualTo("userA");
    }

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        assertThat(member).isEqualTo(memberRepository.findById(savedId).get());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원가입() throws Exception {
        //given
        Member member = new Member("kim");
        Member member2 = new Member("kim");
        //when
        Long join = memberService.join(member);
        Long join1 = memberService.join(member2);
        //then
        fail("예외가 발생해야 함");

    }
}