package com.jwy.bjtumidas.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.fragments.ClassifyFragment;
import com.jwy.bjtumidas.fragments.HomeFragment;
import com.jwy.bjtumidas.fragments.PublishFragment;
import com.jwy.bjtumidas.fragments.UserFragment;
import com.jwy.bjtumidas.utils.AfTabBarAdapter;
import com.jwy.bjtumidas.utils.SysApplication;

public class MainActivity extends AppCompatActivity
{

    // 每一页就是一个Fragment
    Fragment[] pages = new Fragment[4];

    // 标签栏支持
    AfTabBarAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建Fragment
        pages[0]=new HomeFragment();
        pages[1]=new ClassifyFragment();
        pages[2]=new PublishFragment();
        pages[3]=new UserFragment();
//        pages[0] = new MsgFragment();
//        pages[1] = new CCFragment();
//        pages[2] = new UserFragment();

        // 给ViewPager设置Adapter
        FragmentPagerAdapter pagerAdapter =
                new MyViewPagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager)findViewById(R.id.id_viewpager);
        viewPager.setAdapter(pagerAdapter);


        // 标签栏内容
        AfTabBarAdapter.Item[] labels = new AfTabBarAdapter.Item[4];
        labels[0] = new AfTabBarAdapter.Item("首页","home");
        labels[0].iconNormal = getDrawable(R.mipmap.ic_home_normal);
        //labels[0].iconActive = getDrawable(R.drawable.ic_home_active);
        labels[0].iconActive = getDrawable(R.mipmap.ic_home_active);
        labels[1] = new AfTabBarAdapter.Item("分类","classify");
        labels[1].iconNormal = getDrawable(R.mipmap.ic_classify_normal);
        labels[1].iconActive = getDrawable(R.mipmap.ic_classify_active);
        labels[2] = new AfTabBarAdapter.Item("发布","publish");
        labels[2].iconNormal = getDrawable(R.mipmap.ic_publish_normal);
        labels[2].iconActive = getDrawable(R.mipmap.ic_publish_active);
        labels[3] = new AfTabBarAdapter.Item("我","user");
        labels[3].iconNormal = getDrawable(R.mipmap.ic_user_normal);
        labels[3].iconActive = getDrawable(R.mipmap.ic_user_active);
        tabAdapter = new AfTabBarAdapter(this);
        tabAdapter.addItems(labels);

        GridView gridView = (GridView)findViewById(R.id.id_gridview);
        gridView.setAdapter(tabAdapter);
        gridView.setNumColumns( labels.length ); // GridView设置：列
        tabAdapter.setActive(0, true); // 默认选中第一页

        // 监听器：当点击标签页时, 显示对应的页
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // 让ViewPager切换到对应的页
                viewPager.setCurrentItem(position);
                // 让GridView对应的标签高亮显示
                tabAdapter.setActive(position, true);
            }
        });

        // 监听器：当滑动切换时，设置对应的标签高亮
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                // 让ViewPager切换到对应的页
                tabAdapter.setActive(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    // ViewPager支持
    private class MyViewPagerAdapter extends FragmentPagerAdapter
    {
        public MyViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        // 一共有几页
        @Override
        public int getCount()
        {
            return pages.length;
        }
        // 每一页的对象
        @Override
        public Fragment getItem(int position)
        {
            return pages[position];
        }

    }

    private long exitTime = 0;

    /**
     * 按两次返回键退出系统
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showInfo(this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                SysApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
