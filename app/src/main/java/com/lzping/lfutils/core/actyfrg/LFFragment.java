package com.lzping.lfutils.core.actyfrg;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.core.store.LFStoreBackStack;
import com.lzping.lfutils.core.store.LFStoreHashMap;
import com.lzping.lfutils.interfaces.OnActivityIcomminication;
import com.lzping.lfutils.interfaces.OnFragmentIcomminication;
import com.lzping.lfutils.tool.Fprint;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/1/10.
 */

public class LFFragment extends Fragment implements OnFragmentIcomminication{
    private static final String TAG ="_BaseFragment_";
    private static final String savedInstanceStateBoundKey = "savedInstanceStateBoundKey";
    protected LFStoreHashMap<Integer,LFStoreBackStack<LFFragment>> childs;
    public LFStoreHashMap<Integer, LFStoreBackStack<LFFragment>> getChilds() {
        return childs;
    }
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

    protected LFActivity mActivity; //所在activity
    protected OnActivityIcomminication onActivtyIcm;
    private String mPageName;//页面名
    private boolean isAddToBackStack = false;//是否添加到回退栈
    public boolean isAddToBackStack() {
        return isAddToBackStack;
    }
    public void setAddToBackStack(boolean addToBackStack) {
        isAddToBackStack = addToBackStack;
    }

    public String getPageName() {
        return mPageName;
    }
    public void setPageName(String pageName) {
        mPageName = pageName;
    }
    protected Bundle mBundle;

    protected int mContainerId;//所在容器

    //自己的容器id
    public int getmContainerId() {
        return mContainerId;
    }
    public void setmContainerId(int mContainerId) {
        this.mContainerId = mContainerId;
    }


   public static String getLFFragmentTag(int containerId,String fragmentName)
   {
    return containerId+"#"+fragmentName;
   }

    public LFFragment() {
        if (childs==null){
            childs = new LFStoreHashMap<>();
        }
        Fprint.D("碎片 ["+this.getClass().getSimpleName()+" ,"+this.hashCode()+"]- 创建 成功." );
    }
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        setBundle(args);
    }

    public void setBundle(Bundle bundle){
        if (bundle!=null){
            boolean flag = bundle.getBoolean("onSaveInstanceState",false);
            if (flag){
                this.setPageName(bundle.getString("name"));
                this.setmContainerId(bundle.getInt("containerId"));
                Bundle mbundle1 = bundle.getBundle(savedInstanceStateBoundKey);
                if (mbundle1!=null){
                    mBundle = mbundle1;
                }
            }
            else{
                mBundle = bundle;
            }
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LFActivity){
            mActivity = (LFActivity) activity;
            onActivtyIcm = mActivity;
        }else{
            throw new IllegalArgumentException("the activity not inheritance com.lzping.lfutils.LBaseActivty.");
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Fprint.D(this.getClass().getSimpleName()+">>onCreate() savedInstanceState: [ "+savedInstanceState+" ]");
        if (savedInstanceState!=null){
            setBundle(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fprint.D(this.getClass().getSimpleName()+">>onCreateView() savedInstanceState: [ "+savedInstanceState+" ]");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            setBundle(savedInstanceState);
        }
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Fprint.D(this.getClass().getSimpleName()+">>onActivityCreated() savedInstanceState: [ "+savedInstanceState+" ]");
        if (savedInstanceState!=null){
            setBundle(savedInstanceState);
        }
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        //处理自己的所有回退栈操作
        clearChilds();
        super.onDestroy();
    }

    /**
     * 与 Activity 类似，在 Activity 进程被杀死并被重新创建，且需要恢复 Fragment 的状态时，
     * 可以利用 Bundle 取回 Fragment 的状态。
     * 可以在 Fragment 的 onSaveInstanceState() 回调方法中保存 Fragment 的状态，
     * 并在 onCreate() 、 onCreateView() 或 onActivityCreated() 方法中恢复状态。
     * 关于保存状态的更多信息，请参阅文档Activity。
     * @param outState
     */

    //保存状态
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Fprint.D(this.getClass().getSimpleName()+">>onSaveInstanceState() outState: [ "+outState+" ]");
        outState.putString("name",getPageName());
        outState.putInt("containerId",getmContainerId());
        outState.putBoolean("onSaveInstanceState",true);
        if (mBundle!=null){
            outState.putBundle(savedInstanceStateBoundKey,mBundle);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Fprint.D(this.getClass().getSimpleName()+">>onViewStateRestored() outState: [ "+savedInstanceState+" ]");
        if (savedInstanceState!=null){
            setBundle(savedInstanceState);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        onActivtyIcm = null;
    }

    //如果在Fragment有操作toolbar的菜单的情况，除了要在Fragment中设置setHasOptionsMenu(true);之外，还需要Fragment中重写onHiddenChanged方法：
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    /**接受其他 fragment的消息*/
    @Override
    public void receiveMessage(LFFragment form,Bundle bundle) { }

    //清理回退栈操作
    private void clearChilds() {
        if (childs!=null){
            Iterator<Map.Entry<Integer,LFStoreBackStack<LFFragment>>> iter = childs.getEntrySet();
            LFStoreBackStack<LFFragment> v ;
            while (iter.hasNext()){
                v = iter.next().getValue();
                LFManager.getInstant().clearBackStack(v,this.getChildFragmentManager());
            }
        }
    }
}
