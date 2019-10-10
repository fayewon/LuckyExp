package org.lucky.exp.cache;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.lucky.exp.tokenizer.Token;
/**
 * 缓存计算
 * @author FayeWong
 *
 */
@FunctionalInterface
public interface CacheToken extends Cloneable{
	final static int  expire = 3 * 60 * 1000;//默认3分钟刷新一次缓存，暂时不提供配置入口
	final static Map<String,TokenObject> tokensObject = new ConcurrentHashMap<String,TokenObject>();
	final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	default void setOpenCache(boolean openCache) {
		apply(openCache);
	}
	void apply(boolean openCache);
	default  void putTokens(String key, Token[] value) {
		putTokens(key, value, 0);
	}
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
	default  Token[] getToken(String key) {
		TokenObject tokenObject = tokensObject.get(key);
		return tokenObject == null ? null : tokenObject.getValue();
	}


	default  Map<String,Token[]> getTokensMap(){
		final Map<String,Token[]> tokensMap = new HashMap<String,Token[]>();
		tokensObject.forEach((k,v)->{
			tokensMap.put(k, getToken(k));
		});
		return tokensMap;
	}
	default  Object remove(String key) {
		TokenObject entity = tokensObject.remove(key);
		if (entity == null)
			return null;
		Future<?> future = entity.getFuture();
		if (future != null)
			future.cancel(true);
		return entity.getValue();
	}
	default  int size() {
		return tokensObject.size();
	}
	
	/**
	 * 缓存对象
	 *
	 * @author FayeWong
	 * @date 2019年5月8日
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
