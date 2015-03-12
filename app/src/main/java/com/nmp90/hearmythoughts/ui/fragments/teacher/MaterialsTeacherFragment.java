package com.nmp90.hearmythoughts.ui.fragments.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nmp90.hearmythoughts.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import us.feras.mdv.MarkdownView;

/**
 * Created by nmp on 15-3-12.
 */
public class MaterialsTeacherFragment extends Fragment {

    @InjectView(R.id.et_markdown)
    EditText etMarkdown;

    @InjectView(R.id.markdownView)
    MarkdownView markdownView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materials_teacher, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

    @OnTextChanged(R.id.et_markdown)
    public void previewMarkdown() {
        markdownView.loadMarkDownData(etMarkdown.getText().toString());
    }

    @OnClick(R.id.btn_send)
    public void saveMarkdown(View view) {
        markdownView.loadMarkDownData(etMarkdown.getText().toString());
    }
}
