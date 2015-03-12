package com.nmp90.hearmythoughts.ui.fragments.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
    private static final String KEY_MARKDOWN = "markdown";

    @InjectView(R.id.et_markdown)
    EditText etMarkdown;

    @InjectView(R.id.markdownView)
    MarkdownView markdownView;

    @InjectView(R.id.btn_send)
    ImageView btnSend;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materials_teacher, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MARKDOWN, etMarkdown.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            etMarkdown.setText(savedInstanceState.getString(KEY_MARKDOWN));
        }
    }

    @OnTextChanged(R.id.et_markdown)
    public void previewMarkdown() {
        String text = etMarkdown.getText().toString();
        if(TextUtils.isEmpty(text)) {
            btnSend.setImageResource(R.drawable.ic_send);
        } else {
            btnSend.setImageResource(R.drawable.ic_send_orange);
        }

        markdownView.loadMarkDownData(etMarkdown.getText().toString());
    }

    @OnClick(R.id.btn_send)
    public void saveMarkdown(View view) {
        markdownView.loadMarkDownData(etMarkdown.getText().toString());
    }
}
