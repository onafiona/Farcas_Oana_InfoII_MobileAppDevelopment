package com.example.lab5ex2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ch4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ch4Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ch4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ch4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Ch4Fragment newInstance(String param1, String param2) {
        Ch4Fragment fragment = new Ch4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ch4, container, false);
        MaterialButton exitbookbutton=view.findViewById(R.id.exitbookbttn);
        exitbookbutton.setOnClickListener(v -> exitbook(v));
        MaterialButton backtoch3button=view.findViewById(R.id.backtoch3bttn);
        backtoch3button.setOnClickListener(v -> backtochapter3(v));
        return view;
    }

    public void exitbook(View view){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new IntroFragment())
                .addToBackStack(null)
                .commit();
    }
    public void backtochapter3(View view){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Ch3Fragment())
                .addToBackStack(null)
                .commit();
    }
}