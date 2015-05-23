package com.nmp90.hearmythoughts.ui.models;

import android.graphics.drawable.Drawable;

/**
 * Created by nmp on 15-3-11.
 */
public class NavigationItem {
    private String mText;
    private Drawable mDrawable;
    private Drawable mSelectedDrawable;

    public NavigationItem(String text, Drawable drawable) {
        mText = text;
        mDrawable = drawable;
    }

    public NavigationItem(String text, Drawable drawable, Drawable selectedDrawable) {
        mText = text;
        mDrawable = drawable;
        mSelectedDrawable = selectedDrawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public Drawable getSelectedDrawable() {
        return mSelectedDrawable;
    }

    public void setSelectedDrawable(Drawable selectedDrawable) {
        mSelectedDrawable = selectedDrawable;
    }
}
