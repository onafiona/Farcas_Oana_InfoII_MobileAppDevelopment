package com.example.lab5ex2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ch3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ch3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ch3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ch3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Ch3Fragment newInstance(String param1, String param2) {
        Ch3Fragment fragment = new Ch3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ch3, container, false);
        MaterialButton toch4button=view.findViewById(R.id.toch4bttn);
        toch4button.setOnClickListener(v -> tochapter4(v));
        MaterialButton backtoch2button=view.findViewById(R.id.backtoch2bttn);
        backtoch2button.setOnClickListener(v -> backtochapter2(v));
        return view;
    }

    public void tochapter4(View view){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Ch4Fragment())
                .addToBackStack(null)
                .commit();
    }
    public void backtochapter2(View view){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Ch2Fragment())
                .addToBackStack(null)
                .commit();
    }
}