package com.lzping.lfutils.core.manage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lzping.lfutils.beans.LCorePage;
import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.store.LFStoreBackStack;
import com.lzping.lfutils.core.store.LFStoreLrcCache;
import com.lzping.lfutils.tool.Fprint;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/10
 * fragments  管理 - 页面跳转等
 * 初始化 - 与activity绑定
 */
public class LFManager {

    /**
     * fragment 传递值时,非提前绑定的数据内容KEY
     */
    public static final String SUB_BUNDLE_KEY = "subbundle";
    private static LFManager manager = null;
    //已存在 fragments
    private LFStoreLrcCache<String, LFFragment> mFragmentsMap;
    private LFManager() {
        mFragmentsMap = new LFStoreLrcCache<>(2);
    }
    public static LFManager getInstant() {
        if (manager == null) {
            manager = new LFManager();
        }
        return manager;
    }

    /**
     * 添加fragment 到 缓存中
     */
    private boolean addFrgmentToCache(String key, LFFragment fragment) {
        return mFragmentsMap.put(key, fragment);
    }

    /**
     * 获取一个已存在的fragments 从保存的map或者fragment管理器中获取
     */
    public LFFragment getFragmentOnCache(String key) {
        LFFragment fragment = mFragmentsMap.get(key);
        if (fragment!=null){
            Fprint.D("从Cache中获取到一个存在的碎片 - [" + fragment.getPageName() + "]");
        }
        return fragment;
    }
    //从fragment manager 中 获取一个 fragment
    public LFFragment getFragmentOnFm(String tag, FragmentManager fragmentManager){
        if (fragmentManager != null) {
            LFFragment fragment = (LFFragment) fragmentManager.findFragmentByTag(tag);
            if (fragment != null && !fragment.isRemoving()) { //不在移除中
                Fprint.D("从FragmentManager中获取到一个存在的碎片 - [" + fragment.getPageName() + "]");
//                addFrgmentToExist(fragment.getPageName(), fragment);
               return fragment;
            }
        }
        return null;
    }


    /**
     * 从缓存中移除fragment
     */
    public LFFragment removeFrgmentOnCache(String key) {
            LFFragment fragment = mFragmentsMap.remove(key);
            if (fragment != null) {
                Fprint.E("从缓存列表中移除fragment .success - [" + fragment + "]");
            }else{
                Fprint.E("从缓存列表中移除fragment .failt - [" + fragment + "]");
            }
            return fragment;
        }

