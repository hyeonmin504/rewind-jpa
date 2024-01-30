package rewind.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rewind.jpashop.domain.QMember;

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
}
