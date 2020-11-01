package com.alan.handsome.module.main.ui;

import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.ReqWork;
import com.alan.handsome.module.loans.ui.InfoAdapter;
import com.alan.handsome.module.main.bean.UserInfoBean;
import com.alan.handsome.module.main.constant.SaveInfoConstant;
import com.alan.handsome.module.main.presenter.SaveInfoPresenter;
import com.alan.handsome.user.DictsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkInfoActivity extends BaseActivity<SaveInfoPresenter> implements SaveInfoConstant.View {
    @BindView(R.id.employment_tv)
    TextView employmentTv;
    @BindView(R.id.your_monthly_salary_tv)
    TextView yourMonthlySalaryTv;
    @BindView(R.id.monthly_family_income_tv)
    TextView monthlyFamilyIncomeTv;
    @BindView(R.id.save_tv)
    TextView saveTv;

    //列表选择
    private AlertDialog dialog;
    private InfoAdapter employmentAdapter, monthlyAdapter, familyAdapter;
    private List<DictsBean> employmentList, monthlyList, familyList;
    private static final int EMPLOYMENT_TYPE = 104;
    private static final int MONTHLY_TYPE = 105;
    private static final int FAMILY_TYPE = 106;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_information;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showDialog();
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
                employmentTv.setText(employmentList.get(position).getName());
            }
        });

        monthlyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                monthlyAdapter.setSelect(monthlyList.get(position).getName());
                yourMonthlySalaryTv.setText(monthlyList.get(position).getName());
            }

        });

        familyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                familyAdapter.setSelect(familyList.get(position).getName());
                monthlyFamilyIncomeTv.setText(familyList.get(position).getName());
            }
        });

        //请求用户数据
        mPresenter.getUserInfo();
    }

    @Override
    protected SaveInfoPresenter createPresenter() {
        return new SaveInfoPresenter();
    }

    @OnClick({R.id.employment_type_relate, R.id.your_monthly_salary_relate, R.id.monthly_family_income_relate, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.employment_type_relate:
                showSelectCarOrPhone("Chose employment type",EMPLOYMENT_TYPE);
                break;

            case R.id.your_monthly_salary_relate:
                showSelectCarOrPhone("Chose your monthly salary",MONTHLY_TYPE);
                break;

            case R.id.monthly_family_income_relate:
                showSelectCarOrPhone("Chose monthly family income",FAMILY_TYPE);
                break;

            case R.id.save_tv:
                showDialog();
                //提交数据
                ReqWork work=new ReqWork();
                for (DictsBean dictsBean : employmentList) {
                    if (employmentTv.getText().toString().equals(dictsBean.getName())){
                        work.setEmployment_type(dictsBean.getValue());
                    }
                }

                for (DictsBean dictsBean : monthlyList) {
                    if (yourMonthlySalaryTv.getText().toString().equals(dictsBean.getName())){
                        work.setMonthly_salary(dictsBean.getValue());
                    }
                }

                for (DictsBean dictsBean : familyList) {
                    if (monthlyFamilyIncomeTv.getText().toString().equals(dictsBean.getName())){
                        work.setMonthly_family_salary(dictsBean.getValue());
                    }
                }

                mPresenter.commitWorkInfo(work);
                break;
        }
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

    //获取用户信息
    @Override
    public void getUserInfoSuc(UserInfoBean userInfoBean) {
        hideDialog();
        if (userInfoBean != null) {

            if (!TextUtils.isEmpty(userInfoBean.getMonthly_salary())) {
                yourMonthlySalaryTv.setText(userInfoBean.getMonthly_salary());
                monthlyAdapter.setSelect(userInfoBean.getMonthly_salary());
            }

            if (!TextUtils.isEmpty(userInfoBean.getEmployment_type())) {
                employmentTv.setText(userInfoBean.getEmployment_type());
                employmentAdapter.setSelect(userInfoBean.getEmployment_type());
            }

            if (!TextUtils.isEmpty(userInfoBean.getMonthly_family_salary())) {
                monthlyFamilyIncomeTv.setText(userInfoBean.getMonthly_family_salary());
                familyAdapter.setSelect(userInfoBean.getMonthly_family_salary());
            }

        }
    }

    @Override
    public void getUserInfoFail(String msg) {
        hideDialog();
        showErrorToast(msg);
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
        showErrorToast("Save successfully");
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
