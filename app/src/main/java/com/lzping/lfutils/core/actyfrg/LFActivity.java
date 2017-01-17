package com.lzping.lfutils.core.actyfrg;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.core.store.LFStoreBackStack;
import com.lzping.lfutils.core.store.LFStoreHashMap;
import com.lzping.lfutils.interfaces.IcomminicationEvent;
import com.lzping.lfutils.interfaces.OnActivityIcomminication;
import com.lzping.lfutils.interfaces.OnFragmentIcomminication;
import com.lzping.lfutils.tool.Fprint;
import com.lzping.lfutils.tool.LfTool;

import java.util.Iterator;

/**
 * Created by user on 2017/1/11.
 */

public class LFActivity extends Activity implements OnActivityIcomminication {
    private static final String TAG = "LFActivity";

    private String mName = "";
    protected void setmName(String mName) {
        this.mName = mName;
    }
    public String getmName() {
        return mName;
    }

    protected LFStoreHashMap<Integer,LFStoreBackStack<LFFragment>> childs;
    public  LFStoreBackStack<LFFragment> getChilds(int containerId) {
        return childs.getValue(containerId);
    }
    public LFStoreBackStack setChilds(int containerId){
        LFStoreBackStack<LFFragment> backStack = new LFStoreBackStack<LFFragment>();
        setChilds(containerId,backStack);
        return backStack;
    }
    public void setChilds(int containerId,LFStoreBackStack backStack){

        childs.add(containerId,backStack);
    }


    public FragmentManager fragmentManager;
    //状态
    private boolean saveState = true;
    public void setSaveState(boolean saveState) {
        this.saveState = saveState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fprint.I("LFFActivity"," 创建完成() onCreate-> savedInstanceState - "+savedInstanceState);
        super.onCreate(savedInstanceState);
        if (childs==null){
            childs = new LFStoreHashMap<>();
        }
        fragmentManager = this.getFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (childs!=null){
            Iterator<Integer> keys = childs.getKeySet();
               while (keys.hasNext()){
                  int containerId = keys.next();
                   LFManager.getInstant().clearBackStack(childs.getValue(containerId), fragmentManager);
               }
        }
        super.onDestroy();
    }
    //保存状态
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Fprint.I("LFFActivity"," 保存状态() onSaveInstanceState-> savedInstanceState -  " + savedInstanceState);
        if (!saveState) {
            savedInstanceState = null;
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Fprint.I("LFFActivity"," 重新恢复状态() onRestoreInstanceState ->savedInstanceState - "+savedInstanceState);
        if (savedInstanceState!=null){
            super.onRestoreInstanceState(savedInstanceState);
        }

    }

    // fragment 传递事件 到activity
    @Override
    public void OnComminicationEvent(IcomminicationEvent event) {
        if (event != null) {
            Fprint.E(TAG + "_OnComminicationEvent - " + event.getAction());
            event.openFragment(this);
        }
    }

    @Override
    public void TransmitFragmentMessage(LFFragment form, int containerId,String toName, Bundle bundle) {

        if (!LfTool.checkUiThread()){
            Fprint.E(TAG+"_Fragment 通讯失败 - [" + form.getPageName()  + "] >>> [" + toName + "] ,Message#"+bundle+" ,- 请在ui线程使用.");
            return;
        }
        if (form==null){
            Fprint.E(TAG+"_Fragment 通讯失败 - [" + form.getPageName()  + "] >>> [" + toName + "] ,Message#"+bundle+" ,- 源地址错误 "+form);
            return;
        }

        String tag = LFFragment.getLFFragmentTag(containerId,toName);
          LFManager mag =  LFManager.getInstant();
        OnFragmentIcomminication to = mag.getFragment(tag,fragmentManager);

        if (bundle != null && to != null) {
            to.receiveMessage(form, bundle);
        } else {
            Fprint.E(TAG+ "_Fragment 通讯失败 - [" + form.getPageName()  + "] >>> [" + toName + "] ,Message#"+bundle+" .目标:"+to);
        }
    }



    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    private Toast mToast = null;

    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

}
