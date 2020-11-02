package com.alan.handsome.module.loans.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.ReqWork;
import com.alan.handsome.module.loans.constant.CommitInfoConstant;
import com.alan.handsome.module.loans.presenter.CommitInfoPresenter;
import com.alan.handsome.user.DictsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationWorkActivity extends BaseActivity<CommitInfoPresenter> implements CommitInfoConstant.View {
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
    @BindView(R.id.employment_type_tv)
    TextView employmentTypeTv;
    @BindView(R.id.your_monthly_salary_tv)
    TextView yourMonthlySalaryTv;
    @BindView(R.id.monthly_family_income_tv)
    TextView monthlyFamilyIncomeTv;

    private int selectLoanPosition;//首页进来传过来的

    //列表选择
    private AlertDialog dialog;
    private InfoAdapter employmentAdapter,  monthlyAdapter, familyAdapter;
    private List<DictsBean> employmentList,  monthlyList, familyList;
    private int employmentValue = -1,  monthlyValue = -1, familyValue = -1;
    private static final int EMPLOYMENT_TYPE = 104;
    private static final int  MONTHLY_TYPE = 105;
    private static final int FAMILY_TYPE = 106;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication_work;
    }

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
    protected void initView() {

        lineOneTv.setSelected(true);
        passOneTv.setSelected(true);
        passTwoTv.setSelected(true);
        passTwoTv.setText("2");
    }

    @Override
    protected void initData() {
        selectLoanPosition = getIntent().getIntExtra("selectLoanPosition", -1);
        employmentAdapter = new InfoAdapter();
        monthlyAdapter = new InfoAdapter();
        familyAdapter = new InfoAdapter();
        employmentList = new ArrayList<>();
        monthlyList = new ArrayList<>();
        familyList = new ArrayList<>();

        //加载启动页的下载的本地数据
        for (DictsBean dict : AccountManager.getInstance().getSysInfo().getDicts()) {
            switch (dict.getType()) {
                case "Employment Type":
                    employmentList.add(dict);
                    break;
                case "Your Monthly Salary":
                    monthlyList.add(dict);
                    break;
                case "Monthly Family Income":
                    familyList.add(dict);
                    break;

            }
        }
        if (employmentList.size() > 0) {
            employmentAdapter.setSelect(employmentList.get(0).getName());
            employmentAdapter.setNewData(employmentList);
        }

        if (monthlyList.size() > 0) {
            monthlyAdapter.setSelect(monthlyList.get(0).getName());
            monthlyAdapter.setNewData(monthlyList);
        }

        if (familyList.size() > 0) {
            familyAdapter.setSelect(familyList.get(0).getName());
            familyAdapter.setNewData(familyList);
        }

        employmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                employmentAdapter.setSelect(employmentList.get(position).getName());
                employmentTypeTv.setTextColor(getResources().getColor(R.color.color_33));
                employmentTypeTv.setText(employmentList.get(position).getName());
                employmentValue = employmentList.get(position).getValue();
            }
        });

        monthlyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                monthlyAdapter.setSelect(monthlyList.get(position).getName());
                yourMonthlySalaryTv.setTextColor(getResources().getColor(R.color.color_33));
                yourMonthlySalaryTv.setText(monthlyList.get(position).getName());
                monthlyValue = monthlyList.get(position).getValue();
            }

        });

        familyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                familyAdapter.setSelect(familyList.get(position).getName());
                monthlyFamilyIncomeTv.setTextColor(getResources().getColor(R.color.color_33));
                monthlyFamilyIncomeTv.setText(familyList.get(position).getName());
                familyValue = familyList.get(position).getValue();
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

            case EMPLOYMENT_TYPE:
                carPhoneRL.setAdapter(employmentAdapter);
                break;


            case MONTHLY_TYPE:
                carPhoneRL.setAdapter(monthlyAdapter);
                break;


            case FAMILY_TYPE:
                carPhoneRL.setAdapter(familyAdapter);
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

    @OnClick({R.id.employment_type_tv, R.id.your_monthly_salary_tv, R.id.monthly_family_income_tv, R.id.Next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.employment_type_tv:
                showSelectCarOrPhone("Chose employment type",EMPLOYMENT_TYPE);
                break;

            case R.id.your_monthly_salary_tv:
                showSelectCarOrPhone("Chose your monthly salary",MONTHLY_TYPE);
                break;

            case R.id.monthly_family_income_tv:
                showSelectCarOrPhone("Chose monthly family income",FAMILY_TYPE);
                break;

            case R.id.Next_tv:
                showDialog();

                if (employmentValue==-1){
                    showErrorToast("Please chose employment type");
                    return;
                }

                if (monthlyValue==-1){
                    showErrorToast("Please your monthly salary");
                    return;
                }

                if (familyValue==-1){
                    showErrorToast("Please chose monthly family income");
                    return;
                }
                //提交工作信息
                ReqWork reqWork=new ReqWork();
                reqWork.setEmployment_type(employmentValue);
                reqWork.setMonthly_salary(monthlyValue);
                reqWork.setMonthly_family_salary(familyValue);
                mPresenter.commitWorkInfo(reqWork);
                break;
        }
    }

    //提交基础信息
    @Override
    public void commitBaseInfoSuc() {
    }

    @Override
    public void commitBaseInfoFail(String msg) {
    }

    //提交工作信息
    @Override
    public void commitWorkInfoSuc() {
        hideDialog();
        AccountManager.getInstance().saveAuthenticationType(-1,3);
        Intent intent=new Intent(this,AuthenticationBankActivity.class);
        intent.putExtra("selectLoanPosition",selectLoanPosition);
        startActivity(intent);
        finish();
    }

    @Override
    public void commitWorkInfoFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    //提交银行信息
    @Override
    public void commitBankSuc() {

    }

    @Override
    public void commitBankFail(String msg) {

    }
}
