package com.salesianostriana.jldominguez.righttomoveapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.salesianostriana.jldominguez.righttomoveapp.PropertyFragment.OnListFragmentInteractionListener;
import com.salesianostriana.jldominguez.righttomoveapp.model.Property;

import java.util.List;

public class MyPropertyRecyclerViewAdapter extends RecyclerView.Adapter<MyPropertyRecyclerViewAdapter.ViewHolder> {

    private final List<Property> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPropertyRecyclerViewAdapter(List<Property> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.tvRooms.setText(holder.mItem.getRooms());
        holder.tvPrice.setText(holder.mItem.getPrice().toString());

        /*
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final ImageButton ibFab;
        public Property mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMainImage = view.findViewById(R.id.ivMainImage);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvRooms = view.findViewById(R.id.tvRooms);
            tvPrice = view.findViewById(R.id.tvPrice);
            ibFab = view.findViewById(R.id.ibFab);
        }


    }
}
