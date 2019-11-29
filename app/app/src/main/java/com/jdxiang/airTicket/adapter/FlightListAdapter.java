package com.jdxiang.airTicket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.FlightManagement;
import com.jdxiang.airTicket.entity.TicketPrice;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.DownloadImageTask;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.FlightSearchListHolder>{
    FlightManagement[] flightManagements;
    View.OnClickListener onClickListener;

    public FlightListAdapter(FlightManagement[] flightManagements, View.OnClickListener onClickListener) {
        this.flightManagements = flightManagements;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FlightListAdapter.FlightSearchListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_management_search_list_item, parent, false);
        // 点击搜索的航班列表时显示航班详情
        view.setOnClickListener(onClickListener);
        return new FlightListAdapter.FlightSearchListHolder(view);
    }

    public void setFlightManagements(FlightManagement[] flightManagements) {
        this.flightManagements = flightManagements;
    }

    public FlightManagement getByPosition(int position) {
        return flightManagements[position];
    }

    @Override
    public void onBindViewHolder(@NonNull FlightListAdapter.FlightSearchListHolder holder, int position) {
        FlightManagement flightManagement = flightManagements[position];
        holder.startAirPort.setText(flightManagement.getStartingAirPort().getName());
        holder.startTextView.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(flightManagement.getStartTime()));
        holder.endAirPort.setText(flightManagement.getDestinationAirPort().getName());
        holder.endTextView.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(flightManagement.getArrivalTime()));
        holder.planeNameTextView.setText(flightManagement.getPlane().getName());
        String urlString = BaseHttpService.BASE_HOST + flightManagement.getPlane().getAirlineCompany().getIcon();
        new DownloadImageTask(holder.companyImage)
                .execute(urlString);
        // 查找主舱位
        TicketPrice primaryTicketPrice = null;
        for (TicketPrice ticketPrice :
                flightManagement.getTicketPrices()) {
            if (ticketPrice.getShipSpace().getPrimaried()) {
                primaryTicketPrice = ticketPrice;
                break;
            }
        }
        if (primaryTicketPrice != null) {
            holder.ticketShipSpaceTextView.setText(primaryTicketPrice.getShipSpace().getDescribed());
            holder.ticketPriceTextView.setText(primaryTicketPrice.getPrice().toString());
        }
    }

    @Override
    public int getItemCount() {
        return this.flightManagements.length;
    }

    public class FlightSearchListHolder extends RecyclerView.ViewHolder {

        TextView startTextView;

        TextView endTextView;

        TextView startAirPort;

        TextView endAirPort;

        TextView planeNameTextView;

        TextView ticketShipSpaceTextView;

        TextView ticketPriceTextView;

        ImageView companyImage;

        public FlightSearchListHolder(@NonNull View itemView) {
            super(itemView);
            startTextView = itemView.findViewById(R.id.startTime);
            endTextView = itemView.findViewById(R.id.endTime);
            startAirPort = itemView.findViewById(R.id.startAirPort);
            endAirPort = itemView.findViewById(R.id.endAirPort);
            planeNameTextView = itemView.findViewById(R.id.planeNameTextView);
            ticketShipSpaceTextView = itemView.findViewById(R.id.ticketShipSpaceTextView);
            ticketPriceTextView = itemView.findViewById(R.id.ticketPriceTextView);
            companyImage = itemView.findViewById(R.id.companyImage);
        }
    }
}
