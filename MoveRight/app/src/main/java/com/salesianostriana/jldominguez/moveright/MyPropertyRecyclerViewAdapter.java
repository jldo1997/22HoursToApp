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
import com.salesianostriana.jldominguez.moveright.interfaces.PropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.model.Property;

import java.util.List;

public class MyPropertyRecyclerViewAdapter extends RecyclerView.Adapter<MyPropertyRecyclerViewAdapter.ViewHolder> {

    private List<Property> mValues;
    private final PropertyInteractionListener mListener;
    private Context ctx;

    public MyPropertyRecyclerViewAdapter(Context ctx, int layout, List<Property> items, PropertyInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());
        holder.tvRooms.setText(Integer.toString(holder.mItem.getRooms()));
        holder.tvPrice.setText(Integer.toString(holder.mItem.getPrice()));

        Glide.with(ctx).load(holder.mItem.getPhotos()[0]).into(holder.ivMainImage);



        holder.ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: añadir a favoritos
            }
        });

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

    public void setNewProperties(List<Property> newProperties){
        this.mValues = newProperties;
        notifyDataSetChanged();
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
        public final ImageButton ibFav;
        public Property mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMainImage = view.findViewById(R.id.ivMainImage);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvRooms = view.findViewById(R.id.tvRooms);
            tvPrice = view.findViewById(R.id.tvPrice);
            ibFav = view.findViewById(R.id.ibFab);
        }


    }
}
