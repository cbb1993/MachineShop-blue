package com.huanhong.mashineshop.net.rx;


import com.base.baselib.utils.NetWorkUtil;
import com.google.gson.JsonParseException;
import com.huanhong.mashineshop.AppApplication;
import com.huanhong.mashineshop.net.ServerException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;


public abstract class RxSubscriber<T> extends DisposableObserver<T> {


    public RxSubscriber() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
        if (!NetWorkUtil.isNetworkAvailable(AppApplication.instance())) {
            onNoNetWork();
            return;
        }
    }

    @Override
    public void onComplete() {

    }

    protected void showLoading() {

    }

    protected void onNoNetWork() {

    }

    @Override
    public void onError(Throwable e) {
        String message = null;
        if (e instanceof UnknownHostException) {
            message = "没有网络";
        } else if (e instanceof HttpException) {
            message = "网络错误";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            message = "解析错误";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof ServerException) {
            message = ((ServerException) e).message;
        }
        onFailure(message);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * success
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * failure
     *
     * @param msg
     */
    public abstract void onFailure(String msg);
}
