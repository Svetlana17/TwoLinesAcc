package com.example.user.twolinesacc;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity  extends AppCompatActivity implements  View.OnClickListener{

    SensorManager sensorManager;
    List<Sensor> sensorList=new ArrayList<>();

    SensorAdapter sensorAdapter;
    Sensor sensorAccelerometr;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometr = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorList.add(sensorAccelerometr);

        sensorAdapter = new SensorAdapter(this, sensorList);
        recyclerView.setAdapter(sensorAdapter);
        sensorAdapter.notifyDataSetChanged();

    }
    @Override
    public void onClick(View v) {
        int selectedItemposition=recyclerView.getChildPosition(v);
        Sensor sensor=sensorList.get(selectedItemposition);
        Intent intent=new Intent(this,GrafActivity.class);
        intent.putExtra( "sensortype",sensor.getType());
        this.startActivity(intent);
    }

}