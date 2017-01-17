package appmians.fragments.sharedlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzping.lfutils.R;
import com.lzping.lfutils.core.actyfrg.LFFragment;

/**
 * Created by user on 2017/1/16.
 */

public class ErrorPage extends LFFragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (imageView==null){
            imageView = new ImageView(mActivity);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.error_image);
        }
        return imageView;
    }
}
