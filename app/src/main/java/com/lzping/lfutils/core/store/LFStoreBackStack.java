package com.lzping.lfutils.core.store;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.tool.Fprint;

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
            throw new IllegalStateException("LFFragment already added: " + fragment);
        }
        list.add(fragment);
        fragment.setAddToBackStack(true);
    }

    //删除
    public boolean removeAll(T fragment) {
        boolean f = false;
        if (list.contains(fragment)) {
            Iterator<T> iterator = list.iterator();
            T t = null;
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
    public ArrayList clear(FragmentManager fragmentManager) {
        final String nclass = this.getClass().getSimpleName();

        ArrayList<String> names = null;
        if (list.size() > 0) {
            names = new ArrayList();

            Iterator<T> iterator = list.iterator();
            T t = null;
            while (iterator.hasNext()) {
                t = iterator.next();
                iterator.remove();
                names.add(t.getPageName());
            }
            if (fragmentManager != null) {

                Fprint.I(nclass, "clear() - fragmentManage [ " + fragmentManager + " ]");
                boolean f = fragmentManager.isDestroyed();
                Fprint.I(nclass, " fragment isDestroyed : " + f);
                if (!f) {
                    for (String s : names) {
                        Fragment fg = fragmentManager.findFragmentByTag(s);
                        if (fg == null) continue;
                        Fprint.I(nclass, "fragmentManager find fragment by tag : " + fg);
                        Fprint.I(nclass, "fragmentManager find fragment isAdded : " + fg.isAdded());
                        Fprint.I(nclass, "fragmentManager find fragment isDetached : " + fg.isDetached());
                        Fprint.I(nclass, "fragmentManager find fragment isHidden : " + fg.isHidden());
                        Fprint.I(nclass, "fragmentManager find fragment isInLayout : " + fg.isInLayout());
                        Fprint.I(nclass, "fragmentManager find fragment isRemoving : " + fg.isRemoving());
                        Fprint.I(nclass, "fragmentManager find fragment isResumed : " + fg.isResumed());
                        Fprint.I(nclass, "fragmentManager find fragment isVisible : " + fg.isVisible());
                    }
                }

            }
        }
        return names;
    }

    //判断是否属于这个栈内
    public boolean isExist(T t) {
        return list.contains(t);
    }

    public int size() {
        return list.size();
    }

}
