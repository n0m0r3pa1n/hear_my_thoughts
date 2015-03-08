package com.nmp90.hearmythoughts.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.nmp90.hearmythoughts.R;

public class RobotoEditText extends EditText {
    public RobotoEditText(Context context) {
        super(context);
    }

    public RobotoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context, attrs);
    }

    public RobotoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeFace(context, attrs);
    }

    private void setTypeFace(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RobotoTextView);
        String robotoFont = a.getString(R.styleable.RobotoEditText_robo_edit_typeface);
        if (TextUtils.isEmpty(robotoFont)) {
            robotoFont = context.getString(R.string.Roboto_Regular);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + robotoFont
                + ".ttf");
        setTypeface(font);
        setTextColor(getResources().getColor(R.color.white));
        a.recycle();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setTextColor(getContext().getResources().getColor(
                enabled ? R.color.text_grey : R.color.text_disabled_grey));
    }

}
