package rewind.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rewind.jpashop.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
