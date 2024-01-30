package rewind.jpashop.repository;

import com.querydsl.core.Tuple;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.service.OrderService;

import java.util.List;

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

        List<Tuple> orders = orderRepository.findAll("kim",OrderStatus.ORDER);
        for (Tuple order1 : orders) {
            System.out.println("order1 = " + order1);
        }
        //then

    }
}