package rewind.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rewind.jpashop.domain.Category;
import rewind.jpashop.exception.NotEnoughStockException;

@Entity
@Getter
@Setter
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

    // == 비즈니스 로직 == //
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        if(this.stockQuantity - quantity >= 0) {
            this.stockQuantity -= quantity;
        } else {
            throw new NotEnoughStockException("need more stock");
        }
    }
}
