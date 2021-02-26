package com.androiddev;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.util.Arrays;

public class FirstFragment extends Fragment {

    TextView mTextView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_first, container, false);
        mTextView = root.findViewById(R.id.textview_first);
        mTextView.setText("Welcome. Please begin a walk by using the button in the bottom left corner. End your walk using the button in the bottom right corner.");

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{

            //mTextView.setText((CharSequence) MainActivity.logList);
            mTextView.setText(MainActivity.logList.get(0).getAddressLine() + " was the start point of your journey.\n" +
                    MainActivity.logList.get(MainActivity.logList.size() - 1).getAddressLine() + " was the end point of your journey.");
        } catch (Exception e){

            e.printStackTrace();
        }


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                try{

                    /*mTextView.setText(  MainActivity.logList.get(0) + " was the start point of your journey.\n" +
                                        MainActivity.logList.get(MainActivity.logList.size() - 1) + " was the end point of your journey.");
                    */
                    mTextView.setText(MainActivity.logList.get(0).getAddressLine() + " was the start point of your journey.\n" +
                            MainActivity.logList.get(MainActivity.logList.size() - 1).getAddressLine() + " was the end point of your journey.");
                } catch (Exception e){

                    e.printStackTrace();
                }
            }
        });
    }
}