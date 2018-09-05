package com.qxb.student.ui.home.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qxb.student.R;
import com.qxb.student.adapter.SchoolLibraryRetrieveAdapter;
import com.qxb.student.common.basics.AbsToolbarFragment;

/**
 * @author winky
 * @date 2018/9/4
 */
public class SchoolLibraryRetrieveFragment extends AbsToolbarFragment {


    @Override
    public int bindContentView() {
        return R.layout.view_recycler;
    }

    @Override
    public void initContent(View contentView, @Nullable Bundle savedInstanceState) {
        setTitle(R.string.title_school_library);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new SchoolLibraryRetrieveAdapter(this));
    }
}
