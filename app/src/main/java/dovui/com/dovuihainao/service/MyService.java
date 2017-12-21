package dovui.com.dovuihainao.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 03/06/2017.
 */

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    private Context context;
    private IntentFilter filter;
    private int currention = 0;

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        context = getApplicationContext();
        filter = new IntentFilter(Constants.Intents.CLICK_TRUE);
        filter.addAction(Constants.Intents.EXIT);
        filter.addAction(Constants.Intents.SWITCH_BAR_OFF);
        filter.addAction(Constants.Intents.PAUSE);
        filter.addAction(Constants.Intents.CONTINUE);
        filter.addAction(Constants.Intents.OVER_GAME);
        filter.addAction(Constants.Intents.GAME_PLAY);
        filter.addAction(Constants.Intents.CLICK_TRUE);
        context.registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(context, R.raw.funny);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
        return START_NOT_STICKY;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.Intents.SWITCH_BAR_OFF:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        currention = mediaPlayer.getCurrentPosition();
                    }
                    break;

                case Constants.Intents.PAUSE:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        currention = mediaPlayer.getCurrentPosition();
                    }
                    break;

                case Constants.Intents.OVER_GAME:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer = MediaPlayer.create(context, R.raw.game_over);
                    mediaPlayer.setLooping(true); // Set looping
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    break;

                case Constants.Intents.CONTINUE:
                    if(currention!=0) {
                        mediaPlayer.seekTo(currention);
                        mediaPlayer.start();
                    }
                    else {
                        mediaPlayer = MediaPlayer.create(context, R.raw.funny);
                        mediaPlayer.setLooping(true); // Set looping
                        mediaPlayer.setVolume(100, 100);
                        mediaPlayer.start();
                    }
                    break;

                case Constants.Intents.GAME_PLAY:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer = MediaPlayer.create(context, R.raw.funny);
                    mediaPlayer.setLooping(true); // Set looping
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    break;

                case Constants.Intents.CLICK_TRUE:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer = MediaPlayer.create(context, R.raw.answer_true);
                    mediaPlayer.setLooping(true); // Set looping
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    break;

                default:
                    break;

            }
        }
    };

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            context.unregisterReceiver(mReceiver);
        }
        mediaPlayer.stop();
        super.onDestroy();

    }
}
