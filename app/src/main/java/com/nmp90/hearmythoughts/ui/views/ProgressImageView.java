package com.nmp90.hearmythoughts.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nmp90.hearmythoughts.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nmp on 15-3-12.
 */
public class ProgressImageView extends FrameLayout {
    public static final String TAG = ProgressImageView.class.getSimpleName();

    private boolean isLoading = false;

    private TypedArray array;
    private Context context;
    private Drawable resId;

    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectView(R.id.iv_circle)
    CircleImageView imageView;

    public ProgressImageView(Context context) {
        super(context);
        this.context = context;
        if (!isInEditMode()) {
            init(context);
        }
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (!isInEditMode()) {
            initElementAttributes(attrs);
            init(context);
        }
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if (!isInEditMode()) {
            initElementAttributes(attrs);
            init(context);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        if (!isInEditMode()) {
            initElementAttributes(attrs);
            init(context);
        }
    }

    protected void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View initView = inflater.inflate(R.layout.view_progress_image, null);
        if (initView != null) {
            addView(initView);
        }

        ButterKnife.inject(this, initView);

        imageView .setImageDrawable(resId);
        setLoading(isLoading);
    }

    private void initElementAttributes(AttributeSet attrs) {
        array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ProgressImageView, 0, 0);
        try {
            resId = array.getDrawable(R.styleable.ProgressImageView_image);
            isLoading = array.getBoolean(R.styleable.ProgressImageView_loading, false);
        } catch(Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            array.recycle();
        }
    }

    public void setLoading(boolean shouldBeLoading) {
        isLoading = shouldBeLoading;
        if(shouldBeLoading) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(INVISIBLE);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

}
