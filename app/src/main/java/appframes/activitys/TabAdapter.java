package appframes.activitys;

import com.lzping.lfutils.core.actyfrg.LFActivity;
import com.lzping.lfutils.cusview.LFFragmentPagerAdpter;

/**
 * Created by user on 2017/1/16.
 */

public class TabAdapter extends LFFragmentPagerAdpter {

//    public static final String[] TITLES = new String[] { "业界", "移动", "研发", "程序员杂志", "云计算" };
//    public final String[] TITLES = new String[] { "内容显示","标题栏" };//,"错误显示"};
    public final String[] TITLES = new String[] { "横屏","竖屏" };//,"错误显示"};
    public TabAdapter(LFActivity activity)
    {
        init(activity);

    }

    @Override
    public String getFragmentName(int position) {
        return TITLES[position];
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return TITLES[position];
    }

    @Override
    public int getCount()
    {
        return TITLES.length;
    }
}
