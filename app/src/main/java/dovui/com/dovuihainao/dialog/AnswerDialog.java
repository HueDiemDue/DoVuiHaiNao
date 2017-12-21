package dovui.com.dovuihainao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 01/06/2017.
 */

public class AnswerDialog extends Dialog {
    private TextView tvCmt, tvScore, tvFail, tvStupid;
    private ImageView imgBack;
    private boolean cmt = false;
    private int answer = 0;
    private int stupid = 0, score = 0;
    public onClickImage onClickImage;
    private String[] answer_true = {
            "Bố sợ mày quá!",
            "Tại tao cho qua thôi",
            "Dễ thế ếu qua thì thôi",
            "Trả lời là biết đẹp zai rồi",
            "Nhìn gì, chơi tiếp đi",
            "Tao cũng thấy dễ mày ạ ^^"
    };
    private String[] answer_false = {
            "Dễ thế à mà thôi",
            "Tưởng thế nào",
            "Rất tiếc, em đã quá ngu để đi tiếp",
            "Chúc huynh đệ may mắn lần sau",
            "Anh biết chú đã rất cố gắng",
            "Em đã sai dồi...."
    };
    private Typeface typeface;

    //    private Context context;
    public AnswerDialog(Context context, boolean cmt, int stupid, int score, int answer, onClickImage onClickImage) {
        super(context);
        this.cmt = cmt;
        this.stupid = stupid;
        this.score = score;
        this.answer = answer;
        this.onClickImage = onClickImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_answer);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        initView();
        initAction();
    }

    private void setType() {
        tvCmt.setTypeface(typeface);
        tvScore.setTypeface(typeface);
        tvStupid.setTypeface(typeface);

    }

    private void initView() {
        tvCmt = (TextView) findViewById(R.id.tvCmt);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvStupid = (TextView) findViewById(R.id.tvStupid);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        setType();
        if (cmt) {
            Random r = new Random();
            int i = r.nextInt(answer_true.length);
            tvCmt.setText(answer_true[i]);
        } else {
            Random r = new Random();
            int i = r.nextInt(answer_false.length);
            tvCmt.setText(answer_false[i]);
        }
        if (stupid > 3) {
            stupid = 3;

        }
        tvScore.setText("ĐIỂM:     "+ score);
        if (stupid == 0) {
            tvStupid.setText("Bạn vẫn bảo toàn được 3 lượt ngu ^^");
        } else if(stupid<3){
            tvStupid.setText("Bạn đã mất " + stupid + " lượt ngu");
        }else if(stupid==3){
            tvStupid.setText("Bạn đã hết lượt ngu");
        }

    }

    private void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage.onClickBack();
                dismiss();

            }
        });

    }

    public interface onClickImage {
        void onClickBack();
    }


}
