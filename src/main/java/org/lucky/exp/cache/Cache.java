package org.lucky.exp.cache;
/**
 * 缓存对象
 * @author FayeWong
 *
 */
public  class Cache extends AbstractCache{
	
	private static Cache cache;
	private Cache() {};
	public  static Cache getInstance() {
		synchronized (Cache.class) {
			if (null == cache) {
				cache = new Cache();
			}
		}
	    return cache;
	};
	public  CacheToken builder(CacheToken cacheToken) {
		cacheToken.apply(cacheToken);
		return cacheToken;
    }
}
