package com.plusub.lib.example.activity.tab1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.plusub.lib.activity.BaseActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;

/**
 * 侧滑栏的使用方式：
 * <br>可参考http://blog.csdn.net/eclipsexys/article/details/8688538
 * <br>使用方法：
 * <br>1.设置activity的风格：ActivityActionBarTheme,如果整个应用都使用的话，就设置application风格
 * <br>2.使用ActionBar并修改风格
 * <br>3.侧滑栏的根布局必须使用layout_gravity属性
 * @ClassName: LDrawerActivity
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date 2015-4-9 下午7:58:55
 * @version v1.0
 */
public class LDrawerActivity extends BaseActivity {

	@BindView(id = R.id.drawer_layout)
	private DrawerLayout mDrawerLayout;
	@BindView(id = R.id.navdrawer)
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private boolean drawerArrowColor;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		//action bar头的动画初始化
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		//初始化侧边栏的内容
		String[] values = new String[]{
				"Stop Animation (Back icon)",
				"Stop Animation (Home icon)",
				"Start Animation",
				"Change Color",
				"GitHub Page",
				"Share",
				"Rate",
				"退出"
		};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				switch (position) {
					case 0:
						mDrawerToggle.setAnimateEnabled(false);
						drawerArrow.setProgress(1f);
						break;
					case 1:
						mDrawerToggle.setAnimateEnabled(false);
						drawerArrow.setProgress(0f);
						break;
					case 2:
						mDrawerToggle.setAnimateEnabled(true);
						mDrawerToggle.syncState();
						break;
					case 3:
						if (drawerArrowColor) {
							drawerArrowColor = false;
							drawerArrow.setColor(R.color.ldrawer_color);
						} else {
							drawerArrowColor = true;
							drawerArrow.setColor(R.color.drawer_arrow_second_color);
						}
						mDrawerToggle.syncState();
						break;
					case 4:
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
						startActivity(browserIntent);
						break;
					case 5:
						Intent share = new Intent(Intent.ACTION_SEND);
						share.setType("text/plain");
						share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						share.putExtra(Intent.EXTRA_SUBJECT,
								getString(R.string.app_name));
						share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
								"GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
								"Sample App : https://play.google.com/store/apps/details?id=" +
								getPackageName());
						startActivity(Intent.createChooser(share,
								getString(R.string.app_name)));
						break;
					case 6:
						String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
						Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
						startActivity(rateIntent);
						break;
					case 7:
						finish();
						break;
				}

			}
		});
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_ldrawer);
	}


	/**
	 * 注意，在魅族 m2 note-5.1上如果返回true则会出现底部显示空格的问题
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//添加菜单项
//        MenuItem add=menu.add(0,0,0,"add");  
//        MenuItem del=menu.add(0,0,0,"del");  
//        MenuItem save=menu.add(0,0,0,"save");  
		//绑定到ActionBar
//        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);  
//        del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);  
//        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);  

		//view 添加
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
