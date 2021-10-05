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
public class Image {

    private String imageName;
    private String imageUuid;
    private String imageType;

    @Override
    public int hashCode() {
        return Objects.hash(getImageName(), getImageType(), getImageUuid());
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
