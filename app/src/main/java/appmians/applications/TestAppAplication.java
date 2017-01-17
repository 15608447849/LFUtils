package appmians.applications;

import android.app.Application;

import com.lzping.lfutils.core.manage.LFFatory;

/**
 * Created by user on 2017/1/11.
 */

public class TestAppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LFFatory.getInstant().init(getApplicationContext());
    }
}
