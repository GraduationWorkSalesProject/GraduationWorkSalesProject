package GraduationWorkSalesProject.graduation.com.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 2795374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final GraduationWorkSalesProject.graduation.com.vo.QAddress address;

    public final EnumPath<MemberCertificationStatus> certificationStatus = createEnum("certificationStatus", MemberCertificationStatus.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final GraduationWorkSalesProject.graduation.com.vo.QImage image;

    public final DateTimePath<java.time.LocalDateTime> joinedDate = createDateTime("joinedDate", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public final StringPath userid = createString("userid");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new GraduationWorkSalesProject.graduation.com.vo.QAddress(forProperty("address")) : null;
        this.image = inits.isInitialized("image") ? new GraduationWorkSalesProject.graduation.com.vo.QImage(forProperty("image")) : null;
    }

}

