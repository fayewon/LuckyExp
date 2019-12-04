package org.lucky.exp.cache;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.lucky.exp.Configuration;
import org.lucky.exp.tokenizer.Token;
/**
 * 缓存计算
 * @author FayeWong
 *
 */
@FunctionalInterface
public interface CacheToken extends Cloneable{
	final static Map<String,TokenObject> tokensObject = new ConcurrentHashMap<String,TokenObject>();
	final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();	
	void apply(CacheToken cacheToken);
	/**
	 * 
	 * @return 是否开启缓存
	 */
	default  boolean openCache() {
		return Configuration.openCache;
	}
	/**
	 * 
	 * @return 缓存时间
	 */
	default  int expire() {
		return Configuration.expire;
	}
	/**
	 * 
	 * @param key 公式字符串
	 * @param value  逆波兰表达式对象
	 */
	default  void putTokens(String key, Token[] value) {
		putTokens(key, value, 0);
	}
	/**
	 * 
	 * @param key  公式字符串
	 * @param value  逆波兰表达式对象
	 * @param expire  缓存时间
	 */
	default  void putTokens(String key,Token[] value,long expire) {
		remove(key);
		if (expire > 0) {
			Future<?> future = executor.schedule(new Runnable() {
				public void run() {
					synchronized (CacheToken.class) {
						tokensObject.remove(key);
					}
				}
			}, expire, TimeUnit.MILLISECONDS);
			tokensObject.put(key, new TokenObject(value, future));
		} else {
			tokensObject.put(key, new TokenObject(value, null));
		}
	};
	/**
	 * 
	 * @param key 公式字符串
	 * @return 逆波兰表达式对象
	 */
	default  Token[] getToken(String key) {
		TokenObject tokenObject = tokensObject.get(key);
		return tokenObject == null ? null : tokenObject.getValue();
	}
	/**
	 * 
	 * @return 所有缓存的逆波兰对象
	 */
	default  Map<String,Token[]> getTokensMap(){
		final Map<String,Token[]> tokensMap = new HashMap<String,Token[]>();
		tokensObject.forEach((k,v)->{
			tokensMap.put(k, getToken(k));
		});
		return tokensMap;
	}
	/**
	 * 
	 * @param key 公式字符串
	 * @return 被删除对象
	 */
	default  Object remove(String key) {
		TokenObject entity = tokensObject.remove(key);
		if (entity == null)
			return null;
		Future<?> future = entity.getFuture();
		if (future != null)
			future.cancel(true);
		return entity.getValue();
	}
	/**
	 * 
	 * @return 缓存数量
	 */
	default  int size() {
		return tokensObject.size();
	}	
	/**
	 * 缓存对象
	 *
	 * @author FayeWong
	 * 
	 */
	static class TokenObject {
		private Token[] tokens;
		private Future<?> future;

		public TokenObject(Token[] tokens, Future<?> future) {
			this.tokens = tokens;
			this.future = future;
		}

		public Token[] getValue() {
			return tokens;
		}

		public Future<?> getFuture() {
			return future;
		}
	}
}
