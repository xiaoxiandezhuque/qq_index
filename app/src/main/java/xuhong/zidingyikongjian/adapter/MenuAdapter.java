package xuhong.zidingyikongjian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xuhong.zidingyikongjian.R;

/**
 * Created by BHKJ on 2016/6/8.
 */

//菜单栏的adapter
public class MenuAdapter extends RecyclerView.Adapter {

    private  List<String> mData;
    private LayoutInflater inflater;

    private OnItemClickListener listener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public MenuAdapter(Context context, List<String>  mData){
        this.inflater = LayoutInflater.from(context);
        this.mData = mData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_menu,parent,false);
        MenuViewHolder viewHolder = new MenuViewHolder(view,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuViewHolder viewHolder = (MenuViewHolder) holder;
        viewHolder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private  class  MenuViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MenuViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.click(v,getAdapterPosition());
                    }
                }
            });

        }
    }

}
