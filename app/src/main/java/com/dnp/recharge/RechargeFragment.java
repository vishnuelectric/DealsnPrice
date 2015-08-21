package com.dnp.recharge;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealnprice.activity.R;
import com.dnp.asynctask.Pending_amount;
import com.dnp.recharge.adapter.CircleListAdapter;
import com.dnp.recharge.adapter.OperatorListAdapter;
import com.dnp.recharge.model.OperatorInfo;
import com.dnp.recharge.model.ResGetProvider;
import com.dnp.recharge.model.ResRecharge;

import java.util.ArrayList;

public class RechargeFragment extends Fragment {

    private EditText mEdtMobileNumber;
    private TextView mTxvOperator;
    private TextView mTxvCircle;
    private EditText mEdtAmount;
    private Button mBtnRecharge;
    private String mSelectedCircle = null;
    private OperatorInfo mSelectedOPerator = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_recharge, container, false);
        Pending_amount pp = new Pending_amount(getActivity());
        pp.execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdtMobileNumber = (EditText) getView().findViewById(R.id.edt_recharge_mobile_number);
        mTxvOperator = (TextView) getView().findViewById(R.id.txv_recharge_operator);
        mTxvOperator.setOnClickListener(new OperatorClickListener());
        mTxvCircle = (TextView) getView().findViewById(R.id.txv_recharge_circle);
        mTxvCircle.setOnClickListener(new CircleClickListener());
        mEdtAmount = (EditText) getView().findViewById(R.id.edt_recharge_amount);
        mBtnRecharge = (Button) getView().findViewById(R.id.btn_recharge_recharge);
        mBtnRecharge.setOnClickListener(new RechargeClickButtonListener());
    }


    private class RechargeClickButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class OperatorClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showOperatorDialog();
        }
    }

    private class CircleClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showCircleDialog();
        }
    }

    class GetOperatorHelper implements RequestListener<ResGetProvider> {

        private void getOperatorList() {

        }

        @Override
        public void onSuccess(ResGetProvider output) {

        }

        @Override
        public void onFailure(String errorMsg) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    class RechargeHelper implements RequestListener<ResRecharge> {

        private void getRecharge() {

        }

        @Override
        public void onSuccess(ResRecharge output) {

        }

        @Override
        public void onFailure(String errorMsg) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    private void showOperatorDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomTheme);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_operator, null, false);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.getAttributes().width = metrics.widthPixels;
        window.getAttributes().height = metrics.heightPixels;
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView txvTitle = (TextView) view.findViewById(R.id.txv_dialog_title);
        txvTitle.setText("Select Operator");
        txvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<OperatorInfo> operatorInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OperatorInfo operatorInfo = new OperatorInfo();
            operatorInfo.mOperatorName = "Operator " + i;
            operatorInfoList.add(operatorInfo);
        }
        ListView listView = (ListView) view.findViewById(R.id.lsv_select_dialog_list);
        final OperatorListAdapter adapter = new OperatorListAdapter(getActivity());
        adapter.addAll(operatorInfoList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        adapter.setListener(new OperatorListAdapter.OnOperatorClickListener() {
            @Override
            public void onOperatorItemClick(int position, View view) {
                mSelectedOPerator = adapter.getItem(position);
                mTxvOperator.setText(adapter.getItem(position).mOperatorName);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showCircleDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomTheme);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_operator, null, false);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.getAttributes().width = metrics.widthPixels;
        window.getAttributes().height = metrics.heightPixels;
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView txvTitle = (TextView) view.findViewById(R.id.txv_dialog_title);
        txvTitle.setText("Select Circle");
        txvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<String> circleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            circleList.add("Circle " + i);
        }
        ListView listView = (ListView) view.findViewById(R.id.lsv_select_dialog_list);
        final CircleListAdapter adapter = new CircleListAdapter(getActivity());
        adapter.addAll(circleList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        adapter.setListener(new CircleListAdapter.OnCircleClickListener() {

            @Override
            public void onCircleItemClick(int position, View view) {
                mSelectedCircle = adapter.getItem(position);
                mTxvCircle.setText(adapter.getItem(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
