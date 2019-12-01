package com.jdxiang.airTicket.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jdxiang.airTicket.R;
import com.jdxiang.airTicket.entity.Visitor;
import com.jdxiang.airTicket.flightManagement.SearchListActivity;
import com.jdxiang.airTicket.httpService.BaseHttpService;
import com.jdxiang.airTicket.httpService.DownloadImageTask;
import com.jdxiang.airTicket.httpService.VisitorService;
import com.jdxiang.airTicket.personal.MyCountActivity;
import com.jdxiang.airTicket.personal.MyWalletActivity;
import com.jdxiang.airTicket.personal.TransactionRecordActivity;
import com.jdxiang.airTicket.ui.home.FlightOneWayTripFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

    VisitorService visitorService = VisitorService.getInstance();
    CircleImageView circleImageView;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        circleImageView = root.findViewById(R.id.personImage);

        visitorService.getCurrentVisitor((result -> {
            Visitor visitor = (Visitor) result.getData();

            if (visitor.getImageUrl() != null && !visitor.getImageUrl().equals("")) {
                String urlString = BaseHttpService.BASE_HOST + visitor.getImageUrl();
                new DownloadImageTask(circleImageView)
                        .execute(urlString);
            }
        }));

        // 点击头像时进入个人编辑
        CircleImageView circleImageView = root.findViewById(R.id.personImage);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getContext(), MyCountActivity.class);
                startActivity(intent);
            }
        });

        // 点击消费记录时进入 消费记录
        root.findViewById(R.id.recordBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getContext(), TransactionRecordActivity.class);
                startActivity(intent);
            }
        });

        (root.findViewById(R.id.walletBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getContext(), MyWalletActivity.class);
                startActivity(intent);
            }
        });

        // 进入如何乘机
        root.findViewById(R.id.helpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}