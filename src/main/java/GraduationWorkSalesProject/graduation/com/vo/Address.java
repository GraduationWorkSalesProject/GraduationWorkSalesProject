package GraduationWorkSalesProject.graduation.com.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String address;
    private String detailAddress;
    private String postcode;

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getDetailAddress(), getPostcode());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;

        Address address = (Address) obj;
        return Objects.equals(getAddress(), address.getAddress())
                && Objects.equals(getDetailAddress(), address.getDetailAddress())
                && Objects.equals(getPostcode(), address.getPostcode());
    }
}
