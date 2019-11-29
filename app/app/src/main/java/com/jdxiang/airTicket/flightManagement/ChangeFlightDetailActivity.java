package com.jdxiang.airTicket.flightManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;
import com.jdxiang.airTicket.login.RegisterActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangeFlightDetailActivity extends AppCompatActivity {

    String startName;
    String destinationName;
    Long orderId;
    Double originPrice;
    TicketOrderService ticketOrderService = TicketOrderService.getInstance();

    public static FlightManagement flightManagement = new FlightManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 获取标题
        Intent intent = getIntent();
        startName = intent.getStringExtra("startPlaceName");
        destinationName = intent.getStringExtra("destinationName");
        orderId = intent.getLongExtra("orderId", 0);
        originPrice = intent.getDoubleExtra("originPrice", 0);
        TextView titleTextView = findViewById(R.id.flight_title_text);
        titleTextView.setText(startName + "到" + destinationName);

        // 设置航班信息
        ((TextView) findViewById(R.id.companyNameTextView)).setText(flightManagement.getPlane().getAirlineCompany().getName());
        ((TextView) findViewById(R.id.startTimeTextView)).setText(BaseHttpService.dateFormat(flightManagement.getStartTime(), "HH:mm"));
        ((TextView) findViewById(R.id.arrivalTimeTextView)).setText(BaseHttpService.dateFormat(flightManagement.getArrivalTime(), "HH:mm"));
        ((TextView) findViewById(R.id.startAirPortTextView)).setText(flightManagement.getStartingAirPort().getName());
        ((TextView) findViewById(R.id.arrivalAirPortTextView)).setText(flightManagement.getDestinationAirPort().getName());
        ((TextView) findViewById(R.id.planeNameTextView)).setText(flightManagement.getPlane().getName());


        // 设置舱位列表
        RecyclerView listView = findViewById(R.id.spaceList);
        listView.setNestedScrollingEnabled(false);
        listView.setFocusable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(new ChangeFlightDetailActivity.SpaceListAdapter());

    }

    class SpaceListAdapter extends RecyclerView.Adapter<ChangeFlightDetailActivity.SpaceListAdapter.SpaceListHolder> {

        @NonNull
        @Override
        public ChangeFlightDetailActivity.SpaceListAdapter.SpaceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_management_space_item, parent, false);
            return new ChangeFlightDetailActivity.SpaceListAdapter.SpaceListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChangeFlightDetailActivity.SpaceListAdapter.SpaceListHolder holder, int position) {
            // 设置信息
            TicketPrice ticketPrice = flightManagement.getTicketPrices().get(position);
            holder.price.setText(ticketPrice.getPrice().toString());
            holder.name.setText(ticketPrice.getShipSpace().getDescribed());
            // 点击预定按钮时进入预定界面
            ((Button) holder.view.findViewById(R.id.flightReserveBtn)).setText("改签");
            holder.view.findViewById(R.id.flightReserveBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contentText = "";
                    Double disparity = originPrice - ticketPrice.getPrice();
                    if (disparity < 0) {
                        contentText = "改签需补充"+ Math.abs(disparity) + "元差价";
                    } else if (disparity > 0) {
                        contentText = "改签将退回"+ disparity + "元差价";
                    } else {
                        contentText = "改签无需补充差价";
                    }
                    TicketOrder ticketOrder = new TicketOrder();
                    ticketOrder.setTicketPrice(ticketPrice);
                    FlightManagement flightManagementSend = new FlightManagement();
                    flightManagementSend.setId(flightManagement.getId());
                    ticketOrder.setFlightManagement(flightManagementSend);
                    final SweetAlertDialog confirm = new SweetAlertDialog(ChangeFlightDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("确定要改签吗!")
                            .setContentText(contentText)
                            .setCancelText("取消")
                            .setConfirmText("确定");
                    confirm.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ticketOrderService.changeOrder(orderId, ticketOrder, (response) -> {
                                if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                                    new SweetAlertDialog(ChangeFlightDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("改签成功!")
                                            .setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            // 跳转回主页面
                                            Intent intentToMain = new Intent(ChangeFlightDetailActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intentToMain);
                                        }
                                    }).show();
                                } else {
                                    confirm.cancel();
                                    if (response.getResponse().code() == 403) {
                                        new SweetAlertDialog(ChangeFlightDetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("余额不足!")
                                                .setConfirmText("确定").show();
                                    }
                                }
                            });
                        }
                    });
                    confirm.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return flightManagement.getTicketPrices().size();
        }

        class SpaceListHolder extends RecyclerView.ViewHolder {

            TextView price;
            TextView name;

            View view;

            public SpaceListHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                price = itemView.findViewById(R.id.shipSpacePriceTextView);
                name = itemView.findViewById(R.id.shipSpaceNameTextView);
            }
        }
    }
}
