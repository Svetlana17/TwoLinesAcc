package com.example.user.twolinesacc;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GrafActivity extends AppCompatActivity implements SensorEventListener {
        private SensorManager mSensorManager;
        Sensor sensor;
        GraphView graph;
        ////----- вывод х и ее фильтрованную велечину----///
        private double graph2LastXValue = 5d;
//        private double graph2LastYValue = 5d;
//        private double graph2LastZValue = 5d;
        private Double[] dataPoints;
        LineGraphSeries<DataPoint> seriesX;
    LineGraphSeries<DataPoint> seriesXX;
//    LineGraphSeries<DataPoint> seriesY;
//    LineGraphSeries<DataPoint> seriesYY;
//    LineGraphSeries<DataPoint> seriesZ;
//    LineGraphSeries<DataPoint> seriesZZ;
        private Thread thread;
        private boolean plotData = true;
        FloatingActionButton floatingActionButton;
    private boolean graficflag = false;
    float xx;
//    float yy;
//    float zz;
    private float On_1 = 1;
    private float altha = 0.1f;
    private boolean permissionGranted;
        private boolean state;
        private int timer=0;
        private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        private final static String FILE_NAME = "filename.txt";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_graf);
            state = false;
            deleteFile();
            floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);
            floatingActionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    buttonClick();
                }
            });
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                System.out.println(extras.getInt("sensortype"));
            }
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = mSensorManager.getDefaultSensor(extras.getInt("sensortype"));
            System.out.println(sensor);
            graph = (GraphView) findViewById(R.id.graph);
//            seriesY = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, 0),
//            });
//            seriesY.setColor(Color.GREEN);
            seriesX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 0),
            });
            seriesX.setColor(Color.BLACK);
//            seriesZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, 0),
//            });
//            seriesZ.setColor(Color.RED);
//            graph = (GraphView) findViewById(R.id.graph);
//            seriesY = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, 0),
//            });
//            seriesY.setColor(Color.BLUE);
//
            seriesXX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 0),
            });
            seriesXX.setColor(Color.YELLOW);
//            seriesZZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, 0),
//            });
//            seriesZZ.setColor(Color.LTGRAY);
//            seriesYY=new LineGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0,0),
//            });
//            seriesYY.setColor(Color.MAGENTA);
            graph.addSeries(seriesXX);
//            graph.addSeries(seriesYY);
//            graph.addSeries(seriesZZ);
            graph.addSeries(seriesX);
//            graph.addSeries(seriesY);
//            graph.addSeries(seriesZ);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(20);
            feedMultiple(); }
        public void addEntry(SensorEvent event) {
            /*     LineGraphSeries<DataPoint> series = new LineGraphSeries<>();*/
            float[] values = event.values;
            // Movement
            float x = values[0];
            System.out.println(x);
            float y = values[1];
            System.out.println(y);
            float z = values[2];
            System.out.println(z);
            if (state) {
                timer++;
                if(timer % 5 == 0) {
                    System.out.println(timer);
                }
            }
            graph2LastXValue += 1d;
//            graph2LastYValue += 1d;
//            graph2LastZValue += 1d;
            xx = (float) (On_1 + altha * (x - On_1));
//            yy = (float) (On_1 + altha * (y - On_1));
//            zz = (float) (On_1 + altha * (z - On_1));
//            seriesY.appendData(new DataPoint(graph2LastYValue, y), true, 20);
            seriesX.appendData(new DataPoint(graph2LastXValue, x), true, 20);
//            seriesZ.appendData(new DataPoint(graph2LastZValue, z), true, 20);
            seriesXX.appendData(new DataPoint(graph2LastXValue,xx), true,20);
//            seriesYY.appendData(new DataPoint(graph2LastYValue,yy), true,20);
//            seriesZZ.appendData(new DataPoint(graph2LastZValue,zz), true,20);
//            graph.addSeries(seriesY);
            graph.addSeries(seriesX);
//            graph.addSeries(seriesZ);
            if (!graficflag) {
                graph.removeSeries(seriesXX);
//                graph.removeSeries(seriesYY);
//                graph.removeSeries(seriesZZ);
            }
            else {
                graph.addSeries(seriesXX);
//                graph.addSeries(seriesYY);
//                graph.addSeries(seriesZZ);
            }
            //*добавление фильтра
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(x, y),
            });
            graph.addSeries(series);
        }
        private void addDataPoint(double acceleration) {
            dataPoints[499] = acceleration;
        }
        private void feedMultiple() {

            if (thread != null) {
                thread.interrupt();
            }
            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        plotData = true;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
        @Override
        protected void onPause() {
            super.onPause();

            if (thread != null) {
                thread.interrupt();
            }
            mSensorManager.unregisterListener(this);
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (plotData) {
                addEntry(event);
                plotData = false;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        @Override
        protected void onDestroy() {
            mSensorManager.unregisterListener(GrafActivity.this);
            thread.interrupt();
            super.onDestroy();
        }
    private void buttonClick() {
        graficflag = !graficflag;
    }
        public void deleteFile() {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write("".getBytes());
                Toast.makeText(this, "Файл Обнулен и готов для записи", Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException ex) {

                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } }


        //// выводим  6 линий и отключаем получаем 2

