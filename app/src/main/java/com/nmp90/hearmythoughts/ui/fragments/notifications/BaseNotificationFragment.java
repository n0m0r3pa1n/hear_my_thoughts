package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.nmp90.hearmythoughts.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class BaseNotificationFragment extends Fragment {
    private Activity activity;

    private RelativeLayout notificationLayout;
    private RelativeLayout commonLayout;

    @OnClick(R.id.btn_dismiss)
    void dismissDialog() {
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        getActivity().getSupportFragmentManager().beginTransaction().remove(fragments.get(fragments.size()-1)).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    protected View getInflatedView(LayoutInflater inflater, ViewGroup container,  int viewIdToAdd) {
        View view = inflater.inflate(R.layout.view_notifications, container, false);
        View viewToAdd = inflater.inflate(viewIdToAdd, container, false);
        notificationLayout = (RelativeLayout) viewToAdd.findViewById(R.id.layout_notification);
        if(notificationLayout == null) {
            throw new NullPointerException("All notifications share a common layout layout_notification which is missing");
        }

        commonLayout = (RelativeLayout) view.findViewById(R.id.layout_notifications);
        commonLayout.addView(viewToAdd, 0, viewToAdd.getLayoutParams());
        ButterKnife.inject(this, commonLayout);

        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            Animation enterAnim = AnimationUtils.loadAnimation(activity, R.anim.alpha_in);
            enterAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation notificationEnterAnim = AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_in_top);
                    notificationEnterAnim.setFillAfter(true);
                    notificationLayout.startAnimation(notificationEnterAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            return enterAnim;
        } else {
            Animation outAnim = AnimationUtils.loadAnimation(activity, R.anim.alpha_out);
            notificationLayout.startAnimation(AnimationUtils.loadAnimation(activity,
                    R.anim.slide_out_top));
            return outAnim;
        }
    }
}
