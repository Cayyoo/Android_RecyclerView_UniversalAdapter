package com.example.admin.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/qq_29262849/article/details/50449044
 *
 * 为RecyclerView打造万能适配器，点击事件，5.0水波纹点击效果
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerAdapter<String> TextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_list = findView(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rv_list.setAdapter(TextAdapter = new RecyclerAdapter<String>(this, getData(), R.layout.recycler_item) {
            @Override
            public void convert(RecyclerHolder holder, String data, int position) {
                holder.setText(R.id.tv, data);
                holder.setImageResource(R.id.image, R.mipmap.ic_launcher);
            }
        });

        TextAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                ToastShow("点击" + position);

            }
        });
    }

    public List<String> getData() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("数据项目" + "<" + i + ">");
        }

        return list;
    }

    Toast toast;

    public void ToastShow(String Text) {
        if (toast == null) {
            toast = Toast.makeText(MainActivity.this, Text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(Text);
        }

        toast.show();
    }

    /**
     * 在Fragment中使用时需要两个参数：(View view, int ViewId)
     */
    public <T extends View> T findView(int ViewId) {
        return (T) findViewById(ViewId);
    }

}
