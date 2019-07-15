package br.com.achapet.Activity.Pet.slideImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import br.com.achapet.R;

public class SlideUriAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List mResources;

    public SlideUriAdapter(Context context, List resources) {
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
        View slideImege = mLayoutInflater.inflate(R.layout.include_img, container, false);

        String uri = (String) mResources.get(position);
        SimpleDraweeView slideImegeView = (SimpleDraweeView) slideImege.findViewById(R.id.include_image);
        slideImegeView.setImageURI(uri);
        container.addView(slideImege);

        return slideImege;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}