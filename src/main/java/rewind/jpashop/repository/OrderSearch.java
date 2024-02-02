package rewind.jpashop.repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import rewind.jpashop.domain.OrderStatus;

@Getter @Setter
@NoArgsConstructor
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    //public OrderSearch(){}

    public OrderSearch(String memberName, OrderStatus orderStatus) {
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }
}
