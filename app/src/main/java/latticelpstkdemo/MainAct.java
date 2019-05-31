package latticelpstkdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.huanhong.mashineshop.R;
import com.tcn.latticelpstkboard.control.PayMethod;
import com.tcn.latticelpstkboard.control.TcnVendEventID;
import com.tcn.latticelpstkboard.control.TcnVendEventResultID;
import com.tcn.latticelpstkboard.control.TcnVendIF;
import com.tcn.latticelpstkboard.control.VendEventInfo;

public class MainAct extends TcnMainActivity {

	private static final String TAG = "MainAct";
	private static final int CMD_LATTICE_LIGHT  = 1;
	private static final int CMD_LATTICE_VALID_SET  = 2;


	private int singleitem=0;
	private Titlebar m_Titlebar = null;
	private Button main_serport = null;
	private ButtonEditSelectD menu_lat_query_addr = null;
	private ButtonEditSelectD menu_lat_query_slot = null;
	private ButtonEditSelectD menu_lat_select_addr = null;
	private ButtonEditSelectD menu_lat_ship_slot = null;
	private ButtonEditSelectD menu_lat_reqselect = null;
	private EditText m_lattice_open_batch_start = null;
	private EditText m_lattice_open_batch_end = null;

	private EditText m_lattice_close_batch_start = null;
	private EditText m_lattice_close_batch_end = null;

	private EditText m_scan_light_row_start = null;
	private EditText m_scan_light_row_end = null;
	private EditText m_lattice_num_set_row = null;
	private EditText m_lattice_num_set_column_start = null;
	private EditText m_lattice_num_set_column_end = null;

	private LinearLayout m_lattice_close_batch_layout = null;

	private Button m_lattice_open_batch_btn = null;
	private Button m_lattice_close_batch_btn = null;
	private Button  m_scan_light_btn = null;
	private Button  m_lattice_num_set_btn = null;
	private Button  m_kh_game_win_btn = null;
	private Button  m_kh_game_fail_btn = null;

	private CheckBox m_lattice_num_set_checkbox = null;

