package xuhong.zidingyikongjian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import xuhong.zidingyikongjian.R;
import xuhong.zidingyikongjian.view.DeletedRecyclerview;

/**
 * Created by BHKJ on 2016/6/13.
 */

//主界面的adapter
public class MyAdapter extends RecyclerView.Adapter {

    private List<String> mData;

    private LayoutInflater inflater;

    private OnItemClickListener  listener;

    public void  setOnItemClickListener(OnItemClickListener  listener){
        this.listener = listener;
    }

    public MyAdapter(Context context, List<String> mData){
        inflater = LayoutInflater.from(context);
        this.mData = mData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item,parent,false);
        MyViewHolder  viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String str = mData.get(position);
        MyViewHolder  myViewHolder =(MyViewHolder)holder;
        myViewHolder.textView.setText(str);

        myViewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.click(v,position);
                }
            }
        });

        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.deletedClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//
//        return DeletedRecyclerview.TYPE_RIGHT;
//    }

    private  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button  button;
        LinearLayout linear;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            button = (Button) itemView.findViewById(R.id.btn_deleted);
            linear= (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }


    public  interface  OnItemClickListener{
        void click(View v, int position);
        void deletedClick(View v, int position);

    }

}
