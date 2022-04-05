package com.square.pos.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.square.pos.R;
import com.square.pos.interfaces.OnImageClickListener;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
     Context mContext;
     List<String> imageList;
     OnImageClickListener imageClickListener;


    public SliderAdapter(Context context, List<String> imageList,
                         OnImageClickListener imageClickListener) {
        this.mContext = context;
        this.imageList = imageList;
        this.imageClickListener = imageClickListener;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1300)
                .setBaseAlpha(0.8f)
                .setHighlightAlpha(0.9f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Glide.with(mContext)
                .load(imageList.get(position))
                .placeholder(shimmerDrawable)
                .error(R.drawable.placeholder)
                .into(imageView);

        imageView.setOnClickListener(v ->
                imageClickListener.onImageClick(position)
        );

        imageView.setTag(position);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

}
