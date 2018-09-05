package com.qxb.student.adapter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.qxb.student.R;
import com.qxb.student.common.module.bean.CollegeBatch;
import com.qxb.student.common.module.bean.CollegeProvinces;
import com.qxb.student.common.module.bean.CollegeTag;
import com.qxb.student.common.module.bean.SchoolCondition;
import com.qxb.student.common.utils.ShapeUtils;
import com.qxb.student.common.view.abslist.adapter.AbsAdapter;
import com.qxb.student.common.view.recycler.ViewHolder;
import com.qxb.student.common.view.recycler.adapter.NestingAdapter;
import com.qxb.student.control.SchoolLibraryRetrieveControl;

/**
 * @author winky
 * @date 2018/9/4
 */
public class SchoolLibraryRetrieveAdapter extends NestingAdapter {

    private final int EMPHASES = 0;
    private final int TAG1 = 1;
    private final int BATCH = 2;
    private final int TAG2 = 3;
    private final int REGION = 4;


    private AbsAdapter<CollegeTag> tagAdapter;
    private AbsAdapter<CollegeBatch> batchAdapter;
    private AbsAdapter<CollegeProvinces> provincesAdapter;

    public SchoolLibraryRetrieveAdapter(Fragment fragment) {
        SchoolLibraryRetrieveControl control = ViewModelProviders.of(fragment).get(SchoolLibraryRetrieveControl.class);

        addItemType(EMPHASES, R.layout.view_grid);
        addItemType(TAG1, R.layout.view_module_tag);
        addItemType(BATCH, R.layout.view_grid);
        addItemType(TAG2, R.layout.view_module_tag);
        addItemType(REGION, R.layout.view_grid);

        tagAdapter = new AbsAdapter<CollegeTag>(fragment.getContext(), R.layout.item_school_library_retrieve) {
            @Override
            protected void bindView(View view, int position, CollegeTag item) {
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(item.getTag_name());
                ShapeUtils.getInstance()
                        .setNormalColor(R.color.assess_hollander_a)
                        .setPressColor(R.color.press_a)
                        .setRadius(10)
                        .intoView(text1);
            }
        };
        batchAdapter = new AbsAdapter<CollegeBatch>(fragment.getContext(), R.layout.item_school_library_retrieve) {
            @Override
            protected void bindView(View view, int position, CollegeBatch item) {
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(item.getBat_name());
                ShapeUtils.getInstance()
                        .setNormalColor(R.color.assess_hollander_r)
                        .setPressColor(R.color.press_r)
                        .setRadius(10)
                        .intoView(text1);
            }
        };
        provincesAdapter = new AbsAdapter<CollegeProvinces>(fragment.getContext(), R.layout.item_school_library_retrieve) {
            @Override
            protected void bindView(View view, int position, CollegeProvinces item) {
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(item.getProvince_name());
                ShapeUtils.getInstance()
                        .setNormalColor(R.color.assess_hollander_c)
                        .setPressColor(R.color.press_c)
                        .setRadius(10)
                        .intoView(text1);
            }
        };
        control.getSchoolLibPage().observe(fragment, new Observer<SchoolCondition>() {
            @Override
            public void onChanged(@Nullable SchoolCondition schoolCondition) {
                if (schoolCondition != null) {
                    tagAdapter.addCollection(schoolCondition.getTags());
                    tagAdapter.notifyDataSetChanged();
                    batchAdapter.addCollection(schoolCondition.getBats());
                    batchAdapter.notifyDataSetChanged();
                    provincesAdapter.addCollection(schoolCondition.getProvinces());
                    provincesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setGridView(GridView gridView) {
        gridView.setNumColumns(2);
        gridView.setVerticalSpacing(20);
        gridView.setHorizontalSpacing(40);
        gridView.setPadding(40, 40, 40, 20);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case EMPHASES: {
                GridView gridView = holder.getView(R.id.gridView);
                if (gridView.getAdapter() == null) {
                    setGridView(gridView);
                    gridView.setAdapter(tagAdapter);
                }
            }
            break;
            case TAG1:
                holder.setImageResource(R.id.tag_image, R.mipmap.zzpc);
                holder.setText(R.id.tag_text, R.string.hint_school_library_tag1);
                break;
            case BATCH: {
                GridView gridView = holder.getView(R.id.gridView);
                if (gridView.getAdapter() == null) {
                    setGridView(gridView);
                    gridView.setAdapter(batchAdapter);
                }
            }
            break;
            case TAG2:
                holder.setImageResource(R.id.tag_image, R.mipmap.szd);
                holder.setText(R.id.tag_text, R.string.hint_school_library_tag2);
                break;
            case REGION: {
                GridView gridView = holder.getView(R.id.gridView);
                if (gridView.getAdapter() == null) {
                    setGridView(gridView);
                    gridView.setAdapter(provincesAdapter);
                }
            }
            break;
            default:
                break;
        }
    }
}
