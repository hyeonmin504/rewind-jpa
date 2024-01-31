package rewind.jpashop.repository;

import com.querydsl.core.Tuple;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> findAllbyQuerydsl(OrderSearch orderSearch);
}
