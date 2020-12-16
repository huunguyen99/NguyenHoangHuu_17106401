package com.example.appnghenhac.ui.home;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.MainActivity;
import com.example.appnghenhac.R;
import com.example.appnghenhac.adapter.BaiHatAdapter;
import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.model.CaSi;

import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView rcvDSBaiHat;
    List<BaiHat> lstBaiHat = MainActivity.lstBaiHat;
    BaiHatAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvDSBaiHat = (RecyclerView)view.findViewById(R.id.rcvDSBaiHat);
        rcvDSBaiHat.setHasFixedSize(true);
        adapter = new BaiHatAdapter(getContext(), lstBaiHat);
        rcvDSBaiHat.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rcvDSBaiHat.setAdapter(adapter);
        return view;
    }
}