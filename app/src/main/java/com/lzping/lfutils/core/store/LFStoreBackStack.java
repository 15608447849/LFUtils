package com.lzping.lfutils.core.store;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.lzping.lfutils.core.actyfrg.LFFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2017/1/13.
 * 数据存储 - (回退栈 - “后进先出”)
 */
public class LFStoreBackStack<T extends LFFragment> {
    private int containerId; //回退栈容器id

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public int getContainerId() {
        return containerId;
    }

    private List<T> list;

    public LFStoreBackStack(int containerId) {
        this.containerId = containerId;
        this.list = new ArrayList<>();
    }

    public LFStoreBackStack() {
        this.list = new ArrayList<>();
    }

    /**
     * 增加 - 如果存在,先删除
     */
    public void add(T fragment) {
        if (list.contains(fragment)) {
            throw new IllegalStateException("LFFragment already exist: " + fragment);
        }
        list.add(fragment);
        fragment.setAddToBackStack(true);
    }

    //删除
    public boolean remove(T fragment) {
        boolean f = false;
        if (list.contains(fragment)) {
            Iterator<T> iterator = list.iterator();
            T t;
            while (iterator.hasNext()) {
                t = iterator.next();
                if (t.getPageName().equals(fragment.getPageName())) {
                    //认为是同一个 fragment
                    iterator.remove();
                    t.setAddToBackStack(false);
                    f = true;
                }
            }
        }
        return f;
    }
    public T remove(int index){
        return list.remove(index);
    }

    //获取栈顶对象
    public T getStackTop() {
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    //隐藏栈内所有
    public void hideStackAll(FragmentManager fragmentManager) {

        if (list.size() > 0 && fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Iterator<T> iterator = list.iterator();
            T t = null;
            while (iterator.hasNext()) {
                t = iterator.next();
                ft.hide(t);
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 清空栈内所有对象
     */
    public ArrayList<T> clear(FragmentManager fragmentManager) {
        final String nclass = this.getClass().getSimpleName();

        ArrayList<T> arr = null;
        if (list.size() > 0) {
            arr = new ArrayList();

            Iterator<T> iterator = list.iterator();
            T t = null;
            while (iterator.hasNext()) {
                t = iterator.next();
                iterator.remove();
                arr.add(t);
            }

        }
        return arr;
    }

    //判断是否属于这个栈内
    public boolean isExist(T t) {
        return list.contains(t);
    }

    //获取下标
    public int getIndex(T t){
      return list.indexOf(t);
    }

    //获取指定位置的fragment
    public T getIndexObject(int i){
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    /**
     * 移动到栈顶
     */
    public void moveTop(T t){
        if (list.contains(t)){
            remove(t);
            list.add(t);
        }
    }


}
