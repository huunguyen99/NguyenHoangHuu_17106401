package com.example.appnghenhac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.MainActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.interface_truyendulieu.ISendBaiHat;
import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.model.CaSi;

import java.util.List;

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.ViewHolder> {
    List<BaiHat> lstBaiHat;
    Context context;
    ISendBaiHat sendData;
    List<CaSi> lstCasi = MainActivity.lstCaSi;
    LayoutInflater inflater;
    View view;
    public BaiHatAdapter(Context context, List<BaiHat> data)
    {
        this.context = context;
        this.lstBaiHat = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.item_baihat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = lstBaiHat.get(position);
        holder.txtTenBaiHat.setText(String.valueOf(baihat.getTenBH()));
        holder.txtTenCasi.setText(lstCasi.get(baihat.getMaCS() - 1).getHoTen());
        holder.txtThoiLuong.setText(baihat.getThoiLuong());
        holder.imgBaiHat.setImageResource(baihat.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData = (ISendBaiHat)context;
                sendData.sendData(baihat, lstCasi.get(baihat.getMaCS() - 1).getHoTen());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return (long)lstBaiHat.get(position).getMaBH();
    }

    @Override
    public int getItemCount() {
        return lstBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenBaiHat, txtTenCasi, txtThoiLuong;
        ImageView imgBaiHat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = (TextView) itemView.findViewById(R.id.txtTenBaiHat);
            txtTenCasi = (TextView) itemView.findViewById(R.id.txtTenCaSi);
            txtThoiLuong = (TextView)itemView.findViewById(R.id.txtThoiGian);
            imgBaiHat = (ImageView) itemView.findViewById(R.id.imgBaiHat);
        }
    }
}
