package com.jdxiang.airTicket.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.flightManagement.FlightDetailActivity;
import com.jdxiang.airTicket.flightManagement.FlightReserveActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

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
            RecyclerView orderList = root.findViewById(R.id.orderList);
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
            }
        }
    }
}