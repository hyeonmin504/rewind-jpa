package rewind.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.*;
import rewind.jpashop.domain.Dto.MemberOrderDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;
import static rewind.jpashop.domain.QMember.member;
import static rewind.jpashop.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final EntityManager em;
    JPAQueryFactory queryFactory;


    @Override
    public List<Order> findAllbyQuerydsl(OrderSearch orderSearch) {
        QOrder order = new QOrder("o");
        QMember member = new QMember("m");
        QDelivery delivery = new QDelivery("d");
        queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();

        if(orderSearch.getOrderStatus()!=null){
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }
        if(orderSearch.getMemberName()!=null) {
            builder.and(member.username.eq(orderSearch.getMemberName()));
        }
        return queryFactory
                .select(order)
                .from(order)
                .innerJoin(order.member, member).fetchJoin()
                .innerJoin(order.delivery, delivery).fetchJoin()
                .where(builder)
                .fetch();
    }

    public List<Order> findAllQuerydsl2(OrderSearch orderSearch){
        QOrder order = new QOrder("o");
        QMember member = new QMember("m");
        QDelivery delivery = new QDelivery("d");
        queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(order)
                .innerJoin(order.member, member).fetchJoin()
                .innerJoin(order.delivery, delivery).fetchJoin()
                .where(statusEq(orderSearch.getOrderStatus()), usernameEq(orderSearch.getMemberName()))
                .fetch();
    }

    @Override
    public Page<MemberOrderDto> searchPageSimple(MemberOrderDto memberOrderDto, Pageable pageable) {
        queryFactory = new JPAQueryFactory(em);
        QOrder order = new QOrder("o");
        QMember member = new QMember("m");
        QueryResults<MemberOrderDto> results = queryFactory
                .select(Projections.constructor(
                        MemberOrderDto.class,
                        member.username,
                        member.age,
                        order.orderDate,
                        order.status))
                .from(order)
                .innerJoin(order.member, member)
                .where(
                        usernameEq(memberOrderDto.getUsername()),
                        ageEq(memberOrderDto.getAge()),
                        orderDateEq(memberOrderDto.getOrderDate()),
                        statusEq(memberOrderDto.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<MemberOrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
    @Override
    public List<MemberOrderDto> searchPageComplex(MemberOrderDto memberOrderDto, Pageable pageable) {
        return null;
    }

    private BooleanExpression orderDateEq(LocalDateTime orderDate) {
        return orderDate != null ? order.orderDate.eq(orderDate) : null;
    }
    private BooleanExpression ageEq(int age) {
        return age != NULL ? member.age.eq(age) : null;
    }
    private BooleanExpression usernameEq(String memberName) {
        return memberName != null ? member.username.eq(memberName) : null;
    }
    private BooleanExpression statusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

}
