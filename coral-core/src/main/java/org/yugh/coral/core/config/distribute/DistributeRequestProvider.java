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


package org.yugh.coral.core.config.distribute;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.yugh.coral.core.config.DispatcherRequestCustomizer;
import org.yugh.coral.core.config.RequestAdapterProvider;

import java.util.UUID;

/**
 * Request ID generation.
 *
 * Type is {@link RequestAdapterProvider.ProduceValues}
 *
 * @author yugenhai
 */
@ConditionalOnClass(SnowFlake.class)
public class DistributeRequestProvider implements DispatcherRequestCustomizer {

    private final Object LOCK = new Object();
    private final DistributeRequestProperties distributeRequestProperties;

    private static final Logger LOG = LoggerFactory.getLogger(DistributeRequestProvider.class);

    public DistributeRequestProvider(DistributeRequestProperties distributeRequestProperties) {
        this.distributeRequestProperties = distributeRequestProperties;
    }


    @Override
    public void customize(Object object) {
        if (object instanceof RequestAdapterProvider.ProduceValues) {
            RequestAdapterProvider.ProduceValues produceValues = (RequestAdapterProvider.ProduceValues) object;
            try{
                if((distributeRequestProperties.getDataCenterId() > 0x00 && distributeRequestProperties.getDataCenterId() <= 31)
                        && (distributeRequestProperties.getMachineId() > 0x00 && distributeRequestProperties.getMachineId() <= 31)){
                    produceValues.setMessageId(
                            String.valueOf(new SnowFlake(distributeRequestProperties.getDataCenterId(),
                                    distributeRequestProperties.getMachineId()).nextId())
                    );

                }else {
                    produceValues.setMessageId(
                            String.valueOf(new SnowFlake(0x00, 0x00).nextId())
                    );
                }
            }catch (Exception e){

                synchronized (LOCK){
                    produceValues.setMessageId(UUID.randomUUID().toString().replace("-", ""));
                }
                LOG.warn("DistributeRequestProvider's SnowFlake happen error ", e);
            }
        }
        // else throw new IllegalArgumentException("");
    }
}
