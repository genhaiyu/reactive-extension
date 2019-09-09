package org.yugh.cqrs.goods.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGoodsEntity is a Querydsl query type for GoodsEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGoodsEntity extends EntityPathBase<GoodsEntity> {

    private static final long serialVersionUID = 759460543L;

    public static final QGoodsEntity goodsEntity = new QGoodsEntity("goodsEntity");

    public final org.yugh.cqrs.jpa.ddd.QAbstractEntity _super = new org.yugh.cqrs.jpa.ddd.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final DateTimePath<java.util.Date> tokenTime = createDateTime("tokenTime", java.util.Date.class);

    //inherited
    public final DateTimePath<java.time.Instant> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QGoodsEntity(String variable) {
        super(GoodsEntity.class, forVariable(variable));
    }

    public QGoodsEntity(Path<? extends GoodsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoodsEntity(PathMetadata metadata) {
        super(GoodsEntity.class, metadata);
    }

}

