package com.project.taha.dicoveregypt.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.project.taha.dicoveregypt.R;
import com.project.taha.dicoveregypt.adapters.CustomAdapter;
import com.project.taha.dicoveregypt.utilies.Utilities;
import com.project.taha.dicoveregypt.interfaces.SiteListener;
import com.project.taha.dicoveregypt.models.Site;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.DESC_STRING;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.ENGLISHVERSION_NODE;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.IMAGE_URL_NODE;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.LATLONG_NODE;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.NAME_NODE;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.ROOT_NODE;
import static com.project.taha.dicoveregypt.utilies.FirebaseNodes.SITES_NODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegionFragment extends Fragment {
    private static final int ROW_COUNT = 1;
    @BindView(R.id.region_detail_list)
    RecyclerView recyclerView;
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    private GridLayoutManager gridLayoutManager;
    private Firebase mRef;
    private CustomAdapter customAdapter;
    private List<Site> list = new ArrayList<>();
    String regionName;
    SiteListener siteListener;
    private Unbinder unbinder;

    public RegionFragment() {
        // Required empty public constructor
    }

    public static RegionFragment newInstance() {
        RegionFragment f = new RegionFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            siteListener = (SiteListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        siteListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("regionName")) {
            regionName = intent.getStringExtra("regionName");

        }
        getActivity().setTitle(regionName);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_region, container, false);

        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mRef = new Firebase(ROOT_NODE + "/" + ENGLISHVERSION_NODE + "/" + regionName + "/"+SITES_NODE);
        recyclerView.setHasFixedSize(true);
        setGridManager();
        setHasOptionsMenu(true);
        list.clear();
        customAdapter = new CustomAdapter(getContext(), list, siteListener);
        recyclerView.setAdapter(customAdapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Site site = new Site();
                site.setName(dataSnapshot.child(NAME_NODE).getValue(String.class));
                site.setImage(dataSnapshot.child(IMAGE_URL_NODE).getValue(String.class));
                site.setDesc(dataSnapshot.child(DESC_STRING).getValue(String.class));
                site.setLatLong(dataSnapshot.child(LATLONG_NODE).getValue(String.class));
                list.add(site);
                customAdapter.notifyItemInserted(list.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }

    private void setGridManager() {
        gridLayoutManager = new GridLayoutManager(getActivity(), ROW_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Utilities.message(getContext(),getString(R.string.error_msg));
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
