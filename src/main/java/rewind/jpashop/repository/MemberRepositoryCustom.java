package rewind.jpashop.repository;

import org.springframework.data.domain.Pageable;
import rewind.jpashop.domain.Dto.MemberOrderDto;
import rewind.jpashop.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    public void updateUsername(Long id, String username);
    public List<Member> findByPage(int age, int offset, int limit);
    public long totalCount(int age);
}
