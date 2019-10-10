package org.lucky.exp.cache;
/**
 * 缓存对象
 * @author FayeWong
 *
 */
public  class Cache extends AbstractCache{
	private static boolean openCache = true;//应该配置在计算入口，是否需要缓存计算。全部使用缓存计算
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
		cacheToken.setOpenCache(openCache);
		return cacheToken;
    }
}
