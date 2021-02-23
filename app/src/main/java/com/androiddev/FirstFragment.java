package com.androiddev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{

            mTextView.setText(Arrays.toString(MainActivity.startPosition));
        } catch (Exception e){

            e.printStackTrace();
        }


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                try{

                    mTextView.setText(  Arrays.toString(MainActivity.startPosition) + " was the start point of your journey.\n" +
                                        Arrays.toString(MainActivity.endPosition) + " was the end point of your journey.");
                } catch (Exception e){

                    e.printStackTrace();
                }
            }
        });
    }
}