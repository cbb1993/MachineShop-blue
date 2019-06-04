package com.huanhong.mashineshop.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huanhong.mashineshop.R;
import com.tcn.latticelpstkboard.control.TcnVendIF;

/**
 * Created by 坎坎.
 * Date: 2019/6/4
 * Time: 13:33
 * describe:
 */
public class ChooseDialog extends Dialog {

    public ChooseDialog(Context context) {
        super(context);
        init();
    }

    private TextView tv_open, tv_finish;

    private void init() {
        setContentView(R.layout.dialog_choose);
        setCanceledOnTouchOutside(false);

        tv_open = findViewById(R.id.tv_open);
        tv_finish = findViewById(R.id.tv_finish);

        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TcnVendIF.getInstance().reqWriteDataShipTest(1, 64);
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
