package com.alan.handsome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alan.handsome.R;


/**
 * description：titleBar
 * date:2020/3/11
 */
public class TitleBar extends LinearLayout {
    private String title;
    private boolean back;
    private int textRightColor;
    private int bgColor;
    private int titleColor;
    private String rightTextStr;
    private TextView textViewTitle;
    private TextView textViewRight;
    private ImageView imageViewRight;
    private ImageView imageViewBack;
    private RelativeLayout titleRL;
    private boolean haveRightIcon;
    private int rightIconSrc;
    private int leftIconSrc;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.TitleBar);
        back = typedArray.getBoolean(R.styleable.TitleBar_have_back, true);
        title = typedArray.getString(R.styleable.TitleBar_title);
        textRightColor = typedArray.getColor(R.styleable.TitleBar_right_text_color, ContextCompat.getColor(context, R.color.color_121624));
        titleColor = typedArray.getColor(R.styleable.TitleBar_title_color, ContextCompat.getColor(context, R.color.color_121624));
        bgColor = typedArray.getColor(R.styleable.TitleBar_bg_color, ContextCompat.getColor(context, R.color.white));
        rightTextStr = typedArray.getString(R.styleable.TitleBar_right_text);
        rightIconSrc = typedArray.getResourceId(R.styleable.TitleBar_right_icon_src, R.mipmap.back );
        leftIconSrc = typedArray.getResourceId(R.styleable.TitleBar_left_icon_src, R.mipmap.back);
        haveRightIcon = typedArray.getBoolean(R.styleable.TitleBar_have_right_icon, false);
        initView();
        typedArray.recycle();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar_layout, this);
        textViewTitle = findViewById(R.id.tv_titlebar_title);
        textViewRight = findViewById(R.id.tv_title_right);
        imageViewRight = findViewById(R.id.iv_right_content);
        imageViewBack = findViewById(R.id.iv_back);
        titleRL = findViewById(R.id.title_bar_rl);
        if (!TextUtils.isEmpty(title)) {
            textViewTitle.setText(title);
        }

        if (back) {
            imageViewBack.setVisibility(VISIBLE);
        } else {
            imageViewBack.setVisibility(GONE);
        }
        titleRL.setBackgroundColor(bgColor);
        textViewTitle.setTextColor(titleColor);
        textViewRight.setTextColor(textRightColor);
        imageViewBack.setImageResource(leftIconSrc);


        if (!TextUtils.isEmpty(rightTextStr)) {
            textViewRight.setText(rightTextStr);
            textViewRight.setVisibility(VISIBLE);
        } else {
            textViewRight.setVisibility(GONE);
        }

        if (haveRightIcon) {
            imageViewRight.setVisibility(VISIBLE);
            imageViewRight.setImageResource(rightIconSrc);
        } else {
            imageViewRight.setVisibility(GONE);
        }
    }

    public void setOnRightTextClickListener(OnClickListener listener) {
        textViewRight.setOnClickListener(listener);
    }

    public void setRightImage(int imageSrc) {
        if (imageSrc != 0) {
            imageViewRight.setImageResource(imageSrc);
        }
    }

    public void setRightText(String text) {
        rightTextStr = text;
        if (!TextUtils.isEmpty(rightTextStr)) {
            textViewRight.setText(rightTextStr);
            textViewRight.setVisibility(VISIBLE);
        } else {
            textViewRight.setVisibility(GONE);
        }
    }

    public void setRightTxtVisible(boolean isVisible) {
        textViewRight.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setOnRightImgClickListener(OnClickListener listener) {
        imageViewRight.setOnClickListener(listener);
    }

    public void setOnBackClickListener(OnClickListener listener) {
        imageViewBack.setOnClickListener(listener);
    }

    public void setTitle(String text) {
        title = text;
        textViewTitle.setText(text);
    }

    /**
     * @param resource 传的是R.dimen.sp_18里面的资源
     */
    public void setRightTxtSize(int resource) {
        textViewRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(resource));
    }

    /**
     * @param resource 传的是R.dimen.sp_18里面的资源
     */
    public void setTitleTxtSize(int resource) {
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(resource));
    }

}
