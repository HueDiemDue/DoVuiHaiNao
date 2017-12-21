package dovui.com.dovuihainao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.models.Player;
import dovui.com.dovuihainao.sharepreference.PreferenceUtils;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 02/06/2017.
 */

public class SaveScoreDialog extends Dialog {
    private String name_player;
    private int point = 0;
    private EditText edtName;
    private TextView edtPoint;
    private Button btnOk;
    private TextView tvTitle;
    private ArrayList<Player> arrScore = new ArrayList<>();
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
    public onClick onClick;

    public SaveScoreDialog(Context context, int point, onClick onClick) {
        super(context);
        this.onClick = onClick;
//        this.name_player = name_player;
        this.point = point;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_score_save);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        initView();
        initAction();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        edtName = (EditText) findViewById(R.id.EdtNamePlayer);
        edtPoint = (TextView) findViewById(R.id.EdtNamePoint);
        btnOk = (Button) findViewById(R.id.btnOk);
        edtPoint.setText(point + "");
        edtName.setTypeface(typeface);
        edtPoint.setTypeface(typeface);
        tvTitle.setTypeface(typeface);
        btnOk.setTypeface(typeface);
        arrScore.add(new Player(PreferenceUtils.getStringFromPreference(names[0], getContext()),
                PreferenceUtils.getIntFromPreference(scores[0], getContext())));
        arrScore.add(new Player(PreferenceUtils.getStringFromPreference(names[1], getContext()),
                PreferenceUtils.getIntFromPreference(scores[1], getContext())));
        arrScore.add(new Player(PreferenceUtils.getStringFromPreference(names[2], getContext()),
                PreferenceUtils.getIntFromPreference(scores[2], getContext())));
        arrScore.add(new Player(PreferenceUtils.getStringFromPreference(names[3], getContext()),
                PreferenceUtils.getIntFromPreference(scores[3], getContext())));
        arrScore.add(new Player(PreferenceUtils.getStringFromPreference(names[4], getContext()),
                PreferenceUtils.getIntFromPreference(scores[4], getContext())));
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    btnOk.setEnabled(false);
                } else {
                    btnOk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initAction() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickOk();
                if (edtName.equals("")) {
                    edtName.forceLayout();

                } else {
                    String name = edtName.getText().toString();
                    Player player = new Player(name, point);
                    updateList(arrScore, player);
                    saveResultPreference(arrScore);
                }
                dismiss();
            }
        });
    }

    private void updateList(ArrayList<Player> list, Player object) {
        int position = 5;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (object.getPoint() > list.get(i).getPoint()) {
                position = i;
            }
        }
        list.add(position, object);

    }

    private void saveResultPreference(ArrayList<Player> list) {
        String s = "";
        for (int i = 0; i <= 4; i++) {
            s += "(" + list.get(i).getNamePlayer() + "," + list.get(i).getPoint() + ")";
            PreferenceUtils.save(names[i], list.get(i).getNamePlayer(), getContext());
            PreferenceUtils.save(scores[i], list.get(i).getPoint(), getContext());
        }
    }

    public interface onClick {
        void onClickOk();
    }
}
