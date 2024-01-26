package rewind.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rewind.jpashop.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryImpl {
    public String findByusername(String username);
}
