package dovui.com.dovuihainao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 07/06/2017.
 */

public class ContinueDialog extends Dialog {

    private TextView tvTitle;
    private Button btnOk,btnCancel;
    private Typeface typeface;
    public onClick onClick;
    private int stupid = 0,score = 0;
    public ContinueDialog(Context context,int score,int stupid,onClick onClick) {
        super(context);
        this.onClick = onClick;
        this.score = score;
        this.stupid = stupid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_continue);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        initView();
        initAction();
    }
    private void initView(){
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);

        if(stupid>=3 ) {
            if(score <100){
                tvTitle.setText("Cay quá! Chiến tiếp đi?");
            }
            else {
                tvTitle.setText("Chúc mừng tân bá chủ!");
            }

        }
        tvTitle.setTypeface(typeface);
        btnOk.setTypeface(typeface);
        btnCancel.setTypeface(typeface);
    }
    private void initAction(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickOk();
                dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickCancel();
                dismiss();
            }
        });
    }
    public interface onClick{
        void onClickOk();
        void onClickCancel();
    }
}
