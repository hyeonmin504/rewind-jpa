package rewind.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rewind.jpashop.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
