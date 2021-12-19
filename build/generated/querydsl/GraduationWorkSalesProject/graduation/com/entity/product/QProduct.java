package GraduationWorkSalesProject.graduation.com.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1188494928L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> deliveryPrice = createNumber("deliveryPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath information = createString("information");

    public final GraduationWorkSalesProject.graduation.com.entity.member.QMember member;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<ProductHashtag, QProductHashtag> productHashtags = this.<ProductHashtag, QProductHashtag>createList("productHashtags", ProductHashtag.class, QProductHashtag.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final DateTimePath<java.sql.Timestamp> registerDate = createDateTime("registerDate", java.sql.Timestamp.class);

    public final GraduationWorkSalesProject.graduation.com.vo.QImage representationImage;

    public final NumberPath<Integer> term = createNumber("term", Integer.class);

    public final DateTimePath<java.sql.Timestamp> updateDate = createDateTime("updateDate", java.sql.Timestamp.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new GraduationWorkSalesProject.graduation.com.entity.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.representationImage = inits.isInitialized("representationImage") ? new GraduationWorkSalesProject.graduation.com.vo.QImage(forProperty("representationImage")) : null;
    }

}

