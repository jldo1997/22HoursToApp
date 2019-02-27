package com.salesianostriana.jldominguez.moveright;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salesianostriana.jldominguez.moveright.interfaces.MyPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.model.Property;

import java.util.List;


public class MyMyPropertyRecyclerViewAdapter extends RecyclerView.Adapter<MyMyPropertyRecyclerViewAdapter.ViewHolder> {

    private List<Property> mValues;
    private final MyPropertyInteractionListener mListener;
    private Context ctx;

    public MyMyPropertyRecyclerViewAdapter(Context ctx, int layout, List<Property> items, MyPropertyInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myproperty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());
        holder.tvRooms.setText(Integer.toString(holder.mItem.getRooms()));
        holder.tvPrice.setText(Integer.toString(holder.mItem.getPrice()));
        if(holder.mItem.getPhotos() != null) {
            Glide.with(ctx).load(holder.mItem.getPhotos()[0]).into(holder.ivMainImage);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClickMyView(holder.mItem.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivMainImage;
        public final TextView tvTitle;
        public final TextView tvRooms;
        public final TextView tvPrice;
        public Property mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMainImage = view.findViewById(R.id.ivMyPMainImage);
            tvTitle = view.findViewById(R.id.tvMyPTitle);
            tvRooms = view.findViewById(R.id.tvMyPRooms);
            tvPrice = view.findViewById(R.id.tvMyPPrice);
        }

    }

    public void setNewMyProperties(List<Property> newProperties){
        this.mValues = newProperties;
        notifyDataSetChanged();
    }
}
