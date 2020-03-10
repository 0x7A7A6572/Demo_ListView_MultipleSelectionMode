package com.noform.demo_listview_multipleselectionmode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private View page0, page1;
    private List<View> viewList;//view数组
    static ListView list;
    private MyPagerAdapter pagerAdapter;
    static MyAdapter listItemAdapter;//适配器
    static boolean isMultipleSelectionMode;//判断进入多选模式
    public static ArrayList<HashMap<String, Object>> AdapterList = new ArrayList<>();  //数据

    public static Context CONTEXT;
    TextView counttext;
    LinearLayout ItemToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONTEXT = this.getApplicationContext();

        mViewPager=findViewById(R.id.activityViewPager);

        LayoutInflater inflater = getLayoutInflater();
        page0 =inflater.inflate(R.layout.list_view, null);
        page1 = inflater.inflate(R.layout.null_layout, null);
        list = page0.findViewById(R.id.mylistview);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(page0);
        viewList.add(page1);
        pagerAdapter = new MyPagerAdapter(viewList);
        mViewPager.setAdapter(pagerAdapter);





        //初始化数据
        initData();

         counttext = this.findViewById(R.id.counttext);//选中时更改的textview
        ItemToolBar = this.findViewById(R.id.ItemToolBar);

        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listItemAdapter = new MyAdapter(AdapterList); //新建并配置ArrayAapeter
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p, View v, int index,
                                    long arg3) {
                if (isMultipleSelectionMode) {
                    setCountChange();

                }
                listItemAdapter.notifyDataSetChanged();
                //Toast.makeText(MainActivity.CONTEXT, "当前数据为:" + list.isItemChecked(index), Toast.LENGTH_LONG).show();
            }
        });



        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long itemId) {
                list.clearChoices();//取消选中状态
                if (isMultipleSelectionMode) {
                    ItemToolBar.setVisibility(View.GONE);
                    isMultipleSelectionMode = false;
                    list.clearChoices();//取消选中状态
                    Toast.makeText(CONTEXT, "退出多选模式", Toast.LENGTH_LONG).show();
                } else {
                    ItemToolBar.setVisibility(View.VISIBLE);
                    isMultipleSelectionMode =true;
                    listItemAdapter.notifyDataSetChanged();
                    //多选模式
                    Toast.makeText(CONTEXT, "进入多选模式", Toast.LENGTH_LONG).show();



                    return true;

                }

                listItemAdapter.notifyDataSetChanged();
                setCountChange();

                return false;
            }
        });
//设置按钮单机事件绑定
 setButtonClick();

    }
    public void setCountChange(){
        counttext.setText("选中了" + list.getCheckedItemCount() +"项");
        listItemAdapter.notifyDataSetChanged();
    }

    public void initData(){

        for(int i = 0;i< 50;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("标题","这是标题" + i);
            map.put("内容","这是内容" + i);
            AdapterList.add(map);
        }
    }

    public void setButtonClick(){
        Button all = this.findViewById(R.id.all);
        Button unall = this.findViewById(R.id.unall);
        Button del = this.findViewById(R.id.del);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(int i = 0;i < listItemAdapter.balistItem.size();i++){
                  list.setItemChecked(i,true);
               }
                setCountChange();
            }
        });

        unall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i < listItemAdapter.balistItem.size();i++){
                    if(list.isItemChecked(i)){
                        list.setItemChecked(i,false);
                    }else {
                        list.setItemChecked(i,true);
                    }
                }
                setCountChange();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList< HashMap<String,Object>> mapStorage = new ArrayList<HashMap<String,Object>>();
                for (int i = 0;i < listItemAdapter.balistItem.size();i++) {
                    if(list.isItemChecked(i)){
                        //是选中状态时
                        mapStorage.add(listItemAdapter.balistItem.get(i));
                    }
                }
                listItemAdapter.balistItem.removeAll(mapStorage);
                listItemAdapter.notifyDataSetChanged();
                list.clearChoices();
                setCountChange();
              //  Log.d("del","删除后：" + listItemAdapter.balistItem.toString());

            }
        });



    }


}