package com.drawermenuitem;

/**
 * Created by Zeki Guler on 03,June,2016
 * ©2015 Appscore. All Rights Reserved
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A compound view for nav drawer items.  This neatly encapsulates states, tinting text and
 * icons and setting a background when in state_activated.
 */
public class NavDrawerItemView extends ForegroundLinearLayout {

    private int mPaddingBetweenItem = 7; // 7dp
    private boolean mIsHorizontal = false;
    private ColorStateList mIconTints;
    private ColorStateList mTextTints;
    private @DrawableRes int mIconResId = 0;
    private String mItemText = "";

    private ImageView navIcon;
    private TextView navTextView;
    private int mGravity;

    public NavDrawerItemView(Context context) {
        this(context, null);
    }

    public NavDrawerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.navdrawer_item_view, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavDrawerItemView);
        if (a.hasValue(R.styleable.NavDrawerItemView_iconTints)) {
            mIconTints = a.getColorStateList(R.styleable.NavDrawerItemView_iconTints);
        }
        if (a.hasValue(R.styleable.NavDrawerItemView_textTints)) {
            mTextTints = a.getColorStateList(R.styleable.NavDrawerItemView_textTints);
        }

        if (a.hasValue(R.styleable.NavDrawerItemView_navItemIcon)){
            mIconResId = a.getResourceId(R.styleable.NavDrawerItemView_navItemIcon, 0);
        }
        if (a.hasValue(R.styleable.NavDrawerItemView_navItemText)){
            mItemText = a.getString(R.styleable.NavDrawerItemView_navItemText);
        }

        if (a.hasValue(R.styleable.NavDrawerItemView_isHorizontal)){
            mIsHorizontal = a.getBoolean(R.styleable.NavDrawerItemView_isHorizontal, false);
        }

        if (a.hasValue(R.styleable.NavDrawerItemView_navPaddingDrawable)){
            mPaddingBetweenItem = a.getDimensionPixelSize(R.styleable.NavDrawerItemView_navPaddingDrawable, mPaddingBetweenItem);
        }

        mGravity = mIsHorizontal ? Gravity.CENTER_VERTICAL | Gravity.START : Gravity.CENTER_HORIZONTAL;
        setOrientation(mIsHorizontal?HORIZONTAL:VERTICAL);

        navTextView =  ((TextView) findViewById(R.id.title));
        navIcon  = ((ImageView) findViewById(R.id.icon));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        LayoutParams iconParams = (LayoutParams) navIcon.getLayoutParams();
        LayoutParams textParams = (LayoutParams) navTextView.getLayoutParams();

        iconParams.gravity = mGravity;
        textParams.gravity = mGravity;

        if (mIsHorizontal){
            textParams.leftMargin = mPaddingBetweenItem;
        }
        else{
            navTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            textParams.topMargin = mPaddingBetweenItem;
        }

        navIcon.setLayoutParams(iconParams);
        navTextView.setLayoutParams(textParams);

        setContents();


        invalidate();
    }

    private void setContents() {
        if (mIconResId != 0){
            Drawable icon = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), mIconResId));

            if (mIconTints != null) {
                DrawableCompat.setTintList(icon, mIconTints);
            }

            ((ImageView) findViewById(R.id.icon)).setImageDrawable(icon);
        }

        if (!mItemText.isEmpty()){
            ((TextView) findViewById(R.id.title)).setText(mItemText);
            ((TextView) findViewById(R.id.title)).setTextColor(mTextTints);
        }
    }

//    public void setContent(@DrawableRes int iconResId, @StringRes int titleResId) {
//        if (iconResId > 0) {
//            Drawable icon = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), iconResId));
//            if (mIconTints != null) {
//                DrawableCompat.setTintList(icon, mIconTints);
//            }
//            ((ImageView) findViewById(R.id.icon)).setImageDrawable(icon);
//        }
//        ((TextView) findViewById(R.id.title)).setText(titleResId);
//    }

}
