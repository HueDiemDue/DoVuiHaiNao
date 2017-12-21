package dovui.com.dovuihainao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.models.Player;
import dovui.com.dovuihainao.sharepreference.PreferenceUtils;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 03/06/2017.
 */

public class ScorePlayerDialog extends Dialog {
    private TextView tvScore1, tvScore2, tvScore3, tvScore4, tvScore5;
    private TextView tvName1, tvName2, tvName3, tvName4, tvName5, tvTitle;
    private ImageView imgBack;
    private ArrayList<Player> arrScore = new ArrayList<>();
    public onBackImage onBackImage;
    private Typeface typeface;
    public static String[] names = {
            "name1",
            "name2",
            "name3",
            "name4",
            "name5"
    };
    public static String[] scores = {
            "score1",
            "score2",
            "score3",
            "score4",
            "score5"
    };

    public ScorePlayerDialog(Context context, onBackImage onBackImage) {
        super(context);
        this.onBackImage = onBackImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_score_player);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        initView();
        initData();
        initAction();
    }

    private void initView() {
        tvScore1 = (TextView) findViewById(R.id.tvScore1);
        tvScore2 = (TextView) findViewById(R.id.tvScore2);
        tvScore3 = (TextView) findViewById(R.id.tvScore3);
        tvScore4 = (TextView) findViewById(R.id.tvScore4);
        tvScore5 = (TextView) findViewById(R.id.tvScore5);

        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvName2 = (TextView) findViewById(R.id.tvName2);
        tvName3 = (TextView) findViewById(R.id.tvName3);
        tvName4 = (TextView) findViewById(R.id.tvName4);
        tvName5 = (TextView) findViewById(R.id.tvName5);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        setType();

    }

    private void setType() {
        tvName1.setTypeface(typeface);
        tvName2.setTypeface(typeface);
        tvName3.setTypeface(typeface);
        tvName4.setTypeface(typeface);
        tvName5.setTypeface(typeface);
        tvScore1.setTypeface(typeface);
        tvScore2.setTypeface(typeface);
        tvScore3.setTypeface(typeface);
        tvScore4.setTypeface(typeface);
        tvScore5.setTypeface(typeface);
        tvTitle.setTypeface(typeface);
    }

    private void initData() {
        for (int i = 0; i <= 4; i++) {
            String name = PreferenceUtils.getStringFromPreference(names[i], getContext());
            int point = PreferenceUtils.getIntFromPreference(scores[i], getContext());
            Player player = new Player(name, point);
            arrScore.add(player);
        }
        if (arrScore.get(0).getPoint() != 0) {
            tvScore1.setText(arrScore.get(0).getNamePlayer() + "                        " + arrScore.get(0).getPoint() + "");
        }
        if (arrScore.get(1).getPoint() != 0) {
            tvScore2.setText(arrScore.get(1).getNamePlayer() + "                        " + arrScore.get(1).getPoint() + "");
        }
        if (arrScore.get(2).getPoint() != 0) {
            tvScore3.setText(arrScore.get(2).getNamePlayer() + "                        " + arrScore.get(2).getPoint() + "");
        }
        if (arrScore.get(3).getPoint() != 0) {
            tvScore4.setText(arrScore.get(3).getNamePlayer() + "                        " + arrScore.get(3).getPoint() + "");
        }
        if (arrScore.get(4).getPoint() != 0) {
            tvScore5.setText(arrScore.get(4).getNamePlayer() + "                        " + arrScore.get(4).getPoint() + "");
        }


    }

    private void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackImage.onBack();
                dismiss();
            }

        });
    }

    public interface onBackImage {
        void onBack();
    }
}
