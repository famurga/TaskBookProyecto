package com.example.taskbookproyecto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DataPickerFragment  extends DialogFragment  {
    Calendar calendar;
    int day, month,year;
    DatePickerDialog datePickerDialog;
  private DatePickerDialog.OnDateSetListener listener;


    public static DataPickerFragment newInstance(DatePickerDialog.OnDateSetListener listener){
        DataPickerFragment fragment = new DataPickerFragment() ;
        fragment.setListener(listener);
        return fragment;
    }

    private void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(),listener,year, month,day);

       // Utils.setAlarm(1, calendar.getTimeInMillis(), getActivity());
       // Toast.makeText(getActivity(), "El dia es : "+day, Toast.LENGTH_LONG).show();
      //  Log.e("Mensaje de prueba ","El dia es"+day);

        return datePickerDialog;
    }



    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
