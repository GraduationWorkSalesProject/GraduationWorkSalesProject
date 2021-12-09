package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.exception.ProductImageNotExistException;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ImageLinkResponse {

    private String imageName;
    private String imageUuid;
    private String imageType;
    private String imageHref;

    public ImageLinkResponse(Optional<Image> image){

        String shareLinkTop = "file/d/";
        String shareLinkEnd = "/view?usp=sharing";
        String returnLink = "uc?export=view&id=";

        //replace sharelinktop & return link and erase sharedlinkend by split
        String[] result = image.orElseThrow(ProductImageNotExistException::new).getImageHref().replace(shareLinkTop,returnLink).split(shareLinkEnd);

        this.imageName = image.get().getImageName();
        this.imageUuid = image.get().getImageUuid();
        this.imageType = image.get().getImageType();
        this.imageHref = result[0];

    }


}