	private OutDialog m_OutDialog = null;
	private LoadingDialog m_LoadingDialog = null;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.background_menu_settings_layout_lattice);
		TcnVendIF.getInstance().LoggerDebug(TAG, "MainAct onCreate()");
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		TcnVendIF.getInstance().registerListener(m_vendListener);
		m_lattice_close_batch_layout.setVisibility(View.GONE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		TcnVendIF.getInstance().unregisterListener(m_vendListener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (m_Titlebar != null) {
			m_Titlebar.removeButtonListener();
			m_Titlebar = null;
		}

		if (menu_lat_query_addr != null) {
			menu_lat_query_addr.removeButtonListener();
			menu_lat_query_addr = null;
		}
		if (main_serport != null) {
			main_serport.setOnClickListener(null);
			main_serport = null;
		}
		if (menu_lat_query_slot != null) {
			menu_lat_query_slot.removeButtonListener();
			menu_lat_query_slot = null;
		}

		if (menu_lat_select_addr != null) {
			menu_lat_select_addr.removeButtonListener();
			menu_lat_select_addr = null;
		}

		if (m_lattice_open_batch_btn != null) {
			m_lattice_open_batch_btn.setOnClickListener(null);
			m_lattice_open_batch_btn = null;
		}

		if (m_lattice_close_batch_btn != null) {
			m_lattice_close_batch_btn.setOnClickListener(null);
			m_lattice_close_batch_btn = null;
		}

		if (m_scan_light_btn != null) {
			m_scan_light_btn.setOnClickListener(null);
			m_scan_light_btn = null;
		}

		if (m_lattice_num_set_btn != null) {
			m_lattice_num_set_btn.setOnClickListener(null);
			m_lattice_num_set_btn = null;
		}

		if (menu_lat_ship_slot != null) {
			menu_lat_ship_slot.removeButtonListener();
			menu_lat_ship_slot = null;
		}

		if (menu_lat_reqselect != null) {
			menu_lat_reqselect.removeButtonListener();
			menu_lat_reqselect = null;
		}

		if (m_OutDialog != null) {
			m_OutDialog.deInit();
			m_OutDialog = null;
		}
		m_lattice_close_batch_layout = null;
		m_lattice_open_batch_start = null;
		m_lattice_open_batch_end = null;
		m_lattice_close_batch_start = null;
		m_lattice_close_batch_end = null;
		m_scan_light_row_start = null;
		m_scan_light_row_end = null;
		m_lattice_num_set_row = null;
		m_lattice_num_set_column_start = null;
		m_lattice_num_set_column_end = null;
		m_lattice_num_set_checkbox = null;
		m_TitleBarListener = null;
		m_ClickListener = null;
		m_ButtonEditClickListener = null;
		m_vendListener = null;
	}

	private void initView() {

		if (m_OutDialog == null) {
			m_OutDialog = new OutDialog(MainAct.this, "", getString(R.string.background_drive_setting));
			m_OutDialog.setShowTime(10000);
		}

		m_Titlebar = (Titlebar) findViewById(R.id.menu_setttings_titlebar);
		if (m_Titlebar != null) {
			m_Titlebar.setButtonType(Titlebar.BUTTON_TYPE_BACK);
			m_Titlebar.setButtonName(R.string.background_menu_settings);
			m_Titlebar.setTitleBarListener(m_TitleBarListener);
		}

		main_serport = (Button) findViewById(R.id.main_serport);
		main_serport.setOnClickListener(m_ClickListener);

		menu_lat_ship_slot = (ButtonEditSelectD) findViewById(R.id.menu_lat_ship_slot);
		if (menu_lat_ship_slot != null) {
			menu_lat_ship_slot.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY);
			menu_lat_ship_slot.setButtonName("出货例子(同一个订单只出一次)");
			menu_lat_ship_slot.setButtonNameTextSize(TcnVendIF.getInstance().getFitScreenSize(20));
			menu_lat_ship_slot.setButtonQueryText("出货");
			menu_lat_ship_slot.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16));
			menu_lat_ship_slot.setButtonQueryTextColor("#ffffff");
			menu_lat_ship_slot.setButtonDisplayTextColor("#4e5d72");
			menu_lat_ship_slot.setInputTypeInput(InputType.TYPE_CLASS_NUMBER);
			menu_lat_ship_slot.setButtonListener(m_ButtonEditClickListener);

		}

		menu_lat_reqselect = (ButtonEditSelectD) findViewById(R.id.menu_lat_reqselect);
		if (menu_lat_reqselect != null) {
			menu_lat_reqselect.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY);
			menu_lat_reqselect.setButtonName("选择货道");
			menu_lat_reqselect.setButtonNameTextSize(TcnVendIF.getInstance().getFitScreenSize(20));
			menu_lat_reqselect.setButtonQueryText("选择");
			menu_lat_reqselect.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16));
			menu_lat_reqselect.setButtonQueryTextColor("#ffffff");
			menu_lat_reqselect.setButtonDisplayTextColor("#4e5d72");
			menu_lat_reqselect.setInputTypeInput(InputType.TYPE_CLASS_NUMBER);
			menu_lat_reqselect.setButtonListener(m_ButtonEditClickListener);

		}

		menu_lat_query_addr = (ButtonEditSelectD) findViewById(R.id.menu_lat_query_addr);
		if (menu_lat_query_addr != null) {
			menu_lat_query_addr.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY);
			menu_lat_query_addr.setButtonName(getString(R.string.background_lat_query_addr));
			menu_lat_query_addr.setButtonNameTextSize(TcnVendIF.getInstance().getFitScreenSize(20));
			menu_lat_query_addr.setButtonQueryText(getString(R.string.background_drive_query));
			menu_lat_query_addr.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16));
			menu_lat_query_addr.setButtonQueryTextColor("#ffffff");
			menu_lat_query_addr.setButtonDisplayTextColor("#4e5d72");
			menu_lat_query_addr.setInputTypeInput(InputType.TYPE_CLASS_NUMBER);
			menu_lat_query_addr.setButtonListener(m_ButtonEditClickListener);

		}

		menu_lat_query_slot = (ButtonEditSelectD) findViewById(R.id.menu_lat_query_slot);
		if (menu_lat_query_slot != null) {
			menu_lat_query_slot.setButtonType(ButtonEditSelectD.BUTTON_TYPE_EDIT_QUERY);
			menu_lat_query_slot.setButtonName(getString(R.string.background_lat_query_slot));
			menu_lat_query_slot.setButtonNameTextSize(TcnVendIF.getInstance().getFitScreenSize(20));
			menu_lat_query_slot.setButtonQueryText(getString(R.string.background_drive_query));
			menu_lat_query_slot.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16));
			menu_lat_query_slot.setButtonQueryTextColor("#ffffff");
			menu_lat_query_slot.setButtonDisplayTextColor("#4e5d72");
			menu_lat_query_slot.setInputTypeInput(InputType.TYPE_CLASS_NUMBER);
			menu_lat_query_slot.setButtonListener(m_ButtonEditClickListener);

		}

		m_lattice_open_batch_start = (EditText) findViewById(R.id.m_lattice_open_batch_start);
		m_lattice_open_batch_end = (EditText) findViewById(R.id.m_lattice_open_batch_end);

		m_lattice_open_batch_btn = (Button)findViewById(R.id.m_lattice_open_batch_btn);
		m_lattice_open_batch_btn.setOnClickListener(m_ClickListener);

		m_lattice_close_batch_layout = (LinearLayout)findViewById(R.id.m_lattice_close_batch_layout);

		m_lattice_close_batch_start = (EditText) findViewById(R.id.m_lattice_close_batch_start);
		m_lattice_close_batch_end = (EditText) findViewById(R.id.m_lattice_close_batch_end);

		m_lattice_close_batch_btn = (Button)findViewById(R.id.m_lattice_close_batch_btn);
		m_lattice_close_batch_btn.setOnClickListener(m_ClickListener);

		menu_lat_select_addr = (ButtonEditSelectD) findViewById(R.id.menu_lat_select_addr);
		if (menu_lat_select_addr != null) {
			menu_lat_select_addr.setButtonType(ButtonEditSelectD.BUTTON_TYPE_SELECT);
			menu_lat_select_addr.setButtonName(getString(R.string.background_tip_set_select_cabinet_no));
			menu_lat_select_addr.setButtonNameTextSize(TcnVendIF.getInstance().getFitScreenSize(20));
			menu_lat_select_addr.setButtonQueryText(getString(R.string.background_drive_query));
			menu_lat_select_addr.setButtonQueryTextSize(TcnVendIF.getInstance().getFitScreenSize(16));
			menu_lat_select_addr.setButtonQueryTextColor("#ffffff");
			menu_lat_select_addr.setButtonDisplayTextColor("#4e5d72");
			menu_lat_select_addr.setInputTypeInput(InputType.TYPE_CLASS_NUMBER);
			menu_lat_select_addr.setButtonListener(m_ButtonEditClickListener);
			if (UIComBack.getInstance().isMutiGrpLattice()) {
				menu_lat_select_addr.setVisibility(View.VISIBLE);
			} else {
				menu_lat_select_addr.setVisibility(View.GONE);
			}
		}


		m_scan_light_row_start = (EditText) findViewById(R.id.m_scan_light_num_start);
		m_scan_light_row_end = (EditText) findViewById(R.id.m_scan_light_num_end);
		m_scan_light_btn = (Button)findViewById(R.id.m_scan_light_btn);
		m_scan_light_btn.setOnClickListener(m_ClickListener);


		m_lattice_num_set_row = (EditText) findViewById(R.id.m_lattice_num_set_row);
		m_lattice_num_set_column_start = (EditText) findViewById(R.id.m_lattice_num_set_column_start);
		m_lattice_num_set_column_end = (EditText) findViewById(R.id.m_lattice_num_set_column_end);
		m_lattice_num_set_checkbox = (CheckBox) findViewById(R.id.m_lattice_num_set_checkbox);
		m_lattice_num_set_btn = (Button)findViewById(R.id.m_lattice_num_set_btn);
		m_lattice_num_set_btn.setOnClickListener(m_ClickListener);

		m_kh_game_win_btn = (Button)findViewById(R.id.m_kh_game_win_btn);
		m_kh_game_win_btn.setOnClickListener(m_ClickListener);
		m_kh_game_fail_btn = (Button)findViewById(R.id.m_kh_game_fail_btn);
		m_kh_game_fail_btn.setOnClickListener(m_ClickListener);
	}

	private void setDialogShow() {
		if (m_OutDialog != null) {
			m_OutDialog.setShowTime(5);
			m_OutDialog.setTitle(getString(R.string.background_tip_wait_amoment));
			m_OutDialog.show();
		}
	}

	private void showSetConfirm(final int cmdType,final String grp,final String data1,final String data2,final String data3,final boolean invalid) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.background_drive_modify_ask));
		builder.setPositiveButton(getString(R.string.background_backgroound_ensure), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (CMD_LATTICE_VALID_SET == cmdType) {
					if (TcnVendIF.getInstance().isDigital(data1)) {
						if (invalid) {
							TcnVendIF.getInstance().reqLatticeNumSetInVaild(Integer.valueOf(grp),Integer.valueOf(data1),Integer.valueOf(data2),Integer.valueOf(data3));
						} else {
							TcnVendIF.getInstance().reqLatticeNumSetVaild(Integer.valueOf(grp),Integer.valueOf(data1),Integer.valueOf(data2),Integer.valueOf(data3));
						}

					} else {
						if (invalid) {
							TcnVendIF.getInstance().reqLatticeNumSetInVaild(-1,Integer.valueOf(data1),Integer.valueOf(data2),Integer.valueOf(data3));
						} else {
							TcnVendIF.getInstance().reqLatticeNumSetVaild(-1,Integer.valueOf(data1),Integer.valueOf(data2),Integer.valueOf(data3));
						}
					}

				}

			}
		});
		builder.setNegativeButton(getString(R.string.background_backgroound_cancel), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		});
		builder.show();
	}


	private void selectData(int type, int which, String[] str) {
		if ((which < 0) || (null == str) || (which >= str.length)) {
			TcnVendIF.getInstance().LoggerError(TAG, "selectData which: "+which+" str: "+str);
			return;
		}
	}

	private void showSelectDialog(final int type, String title,final EditText v, String selectData,final String[] str) {
		if (null == str) {
			return;
		}
		int checkedItem = -1;
		if ((selectData != null) && (selectData.length() > 0)) {
			for (int i = 0; i < str.length; i++) {
				if (str[i].equals(selectData)) {
					checkedItem = i;
					break;
				}
			}
		}

		singleitem=0;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setSingleChoiceItems(str, checkedItem, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				singleitem=which;
			}
		});
		builder.setPositiveButton(getString(R.string.background_backgroound_ensure), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				v.setText(str[singleitem]);
			}
		});
		builder.setNegativeButton(getString(R.string.background_backgroound_cancel), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{

			}
		});
		builder.show();
	}

	protected void showBusyDialog(String data) {
		if (null == m_OutDialog) {
			m_OutDialog = new OutDialog(MainAct.this, "",data);
		}
		m_OutDialog.setNumber("");
		m_OutDialog.setTitle(data);
		m_OutDialog.show();
	}

	protected void showBusyDialog(int maxSlotNo,int maxShowSecond,String showMsg) {
		if (null == m_OutDialog) {
			m_OutDialog = new OutDialog(MainAct.this, String.valueOf(maxSlotNo),showMsg);
		}
		m_OutDialog.setNumber(String.valueOf(maxSlotNo));
		m_OutDialog.setShowTime(maxShowSecond);
		m_OutDialog.setTitle(showMsg);
		m_OutDialog.show();
	}

	protected void cancelBusyDialog() {
		if (m_OutDialog != null) {
			m_OutDialog.cancel();
		}
	}

	private MenuSetTitleBarListener m_TitleBarListener = new MenuSetTitleBarListener();
	private class MenuSetTitleBarListener implements Titlebar.TitleBarListener {

		@Override
		public void onClick(View v, int buttonId) {
			if (Titlebar.BUTTON_ID_BACK == buttonId) {
				MainAct.this.finish();
			}
		}
	}

	private ButtonClickListener m_ClickListener = new ButtonClickListener();
	private class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (R.id.main_serport == id) {
				TcnVendIF.getInstance().LoggerDebug(TAG, "onClick() to SerialPortSetting");
				Intent in = new Intent(MainAct.this, SerialPortSetting.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(in);
			} else if (R.id.m_lattice_open_batch_btn == id) {

				String start = m_lattice_open_batch_start.getText().toString();
				String end = m_lattice_open_batch_end.getText().toString();

				if ((start == null) || (start.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_start_cabinetno));
					return;
				}

				if ((end == null) || (end.length() < 1)) {
					end = start;
				}

				TcnVendIF.getInstance().reqWriteDataShipTest(Integer.valueOf(start),Integer.valueOf(end));

			} else if (R.id.m_lattice_close_batch_btn == id) {
				String start = m_lattice_close_batch_start.getText().toString();
				String end = m_lattice_close_batch_end.getText().toString();

				if ((start == null) || (start.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_start_cabinetno));
					return;
				}

				if ((end == null) || (end.length() < 1)) {
					end = start;
				}

				TcnVendIF.getInstance().reqOffCabinetNo(Integer.valueOf(start),Integer.valueOf(end));
			}
			else if (R.id.m_scan_light_btn == id) {
				String latticeAddr = menu_lat_select_addr.getButtonEditText();
				if (UIComBack.getInstance().isMutiGrpLattice()) {
					if ((latticeAddr == null) || (latticeAddr.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_drive_tips_select_cabinetno));
						return;
					}
				}

				String light_row_start = m_scan_light_row_start.getText().toString();
				String light_row_end = m_scan_light_row_end.getText().toString();

				if ((light_row_start == null) || (light_row_start.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_start_row));
					return;
				}
				if ((light_row_end == null) || (light_row_end.length() < 1)) {
					light_row_end = light_row_start;
				}
				TcnVendIF.getInstance().reqSetLight(UIComBack.getInstance().getGroupLatticeId(latticeAddr),Integer.valueOf(light_row_start),Integer.valueOf(light_row_end));
			} else if (R.id.m_lattice_num_set_btn == id) {
				String latticeAddr = menu_lat_select_addr.getButtonEditText();
				if (UIComBack.getInstance().isMutiGrpLattice()) {
					if ((latticeAddr == null) || (latticeAddr.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_drive_tips_select_cabinetno));
						return;
					}
				}

				String row = m_lattice_num_set_row.getText().toString();
				if ((row == null) || (row.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_row));
					return;
				}
				String columnstart = m_lattice_num_set_column_start.getText().toString();
				if ((columnstart == null) || (columnstart.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_start_column));
					return;
				}
				String columnend = m_lattice_num_set_column_end.getText().toString();
				if ((columnend == null) || (columnend.length() < 1)) {
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_end_column));
					return;
				}
				boolean isCheckboxInVaild = m_lattice_num_set_checkbox.isChecked();
				showSetConfirm(CMD_LATTICE_VALID_SET,String.valueOf(UIComBack.getInstance().getGroupLatticeId(latticeAddr)),row,columnstart,columnend,isCheckboxInVaild);
			} else if (R.id.m_kh_game_win_btn == id) {
				TcnVendIF.getInstance().reqGameWinKouhong();
			} else if (R.id.m_kh_game_fail_btn == id) {
				TcnVendIF.getInstance().reqGameFailKouhong();
			} else {

			}
		}
	}

	private ButtonEditClickListener m_ButtonEditClickListener= new ButtonEditClickListener();
	private class ButtonEditClickListener implements ButtonEditSelectD.ButtonListener {
		@Override
		public void onClick(View v, int buttonId) {
			if (null == v) {
				return;
			}
			int id = v.getId();
			if (R.id.menu_lat_query_addr == id) {
				if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
					menu_lat_query_addr.setButtonDisplayText("");
					String strParam = menu_lat_query_addr.getButtonEditInputText();
					if ((null == strParam) || (strParam.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_cabinetno));
					} else {
						TcnVendIF.getInstance().reqQueryAddress(Integer.valueOf(strParam));
					}
				}
			} else if (R.id.menu_lat_query_slot == id) {
				if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
					menu_lat_query_slot.setButtonDisplayText("");
					String strParam = menu_lat_query_slot.getButtonEditInputText();
					if ((null == strParam) || (strParam.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_input_cabinetno));
					} else {
						TcnVendIF.getInstance().reqQuerySlotStatus(Integer.valueOf(strParam));
					}
				}
			} else if (R.id.menu_lat_select_addr == id) {
				if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
					menu_lat_select_addr.setButtonDisplayText("");
				} else if (ButtonEditSelectD.BUTTON_ID_SELECT == buttonId) {
					showSelectDialog(-1,getString(R.string.background_drive_tips_select_cabinetno),menu_lat_select_addr.getButtonEdit(), "",UIComBack.getInstance().getGroupListLatticeShow());
				} else {

				}
			}  else if (R.id.menu_lat_ship_slot == id) {
				if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
					String strParam = menu_lat_ship_slot.getButtonEditInputText();
					if ((null == strParam) || (strParam.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_drive_tips_input_slotno));
					} else {
						int slotNo = Integer.valueOf(strParam);//出货的货道号
						String shipMethod = PayMethod.PAYMETHED_WECHAT; //出货方法,微信支付出货，此处自己可以修改。
						String amount = "0.1";    //支付的金额（元）,自己修改
						String tradeNo = "1811020095201811150126888";//支付订单号，每次出货，订单号不能一样，此处自己修改。
						TcnVendIF.getInstance().reqShip(slotNo,shipMethod,amount,tradeNo);
					}
				}
			} else if (R.id.menu_lat_reqselect == id) {
				if (ButtonEditSelectD.BUTTON_ID_QUERY == buttonId) {
					String strParam = menu_lat_reqselect.getButtonEditInputText();
					if ((null == strParam) || (strParam.length() < 1)) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_drive_tips_input_slotno));
					} else {
						TcnVendIF.getInstance().reqSelectSlotNo(Integer.valueOf(strParam));

//						选货成功之后，会调用TcnVendIF.getInstance().reqSelectSlotNoKouhong(slotNo),这个是选中格子的效果,参考VendIF.java 这个文件的 OnSelectedSlotNo方法
					}
				}
			} else if (R.id.m_kh_game_win_btn == id) {
				TcnVendIF.getInstance().reqGameWinKouhong();   //游戏成功效果
			} else if (R.id.m_kh_game_fail_btn == id) {
				TcnVendIF.getInstance().reqGameFailKouhong();       //游戏失败效果
			}
			else {

			}
		}
	}

	private VendListener m_vendListener = new VendListener();
	private class VendListener implements TcnVendIF.VendEventListener {
		@Override
		public void VendEvent(VendEventInfo cEventInfo) {
			if (null == cEventInfo) {
				TcnVendIF.getInstance().LoggerError(TAG, "VendListener cEventInfo is null");
				return;
			}
			switch (cEventInfo.m_iEventID) {
				case TcnVendEventID.COMMAND_SYSTEM_BUSY:
					TcnUtilityUI.getToast(MainAct.this, cEventInfo.m_lParam4, 20).show();
					break;

				case TcnVendEventID.SERIAL_PORT_CONFIG_ERROR:
					//TcnUtilityUI.getToast(m_MainActivity, getString(R.string.error_seriport));
					//打开串口错误，一般是串口配置出错
					break;
				case TcnVendEventID.SERIAL_PORT_SECURITY_ERROR:
					///打开串口错误，一般是串口配置出错
					break;
				case TcnVendEventID.SERIAL_PORT_UNKNOWN_ERROR:
					//打开串口错误，一般是串口配置出错
					break;
				case TcnVendEventID.COMMAND_SELECT_GOODS:  //选货成功
					TcnUtilityUI.getToast(MainAct.this, "选货成功");
					break;
				case TcnVendEventID.COMMAND_INVALID_SLOTNO:
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.ui_base_notify_invalid_slot), 22).show();
					break;
				case TcnVendEventID.COMMAND_SOLD_OUT:
					if (cEventInfo.m_lParam1 > 0) {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.ui_base_aisle_name) + cEventInfo.m_lParam1 + getString(R.string.ui_base_notify_sold_out));
					} else {
						TcnUtilityUI.getToast(MainAct.this, getString(R.string.ui_base_notify_sold_out));
					}
					break;
				case TcnVendEventID.COMMAND_FAULT_SLOTNO:
					TcnUtilityUI.getToast(MainAct.this, cEventInfo.m_lParam4);
					break;
				case TcnVendEventID.COMMAND_SHIPPING:    //正在出货
					if ((cEventInfo.m_lParam4 != null) && ((cEventInfo.m_lParam4).length() > 0)) {
						if (m_OutDialog == null) {
							m_OutDialog = new OutDialog(MainAct.this, String.valueOf(cEventInfo.m_lParam1), cEventInfo.m_lParam4);
						} else {
							m_OutDialog.setText(cEventInfo.m_lParam4);
						}
						m_OutDialog.cleanData();
					} else {
						if (m_OutDialog == null) {
							m_OutDialog = new OutDialog(MainAct.this, String.valueOf(cEventInfo.m_lParam1), getString(R.string.ui_base_notify_shipping));
						} else {
							m_OutDialog.setText(MainAct.this.getString(R.string.ui_base_notify_shipping));
						}
					}
					m_OutDialog.setNumber(String.valueOf(cEventInfo.m_lParam1));
					m_OutDialog.show();
					break;

				case TcnVendEventID.COMMAND_SHIPMENT_SUCCESS:    //出货成功
					if (null != m_OutDialog) {
						m_OutDialog.cancel();
					}
					if (m_LoadingDialog == null) {
						m_LoadingDialog = new LoadingDialog(MainAct.this, getString(R.string.ui_base_notify_shipment_success), getString(R.string.ui_base_notify_receive_goods));
					} else {
						m_LoadingDialog.setLoadText(getString(R.string.ui_base_notify_shipment_success));
						m_LoadingDialog.setTitle(getString(R.string.ui_base_notify_receive_goods));
					}
					m_LoadingDialog.setShowTime(3);
					m_LoadingDialog.show();
					break;
				case TcnVendEventID.COMMAND_SHIPMENT_FAILURE:    //出货失败
					if (null != m_OutDialog) {
						m_OutDialog.cancel();
					}
					if (null == m_LoadingDialog) {
						m_LoadingDialog = new LoadingDialog(MainAct.this, getString(R.string.ui_base_notify_shipment_fail), getString(R.string.ui_base_notify_contact_merchant));
					}
					m_LoadingDialog.setLoadText(getString(R.string.ui_base_notify_shipment_fail));
					m_LoadingDialog.setTitle(getString(R.string.ui_base_notify_contact_merchant));
					m_LoadingDialog.setShowTime(3);
					m_LoadingDialog.show();
					break;
				case TcnVendEventID.PROMPT_INFO:
					TcnUtilityUI.getToast(MainAct.this, cEventInfo.m_lParam4);
					break;
				case TcnVendEventID.CMD_QUERY_SLOT_STATUS:
					menu_lat_query_slot.setButtonDisplayText(cEventInfo.m_lParam4);
					break;
				case TcnVendEventID.CMD_QUERY_ADDRESS:
					menu_lat_query_addr.setButtonDisplayText(String.valueOf(cEventInfo.m_lParam1));
					break;
				case TcnVendEventID.CMD_TEST_SLOT:
					TcnVendIF.getInstance().LoggerDebug(TAG, "VendListener CMD_TEST_SLOT m_lParam3: "+cEventInfo.m_lParam3);
					if (cEventInfo.m_lParam3 == TcnVendEventResultID.SHIP_SHIPING) {
						showBusyDialog(cEventInfo.m_lParam1,5, getString(R.string.background_drive_slot_testing));
					}
					else if (cEventInfo.m_lParam3 == TcnVendEventResultID.SHIP_FAIL) {
						showBusyDialog(cEventInfo.m_lParam1,2, getString(R.string.background_notify_shipment_fail));
					} else if (cEventInfo.m_lParam3 == TcnVendEventResultID.SHIP_SUCCESS) {
						showBusyDialog(cEventInfo.m_lParam1,2, getString(R.string.background_notify_shipment_success));
					}
					else {
						cancelBusyDialog();
					}
					break;
				case TcnVendEventID.CMD_SCAN_LIGHT_SET:
					TcnUtilityUI.getToast(MainAct.this, getString(R.string.background_lat_set_success));
					break;
				case TcnVendEventID.CMD_CABINETNO_SET_VAILD:
					showBusyDialog(cEventInfo.m_lParam2,1, getString(R.string.background_drive_setting));
					break;
				case TcnVendEventID.CMD_CABINETNO_SET_INVAILD:
					showBusyDialog(cEventInfo.m_lParam2,1, getString(R.string.background_drive_setting));
					break;
				case TcnVendEventID.CMD_CABINETNO_OFF:
					if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_CLOSING) {
						if (m_LoadingDialog != null) {
							m_LoadingDialog.cancel();
						}
						if (m_OutDialog == null) {
							m_OutDialog = new OutDialog(MainAct.this, String.valueOf(cEventInfo.m_lParam2), cEventInfo.m_lParam4);
						} else {
							m_OutDialog.setText(cEventInfo.m_lParam4);
						}
						m_OutDialog.setNumber(String.valueOf(cEventInfo.m_lParam2));
						m_OutDialog.setShowTime(10);
						m_OutDialog.show();
					} else if (cEventInfo.m_lParam1 == TcnVendEventResultID.OFF_SUCCESS) {
						if (m_OutDialog != null) {
							m_OutDialog.cancel();
						}
						if (m_LoadingDialog == null) {
							m_LoadingDialog = new LoadingDialog(MainAct.this, cEventInfo.m_lParam4, String.valueOf(cEventInfo.m_lParam2));
						} else {
							m_LoadingDialog.setLoadText(cEventInfo.m_lParam4);
							m_LoadingDialog.setTitle(String.valueOf(cEventInfo.m_lParam2));
						}
						m_LoadingDialog.setShowTime(3);
						m_LoadingDialog.show();
					}
					else {
						if (m_OutDialog != null) {
							m_OutDialog.cancel();
						}
						if (m_LoadingDialog == null) {
							m_LoadingDialog = new LoadingDialog(MainAct.this, cEventInfo.m_lParam4, String.valueOf(cEventInfo.m_lParam2));
						} else {
							m_LoadingDialog.setLoadText(cEventInfo.m_lParam4);
							m_LoadingDialog.setTitle(String.valueOf(cEventInfo.m_lParam2));
						}
						m_LoadingDialog.setShowTime(3);
						m_LoadingDialog.show();
					}
					break;
				case TcnVendEventID.CMD_REQ_PERMISSION:     //授权访问文件夹
					TcnUtilityUI.getToast(MainAct.this, "请选择确定");
					ActivityCompat.requestPermissions(MainAct.this, TcnVendIF.getInstance().getPermission(cEventInfo.m_lParam4),126);
					break;
				default:
					break;
			}
		}
	}
}
