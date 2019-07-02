package com.huanhong.mashineshop.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.huanhong.mashineshop.R;

/**
 * Created by 坎坎.
 * Date: 2019/7/2
 * Time: 15:46
 * describe:
 */
public class ConfirmPopwindow  {
    public ConfirmPopwindow(Context context, View view, int content,int confirm,final ConfirmCallback confirmCallback){
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_confirm,null);
        final PopupWindow  popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
       ImageView iv_close = contentView.findViewById(R.id.iv_close);
       ImageView iv_content = contentView.findViewById(R.id.iv_content);
       ImageView iv_confirm = contentView.findViewById(R.id.iv_confirm);
       View rl_confirm = contentView.findViewById(R.id.rl_confirm);

        iv_content.setImageResource(content);
        iv_confirm.setImageResource(confirm);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        rl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(confirmCallback!=null){
                    confirmCallback.confirm();
                }
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0xc8000000));

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }

    public interface ConfirmCallback{
        void confirm();
    }
}
