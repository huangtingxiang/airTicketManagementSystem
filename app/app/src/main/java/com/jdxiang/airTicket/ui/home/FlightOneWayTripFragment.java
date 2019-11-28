package com.jdxiang.airTicket.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.City;
import com.jdxiang.airTicket.flightManagement.SearchListActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.CityService;
import com.jdxiang.airTicket.httpService.UserService;
import com.jdxiang.airTicket.login.LoginActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FlightOneWayTripFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    public static final String FlightMessage = "flight_message";

    private TextView startTextLabel;

    CityService cityService = new CityService(); // 城市服务

    SharedPreferences sharedPreferences;

    City startCity; // 起始城市

    City arriveCity; // 到达城市

    Calendar searchDate; // 搜索时间

    TextView startCityTextView;

    TextView arriveCityTextView;

    String startPlaceName;
    String destinationName;
    Long startPlaceId;
    Long destinationId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.flight_one_way_trip, container, false);
        sharedPreferences = FlightOneWayTripFragment.this.getContext().getSharedPreferences(FlightMessage, MODE_PRIVATE);
        // 点击出发地时 设置城市弹出框
        startCityTextView = root.findViewById(R.id.startPlaceLabel);
        startCityTextView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                FlightOneWayTripFragment.this.showCityDialog((city) -> {
                    startCity = city;
                    startCityTextView.setText(city.getName());
                });
            }
        });

        // 查询是否存在上次数据
        startPlaceId = sharedPreferences.getLong("startPlaceId" + UserService.loginUser.getId(), 0);
        destinationId = sharedPreferences.getLong("destinationId" + UserService.loginUser.getId(), 0);
        startPlaceName = sharedPreferences.getString("startPlaceName" + UserService.loginUser.getId(), "起始地");
        destinationName = sharedPreferences.getString("destinationName" + UserService.loginUser.getId(), "目的地");

        // 设置上次查询数据信息
        ((TextView) root.findViewById(R.id.startPlaceLabel)).setText(startPlaceName);
        ((TextView) root.findViewById(R.id.arrivePlaceLabel)).setText(destinationName);

        // 点击到达地时 设置城市弹出框
        arriveCityTextView = root.findViewById(R.id.arrivePlaceLabel);
        arriveCityTextView.setOnClickListener((View v) -> {
            FlightOneWayTripFragment.this.showCityDialog((city) -> {
                arriveCity = city;
                arriveCityTextView.setText(city.getName());
            });
        });

        //  点击时间选择时
        startTextLabel = root.findViewById(R.id.startDate);
        searchDate = Calendar.getInstance();
        setDateLabel(searchDate.get(Calendar.YEAR), searchDate.get(Calendar.MONTH), searchDate.get(Calendar.DAY_OF_MONTH));
        startTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FlightOneWayTripFragment.this,
                        searchDate.get(Calendar.YEAR), // Initial year selection
                        searchDate.get(Calendar.MONTH), // Initial month selection
                        searchDate.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getFragmentManager(), "出发日期");
                dpd.setLocale(Locale.CHINA);
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                dpd.setOkText("确定");
                dpd.setCancelText("取消");
            }
        });

        // 设置搜索框点中
        Button button = root.findViewById(R.id.one_way_search_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlightOneWayTripFragment.this.getContext(), SearchListActivity.class);
                // 传递起始地点 到达地点 始发时间给航班检索活动
                if (startCity != null) {
                    startPlaceId = startCity.getId();
                    startPlaceName = startCity.getName();
                }
                if (arriveCity != null) {
                    destinationId = arriveCity.getId();
                    destinationName = arriveCity.getName();
                }

                intent.putExtra("startPlaceId", startPlaceId);
                intent.putExtra("destinationId", destinationId);
                intent.putExtra("startTime", searchDate.getTime().getTime());
                intent.putExtra("startPlaceName", startPlaceName);
                intent.putExtra("destinationName", destinationName);

                // 存储最后一次查询信息
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong("startPlaceId" + UserService.loginUser.getId(), startPlaceId);
                edit.putLong("destinationId" + UserService.loginUser.getId(), destinationId);
                edit.putString("startPlaceName" + UserService.loginUser.getId(), startPlaceName);
                edit.putString("destinationName" + UserService.loginUser.getId(), destinationName);
                edit.apply();
                startActivity(intent);
            }
        });
        return root;
    }

    // 显示城市列表弹出框
    private void showCityDialog(CityCallBack cityCallBack) {
        // 请求后台数据 显示dialog
        cityService.getAll((response) -> {
            City[] cities = (City[]) response.getData();
            // 设置城市列表dialog
            final DialogPlus dialog = DialogPlus.newDialog(FlightOneWayTripFragment.this.getContext())
                    .setContentHolder(new ViewHolder(R.layout.city_select_dialog))
                    .setMargin(0, 150, 0, 0)
                    .setExpanded(false, ViewGroup.LayoutParams.WRAP_CONTENT)  // This will enable the expand feature, (similar to android L share dialog)
                    .create();
            View view = dialog.getHolderView();
            view.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // 封装callback 关闭弹出框
            CityCallBack newCityCallBack = (city) -> {
                dialog.dismiss();
                cityCallBack.onSelect(city);
            };
            // 设置所有城市
            RecyclerView allListView = view.findViewById(R.id.all_city_list);
            allListView.setNestedScrollingEnabled(false);
            allListView.setLayoutManager(new GridLayoutManager(FlightOneWayTripFragment.this.getContext(), 4));
            allListView.setAdapter(new CityListAdapter(cities, newCityCallBack));
            // 设置热门城市
            RecyclerView primaryListView = view.findViewById(R.id.primaried_city_list);
            primaryListView.setNestedScrollingEnabled(false);
            primaryListView.setLayoutManager(new GridLayoutManager(FlightOneWayTripFragment.this.getContext(), 4));
            ArrayList<City> primaryCities = new ArrayList<City>();
            for (City city :
                    cities) {
                if (city.getPrimaried()) {
                    primaryCities.add(city);
                }
            }
            primaryListView.setAdapter(new CityListAdapter((City[]) primaryCities.toArray(new City[primaryCities.size()]), newCityCallBack));
            dialog.show();
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setDateLabel(year, monthOfYear, dayOfMonth);
    }

    private void setDateLabel(int year, int month, int day) {
        searchDate.set(Calendar.YEAR, year);
        searchDate.set(Calendar.MONTH, month);
        searchDate.set(Calendar.DAY_OF_MONTH, day);
        startTextLabel.setText(year + "年" + (month + 1) + "月" + day + "日");
    }

    interface CityCallBack {
        void onSelect(City city);
    }

    //  城市列表设配器
    class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListHolder> {

        private City[] allCity = {};
        private CityCallBack cityCallBack;

        CityListAdapter(City[] cities, CityCallBack cityCallBack) {
            this.allCity = cities;
            this.cityCallBack = cityCallBack;
        }

        @NonNull
        @Override
        public CityListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_select_list_item, parent, false);
            return new CityListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CityListHolder holder, int position) {
            holder.button.setText(allCity[position].getName());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    cityCallBack.onSelect(allCity[position]);
                }
            });
        }

        @Override
        public int getItemCount() {
            return allCity.length;
        }

        class CityListHolder extends RecyclerView.ViewHolder {

            Button button;

            public CityListHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.city_list_item);
            }
        }
    }
}
