package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.huanhong.mashineshop.R;
import com.huanhong.mashineshop.utils.SharedPreferencesUtils;

/**
 * Created by 坎坎.
 * Date: 2019/6/4
 * Time: 13:33
 * describe:
 */
public class DeviceNoDialog extends Dialog {

    public DeviceNoDialog(Context context) {
        super(context, R.style.app_dialog);
        init();
    }

    private TextView tv_cancel, tv_confirm;
    private EditText et_password;

    private void init() {
        setContentView(R.layout.dialog_device_no);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
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
                if (et_password.length() > 0) {
                    if(SharedPreferencesUtils.readData("device_no")!=null&& !SharedPreferencesUtils.readData("device_no").equals("")){
                        PushServiceFactory.getCloudPushService().removeAlias(SharedPreferencesUtils.readData("device_no"), new CommonCallback() {
                            @Override
                            public void onSuccess(String s) {
                            }
                            @Override
                            public void onFailed(String s, String s1) {
                            }
                        });
                    }
                    SharedPreferencesUtils.addData("device_no", et_password.getText().toString());
                    PushServiceFactory.getCloudPushService().addAlias(et_password.getText().toString(), new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                        }
                    });
                    dismiss();
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
