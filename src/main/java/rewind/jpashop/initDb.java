package rewind.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.*;
import rewind.jpashop.domain.item.Book;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1(){
            Member member = new Member("kim");
            member.setAddress(new Address("서울", "삼육대", "85"));
            member.setAge(10);
            em.persist(member);

            Book book1 = new Book("autor", "isbn", "name", 10000, 5);
            Book book2 = new Book("autor2", "isbn2", "name2", 20000, 10);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,10000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,20000,2);

            Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = new Member("lee");
            member.setAddress(new Address("경기도", "김포한강11로", "287"));
            member.setAge(10);
            em.persist(member);

            Book book1 = new Book("autor3", "isbn3", "username", 30000, 15);
            Book book2 = new Book("autor4", "isbn4", "username2", 40000, 20);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,30000,3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,40000,4);

            Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
