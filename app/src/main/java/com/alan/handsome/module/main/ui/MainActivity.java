package com.alan.handsome.module.main.ui;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.handsome.R;
import com.alan.handsome.base.BaseActivity;
import com.alan.handsome.base.BaseContract;
import com.alan.handsome.manager.FragmentViewPagerAdapter;
import com.alan.handsome.widget.NoScrollViewPager;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.home_img)
    ImageView homeImg;
    @BindView(R.id.home_txt)
    TextView homeTxt;
    @BindView(R.id.home_relate)
    LinearLayout homeRelate;
    @BindView(R.id.mine_img)
    ImageView mineImg;
    @BindView(R.id.mine_txt)
    TextView mineTxt;
    @BindView(R.id.mine_relate)
    LinearLayout mineRelate;

    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private List<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager.setCanScroll(false);
    }

    @Override
    protected void initData() {

        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(mineFragment);
        viewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(2);
        setPage(0);
    }

    @Override
    protected BaseContract.BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.home_relate, R.id.mine_relate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_relate:
                setPage(0);
                break;
            case R.id.mine_relate:
                setPage(1);
                break;
        }
    }

    /**
     * 点击下面切换UI
     *
     * @param page
     */
    private void setPage(int page) {
        viewPager.setCurrentItem(page, false);
        homeImg.setSelected(false);
        homeTxt.setSelected(false);
        mineImg.setSelected(false);
        mineTxt.setSelected(false);
        if (page == 0) {
            homeImg.setSelected(true);
            homeTxt.setSelected(true);

            mImmersionBar = ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(true, 0.2f)
                    .statusBarColor(R.color.white);
            mImmersionBar.init();

        } else if (page == 1) {
            mineImg.setSelected(true);
            mineTxt.setSelected(true);

            mImmersionBar = ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarDarkFont(true, 0.2f)
                    .statusBarColor(R.color.color_55EDCF);
            mImmersionBar.init();
        }
    }

    //监听返回键
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
