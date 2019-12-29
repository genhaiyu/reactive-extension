package org.yugh.coral.core.config.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.yugh.coral.core.config.cache.CoreRedisConfig;
import org.yugh.coral.core.config.handler.AbstractCacheHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yugenhai
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class CoreProcessCacheAdapter extends AbstractCacheHandler implements SmartInitializingSingleton {

    private ValueOperations valueOps;
    private HashOperations hashOps;
    private ListOperations listOps;
    private SetOperations setOps;
    private ZSetOperations zSetOps;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CoreRedisConfig coreRedisConfig;


    /**
     * getKeyPrefix
     *
     * @param key
     * @return
     */
    public String getKeyPrefix(String key) {
        return coreRedisConfig.getKeyPrefix() + ":" + key;
    }


    @Override
    public Object getObjectByCache(String key) {
        try {
            long begin = System.currentTimeMillis();
            log.info("Guava Cache :{} ...", key);
            Object obj = cache.get(key);
            long end = System.currentTimeMillis();
            log.info("Guava Cache Take Time : {}", (end - begin));
            return obj;
        } catch (Exception e) {
            log.error("Guava Cache Exception:{}", e);
        }
        return null;
    }

    @Override
    public Object loadCache(String id) {
        return this.get(id);
    }

    @Override
    public void removeCacheKey(String key) {
        this.cache.invalidate(key);
    }


    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(getKeyPrefix(key), time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire error", e);
            return false;
        }
    }


    @Override
    public long getExpire(String key) {
        try {
            return redisTemplate.getExpire(getKeyPrefix(key), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("getExpire error", e);
        }
        return 0;
    }


    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(getKeyPrefix(key));
        } catch (Exception e) {
            log.error("hasKey error", e);
            return false;
        }
    }


    @Override
    public void delete(String key) {
        redisTemplate.delete(getKeyPrefix(key));
    }


    @Override
    public Object get(String key) {
        return key == null ? null : valueOps.get(getKeyPrefix(key));
    }

    // ============================String=============================


    @Override
    public boolean set(String key, Object value) {
        try {
            valueOps.set(getKeyPrefix(key), value, coreRedisConfig.getExpire(), TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("set common cache error", e);
            return false;
        }
    }


    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                valueOps.set(getKeyPrefix(key), value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set common cache with expire error", e);
            return false;
        }
    }

    @Override
    public boolean setWithHour(String key, Object value, long hour) {
        try {
            if (hour > 0) {
                valueOps.set(getKeyPrefix(key), value, hour, TimeUnit.HOURS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set common cache with hour expire error", e);
            return false;
        }
    }


    @Override
    public boolean setWithDay(String key, Object value, long day) {
        try {
            if (day > 0) {
                valueOps.set(getKeyPrefix(key), value, day, TimeUnit.DAYS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set common cache with day expire error", e);
            return false;
        }
    }


    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return valueOps.increment(getKeyPrefix(key), delta);
    }

    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return valueOps.increment(getKeyPrefix(key), -delta);
    }


    // ================================Map=================================

    @Override
    public Object hget(String key, String item) {
        return hashOps.get(getKeyPrefix(key), item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return hashOps.entries(getKeyPrefix(key));
    }


    @Override
    public Set<Object> hkeys(String key) {
        return hashOps.keys(getKeyPrefix(key));
    }


    @Override
    public List<String> hvalues(String key) {
        return (List) hashOps.values(getKeyPrefix(key));
    }


    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            hashOps.putAll(getKeyPrefix(key), map);
            return true;
        } catch (Exception e) {
            log.error("hash set error", e);
            return false;
        }
    }


    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            hashOps.putAll(getKeyPrefix(key), map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hash set with expire error", e);
            return false;
        }
    }


    @Override
    public boolean hset(String key, String item, Object value) {
        try {
            hashOps.put(getKeyPrefix(key), item, value);
            return true;
        } catch (Exception e) {
            log.error("hash set or create error", e);
            return false;
        }
    }


    @Override
    public boolean hset(String key, String item, Object value, long time) {
        try {
            hashOps.put(getKeyPrefix(key), item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hash set or create with expire error", e);
            return false;
        }
    }


    @Override
    public void hdelete(String key, Object... item) {
        hashOps.delete(getKeyPrefix(key), item);
    }


    @Override
    public boolean hHasKey(String key, String item) {
        return hashOps.hasKey(getKeyPrefix(key), item);
    }


    @Override
    public double hincr(String key, String item, double by) {
        return hashOps.increment(getKeyPrefix(key), item, by);
    }


    @Override
    public double hdecr(String key, String item, double by) {
        return hashOps.increment(getKeyPrefix(key), item, -by);
    }


    @Override
    public Set<Object> sGet(String key) {
        try {
            return setOps.members(getKeyPrefix(key));
        } catch (Exception e) {
            log.error("set set error", e);
            return null;
        }
    }

    // ============================set=============================


    @Override
    public boolean sHasKey(String key, Object value) {
        try {
            return setOps.isMember(getKeyPrefix(key), value);
        } catch (Exception e) {
            log.error("set key exits error", e);
            return false;
        }
    }


    @Override
    public long sSet(String key, Object... values) {
        try {
            return setOps.add(getKeyPrefix(key), values);
        } catch (Exception e) {
            log.error("set set values error", e);
            return 0;
        }
    }


    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = setOps.add(getKeyPrefix(key), values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("set set values with expire error", e);
            return 0;
        }
    }


    @Override
    public long sGetSetSize(String key) {
        try {
            return setOps.size(getKeyPrefix(key));
        } catch (Exception e) {
            log.error("set get size error", e);
            return 0;
        }
    }


    @Override
    public long setRemove(String key, Object... values) {
        try {
            Long count = setOps.remove(getKeyPrefix(key), values);
            return count;
        } catch (Exception e) {
            log.error("set remove error", e);
            return 0;
        }
    }


    // ===============================list=================================


    @Override
    public long lGetListSize(String key) {
        try {
            return listOps.size(getKeyPrefix(key));
        } catch (Exception e) {
            log.error("list get size error", e);
            return 0;
        }
    }

    @Override
    public Object lGetIndex(String key, long index) {
        try {
            return listOps.index(getKeyPrefix(key), index);
        } catch (Exception e) {
            log.error("list get index error", e);
            return null;
        }
    }


    @Override
    public boolean lSet(String key, Object value) {
        try {
            listOps.rightPush(getKeyPrefix(key), value);
            return true;
        } catch (Exception e) {
            log.error("list set error", e);
            return false;
        }
    }


    @Override
    public boolean lSet(String key, Object value, long time) {
        try {
            listOps.rightPush(getKeyPrefix(key), value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("list set with expire error", e);
            return false;
        }
    }


    @Override
    public boolean lSet(String key, List<Object> value) {
        try {
            listOps.rightPushAll(getKeyPrefix(key), value);
            return true;
        } catch (Exception e) {
            log.error("list set values", e);
            return false;
        }
    }


    @Override
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            listOps.rightPushAll(getKeyPrefix(key), value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("list set values with expire error", e);
            return false;
        }

    }


    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            listOps.set(getKeyPrefix(key), index, value);
            return true;
        } catch (Exception e) {
            log.error("list update error", e);
            return false;
        }
    }


    @Override
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = listOps.remove(getKeyPrefix(key), count, value);
            return remove;
        } catch (Exception e) {
            log.error("list remove error", e);
            return 0;
        }
    }


    @Override
    public List<Object> lGet(String key, long start, long end) {
        try {
            return listOps.range(getKeyPrefix(key), start, end);
        } catch (Exception e) {
            log.error("list get error", e);
            return null;
        }
    }


    @Override
    public void afterSingletonsInstantiated() {
        valueOps = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
        listOps = redisTemplate.opsForList();
        setOps = redisTemplate.opsForSet();
        zSetOps = redisTemplate.opsForZSet();
    }
}
