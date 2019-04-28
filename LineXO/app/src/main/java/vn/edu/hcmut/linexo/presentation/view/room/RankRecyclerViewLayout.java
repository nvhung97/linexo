package vn.edu.hcmut.linexo.presentation.view.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import vn.edu.hcmut.linexo.R;

public class RankRecyclerViewLayout extends FrameLayout {

    private int defaultMargin;

    private View lstRank;
    private View btnClose;

    public RankRecyclerViewLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RankRecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankRecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        defaultMargin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        lstRank = findViewById(R.id.lst_rank);
        btnClose = findViewById(R.id.btn_close);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec) * 10 / 11;
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        lstRank.measure(
                width | MeasureSpec.EXACTLY,
                height / 2 | MeasureSpec.EXACTLY
        );
        btnClose.measure(
                width-defaultMargin*2 | MeasureSpec.EXACTLY,
                width / 4 | MeasureSpec.EXACTLY
        );

        setMeasuredDimension(
                width,
                lstRank.getMeasuredHeight() + btnClose.getMeasuredHeight()+defaultMargin
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int l = left;
        int t = top;
        lstRank.layout(l, t, l + lstRank.getMeasuredWidth(), t + lstRank.getMeasuredHeight());

        l = (getMeasuredWidth()-btnClose.getMeasuredWidth())/2;
        t += lstRank.getMeasuredHeight();
        btnClose.layout(l, t, l + btnClose.getMeasuredWidth(), t + btnClose.getMeasuredHeight());
    }
}