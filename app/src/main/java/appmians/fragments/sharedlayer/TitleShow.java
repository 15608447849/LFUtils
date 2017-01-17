package appmians.fragments.sharedlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.impls.LFAnimImp;
import com.lzping.lfutils.core.impls.LFIntent;
import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.interfaces.IcomminicationEvent;
import com.lzping.lfutils.tool.Fprint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import appmians.others.FData;

/**
 * Created by user on 2017/1/12.
 */

public class TitleShow extends LFFragment {
    private static final String TAG ="_TitleShow";


    private View baselayout = null;
    private ListView listView;
    private TextView textView;
    private int contentlayoutid;

    private String data = "";
    private  List<String> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (baselayout == null){

            baselayout = inflater.inflate(R.layout.title_frag_layou,null);
            listView = (ListView) baselayout.findViewById(R.id.title_list);
            textView = (TextView) baselayout.findViewById(R.id.title_name);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //点击弹出 - webview界面 - 显示网页 (横屏-右边空白界面) (竖屏-覆盖当前界面)
                    String key = list.get(position);
                    Fprint.E("data - "+ data + "-" +contentlayoutid);
                    if ("data1".equals(data)){//竖屏
                        //横屏
                        String url = FData.data1.get(key);
                        Bundle bundler1 = new Bundle();
                        bundler1.putString("url",url);
                        Bundle bundler2 = IcomminicationEvent.factory("内容显示",
                                TitleShow.this,
                                R.id.title_container,
                                bundler1,
                                LFAnimImp.getInstans().convertAnimations(LFAnimImp.CoreAnim.button2top),
                                0);
                        onActivtyIcm.OnComminicationEvent(new LFIntent(bundler2,IcomminicationEvent.ACTION_OPEN_ON_FRAGMENT));
                        baselayout.findViewById(R.id.title_container).setVisibility(View.VISIBLE);
                    }
                    if ("data2".equals(data)){

                        String url = FData.data2.get(key);
                        Bundle bundle = new Bundle();
                        bundle.putString("url",url);
                        onActivtyIcm.TransmitFragmentMessage(TitleShow.this,contentlayoutid,"内容显示",bundle);
                    }
                }
            });
        }
        return baselayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fprint.I(TAG,"onViewCreated "+mBundle);
        try {
            if (baselayout!=null){

                if (mBundle !=null){
                    String title = mBundle.getString("text");
                    textView.setText(title);
                    Bundle bundle = mBundle.getBundle(LFManager.SUB_BUNDLE_KEY);
                    if (bundle!=null){
                        Fprint.I(TAG,"onViewCreated subBundle -  "+bundle);
                        //设置数据源
                        String dataTem = bundle.getString("data");
                        contentlayoutid = bundle.getInt("contentlayoutid",-1);//横屏时,横屏的容器id
                        if (dataTem!=null){
                            data = dataTem;
                        }
                    }
                }

                Iterator<String> itr = null;
                if ("data1".equals(data)){ //竖屏
                    itr = FData.data1.keySet().iterator();
                }
                if ("data2".equals(data)){ //横屏
                    itr = FData.data2.keySet().iterator();

                }
                if (itr==null){
                    return;
                }
                list = new ArrayList<>();
                while (itr.hasNext()){
                    list.add(itr.next());
                }
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mActivity,
                        android.R.layout.simple_list_item_1, list);//适配器
                listView.setAdapter(myAdapter);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        Fprint.I(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Fprint.I(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Fprint.I(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Fprint.I(TAG,"onStop");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Fprint.I(TAG,"onDestroy");
    }

    @Override
    public void receiveMessage(LFFragment form, Bundle bundle) {
        super.receiveMessage(form, bundle);
        Fprint.E(TAG+"receiveMessage>>>>");
         baselayout.findViewById(R.id.title_container).setVisibility(View.GONE);
    }
}
