package com.example.user.twolinesacc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder>  {
   private List<Sensor> sensors=new ArrayList<>();
    private LayoutInflater mIflater;
    private Context mContext;
    public SensorAdapter(Context context, List<Sensor> sensorList) {
    this.mContext=context;
   this.sensors=sensorList;
    this.mIflater=LayoutInflater.from(mContext);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view=mIflater.inflate(R.layout.adapter_item,viewGroup,false);
      view.setOnClickListener((View.OnClickListener) mContext);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Sensor sensor=sensors.get(i);
    String[] finalSensorName = sensors.get(i).getName().toString().split(" ");


//        viewHolder.name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 Intent intent=new Intent(mContext, GrafActivity.class);
//                 Integer i=sensor.getType();
////              String s= String.valueOf(sensor.getType());
//                    intent.putExtra("sensortype",sensor.getType());
//
//                StringBuilder sb = new StringBuilder();
//
//                mContext.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements SensorEventListener{




        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER: {
                    //собственно выводим все полученые параметры в текствьюшки наши
//                    textViewX.setText("ttt");
//                mYValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Y]));
//                mZValueText.setText(String.format("%1.3f", event.values[SensorManager.DATA_Z]));
                }
                break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
