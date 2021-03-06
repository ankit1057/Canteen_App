package com.example.getfood.ui.orderlist;

import com.example.canteen_app_models.models.FullOrder;
import com.example.firebase_api_library.listeners.DBValueEventListener;
import com.example.getfood.ui.base.BasePresenter;
import com.example.getfood.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;

public class OrderListPresenter<V extends OrderListMvpView> extends BasePresenter<V> implements OrderListMvpPresenter<V> {

    public OrderListPresenter() {
    }

    @Override
    public void fetchOrderList() {
        String userRollNo = AppUtils.getRollNoFromEmail(apiManager.getCurrentUserEmail());
        if (userRollNo == null) {
            getMvpView().onRollNumberNull();
            return;
        }

        apiManager.orderListListener(new DBValueEventListener<ArrayList<FullOrder>>() {
            @Override
            public void onDataChange(ArrayList<FullOrder> data) {
//                TODO: Later add option to flip the order list based on order timing
                Collections.reverse(data);
                getMvpView().bindListAdapter(data);
            }

            @Override
            public void onCancelled(Error error) {

            }
        });
    }
}
