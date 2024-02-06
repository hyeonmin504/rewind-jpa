package rewind.jpashop.repository;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Dto.MemberOrderDto;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.service.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRepositoryCustomImplTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Commit
    public void OrderSearch() throws Exception {
        //given
        Member member = new Member("kim");
        Item book = new Book("autor", "1111","book", 10000, 5);
        itemRepository.save(book);
        memberRepository.save(member);
        Long orderId = orderService.order(member.getId(), book.getId(), 3);
        //when
        Order order = orderRepository.findById(orderId).get();
        OrderSearch orderSearch = new OrderSearch(order.getMember().getUsername(),order.getStatus());
        List<Order> orders = orderRepository.findAllbyQuerydsl(orderSearch);
        for (Order order1 : orders) {
            System.out.println("order1 = " + order1);
        }
        //then

    }

    @Test
    @Commit
    public void OrderMemberDtoPageing() throws Exception {
        //given
        MemberOrderDto memberOrderDto = new MemberOrderDto();

        PageRequest pageRequest = PageRequest.of(0,3);

        Page<MemberOrderDto> result = orderRepository.searchPageSimple(memberOrderDto,pageRequest);

        //when
        //then
        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username").containsExactly("kim","lee");
    }
}