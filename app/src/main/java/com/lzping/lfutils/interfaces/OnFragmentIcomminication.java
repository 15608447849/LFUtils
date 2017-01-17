package com.lzping.lfutils.interfaces;

import android.os.Bundle;

import com.lzping.lfutils.core.actyfrg.LFFragment;

/**
 * Created by user on 2017/1/12.
 * fragment 和 fragment 通讯 >> 通过 activity
 */
public interface OnFragmentIcomminication {
    void receiveMessage(LFFragment form, Bundle bundle);
}
