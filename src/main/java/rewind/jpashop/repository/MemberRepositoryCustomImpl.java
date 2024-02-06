package rewind.jpashop.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rewind.jpashop.domain.Dto.MemberOrderDto;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.QMember;
import rewind.jpashop.domain.QOrder;

import java.util.List;

import static rewind.jpashop.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateUsername(Long memberId, String username) {
        jpaQueryFactory = new JPAQueryFactory(em);
        QMember member = new QMember("m");

        jpaQueryFactory
                .update(member)
                .set(member.username, username)
                .where(member.id.eq(memberId))
                .execute();
    }

    @Override
    public List<Member> findByPage(int age, int offset, int limit) {
        jpaQueryFactory = new JPAQueryFactory(em);
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(age))
                .orderBy(member.username.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public long totalCount(int age) {
        jpaQueryFactory = new JPAQueryFactory(em);
        return jpaQueryFactory
                .select(member.count())
                .from(member)
                .where(member.age.eq(age))
                .fetchCount();
    }
}
