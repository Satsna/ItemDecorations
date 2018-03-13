package com.sheldonchen.itemdecorations.decorations;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cxd on 2018/3/7
 */

@IntDef({LinearLayoutManager.VERTICAL, LinearLayoutManager.HORIZONTAL})
@Retention(RetentionPolicy.SOURCE)
public @interface DecorationOrientType {

}