    /**
     * 从FragmentManager移除fragment
     */
    public boolean removeFrgmentOnFragmentManager(String tag, FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            try {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
                Fprint.E("从FragmentManager中移除fragment .success - [" + fragment + "]");
                return true;
            } catch (Exception e) {
                Fprint.E("从FragmentManager中移除fragment .error - " + e);
            }
        }
        return false;
    }




    //---------------------回退栈操作函数--------------------------------------------//

    /**
     * 检查回退栈是否存在fragment -> 存在 ->是否属于栈顶
     */
    private boolean checkFragmentIsTopStack(LFFragment fragment, LFStoreBackStack<LFFragment> backStack) {
        if (checkFragmentOnBackStack(fragment, backStack) > 0) {
            return backStack.getStackTop().getPageName().equals(fragment.getPageName());
        }
        return false;
    }

    //检查是否存在于这个栈中, 存在->返回在这个栈中的index,否则返回-1
    private int checkFragmentOnBackStack(LFFragment fragment, LFStoreBackStack<LFFragment> backStack) {
        if (!backStack.isExist(fragment)) {
            return -1;
        }
        //存在计算下标
        return backStack.getIndex(fragment);
    }

    /**
     * 隐藏一个回退栈所有fragment
     */
    private void hideFragmentOnBackStack(FragmentManager fragmentManager, LFStoreBackStack<LFFragment> backStack) {
        backStack.hideStackAll(fragmentManager);
    }

    /**
     * 添加fragment 到回退栈
     */
    public void addFragmentToBackStack(LFStoreBackStack<LFFragment> backStack, LFFragment fragment) {
        if (backStack != null && fragment != null) {
            if (!backStack.isExist(fragment)) {//如果回退栈不存在这个 -> 添加到回退栈中!
                //添加到栈中
                try {
                    backStack.add(fragment);
                } catch (Exception e) {
                    Fprint.E("addFragmentToBackStack() - error: " + e.getMessage());
                }
            }
        }
    }


    //清空回退栈内所有fragment
    public void clearBackStack(LFStoreBackStack<LFFragment> backStack, FragmentManager fragmentManager) {
        if (backStack != null && backStack.size() > 0) {
            Fprint.E("清空回退栈中"+backStack.getContainerId()+"- 大小: " + backStack.size());
            ArrayList<LFFragment> vars = backStack.clear(fragmentManager);
            //没有清理缓存 好 fragment管理器
           for (LFFragment fg : vars){
               String tag  = LFFragment.getLFFragmentTag(fg.getmContainerId(),fg.getPageName());
               removeFrgmentOnCache(tag);
               removeFrgmentOnFragmentManager(tag,fragmentManager);
           }
        }
        System.gc();
    }

    //移除栈中指定下标后面的fragment
    private void removeBackStackTarger(LFStoreBackStack<LFFragment> backStack, int index, FragmentManager fragmentManager) {

        if (++index == backStack.size()) {//下标越界
            return;
        }
        ArrayList<LFFragment> list = new ArrayList<>();
        for (int i = index; i < backStack.size(); i++) {
            //获取一个fragment
            list.add(backStack.getIndexObject(i));
        }
        boolean fg;
        if (list.size()>0){
            for (LFFragment f : list){
                fg = backStack.remove(f);
                if (fg){
                    Fprint.E("从 回退栈 移除 - [ " + f.getPageName() +" ]");
                    if (fragmentManager !=null) {
                        String tag = LFFragment.getLFFragmentTag(f.getmContainerId(),f.getPageName());
                        //缓存中移除
                        removeFrgmentOnCache(tag);
                        //fragmentManager中移除
                        removeFrgmentOnFragmentManager(tag,fragmentManager);
                    }
                }
            }
        }
    }
    //移动到栈顶
    private void moveFragmentOnBackStackTop(LFFragment fragment, LFStoreBackStack<LFFragment> backStack) {
        backStack.moveTop(fragment);
    }

    //-------------------------------------------------------------fragment 增加修改删除创建----------------------------------------------------------


    /**
     * 创建一个fragment
     */
    public LFFragment newFragment(LCorePage page){

        if (page == null){
            Fprint.E("无法创建 , LcorePage is null");
            return null;
        }
        //反射实体
        try {
            LFFragment fragment = (LFFragment) Class.forName(page.getClasspath()).newInstance();
            Fprint.D("创建 fragment : " + fragment +" \n 信息 : "+page.toInfo());
            //设置页面名字
            fragment.setPageName(page.getName());
            //设置页面参数
            Bundle pageBundle = page.buildBundle();
            if (pageBundle != null) {
                fragmentSettingBundle(fragment, pageBundle);
            }
            return fragment;
        } catch (Exception e) {
            Fprint.E("getFragment() .创建fragment - " + e.getMessage());
        }
        return null;
    }

    //设置 fragment 参数
    private void fragmentSettingBundle(LFFragment fragment, Bundle pageBundle) {
        try {
            fragment.setArguments(pageBundle);//传递并且保存数据
        } catch (IllegalStateException e) {
            Fprint.E("  fragment.setArguments(bundle) error : " + e.getMessage());
        }
    }

    /**
     * 获取一个fragemnt
     **/
    public LFFragment getFragment(String fragmentTAG, FragmentManager fragmentManager) {
        LFFragment fragment = getFragmentOnCache(fragmentTAG);//尝试获取
        if (fragment == null) {
            fragment = getFragmentOnFm(fragmentTAG,fragmentManager);
            //addFrgmentToExist(fragment.getLfPath(), fragment);//添加   key - lfath ,value - lffragment
        }
        return fragment;
    }

    /**
     * 显示一个fragment
     *
     * @param animations 设置动画 - int enter, int exit, int popEnter, int popExit
     */
    private LFFragment showFragment(FragmentManager fragmentManager,
                                    LFFragment fragment,
                                    Bundle bundle,
                                    int containerId,
                                    LFStoreBackStack<LFFragment> backStack,
                                    int[] animations, int tanslationAnimType,
                                    boolean hideBackStackOtherElement,
                                    boolean removeBackStackElementOnTop,
                                    Object info)
    {

        if (fragmentManager == null || fragment == null || fragment.getPageName() == null || backStack == null) {
            Fprint.E("事务管理器不存在. 或者 碎片不存在. 或者 碎片非法 或者 回退栈不存在. 或者 无容器布局. Why do you debug model? Fack.");
            return fragment;
        }
        Fprint.D("准备显示 - [ " + fragment.getPageName() + " ] ");

        //检查fragment是不是已存在回退栈且属于栈顶
        boolean isShowing = checkFragmentIsTopStack(fragment, backStack);
        if (isShowing) {
            //显示中
            Fprint.D("显示中的fragment - [" + fragment.getPageName() + "]");
            return fragment;
        }
        //隐藏-所有-回退栈中的fragment
        if (hideBackStackOtherElement) {
            hideFragmentOnBackStack(fragmentManager, backStack);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//事务操作
        String tag = LFFragment.getLFFragmentTag(containerId,fragment.getPageName());
        //设置动画
        if (animations != null) {
            if (animations.length == 4) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
            }
            if (animations.length == 2) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1]);
            }
        } else {
            if (tanslationAnimType == FragmentTransaction.TRANSIT_NONE || tanslationAnimType == FragmentTransaction.TRANSIT_FRAGMENT_OPEN || tanslationAnimType == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
                fragmentTransaction.setTransition(tanslationAnimType);
            }
        }

            //获取栈中的位置
            int index = checkFragmentOnBackStack(fragment, backStack);
            if (index >= 0 && removeBackStackElementOnTop) {
                //存在 (移除上面的)
                //移除fragment中,指定下标后面的所有
                removeBackStackTarger(backStack, index, fragmentManager);
            }
            else{
                //移动到栈顶
                moveFragmentOnBackStackTop(fragment, backStack);
            }

            //Fprint.E("状态: fragment.isAdded() -  "+fragment.isAdded()+" fragment.isRemoving() - "+fragment.isRemoving()+"fragment.isHidden() "+fragment.isHidden()+"- fragment.isVisible() :"+fragment.isVisible());
            boolean isAdd = !fragment.isAdded() && !fragment.isRemoving(); //未添加,并且不在移除中
            if (isAdd) {
                if (containerId > 0) {
                    //新添加 add

                    fragmentTransaction.add(containerId, fragment,tag);

                    Fprint.D("添加到FragmentManage - Tag: \' " + tag + " \'");
                }
            } else {
                //显示show 不在移除中,并且 显示是隐藏true,显示false
                boolean isShow = !fragment.isRemoving();//&& (fragment.isHidden() || fragment.isVisible());//&& fragment.isHidden();// && !fragment.isVisible();
                if (isShow) {
                    fragmentTransaction.show(fragment);
                } else {
                    Fprint.E("状态无法处理");
                }
        }
        //设置bundle数据
        if (bundle != null) {
            if (isAdd) {
                //不存在新增时
                Bundle smBundle = fragment.getArguments();
                if (smBundle == null) {
                    smBundle = new Bundle();
                }
                smBundle.putBundle(SUB_BUNDLE_KEY, bundle);
                fragmentSettingBundle(fragment, smBundle);
            } else {
                //存在
                fragment.receiveMessage((LFFragment) info, bundle);
            }
        }
        addFragmentToBackStack(backStack, fragment);
        //添加到缓存?
        addFrgmentToCache(tag,fragment);
        fragment.setmContainerId(containerId);
        fragmentTransaction.commitAllowingStateLoss();
        return fragment;
    }




    //getFragmentManager到的是activity对所包含fragment的Manager，
    // 而如果是fragment嵌套fragment，那么就需要利用getChildFragmentManager()了
    // fragment 上面 加载fragment
    private LFFragment showFragment(LFFragment containerFrag, LFFragment fragment, int containerId, Bundle bundle, int[] animations, int tanslationAnimType) {
        if (containerFrag == null || fragment == null || containerId == 0) {
            Fprint.E("容器fragment 不存在. 或者 碎片fragment不存在. 或者 回退栈不存在. 或者 无容器布局. Why do you debug model? Fack.");
            return fragment;
        }

        LFStoreBackStack<LFFragment> backStack = null;
        try {
            backStack = containerFrag.getChilds(containerId);
            if (backStack == null) {
                backStack = containerFrag.setChilds(containerId);
            }
            backStack.setContainerId(containerId);//设置容器的id;
        } catch (Exception e) {
            Fprint.E("无法在fragment[" + containerFrag.getPageName() + "]中打开fragment[" + fragment.getPageName() + "]");
            return fragment;
        }
        return showFragment(
                containerFrag.getChildFragmentManager(),//fragment子类管理器
                fragment,//目标
                bundle,//绑定的数据
                containerId,//容器id
                backStack,//回退栈
                animations,//动画
                tanslationAnimType,//动画类型
                true,//栈中frg是否全部隐藏
                true,//是否移除 回退栈 栈顶上面的 元素
                containerFrag
        );
    }


    /***********************************************************
     * 重载方法
     *********************************************************************/




    //frgmrnt 打开fragment
    public LFFragment fragmentOpenFragment(LFFragment containerFrag, String fragmentName, int containerID, Bundle bundle, int[] animations, int tanslationAnimType) {



        if (containerFrag == null) {
            Fprint.D("容器fragment[" + containerFrag.getPageName() + "] - 尝试打开fragment - [" + fragmentName + "] 失败, 容器不存在.");
            return null;
        }
        Fprint.D("容器fragment[" + containerFrag.getPageName() + "] - 尝试打开fragment - [" + fragmentName + "]");

        String tag= containerID+"#"+fragmentName;
        LFFragment fragment = LFManager.getInstant().getFragment(tag, containerFrag.getChildFragmentManager());
        if (fragment==null){
            //创建
            LCorePage corePage = LFFatory.getInstant().getPage(fragmentName);
            fragment = newFragment(corePage);
        }
        return showFragment(containerFrag, fragment, containerID, bundle, animations, FragmentTransaction.TRANSIT_NONE);
    }

    /**
     * @param fragmentName
     * @param bundle
     * @param containerId
     * @param animations
     * @param tanslationAnimType
     * @param hide
     * @param flag
     * @return
     */
    public LFFragment activityOpenFragment(LFActivity activity,
                                           String fragmentName,
                                           Bundle bundle,
                                           int containerId,
                                           int[] animations,
                                           int tanslationAnimType,
                                           boolean hide,
                                           boolean flag)
    {

        LFStoreBackStack<LFFragment> backStack = null;
        try {
            backStack = activity.getChilds(containerId);
            if (backStack == null) {
                backStack = activity.setChilds(containerId);
            }
            backStack.setContainerId(containerId);//设置容器的id;
        } catch (Exception e) {
            Fprint.E("无法设置LFActivity的容器id - "+containerId);
            return null;
        }

        Fprint.D("容器activtiy[" + activity.getmName() + "] - 尝试打开fragment - [" + fragmentName + "]");
        String tag = containerId+"#"+fragmentName;
        LFFragment fragment = LFManager.getInstant().getFragment(tag,activity.fragmentManager);
        if (fragment==null){
            //创建
            LCorePage corePage = LFFatory.getInstant().getPage(fragmentName);
            fragment = newFragment(corePage);
        }
        return showFragment(activity.fragmentManager, fragment, bundle, containerId, backStack, animations, tanslationAnimType, hide, flag, null);
    }


}
