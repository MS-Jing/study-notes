package com.token.ssp.adstore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author luojing
 * @version 1.0
 * @date 2021/7/7 10:13
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * 给指定key设置失效时间
     *
     * @param key 键
     * @param time 默认为秒
     * @return 状态
     */
    public boolean expire(String key, long time) {
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 给指定key设置失效时间
     *
     * @param key 键
     * @param time 时间
     * @param unit 时间单位
     * @return 状态
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取key的过期时间
     *
     * @param key 键
     * @param unit 时间单位
     * @return 时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 状态
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除一个或多个key
     *
     * @param keys 键
     */
    public void delete(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    // =============================String============================

    /**
     * 放入一个值
     *
     * @param key 键
     * @param value 值
     * @return 状态
     */
    public boolean set(String key, Object value) {
        try {
            //key为空会报 non null key required
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加key时 设置过期时间
     *
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param unit  时间单位
     * @return 状态
     */
    public boolean set(String key, Object value, long time, TimeUnit unit) {
        try {
            // 过期时间小于 0会报   ERR invalid expire time in setex
            redisTemplate.opsForValue().set(key, value, time, unit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取一个key的值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 递增或递减
     *
     * @param key 键
     * @param date 大于0增加  小于0减少
     * @return 操作后的值
     */
    public Long incrOrDecr(String key, long date) {
        return redisTemplate.opsForValue().increment(key, date);
    }

    // =============================Map============================

    /**
     * 为一个hash放入一个值
     *
     * @param key 键
     * @param hk hk
     * @param hv hv
     * @return 状态
     */
    public boolean putHash(String key, String hk, Object hv) {
        try {
            redisTemplate.opsForHash().put(key, hk, hv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量put Hash
     *
     * @param key 键
     * @param map 值
     * @return 状态
     */
    public boolean putMultiHash(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hash的一个值
     *
     * @param key 键
     * @param hk hk
     * @return 值
     */
    public Object getHash(String key, String hk) {
        return redisTemplate.opsForHash().get(key, hk);
    }

    /**
     * 批量获取key对应的hash的所有键值
     *
     * @param key 键
     * @return 值
     */
    public Map<Object, Object> getMultiHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 判断是否存在hk
     *
     * @param key 键
     * @param hk hk
     * @return 状态
     */
    public boolean hasHashHk(String key, Object hk) {
        return redisTemplate.opsForHash().hasKey(key, hk);
    }

    /**
     * 删除hash的hk
     *
     * @param key 键
     * @param hks hks
     */
    public void delHashHks(String key, Object... hks) {
        redisTemplate.opsForHash().delete(key, hks);
    }

    /**
     * 递增或递减
     *
     * @param key 键
     * @param hk hk
     * @param date 大于0递增，小于0递减
     * @return 操作完成后当前的量
     */
    public long hashIncrOrDecr(String key, String hk, long date) {
        return redisTemplate.opsForHash().increment(key, hk, date);
    }

    // ============================set=============================

    /**
     * 添加set值
     *
     * @param key 键
     * @param values 值
     * @return 添加成功的个数
     */
    public long addSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> getAllSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断value是否在key的set中
     *
     * @param key 键
     * @param value 值
     * @return 判断状态
     */
    public boolean hasKeySet(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除set的值
     *
     * @param key 键
     * @param values 值
     * @return 删除的个数
     */
    public long removeSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set的大小
     *
     * @param key 键
     * @return 大小
     */
    public long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * 获取list列表的值
     *
     * @param key 键
     * @param start 开始下标
     * @param end   结束下标
     * @return 值
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key 键
     * @param index 大于0 从第一个开始 小于0 从最后一个开始
     * @return 值
     */
    public Object getListIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将值从list右边放入
     *
     * @param key 键
     * @param value 值的列表
     * @return 放入状态
     */
    public boolean listRightPush(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量放入
     *
     * @param key 键
     * @param value 值的列表
     * @return 放入状态
     */
    public boolean listRightPushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return 更新状态
     */
    public boolean updateListByIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除count个值为value 的元素
     * @param key 键
     * @param count 移除个数
     * @param value 值
     * @return 移除个数
     */
    public long listRemoveCount(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
