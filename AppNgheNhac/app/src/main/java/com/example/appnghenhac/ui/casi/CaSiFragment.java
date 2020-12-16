package com.example.appnghenhac.ui.casi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.MainActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.CaSiAdapter;
import com.example.appnghenhac.interface_truyendulieu.ISendCaSi;
import com.example.appnghenhac.model.CaSi;

import java.util.List;

public class CaSiFragment extends Fragment {
    View view;
    List<CaSi> lstCaSi = MainActivity.lstCaSi;
    RecyclerView rcvDSCaSi;
    CaSiAdapter adapter;
    ISendCaSi sendData;
    Context context = this.getContext();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_casi, container, false);
        rcvDSCaSi = (RecyclerView) view.findViewById(R.id.rcvDSCaSi);
        rcvDSCaSi.setHasFixedSize(true);
        adapter = new CaSiAdapter(getContext(), lstCaSi);

        rcvDSCaSi.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rcvDSCaSi.setAdapter(adapter);
        return view;
    }
}
