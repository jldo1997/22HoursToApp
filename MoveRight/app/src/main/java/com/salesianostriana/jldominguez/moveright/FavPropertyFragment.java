package com.salesianostriana.jldominguez.moveright;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salesianostriana.jldominguez.moveright.interfaces.FavPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavPropertyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FavPropertyInteractionListener mListener;
    private PropertyViewModel favPropertyViewModel;
    private MyFavPropertyRecyclerViewAdapter favAdapter;
    private List<Property> favPropertyList;


    public FavPropertyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FavPropertyFragment newInstance(int columnCount) {
        FavPropertyFragment fragment = new FavPropertyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favproperty_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            favPropertyList = new ArrayList<>();

            favAdapter = new MyFavPropertyRecyclerViewAdapter(
                    getActivity(),
                    R.layout.fragment_favproperty,
                    favPropertyList,
                    mListener
            );

            recyclerView.setAdapter(favAdapter);
            launchViewModel();

        }
        return view;
    }

    private void launchViewModel() {
        favPropertyViewModel = ViewModelProviders.of(getActivity()).get(PropertyViewModel.class);
        favPropertyViewModel.getFavPropertiesFromCall(UtilToken.getToken(getActivity()));
        favPropertyViewModel.getFavProperties().observe(getActivity(), new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> properties) {
                favAdapter.setNewFavProperties(properties);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavPropertyInteractionListener) {
            mListener = (FavPropertyInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
