package rewind.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
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
