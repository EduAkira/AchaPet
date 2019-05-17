package br.com.achapet.Activity.Pet.Detalhe.SlideImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import br.com.achapet.R;

public class SlideImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List mResources;

    public SlideImagePagerAdapter(Context context, List resources) {
        mContext = context;
        mResources = resources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View slideImege = mLayoutInflater.inflate(R.layout.pet_detalhe_slide, container, false);

        ImageView imageView = (ImageView) slideImege.findViewById(R.id.detalhe_slide_image);
        container.addView(slideImege);

        return slideImege;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}