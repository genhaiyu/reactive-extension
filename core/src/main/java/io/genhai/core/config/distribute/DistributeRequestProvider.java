/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */


package io.genhai.core.config.distribute;


import io.genhai.core.config.DispatcherRequestCustomizer;
import io.genhai.core.config.RequestAdapterProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * Request id generation.
 *
 * {@link io.genhai.core.config.RequestAdapterProvider.ProduceValues}
 * {@link SnowFlake}
 *
 * @author yugenhai
 */
@ConditionalOnClass(SnowFlake.class)
public class DistributeRequestProvider implements DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> {

    private final DistributeRequestProperties drp;

    public DistributeRequestProvider(DistributeRequestProperties distributeRequestProperties) {
        this.drp = distributeRequestProperties;
    }

    @Override
    public void customize(final RequestAdapterProvider.ProduceValues produceValues) {

        if (produceValues == null) {
            throw new IllegalArgumentException("ProduceValues not initialized");
        }
        try {
            if ((drp.getDataCenterId() > 0x00 && drp.getDataCenterId() <= 31) && (drp.getMachineId() > 0x00 && drp.getMachineId() <= 31)) {
                produceValues.setMessageId(
                        String.valueOf(new SnowFlake(drp.getDataCenterId(), drp.getMachineId()).nextId())
                );

            } else {
                produceValues.setMessageId(
                        String.valueOf(new SnowFlake(0x00, 0x00).nextId())
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("The Http idProvider Support Invalid");
        }
    }
}
