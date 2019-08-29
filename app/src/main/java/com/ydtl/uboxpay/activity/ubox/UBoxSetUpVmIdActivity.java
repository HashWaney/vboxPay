package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.tool.AndroidUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class UBoxSetUpVmIdActivity extends BaseActivity {


    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.num0)
    Button btnNum0;
    @BindView(R.id.num1)
    Button btnNum1;
    @BindView(R.id.num2)
    Button btnNum2;
    @BindView(R.id.num3)
    Button btnNum3;
    @BindView(R.id.num4)
    Button btnNum4;
    @BindView(R.id.num5)
    Button btnNum5;
    @BindView(R.id.num6)
    Button btnNum6;
    @BindView(R.id.num7)
    Button btnNum7;
    @BindView(R.id.num8)
    Button btnNum8;
    @BindView(R.id.num9)
    Button btnNum9;
    @BindView(R.id.del)
    Button btnDel;
    @BindView(R.id.clear)
    Button btnClear;
    @BindView(R.id.confirm)
    Button btnConfirm;
    @BindView(R.id.etInputId)
    EditText etInput;

    private StringBuilder vimIdInput;


    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_setup_vm_id);
        ButterKnife.bind(this);
        vimIdInput = new StringBuilder();
        etInput.setInputType(InputType.TYPE_NULL);
    }

    @OnClick(R.id.clear)
    public void clearInput() {
        vimIdInput.setLength(0);
        checkInputStr(vimIdInput);
    }

    @OnClick(R.id.del)
    public void delInput() {
        if (vimIdInput.length() > 0) {
            vimIdInput.deleteCharAt(vimIdInput.length() - 1);
            etInput.setText(vimIdInput.toString());
            checkInputStr(vimIdInput);
            Log.e(TAG, "after del vi id:" + vimIdInput);
        }
    }


    @OnClick(R.id.confirm)
    public void confirm() {
//        Toast.makeText(this, "输入机器号进行校验和获取数据列表操作", Toast.LENGTH_LONG).show();
        Log.e(TAG, "校验输入值:" + vimIdInput + "checkInputStr(vimIdInput):" + checkInputStr(vimIdInput));
        if (checkInputStr(vimIdInput)) {
            // TODO: 2019/8/28 请求接口 通过接口返回值 确定机器号是否合法
            AndroidUtil.setConfigValue(this, Constant.SETUP_VM_ID, true);
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();

        }

    }

    @OnClick({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9})
    public void createVmId(View view) {
        Button button = (Button) view;
        vimIdInput.append(button.getText());
        checkInputStr(vimIdInput);
        etInput.setText(vimIdInput);
        Log.e(TAG, "当前选中的值:" + button.getText() + " append vm id :" + vimIdInput);

    }

    /**
     * 校验输入的值
     *
     * @param inputInfo
     */
    private boolean checkInputStr(StringBuilder inputInfo) {
        if (inputInfo.length() >= 5) {
            btnConfirm.setClickable(true);
            // mConfirmBt.setBackgroundResource(R.drawable.click);
            return true;
        } else if (inputInfo.length() > 15) {
            inputInfo.deleteCharAt(15);
            return true;
        } else {
            btnConfirm.setClickable(false);
            if (inputInfo.length() > 0) {
                etInput.setTextColor(Color.BLACK);
            } else {
                etInput.setText("请输入当前售货机的vmId");
                etInput.setTextColor(Color.parseColor("#CCCCCC"));
            }
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
