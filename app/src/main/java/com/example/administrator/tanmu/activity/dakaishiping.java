package com.example.administrator.tanmu.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterViewPageMain;
import com.example.administrator.tanmu.adapter.SearchListAdaoter;
import com.example.administrator.tanmu.object.Person;
import com.example.administrator.tanmu.object.SearchImformation;
import com.example.administrator.tanmu.util.DensityUtil;
import com.example.administrator.tanmu.view.LayoutDianshiju;
import com.example.administrator.tanmu.view.LayoutDianying;
import com.example.administrator.tanmu.view.LayoutDogman;
import com.example.administrator.tanmu.view.LayoutTuijian;
import com.example.administrator.tanmu.view.LayoutZongyi;
import com.example.administrator.tanmu.view.MySwipeRefreshLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class dakaishiping extends AppCompatActivity {

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private FloatingActionButton floatingActionButton;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;
    private PopupWindow mPopupWindow;
    private ImageView settings;
    private ImageView search;
    private CircleImageView user_icon;
    private TextView user_name;
    private View headerView;
    private NavigationView navigationView;
    private LinearLayout bendi_layout;
    private LinearLayout paishe_layout;
    private LinearLayout search_layout;
    private LinearLayout layout_main;
    private DrawerLayout mDrawerLayout;
    private MySwipeRefreshLayout swipeRefresh;
    //搜索框初始化
    private SearchView searchView;
    private ListView listView;
    private List<SearchImformation> list;
    private List<SearchImformation> findList;
    private List<String> nameList;
    private SearchListAdaoter adapter;
    private SearchListAdaoter findAdapter;
    private String userName;
    private Uri imageUri;
    private Window window;

    private Bundle savedInstanceState;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    adapter = new SearchListAdaoter(dakaishiping.this, list);
                    listView.setAdapter(adapter);
                    for (int i = 0; i < list.size(); i++) {
                        SearchImformation information = list.get(i);
                        nameList.add(information.getName());
                    }
                    break;
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "df610b845570afeeebc1a17eb36e726d");
        setContentView(R.layout.activity_dakaishiping);

        bendi_layout = (LinearLayout) findViewById(R.id.bendi_layout);
        paishe_layout = (LinearLayout) findViewById(R.id.paishe_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        navigationView.addHeaderView(headerView);
        user_icon = (CircleImageView) headerView.findViewById(R.id.icon_image);
        user_name = (TextView) headerView.findViewById(R.id.user_name);

        Person person=BmobUser.getCurrentUser(Person.class);
        if (person!=null){
            if (person.getImagePath()!=null){
                Bitmap bitmap = BitmapFactory.decodeFile(person.getImagePath());
                user_icon.setImageBitmap(bitmap);
            }else {
                user_icon.setImageResource(R.drawable.kongbai);
            }
            user_name.setText(person.getUsername());

        }else {
            user_icon.setImageResource(R.drawable.kongbai);
            user_name.setText("点击头像登陆");
        }


        searchView = (SearchView) findViewById(R.id.searchBar);
        listView = (ListView) findViewById(R.id.search_list);
        findList = new ArrayList<SearchImformation>();
        nameList = new ArrayList<String>();
        searchView.setSubmitButtonEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_shezhi:
                        Intent intent=new Intent(dakaishiping.this,SettingActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });

        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person=BmobUser.getCurrentUser(Person.class);
                if (person==null) {
                    finish();
                    Intent intent = new Intent(dakaishiping.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (ContextCompat.checkSelfPermission(dakaishiping.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(dakaishiping.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    show2();
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
            public boolean onQueryTextSubmit(String query) {

                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(dakaishiping.this, "请输入查找内容！", Toast.LENGTH_SHORT).show();
                    listView.setAdapter(adapter);
                } else {
                    findList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        SearchImformation information = list.get(i);
                        if (information.getName().equals(query)) {
                            findList.add(information);
                            break;
                        }
                    }
                    if (findList.size() == 0) {
                        Toast.makeText(dakaishiping.this, "查找的商品不在列表中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(dakaishiping.this, "查找成功", Toast.LENGTH_SHORT).show();
                        findAdapter = new SearchListAdaoter(dakaishiping.this, findList);
                        listView.setAdapter(findAdapter);
                    }
                }
                return true;
            }

            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    listView.setAdapter(adapter);
                } else {
                    findList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        SearchImformation information = list.get(i);
                        if (information.getName().contains(newText)) {
                            findList.add(information);
                        }
                    }
                    findAdapter = new SearchListAdaoter(dakaishiping.this, findList);
                    findAdapter.notifyDataSetChanged();
                    listView.setAdapter(findAdapter);
                }
                return true;
            }
        });


        new Thread(new Runnable() {
            public void run() {
                list = new ArrayList<SearchImformation>();
                SearchImformation iconInfo1 = new SearchImformation();
                iconInfo1.setName("Beer");
                list.add(iconInfo1);
                SearchImformation iconInfo2 = new SearchImformation();
                iconInfo2.setName("Bread");
                list.add(iconInfo2);
                SearchImformation iconInfo3 = new SearchImformation();
                iconInfo3.setName("Breakfast");
                list.add(iconInfo3);
                SearchImformation iconInfo4 = new SearchImformation();
                iconInfo4.setName("Burger");
                list.add(iconInfo4);
                SearchImformation iconInfo5 = new SearchImformation();
                iconInfo5.setName("Cake");
                list.add(iconInfo5);
                SearchImformation iconInfo6 = new SearchImformation();
                iconInfo6.setName("Carrot");
                list.add(iconInfo6);
                SearchImformation iconInfo7 = new SearchImformation();
                iconInfo7.setName("Check");
                list.add(iconInfo7);
                SearchImformation iconInfo8 = new SearchImformation();
                iconInfo8.setName("Chicken");
                list.add(iconInfo8);
                SearchImformation iconInfo9 = new SearchImformation();
                iconInfo9.setName("Closed");
                list.add(iconInfo9);
                SearchImformation iconInfo10 = new SearchImformation();
                iconInfo10.setName("Cocktails");
                list.add(iconInfo10);
                SearchImformation iconInfo11 = new SearchImformation();
                iconInfo11.setName("Coffee-Cup");
                list.add(iconInfo11);
                SearchImformation iconInfo12 = new SearchImformation();
                iconInfo12.setName("Croissant");
                list.add(iconInfo12);
                SearchImformation iconInfo13 = new SearchImformation();
                iconInfo13.setName("Cutlery1");
                list.add(iconInfo13);
                SearchImformation iconInfo14 = new SearchImformation();
                iconInfo14.setName("Cutlery");
                list.add(iconInfo14);
                SearchImformation iconInfo15 = new SearchImformation();
                iconInfo15.setName("Favorite");
                list.add(iconInfo15);
                SearchImformation iconInfo16 = new SearchImformation();
                iconInfo16.setName("Fish");
                list.add(iconInfo16);
                SearchImformation iconInfo17 = new SearchImformation();
                iconInfo17.setName("Fork");
                list.add(iconInfo17);
                SearchImformation iconInfo18 = new SearchImformation();
                iconInfo18.setName("Fruits");
                list.add(iconInfo18);
                SearchImformation iconInfo19 = new SearchImformation();
                iconInfo19.setName("Ice-Cream");
                list.add(iconInfo19);
                SearchImformation iconInfo20 = new SearchImformation();
                iconInfo20.setName("Invoice");
                list.add(iconInfo20);
                SearchImformation iconInfo21 = new SearchImformation();
                iconInfo21.setName("Juice");
                list.add(iconInfo21);
                SearchImformation iconInfo22 = new SearchImformation();
                iconInfo22.setName("Kettle1");
                list.add(iconInfo22);
                SearchImformation iconInfo23 = new SearchImformation();
                iconInfo23.setName("Kettle");
                list.add(iconInfo23);
                SearchImformation iconInfo24 = new SearchImformation();
                iconInfo24.setName("Lobster");
                list.add(iconInfo24);
                SearchImformation iconInfo25 = new SearchImformation();
                iconInfo25.setName("Menu");
                list.add(iconInfo25);
                SearchImformation iconInfo26 = new SearchImformation();
                iconInfo26.setName("Mushrooms");
                list.add(iconInfo26);
                SearchImformation iconInfo27 = new SearchImformation();
                iconInfo27.setName("Napkins");
                list.add(iconInfo27);
                SearchImformation iconInfo28 = new SearchImformation();
                iconInfo28.setName("Open");
                list.add(iconInfo28);
                SearchImformation iconInfo29 = new SearchImformation();
                iconInfo29.setName("Pan");
                list.add(iconInfo29);
                SearchImformation iconInfo30 = new SearchImformation();
                iconInfo30.setName("Payment-Method");
                list.add(iconInfo30);
                SearchImformation iconInfo31 = new SearchImformation();
                iconInfo31.setName("Piece-Of-Cake");
                list.add(iconInfo31);
                SearchImformation iconInfo32 = new SearchImformation();
                iconInfo32.setName("Pizza");
                list.add(iconInfo32);
                SearchImformation iconInfo33 = new SearchImformation();
                iconInfo33.setName("Reserved");
                list.add(iconInfo33);
                SearchImformation iconInfo34 = new SearchImformation();
                iconInfo34.setName("Restaurant");
                list.add(iconInfo34);
                SearchImformation iconInfo35 = new SearchImformation();
                iconInfo35.setName("Salt-And-Pepper");
                list.add(iconInfo35);
                SearchImformation iconInfo36 = new SearchImformation();
                iconInfo36.setName("Sausage");
                list.add(iconInfo36);
                SearchImformation iconInfo37 = new SearchImformation();
                iconInfo37.setName("Skewer");
                list.add(iconInfo37);
                SearchImformation iconInfo38 = new SearchImformation();
                iconInfo38.setName("Soup");
                list.add(iconInfo38);
                SearchImformation iconInfo39 = new SearchImformation();
                iconInfo39.setName("Spoon");
                list.add(iconInfo39);
                SearchImformation iconInfo40 = new SearchImformation();
                iconInfo40.setName("Steak");
                list.add(iconInfo40);
                SearchImformation iconInfo41 = new SearchImformation();
                iconInfo41.setName("Sushi");
                list.add(iconInfo41);
                SearchImformation iconInfo42 = new SearchImformation();
                iconInfo42.setName("Sushi1");
                list.add(iconInfo42);
                SearchImformation iconInfo43 = new SearchImformation();
                iconInfo43.setName("Sushi");
                list.add(iconInfo43);
                SearchImformation iconInfo44 = new SearchImformation();
                iconInfo44.setName("Table");
                list.add(iconInfo44);
                SearchImformation iconInfo45 = new SearchImformation();
                iconInfo45.setName("Tea-Cup");
                list.add(iconInfo45);
                SearchImformation iconInfo46 = new SearchImformation();
                iconInfo46.setName("Terrace");
                list.add(iconInfo46);
                SearchImformation iconInfo47 = new SearchImformation();
                iconInfo47.setName("Tray1");
                list.add(iconInfo47);
                SearchImformation iconInfo48 = new SearchImformation();
                iconInfo48.setName("Tray");
                list.add(iconInfo48);
                SearchImformation iconInfo49 = new SearchImformation();
                iconInfo49.setName("Vegetables");
                list.add(iconInfo49);
                SearchImformation iconInfo50 = new SearchImformation();
                iconInfo50.setName("Wine");
                list.add(iconInfo50);
                Message message = new Message();
                message.what = 123;
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();


        swipeRefresh=(MySwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        layout_main=(LinearLayout)findViewById(R.id.layout_main);
        search_layout=(LinearLayout)findViewById(R.id.search_layout);
        search=(ImageView)findViewById(R.id.search);
        settings=(ImageView) findViewById(R.id.settings);


        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search_layout.getVisibility()==View.GONE){
                    search_layout.setVisibility(View.VISIBLE);
                    layout_main.setVisibility(View.GONE);
                    search.setImageResource(R.drawable.back);
                }else {
                    search_layout.setVisibility(View.GONE);
                    layout_main.setVisibility(View.VISIBLE);
                    search.setImageResource(R.drawable.sousuo);
                }

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showPopupWindow();
            }
        });


        initTab();


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);


        final Intent intent2 = new Intent(this, Video.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "打开本地视频", Snackbar.LENGTH_LONG)
                        .setAction("打开", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(intent2);
                            }
                        }).show();
            }
        });

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.daohang);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void refreshDatas(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void show2() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.button_dialog, null);
        bottomDialog.setContentView(contentView);

        Window window = bottomDialog.getWindow();
        bendi_layout = (LinearLayout) window.findViewById(R.id.bendi_layout);
        paishe_layout = (LinearLayout) window.findViewById(R.id.paishe_layout);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();


        bendi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                bottomDialog.dismiss();
            }
        });


        paishe_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeIcon();
                bottomDialog.dismiss();
            }
        });

    }

    private void takeIcon() {
        File outputImage = new File(getExternalCacheDir(), "out_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(dakaishiping.this, "com.example.caeraalbumtext", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void initTab(){
        viewPager=(ViewPager)findViewById(R.id.vp_main);
        tabLayout=(TabLayout)findViewById(R.id.tab);

        tabList = new ArrayList<>();

        AdapterViewPageMain adapter = new AdapterViewPageMain(getSupportFragmentManager());

        adapter.addFragment(new LayoutTuijian());
        adapter.addFragment(new LayoutDianying());
        adapter.addFragment(new LayoutDianshiju());
        adapter.addFragment(new LayoutDogman());
        adapter.addFragment(new LayoutZongyi());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.add(tabLayout.getTabAt(3));
        tabList.add(tabLayout.getTabAt(4));
        tabList.get(0).setText("推荐");
        tabList.get(1).setText("电影");
        tabList.get(2).setText("电视剧");
        tabList.get(3).setText("动漫");
        tabList.get(4).setText("综艺");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(
                        ContextCompat.getColor(dakaishiping.this, R.color.Black),
                        ContextCompat.getColor(dakaishiping.this, R.color.Green));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showPopupWindow(){
        View contentView = LayoutInflater.from(dakaishiping.this).inflate(R.layout.popuplayout, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(settings,0,30);

        mPopupWindow.update();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
           if (search_layout.getVisibility()==View.VISIBLE){
               search_layout.setVisibility(View.GONE);
               layout_main.setVisibility(View.VISIBLE);
               search.setImageResource(R.drawable.sousuo);
               return true;
           }

       }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        user_icon.setImageBitmap(bitmap);
                        Person person=BmobUser.getCurrentUser(Person.class);
                        person.setImagePath(imageUri.getPath());
                        person.update(person.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handlerImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) ;
        String docId = DocumentsContract.getDocumentId(uri);
        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
            String id = docId.split(":")[1];
            String selection = MediaStore.Images.Media._ID + "=" + id;
            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
            imagePath = getImagePath(contentUri, null);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handlerImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            user_icon.setImageBitmap(bitmap);
            Person person=BmobUser.getCurrentUser(Person.class);
            person.setImagePath(imagePath);
            person.update(person.getObjectId(),new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });

        } else {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //takeIcon();
                    openAlbum();
                } else {
                    Toast.makeText(this, "你未授权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


}
