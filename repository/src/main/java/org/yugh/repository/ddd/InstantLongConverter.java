package org.yugh.repository.ddd;

import javax.persistence.AttributeConverter;
import java.time.Instant;

/**
 * //类型自动转换
 *
 * @author: 余根海
 * @creation: 2019-04-05 22:10
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public class InstantLongConverter implements AttributeConverter<Instant, Long> {


    @Override
    public Long convertToDatabaseColumn(Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    @Override
    public Instant convertToEntityAttribute(Long aLong) {
        return aLong == null ? null : Instant.ofEpochMilli(aLong);
    }
}
