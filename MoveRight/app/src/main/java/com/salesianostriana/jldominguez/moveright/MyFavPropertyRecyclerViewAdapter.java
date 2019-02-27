package com.salesianostriana.jldominguez.moveright;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salesianostriana.jldominguez.moveright.interfaces.FavPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.model.Property;

import java.util.List;


public class MyFavPropertyRecyclerViewAdapter extends RecyclerView.Adapter<MyFavPropertyRecyclerViewAdapter.ViewHolder> {

    private List<Property> mValues;
    private final FavPropertyInteractionListener mListener;
    private Context ctx;

    public MyFavPropertyRecyclerViewAdapter(Context ctx, int layout, List<Property> items, FavPropertyInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favproperty, parent, false);
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
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
            ivMainImage = view.findViewById(R.id.ivFavMainImage);
            tvTitle = view.findViewById(R.id.tvFavTitle);
            tvRooms = view.findViewById(R.id.tvFavRooms);
            tvPrice = view.findViewById(R.id.tvFavPrice);

        }

    }

    public void setNewFavProperties(List<Property> newProperties){
        this.mValues = newProperties;
        notifyDataSetChanged();
    }
}
