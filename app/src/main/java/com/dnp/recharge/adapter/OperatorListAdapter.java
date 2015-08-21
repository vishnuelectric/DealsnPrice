package com.dnp.recharge.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dealnprice.activity.R;
import com.dnp.recharge.model.OperatorInfo;


public class OperatorListAdapter extends ArrayAdapter<OperatorInfo> {
    private OnOperatorClickListener mOnOperatorClickListener;

    public OperatorListAdapter(Context context) {
        super(context, R.layout.view_dialog_list_item);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        viewHolder = (ViewHolder) convertView.getTag();
        final OperatorInfo operatorInfo = getItem(position);
        viewHolder.mTitle.setText(operatorInfo.mOperatorName);


        viewHolder.mTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (null != mOnOperatorClickListener) {
                    mOnOperatorClickListener.onOperatorItemClick(position, view);
                }
            }
        });

        return convertView;
    }

    private static final class ViewHolder {
        private final TextView mTitle;
        ViewHolder(View parentView) {
            mTitle = (TextView) parentView.findViewById(R.id.txv_item_name);
        }
    }

    public void setListener(OnOperatorClickListener listener) {
        mOnOperatorClickListener = listener;
    }

    /**
     * Click listener for delete button
     */
    public interface OnOperatorClickListener {
        /**
         * method will call when delete button hit with position in
         *
         * @param position selected position
         */
        void onOperatorItemClick(int position, View view);
    }

}
