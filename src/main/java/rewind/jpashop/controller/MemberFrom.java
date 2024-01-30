package rewind.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberFrom {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String username;

    private String city;
    private String street;
    private String zipcode;
}
