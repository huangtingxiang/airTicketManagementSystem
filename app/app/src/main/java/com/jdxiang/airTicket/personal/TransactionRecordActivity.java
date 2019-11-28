package com.jdxiang.airTicket.personal;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.TicketOrder;
import com.jdxiang.airTicket.entity.TransactionRecord;
import com.jdxiang.airTicket.flightManagement.FlightPayforActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.TransactionRecordService;
import com.jdxiang.airTicket.ui.dashboard.DashboardFragment;
import com.jdxiang.airTicket.ui.dashboard.OrderDetailActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TransactionRecordActivity extends AppCompatActivity {

    RecyclerView recordList;

    TransactionRecordService transactionRecordService = TransactionRecordService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_record);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 设置订单列表
        transactionRecordService.getAll((response) -> {
            TransactionRecord[] transactionRecords = (TransactionRecord[]) response.getData();
            recordList = findViewById(R.id.recordList);
            recordList.setNestedScrollingEnabled(false);
            recordList.setFocusable(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TransactionRecordActivity.this);
            recordList.setLayoutManager(linearLayoutManager);
            recordList.setAdapter(new RecordListAdapter(transactionRecords));
        });

    }

    // 订单列表设配器
    class RecordListAdapter extends RecyclerView.Adapter<TransactionRecordActivity.RecordListAdapter.RecordListHolder> {

        TransactionRecord[] transactionRecords;

        RecordListAdapter(TransactionRecord[] transactionRecords) {
            this.transactionRecords = transactionRecords;
        }

        @NonNull
        @Override
        public TransactionRecordActivity.RecordListAdapter.RecordListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_record_item, parent, false);
            return new TransactionRecordActivity.RecordListAdapter.RecordListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionRecordActivity.RecordListAdapter.RecordListHolder holder, int position) {
            TransactionRecord transactionRecord = transactionRecords[position];
            String pay = "";
            if (transactionRecord.getPayFor()) {
                holder.isPayFor.setText("账户扣款!");
                pay = "-";
            } else {
                holder.isPayFor.setText("账户收款!");
                pay = "+";
            }
            holder.recordPrice.setText(pay + transactionRecord.getPrice());
            holder.recordDate.setText(BaseHttpService.dateFormat(transactionRecord.getCreateTime(), "yyyy-MM-dd"));
            holder.recordMessage.setText(transactionRecord.getRecordMessage());
        }

        @Override
        public int getItemCount() {
            return transactionRecords.length;
        }

        class RecordListHolder extends RecyclerView.ViewHolder {

            View view;

            TextView recordPrice;

            TextView recordDate;

            TextView isPayFor;

            TextView recordMessage;

            public RecordListHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                recordPrice = itemView.findViewById(R.id.recordPrice);
                recordDate = itemView.findViewById(R.id.recordDate);
                isPayFor = itemView.findViewById(R.id.isPayFor);
                recordMessage = itemView.findViewById(R.id.recordMessage);
            }
        }
    }
}
