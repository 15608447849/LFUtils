package appframes.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.viewpagerindicator.TabPageIndicator;

public class TabContentActivity extends LFActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmName("TabContentActivity");
        setContentView(R.layout.activity_tab_content);
        initView();
    }

    private TabPageIndicator mIndicator ;
    private ViewPager mViewPager ;
    private TabAdapter mAdapter ;
    private void initView() {
        mIndicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        mViewPager = (ViewPager) findViewById(R.id.tab_content_pager);
        mAdapter = new TabAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    protected void onDestroy() {
        if (mAdapter!=null){
            mAdapter.uninit();
        }
        super.onDestroy();
    }
}
