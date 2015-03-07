package com.nmp90.hearmythoughts.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import com.nmp90.hearmythoughts.R;

/**
 * Created by nmp on 15-3-2.
 */
public class RobotoButton extends Button {

    public RobotoButton(Context context) {
        super(context);
    }

    public RobotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context, attrs);
    }

    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeFace(context, attrs);
    }

    private void setTypeFace(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RobotoTextView);
        String robotoFont = a.getString(R.styleable.RobotoButton_robo_button_typeface);
        if (TextUtils.isEmpty(robotoFont)) {
            robotoFont = context.getString(R.string.Roboto_Regular);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + robotoFont
                + ".ttf");
        setTypeface(font);
        a.recycle();
    }

}
