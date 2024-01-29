package rewind.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.*;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.repository.ItemRepository;
import rewind.jpashop.repository.MemberRepository;
import rewind.jpashop.repository.OrderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long ItemId, int orderCount) {
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(ItemId).get();

        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();

    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }


}
