package rewind.jpashop.domain.Dto;

import lombok.Data;
import rewind.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class MemberOrderDto {
    private String username;
    private int age;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public MemberOrderDto() {
    }

    public MemberOrderDto(String username, int age, LocalDateTime orderDate, OrderStatus status) {
        this.username = username;
        this.age = age;
        this.orderDate = orderDate;
        this.status = status;
    }
}
