package GraduationWorkSalesProject.graduation.com.entity.certify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertificate is a Querydsl query type for Certificate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCertificate extends EntityPathBase<Certificate> {

    private static final long serialVersionUID = -1748257343L;

    public static final QCertificate certificate = new QCertificate("certificate");

    public final DateTimePath<java.time.LocalDateTime> expirationDateTime = createDateTime("expirationDateTime", java.time.LocalDateTime.class);

    public final StringPath token = createString("token");

    public QCertificate(String variable) {
        super(Certificate.class, forVariable(variable));
    }

    public QCertificate(Path<? extends Certificate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertificate(PathMetadata metadata) {
        super(Certificate.class, metadata);
    }

}

