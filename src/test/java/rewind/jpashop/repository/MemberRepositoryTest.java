package rewind.jpashop.repository;

import jakarta.persistence.EntityManager;
import lombok.Data;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.OrderItem;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.service.MemberService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
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

    @Test
    public void findUserWithAge() throws Exception {
        //given
        Member member = new Member("kim", 10);
        Member member2 = new Member("lee", 10);
        memberRepository.save(member);
        memberRepository.save(member2);
        List<Member> kim = memberRepository.findUser("kim", 10);


        List<Member> byNames = memberRepository.findByNames(Arrays.asList(member.getUsername(),member2.getUsername()));
        List<String> collect = memberRepository.findAll().stream().map(Member::getUsername).collect(toList());
        List<Findusers> collect2 = memberRepository.findAll().stream().map(m -> new Findusers(m.getUsername())).toList();
        List<String> collect1 = byNames.stream().map(Member::getUsername).collect(toList());
        memberRepository.findByNames(collect);
        //when
        //then
        System.out.println("kim = " + kim);
        for (Findusers s : collect2) {
            System.out.println("Findusers = " + s);
        }
        assertThat(collect1).isEqualTo(collect2.stream().map(Findusers::getUsername).collect(toList()));
        assertThat(kim.get(0)).isEqualTo(member);


    }
    @Data
    static class Findusers {
         private String username;

        public Findusers(String username) {
            this.username = username;
        }
    }

    @Test
    public void findByPage() throws Exception {
        //given
        Member member = new Member("kim", 10);
        Member member2 = new Member("lee", 10);
        memberRepository.save(member);
        memberRepository.save(member2);

        PageRequest pageRequest = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"username"));

        Page<Member> page = memberRepository.findByAge(10, pageRequest);
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        List<Member> byPage = memberRepository.findByPage(10, 0, 3);
        long l = memberRepository.totalCount(10);
        //when
        //then
        assertThat(byPage.size()).isEqualTo(3);
        assertThat(l).isEqualTo(4);
    }
}