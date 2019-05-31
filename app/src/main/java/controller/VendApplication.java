package controller;


import com.tcn.latticelpstkboard.control.TcnShareUseData;
import com.tcn.latticelpstkboard.control.TcnVendApplication;

/**
 * 描述：
 * 作者：Jiancheng,Song on 2016/5/31 15:53
 * 邮箱：m68013@qq.com
 */
public class VendApplication extends TcnVendApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        TcnShareUseData.getInstance().setBoardSerPortFirst("/dev/ttyHSL1");    //此处主板串口接安卓哪个串口，就填哪个串口
//        TcnShareUseData.getInstance().setSerPortGroupMapFirst("2");    //设置主柜组号，默认就是1
//        TcnShareUseData.getInstance().setBoardSerPortFirst("/dev/ttymxc1");
        //VendIF 这个文件里面 TcnComDef.COMMAND_SLOTNO_INFO这个消息，是上报货道信息的消息,每次重启程序都会查询一次所有的货道信息

        /****************  如果接有副柜  则需要如下设置  **********************/
//        TcnShareUseData.getInstance().setSerPortGroupMapFirst("0");    //设置主柜组号，也可不设置，默认就是0
//        TcnShareUseData.getInstance().setSerPortGroupMapSecond("0");   //设置副柜组号为0,副柜需要接安卓另外一个串口
//        TcnShareUseData.getInstance().setBoardTypeSecond(TcnConstant.DEVICE_CONTROL_TYPE[1]);   //设置副柜类型为升降机
//        TcnShareUseData.getInstance().setBoardSerPortSecond("/dev/ttyS1");    //设置副柜串口，副柜接安卓哪个串口，就填哪个串口

        //先运行程序之后，请将TcnKey目录的tcn_sdk_device_id.txt文件发给我们，授权才能使用，每台机器都必须先授权。

                TcnShareUseData.getInstance().setBoardSerPortFirst("/dev/ttyS1");    //此处主板串口接安卓哪个串口，就填哪个串口
//        TcnShareUseData.getInstance().setSerPortGroupMapFirst("1");    //设置主柜组号，默认就是1



        /****************  锁机限制  **********************/
		
		//TcnShareUseData.getInstance().setShipContinFailCount(0);  当前连续出货失败次数
       // TcnShareUseData.getInstance().getShipContinFailCount()；
//
      //  TcnShareUseData.getInstance().setShipFailCountLock(Integer.valueOf(str[which]).intValue());  //设置为9就是取消锁机限制，不锁机
       // TcnShareUseData.getInstance().getShipFailCountLock();连续多少次出货失败锁机

        /*******************************      故障代码表见 VendIF  这个文件 **************************************/

    }
}
