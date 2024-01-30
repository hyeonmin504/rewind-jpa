package rewind.jpashop.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.OrderStatus;
import rewind.jpashop.domain.QMember;
import rewind.jpashop.domain.QOrder;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final EntityManager em;
    JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> findAll(String username, OrderStatus status) {
        QOrder order = new QOrder("o");
        QMember member = new QMember("m");
        queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(order, member)
                .from(order)
                .join(order.member, member)
                .where(member.username.eq(username))
                        //order.status.eq(status))
                .fetch();

    }


}
