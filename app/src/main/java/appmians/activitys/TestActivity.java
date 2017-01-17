package appmians.activitys;

import android.os.Bundle;
import android.util.Log;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.core.actyfrg.LFFragment;
import com.lzping.lfutils.core.impls.LFAnimImp;
import com.lzping.lfutils.core.manage.LFManager;
import com.lzping.lfutils.tool.LfTool;

/**
 * Created by user on 2017/1/11.
 */

public class TestActivity extends LFActivity {

    private static final String  TAG = "_TestActivity_";

    private String fragmentName = "竖屏";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"============onCreate");
        setContentView(R.layout.lfutils_baselayout);
        setSaveState(true);
        setmName("TestActivity");

        if (savedInstanceState!=null){
            String tag = savedInstanceState.getString("currentfragmenttag");
            LFManager.getInstant().removeFrgmentOnFragmentManager(tag,fragmentManager);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            String tag = savedInstanceState.getString("currentfragmenttag");
            LFManager.getInstant().removeFrgmentOnFragmentManager(tag,fragmentManager);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("currentfragmenttag", LFFragment.getLFFragmentTag(R.id.fragment_container,fragmentName));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"===============onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"==============onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"============onResume");
        if (!LfTool.isScreenOriatationPortrait(this)) {
            fragmentName = "横屏";
        }
        LFManager.getInstant().activityOpenFragment(
                this,
                fragmentName,
                null,
                R.id.fragment_container,
                LFAnimImp.getInstans().convertAnimations(LFAnimImp.CoreAnim.fade),
                0,
                true,
                true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"==============onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"=============onStop");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.i(TAG,"============onDestroy");
    }
}
