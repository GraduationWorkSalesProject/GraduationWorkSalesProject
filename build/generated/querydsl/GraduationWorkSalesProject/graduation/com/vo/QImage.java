package GraduationWorkSalesProject.graduation.com.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QImage is a Querydsl query type for Image
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QImage extends BeanPath<Image> {

    private static final long serialVersionUID = 1120219121L;

    public static final QImage image = new QImage("image");

    public final StringPath imageHref = createString("imageHref");

    public final StringPath imageName = createString("imageName");

    public final StringPath imageType = createString("imageType");

    public final StringPath imageUuid = createString("imageUuid");

    public QImage(String variable) {
        super(Image.class, forVariable(variable));
    }

    public QImage(Path<? extends Image> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImage(PathMetadata metadata) {
        super(Image.class, metadata);
    }

}

