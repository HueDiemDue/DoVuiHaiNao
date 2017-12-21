package dovui.com.dovuihainao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.mz.A;
import com.mz.ZAndroidSystemDK;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.dialog.ExitDialog;
import dovui.com.dovuihainao.dialog.ScorePlayerDialog;
import dovui.com.dovuihainao.dialog.SettingsDialog;
import dovui.com.dovuihainao.service.MyService;
import dovui.com.dovuihainao.sharepreference.PreferenceUtils;
import dovui.com.dovuihainao.utils.Constants;
import tyrantgit.explosionfield.ExplosionField;

import static android.view.animation.AnimationUtils.loadAnimation;

public class MainActivity extends AppCompatActivity {
    private ImageView imgPlaying, imgScore, imgExit, imgSetting;
    ExplosionField explosionField;
    private Animation animation,animation_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = loadAnimation(MainActivity.this, R.anim.translate);
        animation_left = loadAnimation(MainActivity.this, R.anim.translate_left);
        boolean b = PreferenceUtils.getBooleanFromPreference(Constants.Intents.SWITCH_VOLUME, this);
        if (b) {
            startService(new Intent(MainActivity.this, MyService.class));
            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
        } else {
            sendBroadcast(new Intent(Constants.Intents.SWITCH_BAR_OFF));
            stopService(new Intent(MainActivity.this, MyService.class));
        }
        initView();
        initAction();
        ZAndroidSystemDK.init(this);
        A.f(this);

    }

    private void initView() {
        imgPlaying = (ImageView) findViewById(R.id.imgPlaying);
        imgScore = (ImageView) findViewById(R.id.imgScore);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        explosionField = ExplosionField.attach2Window(MainActivity.this);
        fadeIn();
    }

    private void fadeIn() {
        imgPlaying.setAnimation(animation);
        imgScore.setAnimation(animation_left);
        imgExit.setAnimation(animation);
    }

    private void initAction() {
        imgPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));


            }
        });
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitDialog exitDialog = new ExitDialog(MainActivity.this, new ExitDialog.onClick() {
                    @Override
                    public void onClickOk() {
//                        onBackPressed();
                        finish();
                    }

                    @Override
                    public void onClickCancel() {

                    }
                });
                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                exitDialog.show();
            }
        });
        imgScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScorePlayerDialog scorePlayer = new ScorePlayerDialog(MainActivity.this, new ScorePlayerDialog.onBackImage() {
                    @Override
                    public void onBack() {

                    }
                });
                scorePlayer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                scorePlayer.show();
//                scorePlayer.setCancelable(false);
//                scorePlayer.setCanceledOnTouchOutside(false);
            }
        });
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SettingsDialog settingsDialog = new SettingsDialog(MainActivity.this, new SettingsDialog.onClickImageBack() {
                    @Override
                    public void onClickBack() {

                    }

                    @Override
                    public void onClickOffVolume() {
                        sendBroadcast(new Intent(Constants.Intents.SWITCH_BAR_OFF));
                    }

                    @Override
                    public void onResumeVolume() {
                        startService(new Intent(MainActivity.this, MyService.class));
                        sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                    }
                });
                settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                settingsDialog.show();
//                settingsDialog.setCancelable(false);
//                settingsDialog.setCanceledOnTouchOutside(false);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        sendBroadcast(new Intent(Constants.Intents.PAUSE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(Constants.Intents.CONTINUE));
    }

    @Override
    public void onBackPressed() {

        ExitDialog exitDialog = new ExitDialog(MainActivity.this, new ExitDialog.onClick() {
            @Override
            public void onClickOk() {
                finish();
            }

            @Override
            public void onClickCancel() {

            }
        });
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }
}
