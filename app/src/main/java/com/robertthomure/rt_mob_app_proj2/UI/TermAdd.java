package com.robertthomure.rt_mob_app_proj2.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.Database.DateConverters;
import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermAdd extends AppCompatActivity {
    private AppRepository mAppRepository;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    EditText startDateText;
    EditText endDateText;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    private EditText editTextTermName;
    private EditText editTextTermStartDate;
    private EditText editTextTermEndDate;


    TermEntity selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);

        editTextTermName = findViewById(R.id.editTextTermName);
        editTextTermStartDate = findViewById(R.id.editTextTextStartDate);
        editTextTermEndDate = findViewById(R.id.editTextTextEndDate);

        startDateText = findViewById(R.id.editTextTextStartDate);
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // TODO Auto-generated method stub
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateStartLabel();
            }
        };

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermAdd.this, startDateListener, startCalendar
                    .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateText = findViewById(R.id.editTextTextEndDate);
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndLabel();
            }
        };

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermAdd.this, endDateListener, endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mAppRepository = new AppRepository(getApplication());
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextTermStartDate.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextTermEndDate.setText(simpleDateFormat.format(endCalendar.getTime()));
    }

    public void saveNewTerm(View view) throws ParseException {

            TermEntity term;
            int termId;
            Intent intent = new Intent(TermAdd.this, TermList.class);

            String termName = editTextTermName.getText().toString();
            String startDateString = editTextTermStartDate.getText().toString();
            String endDateString = editTextTermEndDate.getText().toString();

            List<TermEntity> allTerms = mAppRepository.getAllTerms();
            termId = allTerms.get(allTerms.size()-1).getTermId();

            LocalDate startDateDate = DateConverters.stringToLocalDate(startDateString);
            LocalDate endDateDate = DateConverters.stringToLocalDate(endDateString);

            term = new TermEntity(++termId, termName, startDateDate , endDateDate);

            mAppRepository.insertTerm(term);

            startActivity(intent);

    }
}