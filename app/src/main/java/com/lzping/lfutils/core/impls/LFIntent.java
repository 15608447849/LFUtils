package com.lzping.lfutils.core.impls;

import android.os.Bundle;

import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.interfaces.IcomminicationEvent;

/**
 * Created by user on 2017/1/12.
 */
public class LFIntent extends IcomminicationEvent {


    public LFIntent(Bundle mBundle, int action) {
        super(mBundle, action);
    }

    @Override
    public void openFragment(LFActivity activity) {

        //根据动作判断 是怎么打开
        if (getAction() == IcomminicationEvent.ACTION_OPEN_ON_FRAGMENT) {
            openFragmentOnFragment(activity);
        }
        if (getAction() == IcomminicationEvent.ACTION_OPEN_ON_ACTIVITY) {
            openFragmentOnActivity(activity);
        }
    }


    //在fragment 中 打开 一个 fragment
    private void openFragmentOnFragment(LFActivity activity) {
        Bundle mBundle = getBundle();
        if (activity == null || mBundle == null) {
            return;
        }
        String containerTag = mBundle.getString(KEY_CONTAINER_FRAGMENT_TAG);
        LFFragment containerFlag = LFManager.getInstant().getFragment(containerTag, activity.fragmentManager);

        if (containerFlag == null) {
            //遍历底层activity中的所有fragment

            return;
        }

        String targetName = mBundle.getString(KEY_FRAGMENT_NAME);
        int layoutId = mBundle.getInt(KEY_CONTAINER_ID);
        Bundle sBundle = mBundle.getBundle(KEY_SUB_BUNDLE);
        int[] animArr = mBundle.getIntArray(KEY_FRAGMENT_ANIM_ARRAY);
        int animType = mBundle.getInt(KEY_FRAGMENT_ANIM_TYPE);
        LFManager.getInstant().fragmentOpenFragment(containerFlag, targetName, layoutId, sBundle, animArr, animType);
    }

    //在fragment 中 打开一个 fragment
    private void openFragmentOnActivity(LFActivity activity) {

    }


}
