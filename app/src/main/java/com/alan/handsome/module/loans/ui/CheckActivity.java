package com.alan.handsome.module.loans.ui;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.manager.AccountManager;
import com.alan.handsome.module.loans.bean.LoansBean;
import com.alan.handsome.module.main.constant.LoansPrepareConstant;
import com.alan.handsome.module.main.presenter.LoansPreparePresenter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核界面
 */
public class CheckActivity extends BaseActivity<LoansPreparePresenter> implements LoansPrepareConstant.View {
    @BindView(R.id.processing_iv)
    ImageView processingIv;
    @BindView(R.id.tip_one_tv)
    TextView tipOneTv;
    @BindView(R.id.tip_two_tv)
    TextView tipTwoTv;
    @BindView(R.id.refresh_tv)
    TextView refreshTv;

    //定时请求后台的审核状态
    private Timer mTimer;
    private TimerTask mTimerTask;
    private static final int REQUEST_TYPE = 102;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    protected void initView() {
        tipOneTv.setText("Processing..");
        if (!TextUtils.isEmpty(AccountManager.getInstance().getSysInfo().getTips_processing())) {
            tipTwoTv.setText(AccountManager.getInstance().getSysInfo().getTips_processing());
        }
        refreshTv.setText("Refresh");
    }

    @Override
    protected void initData() {
        initTimer();
        // 参数：0，延时0秒后执行;3000，每隔3秒执行1次task。
        mTimer.schedule(mTimerTask, 0, 3 * 1000);
    }

    // 初始化Timer
    public void initTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = REQUEST_TYPE;
                mHandler.sendMessage(message);

            }
        };

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_TYPE:
                    //轮询请求审核状态
                    mPresenter.getProduct();
                    break;

            }
        }
    };


    //onDestroy上使用的
    public void destroyTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

    }

    @Override
    protected LoansPreparePresenter createPresenter() {
        return new LoansPreparePresenter();
    }

    @OnClick(R.id.refresh_tv)
    public void onViewClicked() {
        showDialog();
        mPresenter.getProduct();

    }

    //获取审核状态
    @Override
    public void getProductSuc(LoansBean loansBean) {
        hideDialog();
        if (loansBean != null) {
            if (loansBean.getPhase() == 2) {
                //审核通过
                AccountManager.getInstance().saveAuthenticationType(2,-1);
                startToActivity(PassSuccessActivity.class);
                destroyTimer();
                finish();
            }
        }

    }

    @Override
    public void getProductFail(String msg) {
        hideDialog();
        showErrorToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyTimer();
    }
}
