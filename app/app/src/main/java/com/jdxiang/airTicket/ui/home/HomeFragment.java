package com.jdxiang.airTicket.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jdxiang.airTicket.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private HomeSectionPagesAdapter homeSectionPagesAdapter; // tab配置器

    private CarouselView carouselView; // 轮滑图

    FlightOneWayTripFragment flightOneWayTripFragment = new FlightOneWayTripFragment();
    FlightGoAndBackFragment flightGoAndBackFragment = new FlightGoAndBackFragment();
    FlightMultiPathTripFragment flightMultiPathTripFragment = new FlightMultiPathTripFragment();


    View root;

    final int[] images = {R.drawable.plane1, R.drawable.plane2, R.drawable.plane3};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        initCarousel();
        initTab();
        // 设置推荐航班
        RecyclerView listView = root.findViewById(R.id.recommendFlight);
        listView.setNestedScrollingEnabled(false);
        listView.setFocusable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(new RecommendFlightListAdapter());
        return root;
    }

    private void initCarousel () {
        // 设置轮滑图
        carouselView = (CarouselView) root.findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images[position]);
            }
        });
    }

    private void initTab() {
        // 设置tab
        homeSectionPagesAdapter = new HomeSectionPagesAdapter(getChildFragmentManager());
        homeSectionPagesAdapter.addFragment(this.flightOneWayTripFragment, "单程");
        homeSectionPagesAdapter.addFragment(this.flightGoAndBackFragment, "往返");
        homeSectionPagesAdapter.addFragment(this.flightMultiPathTripFragment, "多程");

        TabLayout tabLayout = root.findViewById(R.id.tabs);
        ViewPager viewPager = root.findViewById(R.id.container);
        viewPager.setAdapter(homeSectionPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //
    class RecommendFlightListAdapter extends RecyclerView.Adapter<RecommendFlightListAdapter.RecommendFlightListHolder> {

        private ArrayList<String> data = new ArrayList<>();

        {
            for (int i = 0; i < 5; i++) {
                data.add("测试");
            }
        }

        @NonNull
        @Override
        public RecommendFlightListAdapter.RecommendFlightListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_flight_lisit_item, parent, false);
            return new RecommendFlightListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendFlightListAdapter.RecommendFlightListHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class RecommendFlightListHolder extends RecyclerView.ViewHolder {

            public RecommendFlightListHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}