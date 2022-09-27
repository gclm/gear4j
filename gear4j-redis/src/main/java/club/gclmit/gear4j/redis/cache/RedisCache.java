package club.gclmit.gear4j.redis.cache;

import club.gclmit.gear4j.core.utils.ArrayUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 通用redis缓存实现
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/6 11:09
 * @since jdk11
 */
public class RedisCache {

	private final RedisTemplate<String, Object> redisTemplate;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public RedisCache(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	public void setExpire(String key, Long timeout, TimeUnit unit) {
		try {
			if (timeout > 0) {
				redisTemplate.expire(key, timeout, unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}


	public Boolean hasKey(String key) {
		try {
			return Boolean.TRUE.equals(redisTemplate.hasKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public void remove(String... keys) {
		if (ArrayUtils.isNotEmpty(keys)) {
			redisTemplate.delete(List.of(keys));
		}
	}


	public Object getValue(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}


	public Boolean cacheValue(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean cacheValue(String key, Object value, Long time, TimeUnit unit) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, unit);
			} else {
				cacheValue(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateValueCache(String key, Object value) {
		try {
			cacheValue(key, value, getExpire(key), TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Long incrementValue(String key, Long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}


	public Long decrValue(String key, Long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}


	public Object getHash(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}


	public Map<Object, Object> getHash(String key) {
		return redisTemplate.opsForHash().entries(key);
	}


	public Boolean cacheHash(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean cacheHash(String key, Map<String, Object> map, Long time, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				setExpire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean cacheHash(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean cacheHash(String key, String item, Object value, Long time, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				setExpire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public void removeHash(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}


	public Boolean hasHashKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}


	public double incrHash(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}


	public double decrHash(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}


	public Set<Object> getSet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Boolean hasSetKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Long cacheSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public Long cacheSet(String key, Long time, TimeUnit unit, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0) {
				setExpire(key, time, unit);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public Long getSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public Long removeSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public Long getListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public List<Object> getList(String key, Long start, Long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Object getList(String key, Long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Boolean cacheList(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean cacheList(String key, Object value, Long time, TimeUnit unit) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				setExpire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	public Boolean cacheList(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	public Boolean cacheList(String key, List<Object> value, Long time, TimeUnit unit) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				setExpire(key, time, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Boolean updateList(String key, Long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Long removeList(String key, Long count, Object value) {
		try {
			return redisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}


	public void removeList(String key, Long start, Long end) {
		try {
			redisTemplate.opsForList().trim(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

