package rewind.jpashop.domain.item;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Book extends Item{

    private String autor;
    private String isbn;

}
