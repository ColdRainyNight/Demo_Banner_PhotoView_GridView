package com.demo_banner_photoview_gridview.bannerview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 类描述：
 * 创建人：xuyaxi
 * 创建时间：2017/7/26 9:02
 */
public class BannerAdapter extends PagerAdapter {

    private List<View> viewList;
    private int size;
    private final int cacheCount = 3;

    public BannerAdapter(List<View> viewList) {
        this.viewList = viewList;
        size = viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (viewList.size() > cacheCount){
            container.removeView(viewList.get(position%size));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) viewList.get(position%size).getParent();
        if (parent != null) {
            parent.removeView(viewList.get(position%size));
        }
        container.addView(viewList.get(position%size));
        return viewList.get(position%size);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
