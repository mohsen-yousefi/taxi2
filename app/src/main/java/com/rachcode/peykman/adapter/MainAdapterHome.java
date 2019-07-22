package com.rachcode.peykman.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rachcode.peykman.R;
import com.rachcode.peykman.utils.view.RoundedImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by admin on 01-01-2018.
 */

public class MainAdapterHome extends RecyclerView.Adapter<MainAdapterHome.ViewHolder> {

    private Activity activity;
    private String[] name;
    private Integer[] image;
    private int row_index = -1;


    public MainAdapterHome(Activity activity, String[] name, Integer[] image) {
        this.activity = activity;
        this.name = name;
        this.image = image;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.main_adapter_ghome, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Picasso.with(activity).load(image[position]).into(holder.imageView);
        holder.imageView.setImageResource(image[position]);
        holder.textView_Name.setText(name[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        private TextView textView_Name;
        private LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_main_adapter);
            textView_Name = (TextView) itemView.findViewById(R.id.textView_main_adapter);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_main_adapter);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
