package com.alan.handsome.module.main.ui;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.net.RetrofitManger;
import com.alan.handsome.utils.RxSchedulers;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.feedback_edit)
    EditText feedbackEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
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

    }

    @Override
    protected void initData() {
        String content = "";
        if (!TextUtils.isEmpty(AccountManager.getInstance().getSysInfo().getSys_service_email())) {
            content = AccountManager.getInstance().getSysInfo().getSys_service_email();
        }

        contentTv.setText("Any questions about the App, please contact us by E-mail, \n" + "E-mail: " + content);

    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }


    @OnClick(R.id.commit_tv)
    public void onClick() {

        if (TextUtils.isEmpty(feedbackEdit.getText().toString().trim())){
            showErrorToast("Please input feedback");
            return;
        }

        showDialog();
        feedBack(feedbackEdit.getText().toString().trim());
    }

    //反馈信息
    public void feedBack(String content) {

        Map<String, Object> map = new HashMap<>();
        map.put("content", content);

        RetrofitManger.getInstance().create().feedBack(map)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(new Consumer<Map<String, Object>>() {
                    @Override
                    public void accept(Map<String, Object> resultMap) throws Exception {
                        hideDialog();
                        if ((double) resultMap.get("status") == 1) {
                            showErrorToast("Feedback success");
                            finish();
                        } else {
                            showErrorToast((String) resultMap.get("message"));
                        }

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideDialog();
                        showErrorToast(throwable.getMessage());
                    }
                });

    }
}
