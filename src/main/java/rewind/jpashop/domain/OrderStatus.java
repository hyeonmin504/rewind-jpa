package rewind.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
public enum OrderStatus {
    ORDER,CANCEL
}
