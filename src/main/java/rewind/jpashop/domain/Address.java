package rewind.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {}
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
