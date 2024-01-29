package rewind.jpashop.repository;

import org.springframework.data.repository.query.Param;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> findAll(@Param("username") String username, @Param("status") OrderStatus status);
}
