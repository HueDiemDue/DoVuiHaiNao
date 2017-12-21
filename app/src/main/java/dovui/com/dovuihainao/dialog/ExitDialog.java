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
 * Created by hue on 05/06/2017.
 */

public class ExitDialog extends Dialog {
    private TextView tvTitle;
    private Button btnOk,btnCancel;
    private Typeface typeface;
    public onClick onClick;
    public ExitDialog( Context context,onClick onClick) {
        super(context);
        this.onClick = onClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        typeface = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT);
        initView();
        initAction();
    }
    private void initView(){
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);
        tvTitle.setTypeface(typeface);
        tvTitle.setText("BẠN MUỐN THOÁT ỨNG DỤNG?");

        btnOk.setTypeface(typeface);
        btnCancel.setTypeface(typeface);
    }
    private void initAction(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickOk();
//                dismiss();

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
