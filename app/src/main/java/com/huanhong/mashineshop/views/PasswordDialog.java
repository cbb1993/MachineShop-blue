package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huanhong.mashineshop.R;

/**
 * Created by 坎坎.
 * Date: 2019/6/4
 * Time: 13:33
 * describe:
 */
public class PasswordDialog extends Dialog {
    private static String password = "123";

    public PasswordDialog(Context context) {
        super(context);
        init();
    }

    private TextView tv_cancel, tv_confirm;
    private EditText et_password;

    private void init() {
        setContentView(R.layout.dialog_password);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
        et_password = findViewById(R.id.et_password);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setText("");
                dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.equals(getPassword())) {
                    dismiss();
                    new ChooseDialog(getContext()).show();
                } else {
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private String getPassword() {
        if (et_password.length() > 0) {
            return et_password.getText().toString();
        }
        return null;
    }
}
