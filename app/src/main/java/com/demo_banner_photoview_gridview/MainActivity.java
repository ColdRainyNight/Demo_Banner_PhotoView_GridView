package com.demo_banner_photoview_gridview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.demo_banner_photoview_gridview.bannerview.BannerView;
import com.demo_banner_photoview_gridview.base.Data;
import com.demo_banner_photoview_gridview.fragment.HomePage;
import com.demo_banner_photoview_gridview.fragment.NotLoggedIn;
import com.demo_banner_photoview_gridview.fragment.TheHeadlines;
import com.demo_banner_photoview_gridview.fragment.Videos;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private List<View> viewList;
    BannerView bannerView;

    private FragmentManager fm;
    private RadioGroup rg;
    private List<Fragment> list;
    private HomePage hp;
    private Videos vd;
    private TheHeadlines th;
    private NotLoggedIn nli;


    //private String url = "http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBannerView();
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        addFragment();//添加数据
        changeFragment();//改变数据
        initText();

    }

    private void initText() {
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Data.DataBean.ComicsBean> lis = new ArrayList<>();
                List<String> strs = new ArrayList<String>();
                for (Data.DataBean.ComicsBean bean : lis) {
                    strs.add(bean.getLabel_text());
                }
                List<ChannelBean> lists = new ArrayList<ChannelBean>();
                for (int i = 0; i < strs.size(); i++) {
                    ChannelBean b = new ChannelBean(strs.get(i), i < 10 ? true : false);
                    lists.add(b);
                }
                ChannelActivity.startChannelActivity(MainActivity.this,lists);
            }
        });
    }

    private void changeFragment() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                int tag = Integer.parseInt(rb.getTag().toString());
                FragmentTransaction transaction2 = fm.beginTransaction();
                for (int i = 0; i < list.size(); i++) {
                    if (tag == i) {
                        transaction2.show(list.get(i));
                    } else {
                        transaction2.hide(list.get(i));
                    }
                }
                transaction2.commit();
            }
        });
    }

    private void addFragment() {
        list = new ArrayList<>();
        fm = getSupportFragmentManager();
        hp = new HomePage();
        vd = new Videos();
        th = new TheHeadlines();
        nli = new NotLoggedIn();
        list.add(hp);
        list.add(vd);
        list.add(th);
        list.add(nli);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.frameLayout, list.get(0));
        transaction.add(R.id.frameLayout, list.get(1));
        transaction.add(R.id.frameLayout, list.get(2));
        transaction.add(R.id.frameLayout, list.get(3));
        transaction.show(list.get(0)).hide(list.get(1)).hide(list.get(2)).hide(list.get(3));
        transaction.commit();
    }

    private void initBannerView() {
        viewList = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView = (BannerView) findViewById(R.id.banner);
        bannerView.setViewList(viewList);
        bannerView.startLoop(true);
//        bannerView.setTransformAnim(true);
    }
}
