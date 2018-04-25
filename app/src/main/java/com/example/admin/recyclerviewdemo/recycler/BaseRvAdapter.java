package com.example.admin.recyclerviewdemo.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 *
 * @author Administrator
 *         <p>
 *         万能的RecyclerView的Adapter
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseRvHolder> {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 数据源
     */
    private List<T> list;
    /**
     * 布局器
     */
    private LayoutInflater inflater;
    /**
     * 布局id
     */
    private int itemLayoutId;

    /**
     * 点击事件监听器
     */
    private OnItemClickListener listener;
    /**
     * 长按监听器
     */
    private OnItemLongClickListener longClickListener;


    public BaseRvAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public BaseRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(itemLayoutId, parent, false);
        return BaseRvHolder.getRvHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final BaseRvHolder holder, int position) {
        //position从0开始，即，是集合的索引位置。
        Log.i("tag", "BaseRvAdapter.onBindViewHolder()中的position=" + position);

        convert(holder, list.get(position), position);

        if (listener != null) {
            //设置背景
            //holder.itemView.setBackgroundResource(R.drawable.recycler_bg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && view != null) {
                    /*
                    holder.getPosition()已过时。
                    holder.getPosition()=holder.getLayoutPosition()，内部逻辑一样。
                    getLayoutPosition()和getAdapterPosition()的值都是从1开始，且相同。即，实际上等于Item的个数。
                     */
                    Log.i("tag", "布局getLayoutPosition()=" + holder.getLayoutPosition() + "，适配器getAdapterPosition()=" + holder.getAdapterPosition());

                    /*
                    注意：
                    传入的参数不要用onBindViewHolder()的参数position，否则会出现位置错乱。
                    Google推荐使用holder.getAdapterPosition()。

                    传入holder.getAdapterPosition()-1，使点击的Item的position从0开始，以便与集合的索引对应。
                     */
                    listener.onItemClick(view, holder.getAdapterPosition() - 1);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null && view != null) {
                    longClickListener.onItemLongClick(view, holder.getAdapterPosition() - 1);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    /**
     * 点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 长点击事件接口回调
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    /**
     * 设置点击事件
     *
     * @param listener 点击接口
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置长点击事件
     *
     * @param longClickListener 长点击接口
     */
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    /**
     * 刷新:
     * 注意list是否初始化
     *
     * @param mList 请确保mList已初始化并填充数据。
     */
    public void refreshData(List<T> mList) {
        if (null != list) {
            if (list.size() > 0) {
                list.clear();
            }

            list.addAll(mList);
            notifyDataSetChanged();
        }
        // =表示地址的引用，list可能未初始化
        //list = mList;
    }

    /**
     * 加载：
     * 注意list是否初始化
     *
     * @param mList 请确保mList已初始化并填充数据。
     */
    public void loadMoreData(List<T> mList) {
        if (mList == null || mList.isEmpty()) {
            return;
        }

        if (null != list) {
            list.addAll(mList);
            notifyDataSetChanged();
        }
    }


    /**
     * 插入：
     * notifyDataSetChanged()会刷新整个列表，且没有插入、删除动画了。
     * 推荐使用局部刷新notifyItemRangeChanged()。
     *
     * @param position position可为0、list.size()等值。
     */
    public void addItem(int position, T item) {
        if (null != list) {
            list.add(position, item);
            notifyItemInserted(position + 1);
            //notifyDataSetChanged();
            if (position != list.size()) {
                notifyItemRangeChanged(position + 1, list.size() - position);
            }
        }
    }

    /**
     * 删除：
     * notifyDataSetChanged()会刷新整个列表，且没有插入、删除动画了。
     * 推荐使用局部刷新notifyItemRangeChanged()。
     *
     * @param position 要注意position的起始值是0还是1，否则会发生位置错乱。以下代码逻辑默认起始值为0。
     */
    public void removeItem(int position) {
        if (null != list && list.size() > 0) {
            list.remove(position);
            notifyItemRemoved(position + 1);
            //notifyDataSetChanged();
            if (position != list.size()) {
                notifyItemRangeChanged(position + 1, list.size() - position);
            }
        }
    }


    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param holder   ViewHolder
     * @param data     子项
     * @param position 位置
     */
    public abstract void convert(BaseRvHolder holder, T data, int position);

}
