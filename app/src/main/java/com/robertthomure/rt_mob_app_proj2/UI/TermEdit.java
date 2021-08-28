package com.robertthomure.rt_mob_app_proj2.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.Database.DateConverters;
import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermEdit extends AppCompatActivity {

    private AppRepository mAppRepository;

    private int termId;
    private String termName;
    private LocalDate termStartDate;
    private LocalDate termEndDate;

    EditText editTextTermName;
    EditText editTextTermStartDate;
    EditText editTextTermEndDate;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    TermEntity currentTerm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        termId = getIntent().getIntExtra("termId", -1);
        termName = getIntent().getStringExtra("termName");
        termStartDate = (LocalDate) getIntent().getSerializableExtra("termStart");
        termEndDate = (LocalDate) getIntent().getSerializableExtra("termEnd");

        editTextTermName = findViewById(R.id.editTextET_termName);
        editTextTermStartDate = findViewById(R.id.editTextET_startDate);
        editTextTermEndDate = findViewById(R.id.editTextET_endDate);

        editTextTermName.setText(termName);
        editTextTermStartDate.setText(DateConverters.localDateToString(termStartDate));
        editTextTermEndDate.setText(DateConverters.localDateToString(termEndDate));

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateStartLabel();
            }
        };

        editTextTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermEdit.this, startDateListener, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndLabel();
            }
        };

        editTextTermEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermEdit.this, endDateListener, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAppRepository = new AppRepository(getApplication());

        List<TermEntity> allTerms = mAppRepository.getAllTerms();

        for(TermEntity term:allTerms) {
            if(term.getTermId() == termId) {
                currentTerm = term;
            }
        }
    }

    public void saveEditedTermBtn(View view) {
        TermEntity term;
        Intent intent = new Intent(TermEdit.this, TermDetails.class);

        termName = editTextTermName.getText().toString();
        termStartDate = DateConverters.stringToLocalDate(editTextTermStartDate.getText().toString());
        termEndDate = DateConverters.stringToLocalDate(editTextTermEndDate.getText().toString());

        term = new TermEntity(termId, termName, termStartDate, termEndDate);
        mAppRepository.insertTerm(term);

        startActivity(intent);
    }

    public void deleteEditedTermBtn(View view) {
        Intent intent = new Intent(TermEdit.this, TermList.class);

        if(TermDetails.numCourses == 0) {
            mAppRepository.deleteTerm(currentTerm);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Can't delete a Term with Courses assigned",
                    Toast.LENGTH_LONG).show();
        }
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

}