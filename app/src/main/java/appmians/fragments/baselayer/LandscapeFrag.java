package appmians.fragments.baselayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.impls.LFAnimImp;
import com.lzping.lfutils.core.impls.LFIntent;
import com.lzping.lfutils.interfaces.IcomminicationEvent;
import com.lzping.lfutils.tool.Fprint;

/**
 * Created by user on 2017/1/10.
 */

public class LandscapeFrag extends LFFragment {
    private static final String TAG ="LandscapeFrag";
   private View baseLayout;
    private Button button_1 ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (baseLayout==null) {
            baseLayout = inflater.inflate(R.layout.landscpe_base_layout,null);
            button_1 = (Button) baseLayout.findViewById(R.id.landscpe_base_button_1);
            button_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.showToast("横屏_1_按钮");
                    openTitle();
                    openContent();
                }
            });
        }

        return baseLayout;
    }
    //打开 title fragment
    private void openTitle() {
            if (onActivtyIcm!=null){
                Bundle bundler1 = new Bundle();
                bundler1.putString("data","data2");
                bundler1.putInt("contentlayoutid",R.id.landscpe_top_right);
                Bundle bundler2 = IcomminicationEvent.factory("标题栏",
                        LandscapeFrag.this,
                        R.id.landscpe_top_left,
                        bundler1,
                        LFAnimImp.getInstans().convertAnimations(LFAnimImp.CoreAnim.right2left),
                        0);
                onActivtyIcm.OnComminicationEvent(new LFIntent(bundler2,IcomminicationEvent.ACTION_OPEN_ON_FRAGMENT));
            }
    }

    //打开内容显示fragment
    private void openContent(){
        Bundle bundler2 = IcomminicationEvent.factory("内容显示",
                LandscapeFrag.this,
                R.id.landscpe_top_right,
                null,
                LFAnimImp.getInstans().convertAnimations(LFAnimImp.CoreAnim.right2left),
                0);
        onActivtyIcm.OnComminicationEvent(new LFIntent(bundler2,IcomminicationEvent.ACTION_OPEN_ON_FRAGMENT));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fprint.I(TAG,"onCreate");
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
}
