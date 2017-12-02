package com.example.vadym.bootcamplocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vadym.bootcamplocator.R;
import com.example.vadym.bootcamplocator.adapters.LocationsAdapter;
import com.example.vadym.bootcamplocator.services.DataService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO: 02.12.17 Тут цей фрагмент не потрібен, просто заміни на ті в'юхі, що лежать в ньому.
public class LocationsListFragment extends Fragment {

    public LocationsListFragment() {
        // Required empty public constructor
    }

    public static LocationsListFragment newInstance() {
        LocationsListFragment fragment = new LocationsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_locations);
        recyclerView.setHasFixedSize(true);

        LocationsAdapter adapter = new LocationsAdapter(DataService.getInstance().getBootcampLocationWithin10MilesOfZip(10012));
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        return view;
    }

}
