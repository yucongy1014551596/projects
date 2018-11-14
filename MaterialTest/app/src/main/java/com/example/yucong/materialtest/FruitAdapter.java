package com.example.yucong.materialtest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context context;
    private List<Fruit> fruits;

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits=fruits;

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
       CardView cardView;
       ImageView imageView;
       TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =(CardView)itemView;
            imageView= (ImageView)itemView.findViewById(R.id.fruit_image);
            textView=(TextView)itemView.findViewById(R.id.fruit_name);


        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context= parent.getContext();
        }
      View view=  LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Fruit fruit= fruits.get(position);
       holder.textView.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.imageView);//一个开源图片加载框架

    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }








}
