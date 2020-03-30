package com.jibres.android.weight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jibres.android.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    listenerBottomSheet mListener;

    public void setListener(listenerBottomSheet listener) {
        mListener = listener;
    }

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        view.findViewById(R.id.box_refresh).setOnClickListener(view1 -> mListener.refreh());
        return view;
    }

    public interface listenerBottomSheet {
        void refreh();
    }
}