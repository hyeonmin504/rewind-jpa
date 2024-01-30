package rewind.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rewind.jpashop.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String username) {
        this.username = username;
    }

    public Member(Long id, String username, Address address, List<Order> orders) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.orders = orders;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
