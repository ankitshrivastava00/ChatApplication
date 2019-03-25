package com.ziasy.xmppchatapplication.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.ziasy.xmppchatapplication.ChatAdapter;
import com.ziasy.xmppchatapplication.R;

import java.util.ArrayList;

public class MapBoxTesting extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<MOdelLAt> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_chat_screen);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYW5raXRzaHJpdmFzdGF2IiwiYSI6ImNqcDhwZTQ1cTA0MjAzcXJwcmZ3dm01YmIifQ.z3u-alUA91GSmuFF65dHCw");

        list = new ArrayList<>();

        list.add(new MOdelLAt(-34.6054099, -58.363654800000006));
        list.add(new MOdelLAt(-34.6041508, -58.38555650000001));
        list.add(new MOdelLAt(-34.6114412, -58.37808899999999));
        list.add(new MOdelLAt(-34.6097604, -58.382064000000014));
        list.add(new MOdelLAt(-34.596636, -58.373077999999964));
        list.add(new MOdelLAt(-34.590548, -58.38256609999996));
        list.add(new MOdelLAt(-34.5982127, -58.38110440000003));
        list.add(new MOdelLAt(-34.6054099, -58.363654800000006));
        list.add(new MOdelLAt(-34.6041508, -58.38555650000001));
        list.add(new MOdelLAt(-34.6114412, -58.37808899999999));
        list.add(new MOdelLAt(-34.6097604, -58.382064000000014));
        list.add(new MOdelLAt(-34.596636, -58.373077999999964));
        list.add(new MOdelLAt(-34.590548, -58.38256609999996));
        list.add(new MOdelLAt(-34.5982127, -58.38110440000003));
        list.add(new MOdelLAt(-34.6054099, -58.363654800000006));
        list.add(new MOdelLAt(-34.6041508, -58.38555650000001));
        list.add(new MOdelLAt(-34.6114412, -58.37808899999999));
        list.add(new MOdelLAt(-34.6097604, -58.382064000000014));
        list.add(new MOdelLAt(-34.596636, -58.373077999999964));
        list.add(new MOdelLAt(-34.590548, -58.38256609999996));
        list.add(new MOdelLAt(-34.5982127, -58.38110440000003));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MapBoxAdapter adapter = new MapBoxAdapter();
        recyclerView.setAdapter(adapter);
    }

    class MapBoxAdapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_chat_item, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private MapView mapView;


        public ViewHolder(View itemView) {
            super(itemView);
            mapView = (MapView) itemView.findViewById(R.id.mapView);
        }

        @Override
        public void onMapReady(MapboxMap mapboxMap) {

        }
    }
}
