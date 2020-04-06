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

package org.yugh.coral.core.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.redis.core.*;
import org.springframework.util.Assert;
import org.yugh.coral.core.config.embedded.WebServiceContainerMatcher;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * Coral RedisProcessSupport
 * <p>
 * {@link RedisOperationProperties}.
 *
 * @author yugenhai
 */
@SuppressWarnings("unchecked")
public class RedisProcessSupport implements SmartInitializingSingleton {

    private static final Logger LOG = LoggerFactory.getLogger(RedisProcessSupport.class);

    private final RedisOperationProperties redisOperationProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private volatile String assembleCacheKey;
    private ValueOperations valueOps;
    private HashOperations hashOps;
    private ListOperations listOps;
    private SetOperations setOps;
    private ZSetOperations zSetOps;

    public RedisProcessSupport(final RedisOperationProperties redisOperationProperties, final RedisTemplate<String, Object> redisTemplate) {
        this.redisOperationProperties = redisOperationProperties;
        this.redisTemplate = redisTemplate;
    }

    private void keyPrefix(RedisOperationProperties redisOperationProperties, final String key) {
        StringJoiner joiner = new StringJoiner(":");
        assembleCacheKey = joiner.add(redisOperationProperties.getCoralCache().getKeyPrefix())
                .add(key).toString();
    }

    private String getKeyPrefix(String key) {
        keyPrefix(redisOperationProperties, key);
        return assembleCacheKey;
    }


