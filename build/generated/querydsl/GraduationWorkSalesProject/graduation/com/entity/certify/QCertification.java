package GraduationWorkSalesProject.graduation.com.entity.certify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertification is a Querydsl query type for Certification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCertification extends EntityPathBase<Certification> {

    private static final long serialVersionUID = -743086492L;

    public static final QCertification certification = new QCertification("certification");

    public final StringPath certificationCode = createString("certificationCode");

    public final DateTimePath<java.time.LocalDateTime> expirationDateTime = createDateTime("expirationDateTime", java.time.LocalDateTime.class);

    public final StringPath token = createString("token");

    public QCertification(String variable) {
        super(Certification.class, forVariable(variable));
    }

    public QCertification(Path<? extends Certification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertification(PathMetadata metadata) {
        super(Certification.class, metadata);
    }

}

