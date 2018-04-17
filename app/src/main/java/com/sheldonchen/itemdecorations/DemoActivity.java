package com.sheldonchen.itemdecorations;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sheldonchen.itemdecorations.decorations.GridLayoutDivider;
import com.sheldonchen.itemdecorations.decorations.LinearLayoutDivider;

/**
 * Created by cxd on 2018/4/17
 */

public class DemoActivity extends AppCompatActivity {

    public static final String ARG_TYPE = "arg_type";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        int type = getIntent().getIntExtra(ARG_TYPE, 0);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(getLayoutManager(type));

        RecyclerView.ItemDecoration itemDecoration = getItemDecoration(type);
        rv.addItemDecoration(itemDecoration);

        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(new TestAdapter());
    }

    private RecyclerView.LayoutManager getLayoutManager(int type) {
        switch (type) {
            case 0:
            default:
                return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            case 1:
                return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            case 2:
                return new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
            case 3:
                return new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        }
    }

    private RecyclerView.ItemDecoration getItemDecoration(int type) {
        if(type == 0) {
            Drawable dividerDrawable = getResources().getDrawable(R.drawable.shape_divider_vertical);

            return new LinearLayoutDivider.Builder()
                    .setOrientation(LinearLayoutManager.VERTICAL)
                    .drawFirstDivider(true)
                    .drawLastDivider(true)
                    .setDividerDrawable(dividerDrawable)
                    .setDividerThickness(dividerDrawable.getIntrinsicHeight())
                    .build();
        } else if(type == 1) {
            Drawable dividerDrawable = getResources().getDrawable(R.drawable.shape_divider_horizontal);

            return new LinearLayoutDivider.Builder()
                    .setOrientation(LinearLayoutManager.HORIZONTAL)
                    .drawFirstDivider(true)
                    .drawLastDivider(true)
                    .setDividerDrawable(dividerDrawable)
                    .setDividerThickness(dividerDrawable.getIntrinsicWidth())
                    .build();
        } else if(type == 2) {
            return new GridLayoutDivider.Builder()
                    .setOrientation(LinearLayoutManager.VERTICAL)
                    .drawTopSideDivider(true)
                    .drawBottomSideDivider(true)
                    .drawTwoSidesDivider(true)
                    .setDividerColor(Color.parseColor("#FFCCCCCC"))
                    .setSideDividerColor(Color.parseColor("#FF333333"))
                    .setDividerThickness(15)
                    .setSideDividerThickness(20)
                    .build();
        } else if(type == 3) {
            return new GridLayoutDivider.Builder()
                    .setOrientation(LinearLayoutManager.HORIZONTAL)
                    .drawTopSideDivider(true)
                    .drawBottomSideDivider(true)
                    .drawTwoSidesDivider(true)
                    .setDividerColor(Color.parseColor("#FFCCCCCC"))
                    .setSideDividerColor(Color.parseColor("#FF333333"))
                    .setDividerThickness(15)
                    .setSideDividerThickness(20)
                    .build();
        }

        return null;
    }
}
