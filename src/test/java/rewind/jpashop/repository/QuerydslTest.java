package rewind.jpashop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.QMember;

import java.util.List;

@Transactional
@SpringBootTest
public class QuerydslTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @Test
    public void total() throws Exception {
        //given
        QMember member = new QMember("m");
        QMember memberSub = new QMember("memberSub");
        queryFactory = new JPAQueryFactory(em);

        QueryResults<Member> results = queryFactory
                .select(member)
                .from(member)
                .orderBy(member.username.desc().nullsLast())
                .fetchResults();

        long total = results.getTotal();
        //when
        //then
    }
}
