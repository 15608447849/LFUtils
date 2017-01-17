package com.lzping.lfutils.core.manage;

import android.content.Context;
import android.text.TextUtils;

import com.lzping.lfutils.beans.LCorePage;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.store.LFStoreHashMap;
import com.lzping.lfutils.tool.Fprint;
import com.lzping.lfutils.tool.IOHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/1/10.
 * 单例 - 页面工厂 - 使用前调用 初始化
 */
public class LFFatory {
    private static LFFatory instants = null;
    public static LFFatory getInstant() {
        if (instants == null) {
            instants = new LFFatory();
        }
        return instants;
    }

    /**
     * fragments 存储
     */
    private LFStoreHashMap<String, LCorePage> mPageList;
    //构造
    private LFFatory() {
        this.mPageList = new LFStoreHashMap<>();
    }
    private boolean isInit = false;
    ///初始化
    public void init(Context context,String configName){
        if (!isInit){
            isInit = readConfig(context,configName);
        }
    }
    ///初始化
    public void init(Context context){
            readConfig(context,"FragmentManifest.json");
    }
    /**
     * 配置读取出来存进该map，读取的时候判断name和class是否为空，为空则跳过。
     */
    private boolean readConfig(Context mContext, String configName) {
        Fprint.D("读取fragment配置json文件 : " + configName);
        String content = IOHelper.readAssetsFile(mContext, configName);
        if (content == null) {
            Fprint.E("读取错误 : " + configName);
            return false;
        }
        List<LCorePage> list = IOHelper.parseString2JsonList(content, LCorePage.class);
        if (list == null) {
            Fprint.E("解析错误 : " + configName);
            return false;
        }
        Iterator<LCorePage> listIter = list.iterator();
        LCorePage cpage;
        while (listIter.hasNext()) {
            cpage = listIter.next();
            if (cpage.isComplete()) {
                mPageList.add(cpage.getName(), cpage);
            }
        }
        Fprint.D("初始化完成 碎片实体总数 - LCorePage List Size : " + mPageList.size());
        return true;
    }
    /**
     * 提供动态添加的入口
     */
    public boolean putPage(String name, Class<? extends LFFragment> clazz, Map<String, String> datas) {
        if (TextUtils.isEmpty(name) || clazz == null) {
            Fprint.E("添加失败 - name is null or clazz is null.");
            return false;
        }
        try {
            mPageList.add(name, new LCorePage(name, clazz.getName(), IOHelper.mapToJsonString(datas)));
        } catch (Exception e) {
            Fprint.E("添加失败 - error: " + e.getMessage());
            return false;
        }
        return true;
    }
    //获取一个页面实体
    public LCorePage getPage(String name) {
        return mPageList.getValue(name);
    }
}

