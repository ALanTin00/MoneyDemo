package com.alan.handsome.module.loans.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.module.loans.constant.CommitInfoConstant;
import com.alan.handsome.module.loans.presenter.CommitInfoPresenter;
import com.alan.handsome.user.DictsBean;
import com.alan.handsome.widget.DialogDatePickerSelect;
import com.alan.handsome.widget.TitleBar;
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
    private InfoAdapter genderAdapter, maritalAdapter, educationAdapter;
    private List<DictsBean>  genderList, maritalList, educationList;
    private int genderValue,maritalValue, educationValue;

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
                        break;

                }
            }
        });

    }

    @Override
    protected void initData() {
        genderAdapter=new InfoAdapter();
        maritalAdapter=new InfoAdapter();
        educationAdapter=new InfoAdapter();
        genderList=new ArrayList<>();
        maritalList=new ArrayList<>();
        educationList=new ArrayList<>();
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
                break;
            case R.id.marital_tv:
                break;
            case R.id.education_tv:
                break;
            case R.id.Next_tv:
                showDialog();

                break;
        }
    }

    //提交基础信息
    @Override
    public void commitBaseInfoSuc() {
        hideDialog();
        startToActivity(AuthenticationWorkActivity.class);
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
