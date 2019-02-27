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

import com.salesianostriana.jldominguez.moveright.interfaces.MyPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;


public class MyPropertyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MyPropertyInteractionListener mListener;
    private PropertyViewModel myPropertyViewModel;
    private MyMyPropertyRecyclerViewAdapter myAdapter;
    private List<Property> myPropertyList;


    public MyPropertyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyPropertyFragment newInstance(int columnCount) {
        MyPropertyFragment fragment = new MyPropertyFragment();
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
        View view = inflater.inflate(R.layout.fragment_myproperty_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myPropertyList = new ArrayList<>();

            myAdapter = new MyMyPropertyRecyclerViewAdapter(
                    getActivity(),
                    R.layout.fragment_myproperty,
                    myPropertyList,
                    mListener
            );

            recyclerView.setAdapter(myAdapter);
            launchViewModel();
        }
        return view;
    }

    private void launchViewModel() {
        myPropertyViewModel = ViewModelProviders.of(getActivity()).get(PropertyViewModel.class);
        myPropertyViewModel.getMyPropertiesFromCall(UtilToken.getToken(getActivity()));
        myPropertyViewModel.getMyProperties().observe(getActivity(), new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> properties) {
                myAdapter.setNewMyProperties(properties);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyPropertyInteractionListener) {
            mListener = (MyPropertyInteractionListener) context;
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
