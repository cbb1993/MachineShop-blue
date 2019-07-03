package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huanhong.mashineshop.R;
import com.huanhong.mashineshop.activity.GoodsNumberActivity;
import com.tcn.latticelpstkboard.control.TcnVendIF;

/**
 * Created by 坎坎.
 * Date: 2019/6/4
 * Time: 13:33
 * describe:
 */
public class ChooseDialog extends Dialog {

    public ChooseDialog(Context context) {
        super(context,R.style.app_dialog);
        init();
    }

    private TextView tv_open, tv_finish;

    private void init() {
        setContentView(R.layout.dialog_choose);
        setCanceledOnTouchOutside(true);
        if (getWindow() != null) {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
        tv_open = findViewById(R.id.tv_open);
        tv_finish = findViewById(R.id.tv_finish);

        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), GoodsNumberActivity.class).putExtra("open",true));
                dismiss();
            }
        });

        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

}
