package GraduationWorkSalesProject.graduation.com.vo;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {

    private String imageName;
    private String imageUuid;
    private String imageType;
    private String imageHref;

    @Override
    public int hashCode() {
        return Objects.hash(getImageName(), getImageType(), getImageUuid(), getImageHref());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;

        Image image = (Image) obj;
        return Objects.equals(getImageUuid(), image.getImageUuid());
    }
}
