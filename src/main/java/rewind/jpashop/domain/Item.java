package rewind.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToOne(fetch = FetchType.LAZY)

    @OneToMany(mappedBy = "items")
    private Category categories;
}
