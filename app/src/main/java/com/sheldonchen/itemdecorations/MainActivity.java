package com.sheldonchen.itemdecorations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheldonchen.itemdecorations.decorations.GridLayoutDividerDecoration;
import com.sheldonchen.itemdecorations.decorations.LinearLayoutDividerDecoration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    private LinearLayoutManager vLinearLayoutManager;
    private LinearLayoutManager hLinearLayoutManager;
    private GridLayoutManager vGridLayoutManager;
    private GridLayoutManager hGridLayoutManager;

    private LinearLayoutDividerDecoration linearDecoration;
    private GridLayoutDividerDecoration gridDecoration;

    private RecyclerView.ItemDecoration mCurrentDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);

        vLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        hGridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        linearDecoration = new LinearLayoutDividerDecoration()
                .setOrientation(LinearLayoutManager.VERTICAL)
                .setStartPadding(0)
                .setEndPadding(0)
                .setDividerColor(Color.parseColor("#FF888888"))
                .setDrawFirstDivider(true)
                .setDrawLastDivider(true)
                .setDividerWidth(20);

        gridDecoration = new GridLayoutDividerDecoration()
                .setOrientation(GridLayoutManager.VERTICAL)
                .setDividerWidth(20)
                .setSideDividerWidth(40)
                .drawTopSideDivider(true)
                .drawBottomSideDivider(true)
                .drawSideDivider(true)
                .setDividerColor(Color.parseColor("#FF888888"));

        rv.setLayoutManager(vLinearLayoutManager);
        rv.addItemDecoration(linearDecoration);
        mCurrentDecoration = linearDecoration;
        rv.setHasFixedSize(true);
        rv.setAdapter(new InnerAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear_vertical:
                rv.setLayoutManager(vLinearLayoutManager);
                if(mCurrentDecoration != null) {
                    rv.removeItemDecoration(mCurrentDecoration);
                }
                linearDecoration.setOrientation(LinearLayoutManager.VERTICAL);
                rv.addItemDecoration(linearDecoration);
                mCurrentDecoration = linearDecoration;
                return true;

            case R.id.linear_horizontal:
                rv.setLayoutManager(hLinearLayoutManager);
                if(mCurrentDecoration != null) {
                    rv.removeItemDecoration(mCurrentDecoration);
                }
                linearDecoration.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv.addItemDecoration(linearDecoration);
                mCurrentDecoration = linearDecoration;
                return true;

            case R.id.grid_vertical:
                rv.setLayoutManager(vGridLayoutManager);
                if(mCurrentDecoration != null) {
                    rv.removeItemDecoration(mCurrentDecoration);
                }
                gridDecoration.setOrientation(LinearLayoutManager.VERTICAL);
                rv.addItemDecoration(gridDecoration);
                mCurrentDecoration = gridDecoration;
                return true;

            case R.id.grid_horizontal:
                rv.setLayoutManager(hGridLayoutManager);
                if(mCurrentDecoration != null) {
                    rv.removeItemDecoration(mCurrentDecoration);
                }
                gridDecoration.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv.addItemDecoration(gridDecoration);
                mCurrentDecoration = gridDecoration;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class InnerAdapter extends RecyclerView.Adapter<CommonViewHolder> {

        @Override
        public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommonViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
        }

        @Override
        public void onBindViewHolder(CommonViewHolder holder, int position) {
            TextView textView = holder.findView(R.id.tv);
            textView.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 49;
        }
    }
}
