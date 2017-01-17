package com.lzping.lfutils.interfaces;

import android.os.Bundle;

import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.core.actyfrg.LFFragment;

/**
 * Created by user on 2017/1/12.
 */

public abstract class IcomminicationEvent {

    /**
     * key - fragment name
     */
    public static final String KEY_FRAGMENT_NAME = "_fragment_name";//指定的打开的fragment的名字
    public static final String KEY_SUB_BUNDLE = "_fragment_bundle";//指定打开的fragment需要传递的参数
    public static final String KEY_CONTAINER_ID = "_fragment_container_id";//指定的容器id - 主容器:VALUE_FRAGMENT_MAIN_CONTAINER
    public static final String KEY_FRAGMENT_ANIM_ARRAY = "_fragment_anim_arr";//指定的打开动画数组
    public static final String KEY_FRAGMENT_ANIM_TYPE = "_fragment_anim_type";//指定的打开动画类型
    public static final String KEY_CONTAINER_FRAGMENT_TAG = "_fragment_container_tags";//指定的容器fragment的全路径

    //动作
    public static final int ACTION_NOME = 0x00;
    public static final int ACTION_OPEN_ON_ACTIVITY = 0x01; //在activity中打开
    public static final int ACTION_OPEN_ON_FRAGMENT = 0x02; //在fragment中打开

    private Bundle mBundle = null;

    //设置bundler对象
    public void setBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }
    //获取 bundle
    public Bundle getBundle(){
        return mBundle;
    }

    //动作
    private int action = ACTION_NOME;

    //获取动作
    public int getAction() {
        return action;
    }
    //设置动作
    protected void setAction(int action) {
        this.action = action;
    }
    public IcomminicationEvent(Bundle mBundle,int action) {
        setAction(action);
        setBundle(mBundle);
    }
    /**
     * 打开fragment
     */
    public abstract void openFragment(LFActivity activity);


    public static Bundle factory(String fragmentName, LFFragment lfFragment, int containerId, Bundle subBundle, int[] animArr, int animType){
        Bundle bundle = new Bundle();
        bundle.putBundle(IcomminicationEvent.KEY_SUB_BUNDLE,subBundle);
        bundle.putInt(IcomminicationEvent.KEY_CONTAINER_ID, containerId);
        bundle.putString(IcomminicationEvent.KEY_CONTAINER_FRAGMENT_TAG,LFFragment.getLFFragmentTag(lfFragment.getmContainerId(),lfFragment.getPageName()));
        bundle.putString(IcomminicationEvent.KEY_FRAGMENT_NAME,fragmentName);
        bundle.putIntArray(IcomminicationEvent.KEY_FRAGMENT_ANIM_ARRAY,animArr);
        bundle.putInt(IcomminicationEvent.KEY_FRAGMENT_ANIM_TYPE,animType);
        return bundle;
    }
}
