package controller;

import android.os.Message;
import android.util.Log;

import com.tcn.latticelpstkboard.control.MsgTrade;
import com.tcn.latticelpstkboard.control.TcnComDef;
import com.tcn.latticelpstkboard.control.TcnComResultDef;
import com.tcn.latticelpstkboard.control.TcnVendIF;


/**
 * Created by Administrator on 2016/6/30.
 */
public class VendIF {
    private static final String TAG = "VendIF";
    private static VendIF m_Instance = null;

    /**************************  故障代码表 ****************************
     public static final int ERROR_CODE_0 = 0;    //正常
    public static final int ERR_CODE_255            = 255;   //货道不存在

    ********************************************************************************/


    public static synchronized VendIF getInstance() {
        if (null == m_Instance) {
            m_Instance = new VendIF();
        }
        return m_Instance;
    }

    public void initialize() {
        registerListener ();
    }


    public void deInitialize() {
        unregisterListener();
    }

    public void registerListener () {
        TcnVendIF.getInstance().setOnCommunicationListener(m_CommunicationListener);
    }

    public void unregisterListener() {
        TcnVendIF.getInstance().setOnCommunicationListener(null);
    }

    private void OnSelectedSlotNo(int slotNo) {
        TcnVendIF.getInstance().reqSelectSlotNoKouhong(slotNo);
    }

    //驱动板上报过来的数据 slotNo:货道号     status:0 货道状态正常   255：货道号不存在（检测不到该货道）
    public void OnUploadSlotNoInfo(boolean finish, int slotNo, int status) {

    }

    //驱动板上报过来的数据 slotNo:货道号     status:0 货道状态正常   255：货道号不存在（检测不到该货道）
    public void OnUploadSlotNoInfoSingle(boolean finish, int slotNo, int status) {
        Log.i(TAG, "OnUploadSlotNoInfoSingle finish: " + finish + " slotNo: " + slotNo + " status: " + status);
    }

    //出货状态返回    slotNo： 货道号    shipStatus： 出货状态    status: 货道状态正常    支付订单号（出货接口传入，原样返回） amount：支付金额（出货接口传入，原样返回）
    private void OnShipWithMethod(int slotNo, int shipStatus,int errCode, String tradeNo, String amount) {
        Log.i(TAG, "OnShipWithMethod slotNo: " + slotNo + " shipStatus: " + shipStatus+" errCode: "+errCode
                + " tradeNo: " + tradeNo+" amount: "+amount);

        if (TcnComResultDef.SHIP_SHIPING == shipStatus) {   //出货中

        } else if (TcnComResultDef.SHIP_SUCCESS == shipStatus) {   //出货成功

        } else if (TcnComResultDef.SHIP_FAIL == shipStatus) {    //出货失败

        } else {

        }
    }

    private void OnDoorSwitch(int door) {

    }

    private void OnSelectedGoods(int slotNoOrKey, String price) {

    }

    private void OnShipForTestSlot(int slotNo, int errCode, int shipStatus) {
        Log.i(TAG, "OnShipForTestSlot slotNo: " + slotNo + " errCode: " + errCode + " shipStatus: " + shipStatus);
    }

//    private void OnUploadGoodsInfo(int slotNo, int finish, Coil_info slotInfo) {
//
//    }

    /*
     * 此处监听底层发过来的数据，此处接收数据位于线程内
     */
    private VendCommunicationListener m_CommunicationListener = new VendCommunicationListener();
    private class VendCommunicationListener implements TcnVendIF.CommunicationListener {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TcnComDef.COMMAND_SELECT_SLOTNO:
                    OnSelectedSlotNo(msg.arg1);
                    break;
                case TcnComDef.COMMAND_SLOTNO_INFO:
                    OnUploadSlotNoInfo((boolean) msg.obj, msg.arg1, msg.arg2);
                    break;
                case TcnComDef.COMMAND_SLOTNO_INFO_SINGLE:
                    OnUploadSlotNoInfoSingle((boolean) msg.obj, msg.arg1, msg.arg2);
                    break;
                case TcnComDef.COMMAND_SHIPMENT_CASHPAY:
	                MsgTrade mMsgToSendcash = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2,mMsgToSendcash.getErrCode(), mMsgToSendcash.getTradeNo(),mMsgToSendcash.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_WECHATPAY:
	                MsgTrade mMsgToSendWx = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendWx.getErrCode(),mMsgToSendWx.getTradeNo(),mMsgToSendWx.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_ALIPAY:
	                MsgTrade mMsgToSendAli = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendAli.getErrCode(),mMsgToSendAli.getTradeNo(),mMsgToSendAli.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_GIFTS:
                    MsgTrade mMsgToSendGifts = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendGifts.getErrCode(),mMsgToSendGifts.getTradeNo(),mMsgToSendGifts.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_REMOTE:
                    MsgTrade mMsgToSendRemote = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendRemote.getErrCode(),mMsgToSendRemote.getTradeNo(),mMsgToSendRemote.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_VERIFY:
                    MsgTrade mMsgToSendVerfy = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendVerfy.getErrCode(),mMsgToSendVerfy.getTradeNo(),mMsgToSendVerfy.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_BANKCARD_ONE:
                    MsgTrade mMsgToSendBankcard = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2,mMsgToSendBankcard.getErrCode(), mMsgToSendBankcard.getTradeNo(),mMsgToSendBankcard.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_BANKCARD_TWO:
                    MsgTrade mMsgToSendBankcardTwo = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendBankcardTwo.getErrCode(),mMsgToSendBankcardTwo.getTradeNo(),mMsgToSendBankcardTwo.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_TCNCARD_OFFLINE:
                    MsgTrade mMsgToSendBankcardOffLine = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendBankcardOffLine.getErrCode(),mMsgToSendBankcardOffLine.getTradeNo(),mMsgToSendBankcardOffLine.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_TCNCARD_ONLINE:
                    MsgTrade mMsgToSendBankcardOnLine = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendBankcardOnLine.getErrCode(),mMsgToSendBankcardOnLine.getTradeNo(),mMsgToSendBankcardOnLine.getAmount());
                    break;
                case TcnComDef.COMMAND_SHIPMENT_OTHER_PAY:
                    MsgTrade mMsgToSendBankcardPay = (MsgTrade) msg.obj;
                    OnShipWithMethod(msg.arg1, msg.arg2, mMsgToSendBankcardPay.getErrCode(),mMsgToSendBankcardPay.getTradeNo(),mMsgToSendBankcardPay.getAmount());
                    break;
                case TcnComDef.CMD_TEST_SLOT:
                    OnShipForTestSlot(msg.arg1, msg.arg2, (Integer) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