    // ============================Action=============================

    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(getKeyPrefix(key), time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOG.error("expire error", e);
            return false;
        }
    }

    public long getExpire(String key) {
        try {
            return redisTemplate.getExpire(getKeyPrefix(key), TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error("getExpire error", e);
        }
        return 0;
    }


    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(getKeyPrefix(key));
        } catch (Exception e) {
            LOG.error("hasKey error", e);
            return false;
        }
    }


    public void delete(String key) {
        redisTemplate.delete(getKeyPrefix(key));
    }


    public Object get(String key) {
        return key == null ? null : valueOps.get(getKeyPrefix(key));
    }


    // ============================String=============================

    public boolean set(String key, Object value) {
        try {
            valueOps.set(getKeyPrefix(key), value, redisOperationProperties.getCoralCache().getExpire(), TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOG.error("set common cache error", e);
            return false;
        }
    }


    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                valueOps.set(getKeyPrefix(key), value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOG.error("set common cache with expire error", e);
            return false;
        }
    }


    public boolean setWithHour(String key, Object value, long hour) {
        try {
            if (hour > 0) {
                valueOps.set(getKeyPrefix(key), value, hour, TimeUnit.HOURS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOG.error("set common cache with hour expire error", e);
            return false;
        }
    }


    public boolean setWithDay(String key, Object value, long day) {
        try {
            if (day > 0) {
                valueOps.set(getKeyPrefix(key), value, day, TimeUnit.DAYS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOG.error("set common cache with day expire error", e);
            return false;
        }
    }


    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta must larger than 0");
        }
        return valueOps.increment(getKeyPrefix(key), delta);
    }


    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("decrement delta must larger than 0");
        }
        return valueOps.increment(getKeyPrefix(key), -delta);
    }


    // ================================Map=================================

    public Object hget(String key, String item) {
        return hashOps.get(getKeyPrefix(key), item);
    }


    public Map<Object, Object> hmget(String key) {
        return hashOps.entries(getKeyPrefix(key));
    }


    public Set<Object> hkeys(String key) {
        return hashOps.keys(getKeyPrefix(key));
    }


    public List<String> hvalues(String key) {
        return (List) hashOps.values(getKeyPrefix(key));
    }


    public boolean hmset(String key, Map<String, Object> map) {
        try {
            hashOps.putAll(getKeyPrefix(key), map);
            return true;
        } catch (Exception e) {
            LOG.error("hash set error", e);
            return false;
        }
    }


    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            hashOps.putAll(getKeyPrefix(key), map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOG.error("hash set with expire error", e);
            return false;
        }
    }


    public boolean hset(String key, String item, Object value) {
        try {
            hashOps.put(getKeyPrefix(key), item, value);
            return true;
        } catch (Exception e) {
            LOG.error("hash set or create error", e);
            return false;
        }
    }


    public boolean hset(String key, String item, Object value, long time) {
        try {
            hashOps.put(getKeyPrefix(key), item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOG.error("hash set or create with expire error", e);
            return false;
        }
    }


    public void hdelete(String key, Object... item) {
        hashOps.delete(getKeyPrefix(key), item);
    }


    public boolean hHasKey(String key, String item) {
        return hashOps.hasKey(getKeyPrefix(key), item);
    }


    public double hincr(String key, String item, double by) {
        return hashOps.increment(getKeyPrefix(key), item, by);
    }


    public double hdecr(String key, String item, double by) {
        return hashOps.increment(getKeyPrefix(key), item, -by);
    }


    public Set<Object> sGet(String key) {
        try {
            return setOps.members(getKeyPrefix(key));
        } catch (Exception e) {
            LOG.error("set set error", e);
            return null;
        }
    }

    // ============================set=============================


    public boolean sHasKey(String key, Object value) {
        try {
            return setOps.isMember(getKeyPrefix(key), value);
        } catch (Exception e) {
            LOG.error("set key exits error", e);
            return false;
        }
    }


    public long sSet(String key, Object... values) {
        try {
            return setOps.add(getKeyPrefix(key), values);
        } catch (Exception e) {
            LOG.error("set set values error", e);
            return 0;
        }
    }


    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = setOps.add(getKeyPrefix(key), values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            LOG.error("set set values with expire error", e);
            return 0;
        }
    }


    public long sGetSetSize(String key) {
        try {
            return setOps.size(getKeyPrefix(key));
        } catch (Exception e) {
            LOG.error("set get size error", e);
            return 0;
        }
    }


    public long setRemove(String key, Object... values) {
        try {
            Long count = setOps.remove(getKeyPrefix(key), values);
            return count;
        } catch (Exception e) {
            LOG.error("set remove error", e);
            return 0;
        }
    }


    // ===============================list=================================

    public long lGetListSize(String key) {
        try {
            return listOps.size(getKeyPrefix(key));
        } catch (Exception e) {
            LOG.error("list get size error", e);
            return 0;
        }
    }


    public Object lGetIndex(String key, long index) {
        try {
            return listOps.index(getKeyPrefix(key), index);
        } catch (Exception e) {
            LOG.error("list get index error", e);
            return null;
        }
    }


    public boolean lSet(String key, Object value) {
        try {
            listOps.rightPush(getKeyPrefix(key), value);
            return true;
        } catch (Exception e) {
            LOG.error("list set error", e);
            return false;
        }
    }


    public boolean lSet(String key, Object value, long time) {
        try {
            listOps.rightPush(getKeyPrefix(key), value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOG.error("list set with expire error", e);
            return false;
        }
    }


    public boolean lSet(String key, List<Object> value) {
        try {
            listOps.rightPushAll(getKeyPrefix(key), value);
            return true;
        } catch (Exception e) {
            LOG.error("list set values", e);
            return false;
        }
    }


    public boolean lSet(String key, List<Object> value, long time) {
        try {
            listOps.rightPushAll(getKeyPrefix(key), value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOG.error("list set values with expire error", e);
            return false;
        }

    }


    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            listOps.set(getKeyPrefix(key), index, value);
            return true;
        } catch (Exception e) {
            LOG.error("list update error", e);
            return false;
        }
    }


    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = listOps.remove(getKeyPrefix(key), count, value);
            return remove;
        } catch (Exception e) {
            LOG.error("list remove error", e);
            return 0;
        }
    }


    public List<Object> lGet(String key, long start, long end) {
        try {
            return listOps.range(getKeyPrefix(key), start, end);
        } catch (Exception e) {
            LOG.error("list get error", e);
            return null;
        }
    }


    // ===============================get set=================================

    public RedisOperationProperties getRedisOperationProperties() {
        return redisOperationProperties;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public String getAssembleCacheKey() {
        return assembleCacheKey;
    }

    public void setAssembleCacheKey(String assembleCacheKey) {
        this.assembleCacheKey = assembleCacheKey;
    }

    public ValueOperations getValueOps() {
        return valueOps;
    }

    public void setValueOps(ValueOperations valueOps) {
        this.valueOps = valueOps;
    }

    public HashOperations getHashOps() {
        return hashOps;
    }

    public void setHashOps(HashOperations hashOps) {
        this.hashOps = hashOps;
    }

    public ListOperations getListOps() {
        return listOps;
    }

    public void setListOps(ListOperations listOps) {
        this.listOps = listOps;
    }

    public SetOperations getSetOps() {
        return setOps;
    }

    public void setSetOps(SetOperations setOps) {
        this.setOps = setOps;
    }

    public ZSetOperations getzSetOps() {
        return zSetOps;
    }

    public void setzSetOps(ZSetOperations zSetOps) {
        this.zSetOps = zSetOps;
    }


    public void afterSingletonsInstantiated() {
        Assert.notNull(redisTemplate, () -> "redisTemplate '" + redisTemplate + "' must not null");
        valueOps = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
        listOps = redisTemplate.opsForList();
        setOps = redisTemplate.opsForSet();
        zSetOps = redisTemplate.opsForZSet();
        WebServiceContainerMatcher.embeddedContainerMatcher();
    }
}
