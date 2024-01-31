package rewind.jpashop.repository;
import lombok.Getter;
import lombok.Setter;
import rewind.jpashop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
