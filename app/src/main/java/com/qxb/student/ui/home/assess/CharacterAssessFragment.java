package com.qxb.student.ui.home.assess;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.qxb.student.R;
import com.qxb.student.common.basics.AbsExpandFragment;

/**
 * 性格测评
 *
 * @author winky
 * @date 2018/8/17
 */
public class CharacterAssessFragment extends AbsExpandFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_character_assess;
    }

    @Override
    public void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView image1 = view.findViewById(R.id.image1);
        ImageView image2 = view.findViewById(R.id.image2);
        image1.setOnClickListener(clickListener);
        image2.setOnClickListener(clickListener);
        image1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.down_anim));
        image2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.up_anim));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //MBTI
                case R.id.image1:

                    break;
                //霍兰德
                case R.id.image2:

                    break;
                default:
                    break;
            }
        }
    };
}
