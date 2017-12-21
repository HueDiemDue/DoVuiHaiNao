package dovui.com.dovuihainao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.sharepreference.PreferenceUtils;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 03/06/2017.
 */

public class SettingsDialog extends Dialog {
    private static final String EXTRA_SAVE = "extra_save";
    private static final String KEY_SAVE = "key_save";
    private SeekBar skBarVolume;
    private Switch swVolume;
    private TextView tvTitle,tvVolume,tvChangeVolume;
    private ImageView imgBack;
    private AudioManager audioManager = null;
    public onClickImageBack onClick;
    private Typeface typeface;

    public SettingsDialog(Context context, onClickImageBack onClickImage) {
        super(context);
        this.onClick = onClickImage;
//        this.onClick = onclick;
    }

    public void setOnClick(onClickImageBack onClick) {
        this.onClick = onClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        setContentView(R.layout.dialog_music);
        initView();
        initControls();
        initAction();
    }

    private void initView() {
        skBarVolume = (SeekBar) findViewById(R.id.skBarVolume);
        swVolume = (Switch) findViewById(R.id.swVolume);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        swVolume.setChecked(PreferenceUtils.getBooleanFromPreference(Constants.Intents.SWITCH_VOLUME,getContext()));
        tvChangeVolume = (TextView) findViewById(R.id.tvChangeVolume);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvVolume = (TextView) findViewById(R.id.tvVolume);
        typeFace();
    }
    public void typeFace() {
        tvChangeVolume.setTypeface(typeface);
        tvVolume.setTypeface(typeface);
        tvTitle.setTypeface(typeface);
    }

    private void initControls() {
        try {
            audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            skBarVolume.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            skBarVolume.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));

            skBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAction() {
        swVolume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    onClick.onClickOffVolume();
                }
                else {
                    onClick.onResumeVolume();
                }
                PreferenceUtils.save(Constants.Intents.SWITCH_VOLUME,isChecked,getContext());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 onClick.onClickBack();
                 dismiss();
            }
        });
    }

    public interface onClickImageBack {
        void onClickBack();
        void onClickOffVolume();
        void onResumeVolume();
    }
}
