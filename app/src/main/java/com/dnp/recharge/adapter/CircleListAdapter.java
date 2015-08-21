package com.dnp.recharge.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dealnprice.activity.R;


public class CircleListAdapter extends ArrayAdapter<String> {
    private OnCircleClickListener mOnCircleClickListener;

    public CircleListAdapter(Context context) {
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
        final String item = getItem(position);
        viewHolder.mTitle.setText(item);


        viewHolder.mTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (null != mOnCircleClickListener) {
                    mOnCircleClickListener.onCircleItemClick(position, view);
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

    public void setListener(OnCircleClickListener listener) {
        mOnCircleClickListener = listener;
    }

    public interface OnCircleClickListener {

        void onCircleItemClick(int position, View view);
    }

}
