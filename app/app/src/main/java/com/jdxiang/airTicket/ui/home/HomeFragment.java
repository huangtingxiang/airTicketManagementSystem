package com.jdxiang.airTicket.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.flightManagement.FlightDetailActivity;
import com.jdxiang.airTicket.flightManagement.SearchListActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.DownloadImageTask;
import com.jdxiang.airTicket.httpService.FlightManagementService;
import com.jdxiang.airTicket.httpService.UserService;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    Visitor visitor;

    SharedPreferences sharedPreferences;

    private HomeViewModel homeViewModel;

    private HomeSectionPagesAdapter homeSectionPagesAdapter; // tab配置器

    private CarouselView carouselView; // 轮滑图

    FlightManagementService flightManagementService = FlightManagementService.getInstance();

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
        sharedPreferences = HomeFragment.this.getContext().getSharedPreferences(FlightOneWayTripFragment.FlightMessage, MODE_PRIVATE);
        initCarousel();
        initTab();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 设置推荐航班
        // 获取上次查询的航班
        Long lastFlightId = sharedPreferences.getLong("startPlaceId" + UserService.loginUser.getId(), -1);
        flightManagementService.searchFlightByPage(lastFlightId == -1 ? null : lastFlightId,
                null,
                Calendar.getInstance().getTime().getTime(),
                (response) -> {
                    FlightManagement[] flightManagements = (FlightManagement[]) response.getData();
                    RecyclerView listView = root.findViewById(R.id.recommendFlight);
                    listView.setNestedScrollingEnabled(false);
                    listView.setFocusable(false);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    listView.setLayoutManager(linearLayoutManager);
                    listView.setAdapter(new RecommendFlightListAdapter(flightManagements));
                });
    }

    private void initCarousel() {
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
//        homeSectionPagesAdapter.addFragment(this.flightGoAndBackFragment, "往返");
//        homeSectionPagesAdapter.addFragment(this.flightMultiPathTripFragment, "多程");

        TabLayout tabLayout = root.findViewById(R.id.tabs);
        ViewPager viewPager = root.findViewById(R.id.container);
        viewPager.setAdapter(homeSectionPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //
    class RecommendFlightListAdapter extends RecyclerView.Adapter<RecommendFlightListAdapter.RecommendFlightListHolder> {

        FlightManagement[] flightManagements;

        RecommendFlightListAdapter(FlightManagement[] flightManagements) {
            this.flightManagements = flightManagements;
        }

        @NonNull
        @Override
        public RecommendFlightListAdapter.RecommendFlightListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_flight_lisit_item, parent, false);
            return new RecommendFlightListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendFlightListAdapter.RecommendFlightListHolder holder, int position) {
            FlightManagement flightManagement = flightManagements[position];
            holder.startAndArrivePlace.setText(flightManagement.getStartingPlace().getName() + "至" + flightManagement.getDestination().getName());
            Double price = 0.0;
            for (TicketPrice ticketPrice : flightManagement.getTicketPrices()) {
                if (price == 0.0 || price > ticketPrice.getPrice()) {
                    price = ticketPrice.getPrice();
                }
            }
            holder.flightPrice.setText("¥" + price.toString() + "起");
            holder.flightStartDate.setText(BaseHttpService.dateFormat(flightManagement.getStartTime(), "yyyy-MM-dd"));
            holder.flightStartTime.setText(BaseHttpService.dateFormat(flightManagement.getStartTime(), "HH:mm:ss"));
            holder.flightPlaneMessage.setText(flightManagement.getPlane().getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlightDetailActivity.flightManagement = flightManagement;
                    Intent intent = new Intent(HomeFragment.this.getContext(), FlightDetailActivity.class);
                    intent.putExtra("startPlaceName", flightManagement.getStartingPlace().getName());
                    intent.putExtra("destinationName", flightManagement.getDestination().getName());
                    startActivity(intent);
                }
            });
            String urlString = BaseHttpService.BASE_HOST + flightManagement.getPlane().getAirlineCompany().getIcon();
            new DownloadImageTask(holder.companyImage)
                    .execute(urlString);
        }

        @Override
        public int getItemCount() {
            return flightManagements.length;
        }

        class RecommendFlightListHolder extends RecyclerView.ViewHolder {

            TextView startAndArrivePlace;

            TextView flightPrice;

            TextView flightStartDate;

            TextView flightPlaneMessage;

            TextView flightStartTime;

            ImageView companyImage;

            public RecommendFlightListHolder(@NonNull View itemView) {
                super(itemView);
                startAndArrivePlace = itemView.findViewById(R.id.startAndArrivePlace);
                flightPrice = itemView.findViewById(R.id.flightPrice);
                flightStartDate = itemView.findViewById(R.id.flightStartDate);
                flightPlaneMessage = itemView.findViewById(R.id.flightPlaneMessage);
                flightStartTime = itemView.findViewById(R.id.flightStartTime);
                companyImage = itemView.findViewById(R.id.companyImage);
            }
        }
    }

}