package com.example.chanakya.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TimePicker;



import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    Button btn,btn1;
    ListView alarmTimesListView;
    static ArrayList<String> times  = new ArrayList<String>();
    ArrayList<String> itemsToBeDeleted = new ArrayList<String>();
    ArrayList<PendingIntent> pendingIntents = new ArrayList<PendingIntent>();

    static ArrayAdapter<String> adapter ;
    int hrs;
    int mins;

    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimesListView = findViewById(R.id.listView);


        hrs =0;
        mins =0;



        btn1 = findViewById(R.id.buttonDelete);
        btn = findViewById(R.id.buttonCreate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hrs  = hourOfDay;
                        mins = minute;
                        times.add(String.valueOf(hrs)+ ":"+String.valueOf(mins));

                        adapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                R.layout.list_checkable,R.id.checkedTextView,
                                times);

                        for(Object obj: times){

                            setAlarm(obj.toString());
                        }


                        adapter.notifyDataSetChanged();


                    /*    adapterObjext = new MyAdapter(times,MainActivity.this);
                        adapter1 = adapterObjext.getAdapter()*/;


                        alarmTimesListView.setAdapter(adapter);


                        //      alarmUpdate(times);

                        alarmTimesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                                CheckedTextView cb = (CheckedTextView) v.findViewById(R.id.checkedTextView);
                                if(cb.isChecked()){

                                    cb.setChecked(false);

                                     itemsToBeDeleted.add(cb.getText().toString());
                                    //    listdisplay = v;
                                }

                                else{

                                    cb.setChecked(true);

                                    //  listdisplay = v;
                                    // removeAlarm(cb.getText().toString());
                                }

                                //            alarmUpdate(times);

alarmTimesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                        CheckedTextView cb = (CheckedTextView) v.findViewById(R.id.checkedTextView);
                        if(cb.isChecked()){

                            cb.setChecked(false);

                            //    listdisplay = v;

                        }

                        else{

                            cb.setChecked(true);

                            //  listdisplay = v;
                            // removeAlarm(cb.getText().toString());
                        }

                        //            alarmUpdate(times);


                    }
                });
                            }
                        });


                    }
                }, hour, minute, false);

                timePickerDialog.show();

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i=0;i<times.size();i++){

                   for(int j=0 ;j<itemsToBeDeleted.size();j++){

                       if(times.get(i).toString().equals(itemsToBeDeleted.get(j).toString())){

                           times.remove(times.get(i));
                       }

                   }

                }


                adapter.notifyDataSetChanged();





                /* Intent i = new Intent(MainActivity.this,DeleteActivity.class);


                startActivity(i);*/



            /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose item");



                builder.setView( getLayoutInflater().inflate( R.layout.list_checkable, null ));



                builder.show();*/





            }
        });




    }












    private PendingIntent setAlarm(String time) {

        Intent i = new Intent(this,MyBraodcastReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),111,i,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String alarmTime[] = time.split(":");

        Calendar cAlarm = Calendar.getInstance();
        cAlarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(alarmTime[0]));
        cAlarm.set(Calendar.MINUTE,Integer.parseInt(alarmTime[1]));
        cAlarm.set(Calendar.SECOND,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cAlarm.getTimeInMillis(), pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, cAlarm.getTimeInMillis(), pendingIntent);
        }


      return pendingIntent;

    }


    private void removeAlarm(String time) {

        PendingIntent pendingIntent = setAlarm(time);

        alarmManager.cancel(pendingIntent);

    }
}
