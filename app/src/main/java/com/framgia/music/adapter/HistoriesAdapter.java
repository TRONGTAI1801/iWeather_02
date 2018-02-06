package com.framgia.music.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.music.Constants;
import com.framgia.music.R;
import com.framgia.music.model.HistoriesLocation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.framgia.music.Constants.ONE;
import static com.framgia.music.Constants.TWO;
import static com.framgia.music.Constants.ZERO;

/**
 * Created by trong_tai on 23/01/2018.
 */

public class HistoriesAdapter extends RecyclerView.Adapter<HistoriesAdapter.MyViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<HistoriesLocation> mHistoriesLocationArrayList = new ArrayList<>();

    public HistoriesAdapter(Context context, List<HistoriesLocation> historiesLocationArrayList) {
        mContext = context;
        mHistoriesLocationArrayList = historiesLocationArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View mView = mLayoutInflater.inflate(R.layout.item_row_histories_location, parent, false);
        return new HistoriesAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindHolder(mHistoriesLocationArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoriesLocationArrayList != null ? mHistoriesLocationArrayList.size() : 0;
    }

    public void updateData() {
        mHistoriesLocationArrayList.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextNameHistoriesLocation, mTextTimeHistoriesLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextNameHistoriesLocation = itemView.findViewById(R.id.text_name_location);
            mTextTimeHistoriesLocation = itemView.findViewById(R.id.text_time_location);
        }

        public void bindHolder(HistoriesLocation historiesLocation) {
            mTextNameHistoriesLocation.setText(String.format(Locale.US, "%s°",
                    changeLatitudeAndLongitudeToCityName(historiesLocation)));
            mTextTimeHistoriesLocation.setText(String.format(Locale.US, "%s",
                    chageTimestampToDatetime(historiesLocation.getTime())));
        }

        public String chageTimestampToDatetime(long timestamp) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a,yyyy-MM-dd", Locale.US);
            return simpleDateFormat.format(new Date(timestamp * Constants.CONSTANT_DATE_CHANGE));
        }

        public String changeLatitudeAndLongitudeToCityName(HistoriesLocation historiesLocation){
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(historiesLocation.getLatitide(), historiesLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int maxAddressLine = addresses.get(ZERO).getMaxAddressLineIndex();

            return String.format(Locale.US, "%s, %s, %s",
                    addresses.get(ZERO).getAddressLine(addresses.get(ZERO).getMaxAddressLineIndex()-TWO),
                    addresses.get(ZERO).getAddressLine(addresses.get(ZERO).getMaxAddressLineIndex()-ONE),
                    addresses.get(ZERO).getAddressLine(maxAddressLine));
        }
    }
}
