package rewind.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import rewind.jpashop.domain.item.Item;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }
}
