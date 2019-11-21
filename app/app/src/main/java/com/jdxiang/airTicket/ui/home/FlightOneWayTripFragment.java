package com.jdxiang.airTicket.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.flightManagement.SearchListActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FlightOneWayTripFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView startTextLabel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.flight_one_way_trip, container, false);
        // 点击出发地时
        TextView textView = root.findViewById(R.id.startPlaceLabel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置城市列表dialog
                final DialogPlus dialog = DialogPlus.newDialog(FlightOneWayTripFragment.this.getContext())
                        .setContentHolder(new ViewHolder(R.layout.city_select_dialog))
                        .setMargin(0, 150, 0, 0)
                        .setExpanded(true, ViewGroup.LayoutParams.MATCH_PARENT)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                View view = dialog.getHolderView();
                RecyclerView listView = view.findViewById(R.id.list);
                listView.setNestedScrollingEnabled(false);
                view.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                GridLayoutManager linearLayoutManager = new GridLayoutManager(FlightOneWayTripFragment.this.getContext(), 4);
                listView.setLayoutManager(linearLayoutManager);
                listView.setAdapter(new CityListAdapter());
                dialog.show();
            }
        });
        //  点击时间选择时
        startTextLabel = root.findViewById(R.id.startDate);
        startTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        FlightOneWayTripFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
// If you're calling this from a support Fragment
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
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        startTextLabel.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
    }

    //  城市列表设配器
    class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListHolder> {

        private ArrayList<String> data = new ArrayList<>();

        {
            for (int i = 0; i < 100; i++) {
                data.add("天津");
            }
        }

        @NonNull
        @Override
        public CityListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_select_list_item, parent, false);
            return new CityListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CityListHolder holder, int position) {
            holder.button.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
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
