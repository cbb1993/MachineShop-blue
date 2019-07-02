package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huanhong.mashineshop.R;

/**
 * Created by 坎坎.
 * Date: 2019/6/6
 * Time: 11:32
 * describe:
 */
public class ConfirmDialog extends Dialog {
    public ConfirmDialog(Context context,int content,int confirm ,ConfirmCallback callback) {
        super(context,R.style.app_dialog);
        init(content,confirm,callback);
    }

    private ImageView iv_content,iv_confirm;
    private void init(int content, int confirm , final ConfirmCallback callback) {
        setContentView(R.layout.dialog_confirm);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
        iv_content = findViewById(R.id.iv_content);
        iv_confirm = findViewById(R.id.iv_confirm);
        iv_content.setImageResource(content);
        iv_confirm.setImageResource(confirm);

        findViewById(R.id.rl_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(callback!=null){
                    callback.confirm();
                }
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public interface ConfirmCallback{
        void confirm();
    }
}
