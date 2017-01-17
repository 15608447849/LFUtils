package com.lzping.lfutils.cusview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.impls.LFAnimImp;
import com.lzping.lfutils.core.manage.LFManager;

/**
 * Created by user on 2017/1/17.
 */

public abstract class LFFragmentPagerAdpter extends PagerAdapter{
    private static final String TAG = "LFFragmentPagerAdapter";
    private LFFragment mCurrentPrimaryItem = null;//当前显示的fragment

    //关联的单应用activity
    private LFActivity activity;

    //初始化
    public void init(LFActivity activity){
        this.activity = activity;
    }
    //结束
    public void uninit(){
        this.activity = null;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
        if (activity==null){
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a LFActivity.Plase call init(LFActivity activity)");
        }
    }

    public abstract String getFragmentName(int positon);

    //加载
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int containerId = container.getId();



        return LFManager.getInstant().activityOpenFragment(
                activity,
                getFragmentName(position),
                null,
                container.getId(),
                LFAnimImp.getInstans().convertAnimations(LFAnimImp.CoreAnim.fade),
                FragmentTransaction.TRANSIT_NONE,
                false,
                false);

    }
    //销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LFFragment fragment = ((LFFragment)object);
            //删除回退栈并且删除缓存
        String tag = LFFragment.getLFFragmentTag(container.getId(),fragment.getPageName());
        LFManager.getInstant().removeFrgmentOnCache(tag);
        LFManager.getInstant().removeFrgmentOnFragmentManager(tag,activity.fragmentManager);
    }
    //设置当前下标
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        LFFragment fragment = (LFFragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                FragmentCompat.setMenuVisibility(mCurrentPrimaryItem, false);
                FragmentCompat.setUserVisibleHint(mCurrentPrimaryItem, false);
            }
            if (fragment != null) {
                FragmentCompat.setMenuVisibility(fragment, true);
                FragmentCompat.setUserVisibleHint(fragment, true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }





    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment)object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
