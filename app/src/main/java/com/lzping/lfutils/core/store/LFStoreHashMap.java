package com.lzping.lfutils.core.store;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/1/13.
 */
public class LFStoreHashMap<K, V> {
    private Map<K, V> map;
    public LFStoreHashMap() {
        this.map = new HashMap<>();
    }
    //添加元素
    public void add(K key, V vaule) {
        if (map.containsKey(key)) {
            throw new IllegalStateException("key already puted: " + key);
        }
        map.put(key, vaule);
    }
    //获取某个元素的值
    public V getValue(K key){
        if (map.containsKey(key)){
            return map.get(key);
        }
        return null;
    }
    //获取key Itro
    public Iterator<K> getKeySet() {
        return map.keySet().iterator();
    }
    //获取key value itro
    public Iterator<Map.Entry<K, V>> getEntrySet() {
        return map.entrySet().iterator();
    }
    //移除某个元素
    public V remove(K key) {
        Iterator<Map.Entry<K, V>> it = getEntrySet();
        Map.Entry<K, V> ety;
        K oldk;
        V oldv = null;
        while (it.hasNext()) {
            ety = it.next();
            oldk = ety.getKey();
            if (oldk.equals(key)) {
                it.remove();
                oldv = ety.getValue();
                break;
            }
        }
        return oldv;
    }
    //返回大小
    public int size(){
        return map.size();
    }
}
