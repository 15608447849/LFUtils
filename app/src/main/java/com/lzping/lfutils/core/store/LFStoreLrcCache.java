package com.lzping.lfutils.core.store;

import android.util.LruCache;

import com.lzping.lfutils.tool.Fprint;

/**
 * Created by user on 2017/1/13.
 */

public class LFStoreLrcCache<K,V>{

    public interface LFLrccacheMonitor<K,V>{
        void onDelete(boolean evicted,K key,V value);
        void onExist(K key,V value);
        void onError(Exception e);
    }

    public abstract class LFLrccacheMonitorAdapter implements LFLrccacheMonitor{
        @Override
        public void onDelete(boolean evicted, Object key, Object value) {
        }

        @Override
        public void onExist(Object key, Object value) {

        }

        @Override
        public void onError(Exception e) {

        }
    }


    private LruCache<K,V> lruCache;
    private LFLrccacheMonitor<K,V> monitor;
    //设置 缓存监听
    public LFLrccacheMonitor<K, V> getMonitor() {
        return monitor;
    }

    public LFStoreLrcCache(int percentage) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        maxMemory = maxMemory/percentage;
        Fprint.D("缓存lrcCache设置最大使用大小 : "+maxMemory+"kb");
        this.lruCache = new LruCache<K,V>(maxMemory){
            @Override
            protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
//                super.entryRemoved(evicted, key, oldValue, newValue);//默认空实现
                //当item被回收或者删掉时调用。该方法当value被回收释放存储空间时被remove调用， 或者替换item值时put调用.
                //true: 为释放空间被删除；
                // false: put或remove导致
                    if (monitor!=null){
                        monitor.onDelete(evicted,key,oldValue);
                    }
            }
        };
    }

    //添加
    public synchronized boolean put(K key,V value){
        if (key!=null && value!=null){

           if (lruCache.get(key) == null){
               lruCache.put(key,value);
               return true;
           }else{
               if (monitor!=null){
                   monitor.onExist(key,value);
               }
           }
        }else{
            if (monitor!=null){
                monitor.onError(new IllegalArgumentException("lrcCache put err.because key is null or value is null"));
            }
        }
        return false;
    }
    //获取
    public synchronized V get(K key){
        return lruCache.get(key);
    }

    //移除
    public synchronized V remove(K key){
           return lruCache.remove(key);
    }

    //获取大小
    public int size(){
        return lruCache.size();
    }

    public void destory(LFLrccacheMonitor monitor1){
        this.monitor = monitor1;
        destory();
    }
    public void destory(){
        //清除所有
        if (lruCache.size() > 0) {
            lruCache.evictAll();
        }
    }

}
