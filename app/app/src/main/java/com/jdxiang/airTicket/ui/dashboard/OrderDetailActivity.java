package com.jdxiang.airTicket.ui.dashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.Plane;
import com.jdxiang.airTicket.entity.Seat;
import com.jdxiang.airTicket.entity.SelectSeat;
import com.jdxiang.airTicket.entity.ShipSpace;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;

public class OrderDetailActivity extends AppCompatActivity {

    Long orderId;

    Plane plane;

    TicketOrder ticketOrder;

    FlightManagement flightManagement;

    // 订单服务
    TicketOrderService ticketOrderService = TicketOrderService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent = getIntent();
        orderId = intent.getLongExtra("id", -1);
        // 获取订单
        ticketOrderService.getById(orderId, (response) -> {
            ticketOrder = (TicketOrder) response.getData();
            flightManagement = ticketOrder.getFlightManagement();
            Visitor visitor = ticketOrder.getVisitor();
            plane = flightManagement.getPlane();


            // 更新ui
            ((TextView) findViewById(R.id.orderPriceTextView)).setText(ticketOrder.getTicketPrice().getPrice().toString()); // 订单价钱
            ((TextView) findViewById(R.id.orderStatusTextView)).setText(TicketOrder.orderStatusToString(ticketOrder.getOrderStatus())); // 订单状态
            ((TextView) findViewById(R.id.orderCreateDateTextView)).setText(BaseHttpService.dateFormat(ticketOrder.getCreateTime(), "yyyy-MM-dd")); // 订单日期
            ((TextView) findViewById(R.id.startTimeTextView)).setText(BaseHttpService.dateFormat(ticketOrder.getFlightManagement().getStartTime(), "MM月dd日")); // 航班起始日期 月日
            ((TextView) findViewById(R.id.startHourTextView)).setText(BaseHttpService.dateFormat(ticketOrder.getFlightManagement().getStartTime(), "HH:mm")); // 航班起始日期 时分
            ((TextView) findViewById(R.id.arriveTimeTextView)).setText(BaseHttpService.dateFormat(ticketOrder.getFlightManagement().getArrivalTime(), "HH:mm")); // 航班到达日期 时分

            ((TextView) findViewById(R.id.startAndArrivePlace)).setText(flightManagement.getStartingPlace().getName() + "至" + flightManagement.getDestination().getName()); // 起始地和目的地
            ((TextView) findViewById(R.id.startAirPort)).setText(flightManagement.getStartingAirPort().getName()); // 起始机场
            String seatMessage = ticketOrder.getSeat() == null ? "" : "座位号:" + ticketOrder.getSeat().getNumber();
            ((TextView) findViewById(R.id.planeMessage)).setText(flightManagement.getPlane().getName() + "|" + ticketOrder.getTicketPrice().getShipSpace().getDescribed() + "|" + seatMessage); // 飞机信息
            ((TextView) findViewById(R.id.arrivalAirPort)).setText(flightManagement.getDestinationAirPort().getName()); // 到达机场

            ((TextView) findViewById(R.id.visitorName)).setText(visitor.getName());
            ((TextView) findViewById(R.id.visitorIdCard)).setText(visitor.getIdCard());
            ((TextView) findViewById(R.id.visitorPhone)).setText(visitor.getPhoneNumber());

            if (TicketOrder.isActive(ticketOrder.getOrderStatus())) {
                findViewById(R.id.footer).setVisibility(LinearLayout.VISIBLE);
            }

        });

        // 进行座位选择
        (findViewById(R.id.checkInFlight)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeatSelectActivity.defaultSeat = new SelectSeat(plane.getTotalRow(), plane.getTotalCol(), ticketOrder.getTicketPrice().getShipSpace().getSeats());
                Intent intent = new Intent(OrderDetailActivity.this, SeatSelectActivity.class);
                intent.putExtra("row", plane.getTotalRow());
                intent.putExtra("col", plane.getTotalCol());
                intent.putExtra("orderId", orderId);
                intent.putExtra("flightId", flightManagement.getId());
                startActivity(intent);
            }
        });
    }
}
