package com.robertthomure.rt_mob_app_proj2.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.Database.DateConverters;
import com.robertthomure.rt_mob_app_proj2.Entity.AssessmentEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerAssessmentType;

    private EditText editTextAssessmentName;
    private EditText editTextDueDate;
    private String spinnerTextAssessmentType;

    private EditText dueDateText;
    DatePickerDialog.OnDateSetListener dueDateListener;
    final Calendar dueDateCalendar = Calendar.getInstance();

    private AppRepository mAppRepository;

    private String assessmentName;
    private LocalDate assessmentDueDate;
    private String assessmentType;

    private int assessmentId;
    private int courseId_fk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);

        editTextAssessmentName = findViewById(R.id.editTextAA_assessmentName);
        editTextDueDate = findViewById(R.id.editTextAA_dueDate);
        spinnerAssessmentType = (Spinner) findViewById(R.id.spinnerAA_assessmentType);

        dueDateText = findViewById(R.id.editTextAA_dueDate);
        dueDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, month);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDueDateLabel();
            }
        };

        dueDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentAdd.this, dueDateListener, dueDateCalendar
                        .get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH),
                        dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAppRepository = new AppRepository(getApplication());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessmentType_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAssessmentType = (Spinner) findViewById(R.id.spinnerAA_assessmentType);
        spinnerAssessmentType.setAdapter(adapter);
        spinnerAssessmentType.setOnItemSelectedListener(this);
    }

    //for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerTextAssessmentType = parent.getItemAtPosition(position).toString();
    }

    //for spinner
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateDueDateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextDueDate.setText(simpleDateFormat.format(dueDateCalendar.getTime()));
    }

    public void saveNewAssessmentBtn(View view) {
        AssessmentEntity assessment;
        Intent intent = new Intent(AssessmentAdd.this, CourseDetails.class);

        assessmentName = editTextAssessmentName.getText().toString();
        assessmentDueDate = DateConverters.stringToLocalDate(editTextDueDate.getText().toString());
        assessmentType = spinnerTextAssessmentType;

        List<AssessmentEntity> allAssessments = mAppRepository.getAllAssessments();
        assessmentId = allAssessments.get(allAssessments.size()-1).getAssessmentId();

        courseId_fk = getIntent().getIntExtra("courseId", -1);

        assessment = new AssessmentEntity(++assessmentId, courseId_fk, assessmentName, assessmentDueDate, assessmentType);

        mAppRepository.insertAssessment(assessment);
        startActivity(intent);
    }
}