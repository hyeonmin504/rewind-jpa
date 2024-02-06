package rewind.jpashop.repository;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rewind.jpashop.domain.Dto.MemberOrderDto;
import rewind.jpashop.domain.Order;
import rewind.jpashop.domain.OrderStatus;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> findAllbyQuerydsl(OrderSearch orderSearch);

    List<Order> findAllWithMemberDelivery();
    Page<MemberOrderDto> searchPageSimple(MemberOrderDto memberOrderDto, Pageable pageable);
    List<MemberOrderDto> searchPageComplex(MemberOrderDto memberOrderDto, Pageable pageable);
}
