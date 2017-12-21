package dovui.com.dovuihainao.application;

import android.app.Application;

import com.mz.ZAndroidSystemDK;

/**
 * Created by hue on 06/06/2017.
 */

public class DoVuiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSystemDK.initApplication(this, getPackageName());
    }
}
