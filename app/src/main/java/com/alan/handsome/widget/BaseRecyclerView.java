package com.alan.handsome.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

public class BaseRecyclerView extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            RecyclerView.Adapter<?> adapter =  getAdapter();
            //LogUtils.e("is recycler view empty." + adapter.getItemCount() + adapter + emptyView);
            if(adapter != null && emptyView != null) {
                if(adapter.getItemCount() == 0) {
                    LogUtils.e("recycler view is empty.");
                    emptyView.setVisibility(View.VISIBLE);
                    BaseRecyclerView.this.setVisibility(View.GONE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    BaseRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(Context context, View emptyView, String text) {
        this.emptyView = emptyView;
        if (emptyView instanceof  TextView) {
            ((TextView) emptyView).setText(text);
        }
        //((ViewGroup) this.getRootView()).addView(emptyView);
    }
}