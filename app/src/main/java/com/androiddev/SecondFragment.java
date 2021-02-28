package com.androiddev;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.graphics.Color.rgb;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class SecondFragment extends Fragment {

    GraphView graph;
    Date[] dates;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        graph = view.findViewById(R.id.graph);
        drawGraph();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void drawGraph() {
        final double[] graphMaxY = {0};
        final double[] graphMinY = {100};
        Logs[] logs = MainActivity.logList;
        dates = new Date[logs.length];
        DataPoint[] dataPoints = new DataPoint[logs.length];
        for (int i = 0; i < logs.length; i++) {
            if (logs[i].getLongitude() > graphMaxY[0]) {
                graphMaxY[0] = logs[i].getLongitude();
            }
            if (logs[i].getLongitude() < graphMinY[0]) {
                graphMinY[0] = logs[i].getLongitude();
            }
            LocalDateTime time = logs[i].getTimeStamp();
            Date date = convertToDateViaInstant(time);
            dates[i] = date;
            dataPoints[i] = new DataPoint(date, logs[i].getLongitude());
        }

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("HH:mm:ss");
                    return formatter.format(value);
                }
                return super.formatLabel(value, false);
            }
        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(10); // only 4 because of the space
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(120);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.getViewport().setMaxY(graphMaxY[0] + 10);
        graph.getViewport().setMinY(graphMinY[0] - 10);
        graph.getViewport().setYAxisBoundsManual(true);


        graph.getViewport().setMinX(dates[0].getTime());
        graph.getViewport().setMaxX(dates[dates.length - 1].getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setPadding(40);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time");
        gridLabel.setVerticalAxisTitle("Speed");
        graph.setBackgroundColor(rgb(224, 255, 247));
        series.setColor(rgb(255, 0, 0));
        gridLabel.setHorizontalLabelsVisible(true);
        gridLabel.setVerticalLabelsVisible(true);
        gridLabel.setHumanRounding(true);
        graph.setVisibility(View.VISIBLE);
        graph.removeAllSeries();

        graph.addSeries(series);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private Date convertToDateViaInstant (LocalDateTime dateToConvert){
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}