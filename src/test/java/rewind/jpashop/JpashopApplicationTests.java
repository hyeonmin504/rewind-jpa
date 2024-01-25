package rewind.jpashop;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import rewind.jpashop.domain.Member;

@SpringBootTest
@Transactional
@Commit
class JpashopApplicationTests {

	@Autowired
	private EntityManager em;

	@Test
	public void findMember() throws Exception {
	    //given
	    Member member = new Member("userA");
		em.persist(member);

		//when
		em.flush();
		em.clear();

		//then
		Assertions.assertThat(member.getUsername()).isEqualTo("userA");
		System.out.println("member = " + member.getUsername());
	}

}
