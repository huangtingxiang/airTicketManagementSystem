package com.jdxiang.airTicket.ui.dashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jdxiang.airTicket.MainActivity;
import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.Seat;
import com.jdxiang.airTicket.entity.SelectSeat;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TicketOrderService;
import com.jdxiang.airTicket.library.SeatTable;
import com.jdxiang.airTicket.login.LoginActivity;
import com.jdxiang.airTicket.login.RegisterActivity;
import com.jdxiang.airTicket.personal.MyCountActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SeatSelectActivity extends AppCompatActivity {

    public static SelectSeat defaultSeat;

    public SeatTable seatTableView;

    Seat selectSeat;

    Long orderId;

    Long flightId;

    public TicketOrderService ticketOrderService = TicketOrderService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        // 获取行列数
        Intent intent = getIntent();

        orderId = intent.getLongExtra("orderId", 0);
        flightId = intent.getLongExtra("flightId", 0);

        int row = intent.getIntExtra("row", 0);
        int col = intent.getIntExtra("col", 0);
        ticketOrderService.getFinishByFlightId(flightId,(response) -> {
            TicketOrder[] ticketOrders = (TicketOrder[]) response.getData();
            List<Seat> selectSeats = new ArrayList<>();
            for (TicketOrder order :
                    ticketOrders) {
                if (order.getSeat() != null) {
                    selectSeats.add(order.getSeat());
                }
            }
            SelectSeat selectSeatForInit = new SelectSeat(row, col, selectSeats);

            seatTableView.setScreenName("驾驶室");//设置屏幕名称
            seatTableView.setMaxSelected(1);//设置最多选中

            seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

                @Override
                public boolean isValidSeat(int row, int column) {
                    if (defaultSeat.findByRowAndCol(row + 1, column + 1) == null) {
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean isSold(int row, int column) {
                    if (selectSeatForInit.findByRowAndCol(row + 1, column + 1) != null) {
                        return true;
                    }
                    return false;
                }

                @Override
                public void checked(int row, int column) {
                    selectSeat = defaultSeat.findByRowAndCol(row + 1, column + 1);
                }

                @Override
                public void unCheck(int row, int column) {
                    selectSeat = null;
                }

                @Override
                public String[] checkedSeatTxt(int row, int column) {
                    String[] message = {defaultSeat.findByRowAndCol(row  + 1, column + 1).getNumber()};
                    return message;
                }

            });
            seatTableView.setData(row, col);
        });

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectSeat == null) {
                    new SweetAlertDialog(SeatSelectActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请选择座位!")
                            .setConfirmText("确定").show();
                } else {
                    ticketOrderService.selectSeat(orderId, selectSeat, (response) -> {
                        if (BaseHttpService.assertSuccessResponse(response.getResponse())) {
                            // 返回主界面
                            new SweetAlertDialog(SeatSelectActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("已完成订单!")
                                    .setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intentToMain = new Intent(SeatSelectActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentToMain);
                                }
                            }).show();
                        }

                    });
                }
            }
        });
    }
}
