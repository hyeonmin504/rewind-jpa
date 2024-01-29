package rewind.jpashop.domain.item;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    public Book(String autor, String isbn, String name, int price, int stockQuantity) {
        setAutor(autor);
        setIsbn(isbn);
        setPrice(price);
        setName(name);
        setStockQuantity(stockQuantity);
    }

    private String autor;
    private String isbn;

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
