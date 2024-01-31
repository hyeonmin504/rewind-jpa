package rewind.jpashop.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rewind.jpashop.domain.Address;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderItem;
import rewind.jpashop.domain.OrderStatus;
import rewind.jpashop.repository.OrderRepository;
import rewind.jpashop.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public Result SearchOrderItem(OrderSearch orderSearch) {
        List<Order> findOrder = orderRepository.findAllbyQuerydsl(orderSearch);

        List<OrderDto> collect = findOrder.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        return new Result(collect,collect.size());
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto (Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;
        public OrderItemDto( OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private int size;
    }
}
