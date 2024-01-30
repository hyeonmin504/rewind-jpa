package rewind.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rewind.jpashop.domain.Address;
import rewind.jpashop.domain.Member;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderItem;
import rewind.jpashop.domain.item.Book;
import rewind.jpashop.domain.item.Item;
import rewind.jpashop.repository.ItemRepository;
import rewind.jpashop.repository.MemberRepository;
import rewind.jpashop.service.ItemService;
import rewind.jpashop.service.MemberService;
import rewind.jpashop.service.OrderService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/api/v0/{id}")
    public Member saveMemberV0(@RequestBody @Valid Member member,
                               @PathVariable("id") Long id) {
        member = new Member("kim");
        member.setAddress(new Address("city", "street", "zipcode"));
        memberService.join(member);
        Item book = new Book("autor", "isbn", "name", 10000, 5);
        itemService.saveItem(book);

        orderService.order(member.getId(),book.getId(),3 );

        return member;
    }
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member(request.getUsername());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id,request.getUsername());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(id,findMember.getUsername());
    }


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String username;
    }

    @Data
    static class UpdateMemberRequest {
        private String username;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String username;
    }
}
