package rewind.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;
import rewind.jpashop.domain.QOrder;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Order> findAll(String username, OrderStatus status) {
        QOrder order = new QOrder("o");
        return new ArrayList<>();
    }


}
