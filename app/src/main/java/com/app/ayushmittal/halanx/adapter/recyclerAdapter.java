package com.app.ayushmittal.halanx.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.ayushmittal.halanx.R;
import com.app.ayushmittal.halanx.house_detail;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {
    @NonNull
    private ArrayList<Map<String,String >> list;
    Context context;

    public recyclerAdapter(ArrayList<Map<String, String>> mlist, Context mcontext){
            list=mlist;
             context=mcontext;
    }

    @Override
    public myViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        myViewHolder vh=new myViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.myViewHolder holder, final int position) {

        holder.name.setText(list.get(position).get("name"));
        holder.rent.setText("Rs "+ list.get(position).get("rent_from")+"/-");
        holder.type.setText(list.get(position).get("house_type"));

       URL url = null;
        try {
            Log.d("imgurl",list.get(position).get("cover_pic_url"));
            url = new URL(list.get(position).get("cover_pic_url"));
            Picasso.get().load(url.toString()).into(holder.imageView);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,house_detail.class);
                i.putExtra("value",list);
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{


        TextView name,rent,type;
        ImageView imageView;

        public myViewHolder(View itemView) {
            super(itemView);
             name = (TextView) itemView.findViewById(R.id.name);
             rent = (TextView) itemView.findViewById(R.id.rent);
            type = (TextView) itemView.findViewById(R.id.type);
            imageView = (ImageView) itemView.findViewById(R.id.img);


        }



    }


}
