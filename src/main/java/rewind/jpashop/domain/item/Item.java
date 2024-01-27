package rewind.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import rewind.jpashop.domain.Category;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // == 양방향 연관관계 == //
    public void setCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }
}
