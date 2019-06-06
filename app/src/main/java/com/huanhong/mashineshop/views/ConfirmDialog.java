package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huanhong.mashineshop.R;

/**
 * Created by 坎坎.
 * Date: 2019/6/6
 * Time: 11:32
 * describe:
 */
public class ConfirmDialog extends Dialog {
    public ConfirmDialog(Context context,String content) {
        super(context,R.style.app_dialog);
        init(content);
    }

    private TextView tv_content;
    private void init(String content) {
        setContentView(R.layout.dialog_confirm);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(content);


        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
