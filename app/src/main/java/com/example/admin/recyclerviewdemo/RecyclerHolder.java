package com.example.admin.recyclerviewdemo;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * SparseArray
 *
 * 比HashMap更省内存，在某些条件下性能更好，主要是因为它避免了对key的自动装箱（int转为Integer类型），
 * 它内部则是通过两个数组来进行数据存储的，一个存储key，另外一个存储value，
 * 为了优化性能，它内部对数据还采取了压缩的方式来表示稀疏数组的数据，从而节约内存空间，
 * 我们从源码中可以看到key和value分别是用数组表示：
 *   private int[] mKeys;
 *   private Object[] mValues;
 *
 * SparseArray只能存储key为int类型的数据，同时，SparseArray在存储和读取数据时候，使用的是二分查找法。
 *
 * SparseArray应用场景：
 * 虽说SparseArray性能比较好，但是由于其添加、查找、删除数据都需要先进行一次二分查找，所以在数据量大的情况下性能并不明显，将降低至少50%。
 *
 * 满足下面2个条件我们可以使用SparseArray代替HashMap：
 * 1、数据量不大，最好在千级以内。
 * 2、key必须为int类型，这中情况下的HashMap可以用SparseArray代替。
 *   HashMap<Integer, Object> map = new HashMap<>();
 *   用SparseArray代替：SparseArray<Object> array = new SparseArray<>();
 */

/**
 * ArrayMap
 *
 * 是一个<key,value>映射的数据结构，它设计上更多的是考虑内存的优化，
 * 内部是使用两个数组进行数据存储，一个数组记录key的hash值，另外一个数组记录Value值，
 * 它和SparseArray一样，也会对key使用二分法进行从小到大排序，
 * 在添加、删除、查找数据的时候都是先使用二分查找法得到相应的index，然后通过index来进行添加、查找、删除等操作，
 * 所以，应用场景和SparseArray的一样，如果在数据量比较大的情况下，那么它的性能将退化至少50%
 *
 *
 * ArrayMap应用场景：
 * 1、数据量不大，最好在千级以内
 * 2、数据结构类型为Map类型
 *   ArrayMap<Key, Value> arrayMap = new ArrayMap<>();
 *
 * 【注】：
 * 如果我们要兼容aip19以下版本的话，那么导入的包需要为v4包：import android.support.v4.util.ArrayMap;
 */

/**
 * 总结：
 * SparseArray和ArrayMap都差不多，使用哪个呢？
 * 假设数据量都在千级以内的情况下：
 * 1、如果key的类型已经确定为int类型，那么使用SparseArray，因为它避免了自动装箱的过程。
 *    如果key为long类型，它还提供了一个LongSparseArray来确保key为long类型时的使用。
 * 2、如果key类型为其它的类型，则使用ArrayMap
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {

    /** 用于存储当前item当中的View */
    private SparseArray<View> mViews;

    public RecyclerHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<View>();
    }

    public <T extends View> T findView(int ViewId) {
        View view = mViews.get(ViewId);

        //集合中没有，则从item当中获取，并存入集合当中
        if (view == null) {
            view = itemView.findViewById(ViewId);
            mViews.put(ViewId, view);
        }

        return (T) view;
    }

    public RecyclerHolder setText(int viewId, String text) {
        TextView tv = findView(viewId);
        tv.setText(text);

        return this;
    }

    public RecyclerHolder setText(int viewId, int text) {
        TextView tv = findView(viewId);
        tv.setText(text);

        return this;
    }

    public RecyclerHolder setImageResource(int viewId, int ImageId) {
        ImageView image = findView(viewId);
        image.setImageResource(ImageId);

        return this;
    }

    public RecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView image = findView(viewId);
        image.setImageBitmap(bitmap);

        return this;
    }

    public RecyclerHolder setImageNet(int viewId, String url) {
        ImageView image = findView(viewId);

        //使用你所用的网络框架等
        return this;
    }

}
