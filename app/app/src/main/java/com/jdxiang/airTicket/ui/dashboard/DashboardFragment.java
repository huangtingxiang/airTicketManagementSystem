package com.jdxiang.airTicket.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.flightManagement.FlightDetailActivity;
import com.jdxiang.airTicket.flightManagement.FlightPayforActivity;
import com.jdxiang.airTicket.flightManagement.FlightReserveActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    SweetAlertDialog pDialog;

    RecyclerView orderList;


    TicketOrderService ticketOrderService = TicketOrderService.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        // 设置订单列表
        ticketOrderService.getAll((response) -> {
            TicketOrder[] ticketOrders = (TicketOrder[]) response.getData();
            orderList = root.findViewById(R.id.orderList);
            orderList.setNestedScrollingEnabled(false);
            orderList.setFocusable(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardFragment.this.getContext());
            orderList.setLayoutManager(linearLayoutManager);
            orderList.setAdapter(new OrderListAdapter(ticketOrders));
        });


        return root;
    }

    // 订单列表设配器
    class OrderListAdapter extends RecyclerView.Adapter<DashboardFragment.OrderListAdapter.OrderListHolder> {

        TicketOrder[] ticketOrders;

        OrderListAdapter(TicketOrder[] ticketOrders) {
            this.ticketOrders = ticketOrders;
        }

        @NonNull
        @Override
        public DashboardFragment.OrderListAdapter.OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
            return new DashboardFragment.OrderListAdapter.OrderListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DashboardFragment.OrderListAdapter.OrderListHolder holder, int position) {
            TicketOrder order = ticketOrders[position];
            holder.subscribeTextView.setText("预定日期" + BaseHttpService.dateFormat(order.getCreateTime(), "MM-dd"));
            holder.startAndDestinationTextView.setText(order.getFlightManagement().getStartingPlace().getName() + "到" + order.getFlightManagement().getDestination().getName());
            holder.orderPriceTextView.setText(order.getTicketPrice().getPrice().toString());

            holder.startDateTextView.setText(BaseHttpService.dateFormat(order.getFlightManagement().getStartTime(), "MM-dd"));

            holder.startTimeTextView.setText(BaseHttpService.dateFormat(order.getFlightManagement().getStartTime(), "HH:mm"));
            holder.destinationTextView.setText(BaseHttpService.dateFormat(order.getFlightManagement().getArrivalTime(), "HH:mm"));
            // 订单状态
            holder.orderStatusTextView.setText(TicketOrder.orderStatusToString(order.getOrderStatus()));
            holder.startAriPortTextView.setText(order.getFlightManagement().getStartingAirPort().getName());

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DashboardFragment.this.getContext(), OrderDetailActivity.class);
                    intent.putExtra("id", order.getId());

                    startActivity(intent);
                }
            });

            // 显示按钮
            if (TicketOrder.isActive(order.getOrderStatus())) {
                holder.showActiveContainer();
                // 退票
                holder.view.findViewById(R.id.cancelActiveBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ticketOrderService.cancelPayFor(order.getId(), (result -> {
                            if (BaseHttpService.assertSuccessResponse(result.getResponse())) {
                                new SweetAlertDialog(DashboardFragment.this.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("成功退票!")
                                        .setConfirmText("确定").show();
                                order.setOrderStatus(TicketOrder.getCancel());
                                holder.hideAll();
                                OrderListAdapter.this.notifyDataSetChanged();
                            }
                        }));
                    }
                });

            } else if (TicketOrder.isSubscribe(order.getOrderStatus())) {
                holder.showSubscribeContainer();
                // 取消预定
                holder.view.findViewById(R.id.cancelSubscribeBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pDialog = FlightPayforActivity.getLoadingDialog(DashboardFragment.this.getContext());
                        pDialog.show();
                        ticketOrderService.cancelSubscribe(order.getId(), (response) -> {
                            pDialog.cancel();
                            if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                                new SweetAlertDialog(DashboardFragment.this.getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("已取消预约!")
                                        .setConfirmText("确定").show();
                                holder.hideAll();
                                order.setOrderStatus(TicketOrder.getCancel());
                                OrderListAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                });

                // 支付订单
                holder.view.findViewById(R.id.payForBtn).setOnClickListener((View v) -> {
                    pDialog = FlightPayforActivity.getLoadingDialog(DashboardFragment.this.getContext());
                    pDialog.show();
                    ticketOrderService.payForOrder(order.getId(), (response1) -> {
                        pDialog.cancel();
                        if (BaseHttpService.assertSuccessResponse(response1.getResponse())) {
                            new SweetAlertDialog(DashboardFragment.this.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("支付成功!")
                                    .setConfirmText("确定").show();
                            order.setOrderStatus(TicketOrder.getActive());
                            holder.showActiveContainer();
                            OrderListAdapter.this.notifyDataSetChanged();
                        } else {
                            if (response1.getResponse().code() == 403) {
                                new SweetAlertDialog(DashboardFragment.this.getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("余额不足!")
                                        .setConfirmText("确定").show();
                            }
                        }
                    });
                });
            }
        }

        @Override
        public int getItemCount() {
            return ticketOrders.length;
        }

        class OrderListHolder extends RecyclerView.ViewHolder {

            View view;

            TextView subscribeTextView;

            TextView startAndDestinationTextView;

            TextView orderPriceTextView;

            TextView startDateTextView;

            TextView startTimeTextView;

            TextView destinationTextView;

            TextView orderStatusTextView;

            TextView startAriPortTextView;

            LinearLayout orderActiveContainer;

            LinearLayout orderSubscribeContainer;

            LinearLayout container;

            public OrderListHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                subscribeTextView = itemView.findViewById(R.id.subscribeTextView);
                startAndDestinationTextView = itemView.findViewById(R.id.startAndDestinationTextView);
                orderPriceTextView = itemView.findViewById(R.id.orderPriceTextView);
                startDateTextView = itemView.findViewById(R.id.startDateTextView);
                startTimeTextView = itemView.findViewById(R.id.startTimeTextView);
                destinationTextView = itemView.findViewById(R.id.destinationTextView);
                orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
                startAriPortTextView = itemView.findViewById(R.id.startAriPortTextView);

                orderActiveContainer = view.findViewById(R.id.orderActiveContainer);
                orderSubscribeContainer = view.findViewById(R.id.orderSubscribeContainer);
                container = view.findViewById(R.id.container);
            }

            public void showSubscribeContainer() {
                orderActiveContainer.setVisibility(LinearLayout.GONE);
                orderSubscribeContainer.setVisibility(LinearLayout.VISIBLE);
            }

            public void showActiveContainer() {
                orderActiveContainer.setVisibility(LinearLayout.VISIBLE);
                orderSubscribeContainer.setVisibility(LinearLayout.GONE);
            }

            public void hideAll() {
                orderActiveContainer.setVisibility(LinearLayout.GONE);
                orderSubscribeContainer.setVisibility(LinearLayout.GONE);
            }
        }
    }

}