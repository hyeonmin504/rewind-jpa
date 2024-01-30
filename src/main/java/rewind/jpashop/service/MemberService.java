package rewind.jpashop.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;
import rewind.jpashop.repository.MemberRepository;
import rewind.jpashop.repository.MemberRepositoryCustom;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member){
        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return memberRepository.findById(id).get();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> byusername = memberRepository.findByusername(member.getUsername());
        if (!byusername.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    @Transactional
    public void update(Long id, String username) {
        List<Member> byusername = memberRepository.findByusername(username);
        if(byusername.isEmpty()){
            memberRepository.updateUsername(id, username);}
//        } else {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        }


    }
}
