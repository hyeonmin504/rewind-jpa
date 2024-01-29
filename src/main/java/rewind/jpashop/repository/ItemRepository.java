package rewind.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rewind.jpashop.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
