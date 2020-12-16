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
import com.example.appnghenhac.interface_truyendulieu.ISendCaSi;
import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.model.CaSi;

import java.util.ArrayList;
import java.util.List;

public class CaSiAdapter extends RecyclerView.Adapter<CaSiAdapter.ViewHolder> {
        List<CaSi> lstCaSi;
        Context context;
        LayoutInflater inflater;
        ISendCaSi sendData;
        View view;

        public CaSiAdapter( Context context, List<CaSi> data)
        {
            this.lstCaSi = data;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = inflater.inflate(R.layout.item_casi, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            List<BaiHat> lstBaiHat = new ArrayList<BaiHat>();
            for(BaiHat bh : MainActivity.lstBaiHat)
            {
                if(bh.getMaCS() == lstCaSi.get(position).getMaCS())
                    lstBaiHat.add(bh);
            }
            holder.imgView.setImageResource(lstCaSi.get(position).getImage());
            holder.txtTenCaSi.setText(lstCaSi.get(position).getHoTen());
            holder.txtSoBaiHat.setText(String.valueOf(lstBaiHat.size()) + " bài");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData = (ISendCaSi) context;
                    sendData.sendData(lstCaSi.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return lstCaSi.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView txtTenCaSi, txtSoBaiHat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = view.findViewById(R.id.imgCaSi);
            txtTenCaSi = view.findViewById(R.id.txtTenCaSi);
            txtSoBaiHat = view.findViewById(R.id.txtSoBaiHat);
        }
    }
}
