package com.lzping.lfutils.interfaces;

import android.os.Bundle;

import com.lzping.lfutils.core.actyfrg.LFFragment;

/**
 * Created by user on 2017/1/12.
 *  fragment 和 activity 通讯
 */
public interface OnActivityIcomminication {
    void OnComminicationEvent(IcomminicationEvent event);
    void TransmitFragmentMessage(LFFragment form, int containerId,String toName, Bundle bundle);
}
