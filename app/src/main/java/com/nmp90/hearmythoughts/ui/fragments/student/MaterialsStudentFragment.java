package com.nmp90.hearmythoughts.ui.fragments.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.hearmythoughts.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import us.feras.mdv.MarkdownView;

/**
 * Created by nmp on 15-3-12.
 */
public class MaterialsStudentFragment extends Fragment {
    private static final String KEY_MARKDOWN = "markdown";

    @InjectView(R.id.markdownView)
    MarkdownView markdownView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materials_student, container, false);
        ButterKnife.inject(this, view);
        markdownView.loadMarkDownData("# Project requirements \n 1. Register in [DELC](http://delc.fmi.uni-plovdiv.net/)\n " +
                "2. Project planning\n" +
                "  1. Choose a team member\n" +
                "  2. Choose topic\n" +
                " 3. Start working\n" +
                " 4. Do not give up\n" +
                " 5. Write documentation\n" +
                " 6. Great job!\n");

        return view;
    }
}
