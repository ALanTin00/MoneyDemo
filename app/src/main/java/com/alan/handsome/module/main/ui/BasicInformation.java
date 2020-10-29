package com.alan.handsome.module.main.ui;

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
import com.alan.handsome.module.loans.ui.AuthenticationWorkActivity;
import com.alan.handsome.module.loans.ui.InfoAdapter;
import com.alan.handsome.module.main.bean.UserInfoBean;
import com.alan.handsome.module.main.constant.SaveInfoConstant;
import com.alan.handsome.module.main.presenter.SaveInfoPresenter;
import com.alan.handsome.user.DictsBean;
import com.alan.handsome.widget.DialogDatePickerSelect;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BasicInformation extends BaseActivity<SaveInfoPresenter> implements SaveInfoConstant.View {
    @BindView(R.id.full_name_edit)
    EditText fullNameEdit;
    @BindView(R.id.birthday_tv)
    TextView birthdayTv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.marital_tv)
    TextView maritalTv;
    @BindView(R.id.education_tv)
    TextView educationTv;
    @BindView(R.id.e_mail_edit)
    EditText eMailEdit;

    //时间选择器
    private DialogDatePickerSelect datePickerSelect;
    //列表选择
    private AlertDialog dialog;
    private InfoAdapter genderAdapter, maritalAdapter, educationAdapter;
    private List<DictsBean> genderList, maritalList, educationList;
    private String data;//选择的日期
    private static final int GENDER_TYPE = 101;
    private static final int MARITAL_TYPE = 102;
    private static final int EDUCATION_TYPE = 103;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_information;
    }

    @Override
    protected void initView() {
        showDialog();
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
            }
        });


        datePickerSelect = new DialogDatePickerSelect(this, new DialogDatePickerSelect.OnDateSelectCallBack() {
            @Override
            public void onDateSelected(int flag, int year, int month, int day, long time, String dateString) {
                switch (flag) {
                    //截止购买时间
                    case 1:
                        birthdayTv.setText(dateString);
                        data = dateString;
                        break;

                }
            }
        });

        //请求用户信息
        mPresenter.getUserInfo();
    }

    @Override
    protected SaveInfoPresenter createPresenter() {
        return new SaveInfoPresenter();
    }

    @OnClick({R.id.birthday_relate, R.id.gender_relate, R.id.marital_relate, R.id.education_relate, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //生日
            case R.id.birthday_relate:
                datePickerSelect.showDatePickView("Choose your birthday", 1);
                break;

            //性别
            case R.id.gender_relate:
                showSelectCarOrPhone("Choose gender", GENDER_TYPE);
                break;

            //婚姻
            case R.id.marital_relate:
                showSelectCarOrPhone("Marriage Status", MARITAL_TYPE);
                break;

            //教育
            case R.id.education_relate:
                showSelectCarOrPhone("Choose education", EDUCATION_TYPE);
                break;

            //保存数据
            case R.id.save_tv:

                if (TextUtils.isEmpty(fullNameEdit.getText().toString().trim())) {
                    showErrorToast("Please input full name");
                    return;
                }
                if (TextUtils.isEmpty(eMailEdit.getText().toString().trim())) {
                    showErrorToast("Please input  email");
                    return;
                }

                //保存数据
                showDialog();
                ReqBase base = new ReqBase();
                base.setEmail(eMailEdit.getText().toString().trim());
                base.setName(fullNameEdit.getText().toString().trim());
                base.setBirthday(data);

                for (DictsBean dictsBean : genderList) {
                    if (genderTv.getText().toString().equals(dictsBean.getName())) {
                        base.setGender(dictsBean.getValue());
                    }
                }

                for (DictsBean dictsBean : maritalList) {
                    if (maritalTv.getText().toString().equals(dictsBean.getName())) {
                        base.setMarital(dictsBean.getValue());
                    }
                }

                for (DictsBean dictsBean : educationList) {
                    if (educationTv.getText().toString().equals(dictsBean.getName())) {
                        base.setEducation(dictsBean.getValue());
                    }
                }

                mPresenter.commitBaseInfo(base);

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

    //获取用户信息
    @Override
    public void getUserInfoSuc(UserInfoBean userInfoBean) {
        hideDialog();
        if (userInfoBean != null) {
            if (!TextUtils.isEmpty(userInfoBean.getName())) {

                fullNameEdit.setText(userInfoBean.getName());
            }

            if (!TextUtils.isEmpty(userInfoBean.getBirthday())) {
                data = userInfoBean.getBirthday();
                birthdayTv.setText(userInfoBean.getBirthday());
            }

            if (!TextUtils.isEmpty(userInfoBean.getGender())) {

                genderTv.setText(userInfoBean.getGender());
                genderAdapter.setSelect(userInfoBean.getGender());
            }

            if (!TextUtils.isEmpty(userInfoBean.getMarital())) {

                maritalTv.setText(userInfoBean.getMarital());
                maritalAdapter.setSelect(userInfoBean.getMarital());
            }

            if (!TextUtils.isEmpty(userInfoBean.getEducation())) {

                educationTv.setText(userInfoBean.getEducation());
                educationAdapter.setSelect(userInfoBean.getEducation());
            }

            if (!TextUtils.isEmpty(userInfoBean.getEmail())) {

                eMailEdit.setText(userInfoBean.getEmail());

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
        hideDialog();
        showErrorToast("Save successfully");
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
