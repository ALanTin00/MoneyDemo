package com.alan.handsome.module.loans.ui;

import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.ReqBase;
import com.alan.handsome.module.loans.constant.CommitInfoConstant;
import com.alan.handsome.module.loans.presenter.CommitInfoPresenter;
import com.alan.handsome.user.DictsBean;
import com.alan.handsome.widget.DialogDatePickerSelect;
import com.alan.handsome.widget.TitleBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationBaseActivity extends BaseActivity<CommitInfoPresenter> implements CommitInfoConstant.View {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.line_one_tv)
    TextView lineOneTv;
    @BindView(R.id.line_two_tv)
    TextView lineTwoTv;
    @BindView(R.id.pass_one_tv)
    TextView passOneTv;
    @BindView(R.id.pass_two_tv)
    TextView passTwoTv;
    @BindView(R.id.pass_three_tv)
    TextView passThreeTv;
    @BindView(R.id.full_name_edit)
    EditText fullNameEdit;
    @BindView(R.id.e_mail_edit)
    EditText eMailEdit;
    @BindView(R.id.birthday_tv)
    TextView birthdayTv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.marital_tv)
    TextView maritalTv;
    @BindView(R.id.education_tv)
    TextView educationTv;

    //时间选择器
    private DialogDatePickerSelect datePickerSelect;
    //列表选择
    private AlertDialog dialog;
    private InfoAdapter genderAdapter, maritalAdapter, educationAdapter;
    private List<DictsBean> genderList, maritalList, educationList;
    private int genderValue = -1, maritalValue = -1, educationValue = -1;
    private String data;//选择的日期
    private static final int GENDER_TYPE = 101;
    private static final int MARITAL_TYPE = 102;
    private static final int EDUCATION_TYPE = 103;

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_80F0D1);
        mImmersionBar.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication_base;
    }

    @Override
    protected void initView() {
        passOneTv.setSelected(true);
        passOneTv.setText("1");

        datePickerSelect = new DialogDatePickerSelect(this, new DialogDatePickerSelect.OnDateSelectCallBack() {
            @Override
            public void onDateSelected(int flag, int year, int month, int day, long time, String dateString) {
                switch (flag) {
                    //截止购买时间
                    case 1:
                        birthdayTv.setText(dateString);
                        birthdayTv.setTextColor(getResources().getColor(R.color.color_33));
                        data=dateString;
                        break;

                }
            }
        });

    }

    @Override
    protected void initData() {
        genderAdapter = new InfoAdapter();
        maritalAdapter = new InfoAdapter();
        educationAdapter = new InfoAdapter();
        genderList = new ArrayList<>();
        maritalList = new ArrayList<>();
        educationList = new ArrayList<>();

        //加载启动页的下载的本地数据
        for (DictsBean dict : AccountManager.getInstance().getSysInfo().getDicts()) {
            switch (dict.getType()) {
                case "Gender":
                    genderList.add(dict);
                    break;
                case "Marital":
                    maritalList.add(dict);
                    break;
                case "Education":
                    educationList.add(dict);
                    break;

            }
        }
        if (genderList.size() > 0) {
            genderAdapter.setSelect(genderList.get(0).getName());
            genderAdapter.setNewData(genderList);
        }

        if (maritalList.size() > 0) {
            maritalAdapter.setSelect(maritalList.get(0).getName());
            maritalAdapter.setNewData(maritalList);
        }

        if (educationList.size() > 0) {
            educationAdapter.setSelect(educationList.get(0).getName());
            educationAdapter.setNewData(educationList);
        }

        genderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                genderAdapter.setSelect(genderList.get(position).getName());
                genderTv.setTextColor(getResources().getColor(R.color.color_33));
                genderTv.setText(genderList.get(position).getName());
                genderValue = genderList.get(position).getValue();
            }
        });

        maritalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                maritalAdapter.setSelect(maritalList.get(position).getName());
                maritalTv.setTextColor(getResources().getColor(R.color.color_33));
                maritalTv.setText(maritalList.get(position).getName());
                maritalValue = maritalList.get(position).getValue();
            }

        });

        educationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                educationAdapter.setSelect(educationList.get(position).getName());
                educationTv.setTextColor(getResources().getColor(R.color.color_33));
                educationTv.setText(educationList.get(position).getName());
                educationValue = educationList.get(position).getValue();
            }
        });
    }

    /**
     * 信息选择弹出框
     *
     * @param title
     */
    public void showSelectCarOrPhone(String title, int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_car_phone, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        TextView titleTV = view.findViewById(R.id.dsp_title);
        titleTV.setText(title);
        RecyclerView carPhoneRL = view.findViewById(R.id.dsp_rl);
        carPhoneRL.setLayoutManager(new LinearLayoutManager(this));

        switch (type) {
            //性别
            case GENDER_TYPE:
                carPhoneRL.setAdapter(genderAdapter);
                break;

            //婚姻
            case MARITAL_TYPE:
                carPhoneRL.setAdapter(maritalAdapter);
                break;

            //教育
            case EDUCATION_TYPE:
                carPhoneRL.setAdapter(educationAdapter);
                break;

        }
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();

    }

    @Override
    protected CommitInfoPresenter createPresenter() {
        return new CommitInfoPresenter();
    }

    @OnClick({R.id.birthday_tv, R.id.gender_tv, R.id.marital_tv, R.id.education_tv, R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birthday_tv:
                datePickerSelect.showDatePickView("Choose your birthday", 1);
                break;

            case R.id.gender_tv:
                showSelectCarOrPhone("Choose gender", GENDER_TYPE);
                break;

            case R.id.marital_tv:
                showSelectCarOrPhone("Marriage Status", MARITAL_TYPE);
                break;

            case R.id.education_tv:
                showSelectCarOrPhone("Choose education", EDUCATION_TYPE);
                break;

            case R.id.Next_tv:

                if (TextUtils.isEmpty(fullNameEdit.getText().toString().trim())){
                    showErrorToast("Please input full name");
                    return;
                }
                if (TextUtils.isEmpty(eMailEdit.getText().toString().trim())){
                    showErrorToast("Please input  email");
                    return;
                }
                if (TextUtils.isEmpty(data)){
                    showErrorToast("Please choose your birthday");
                    return;
                }
                if (genderValue==-1){
                    showErrorToast("Please choose gender");
                    return;
                }

                if (maritalValue==-1){
                    showErrorToast("Please choose marital");
                    return;
                }
                if (educationValue==-1){
                    showErrorToast("Please choose education");
                    return;
                }
                //提交基础信息
                showDialog();
                ReqBase base=new ReqBase();
                base.setGender(genderValue);
                base.setMarital(maritalValue);
                base.setEducation(educationValue);
                base.setEmail(eMailEdit.getText().toString().trim());
                base.setName(fullNameEdit.getText().toString().trim());
                base.setBirthday(data);
                mPresenter.commitBaseInfo(base);
                break;
        }
    }

    //提交基础信息
    @Override
    public void commitBaseInfoSuc() {
        hideDialog();
        AccountManager.getInstance().saveAuthenticationType(-1,2);
        startToActivity(AuthenticationWorkActivity.class);
        finish();
    }

    @Override
    public void commitBaseInfoFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //提交工作信息
    @Override
    public void commitWorkInfoSuc() {

    }

    @Override
    public void commitWorkInfoFail(String msg) {

    }

    //提交银行信息
    @Override
    public void commitBankSuc() {

    }

    @Override
    public void commitBankFail(String msg) {

    }
}
