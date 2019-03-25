package app.xtoolwallpaper.com.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import app.xtoolwallpaper.com.myapplication.R;

public class AlertDialog extends Dialog implements android.view.View.OnClickListener {
    private Context context;
    private String title;
    private String message;
    private String strPositive;
    private String strNegative;
    private OnDialogButtonClickListener listener;
    private boolean cancelable = true;
    private TextView tvTitle;
    private TextView tvMessage;
    private TextView btnPositive;
    private TextView btnNegative;
    private View viewDeliver;

    /**
     * @param context
     * @param title
     * @param message
     * @param strPositive "确定"类按钮文本
     * @param strNegative "取消"类按钮文本
     * @param listener
     */
    public AlertDialog(Context context, String title, String message,
                       String strPositive, String strNegative, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.strPositive = strPositive;
        this.strNegative = strNegative;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        //alertDialog是否可以点击外围消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        tvTitle = findViewById(R.id.tv_dialog_title);
        tvMessage = findViewById(R.id.tvAlertDialogMessage);
        btnPositive = findViewById(R.id.tv_dialog_positive);
        btnNegative = findViewById(R.id.tv_dialog_negative);
        viewDeliver = findViewById(R.id.view_deliver);

        //如果title为空,则隐藏alertDialog标题
        tvTitle.setVisibility(StringUtil.isNotEmpty(title, true) ? View.VISIBLE : View.GONE);
        tvTitle.setText("" + StringUtil.getCurrentString());

        if (StringUtil.isNotEmpty(strPositive, true)) {
            btnPositive.setText(StringUtil.getCurrentString());
            btnPositive.setOnClickListener(this);
        } else {
            btnPositive.setVisibility(View.GONE);
            viewDeliver.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmpty(strNegative, true)) {
            btnNegative.setText(StringUtil.getCurrentString());
            btnNegative.setOnClickListener(this);
        } else {
            btnNegative.setVisibility(View.GONE);
            viewDeliver.setVisibility(View.GONE);
        }

        tvMessage.setText(StringUtil.getTrimedString(message));

        final WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.tv_dialog_positive) {
            listener.onDialogButtonClick(true);
        } else if (v.getId() == R.id.tv_dialog_negative) {
            listener.onDialogButtonClick(false);
        }
        dismiss();
    }


    /**
     * 自定义Dialog监听器
     */
    public interface OnDialogButtonClickListener {

        /**
         * 点击按钮事件的回调方法
         *
         * @param isPositive
         */
        void onDialogButtonClick(boolean isPositive);
    }


}
