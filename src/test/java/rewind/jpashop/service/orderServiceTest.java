package rewind.jpashop.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.*;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.exception.NotEnoughStockException;
import rewind.jpashop.repository.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class orderServiceTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member("kim");
        member.setAddress(new Address("서울", "거리", "집주소"));
        memberRepository.save(member);

        Item book = new Book("autor", "1111", member.getUsername(), 10000, 10);
        itemRepository.save(book);
        //OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 5);

        //Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        Order getOrder = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000*2,getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과() throws Exception {
        //given
        Member member = new Member("kim");
        member.setAddress(new Address("서울", "거리", "집주소"));
        memberRepository.save(member);

        Item book = new Book("autor", "1111", member.getUsername(), 10000, 10);
        itemRepository.save(book);
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 12);
        //then
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = new Member("kim");
        member.setAddress(new Address("서울", "거리", "집주소"));
        memberRepository.save(member);

        Item book = new Book("autor", "1111", member.getUsername(), 10000, 10);
        itemRepository.save(book);

        Long orderId = orderService.order(member.getId(), book.getId(), 10);
        System.out.println("book = " + book.getStockQuantity());
        //when
        orderService.cancelOrder(orderId);
        //then
        assertEquals(10, book.getStockQuantity(),"주문 취소");
        assertEquals(OrderStatus.CANCEL,orderRepository.findById(orderId).get().getStatus(),"주문 취소후 상태");
    }

    @Test
    public void queryTest() throws Exception {
        //given
        Member member = new Member("kim");
        member.setAddress(new Address("서울", "거리", "집주소"));
        memberRepository.save(member);

        Item book = new Book("autor", "1111", member.getUsername(), 10000, 10);
        itemRepository.save(book);

        Long orderId = orderService.order(member.getId(), book.getId(), 10);

        //when
        Order order1 = orderRepository.findById(orderId).get();

        //then
        Assertions.assertThat(order1.getId()).isEqualTo(orderRepository.findById(orderId));
    }



}