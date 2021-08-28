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

public class AssessmentEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AppRepository mAppRepository;
    private int assessmentId;
    private int courseId_fk;
    private String assessmentName;
    private LocalDate dueDate;
    private String assessmentType;

    private EditText editTextAssessmentName;
    private EditText editTextDueDate;
    private Spinner spinnerAssessmentType;

    private String spinnerTextAssessmentType;

    private EditText dueDateText;
    final Calendar dueDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dueDateListener;

    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        courseId_fk = getIntent().getIntExtra("courseId_fk", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        dueDate = (LocalDate) getIntent().getSerializableExtra("dueDate");
        assessmentType = getIntent().getStringExtra("assessmentType");

        editTextAssessmentName = findViewById(R.id.editTextAE_assessmentName);
        editTextDueDate = findViewById(R.id.editTextAE_dueDate);
        spinnerAssessmentType = (Spinner) findViewById(R.id.spinnerAE_assessmentType);

        editTextAssessmentName.setText(assessmentName);
        editTextDueDate.setText(DateConverters.localDateToString(dueDate));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessmentType_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAssessmentType.setAdapter(adapter);
        spinnerAssessmentType.setOnItemSelectedListener(this);

        // set the initial spinner selection
        int adapterPostion = adapter.getPosition(assessmentType);
        spinnerAssessmentType.setSelection(adapterPostion);

        dueDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, month);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDueDateLabel();
            }
        };

        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentEdit.this, dueDateListener, dueDateCalendar
                        .get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH),
                        dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAppRepository = new AppRepository(getApplication());

        List<AssessmentEntity> allAssessments = mAppRepository.getAllAssessments();
        for(AssessmentEntity assessment:allAssessments) {
            if(assessment.getAssessmentId() == assessmentId) {
                currentAssessment = assessment;
            }
        }

    }

    // for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerTextAssessmentType = parent.getItemAtPosition(position).toString();
    }

    // for spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateDueDateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextDueDate.setText(simpleDateFormat.format(dueDateCalendar.getTime()));
    }

    public void deleteAssessmentBtn(View view) {
        Intent intent = new Intent(AssessmentEdit.this, CourseDetails.class);
        mAppRepository.deleteAssessment(currentAssessment);
        startActivity(intent);
    }

    public void saveAssessmentBtn(View view) {
        AssessmentEntity assessment;
        Intent intent = new Intent(AssessmentEdit.this, AssessmentDetails.class);

        assessmentName = editTextAssessmentName.getText().toString();
        dueDate = DateConverters.stringToLocalDate(editTextDueDate.getText().toString());
        assessmentType = spinnerTextAssessmentType;

        assessment = new AssessmentEntity(assessmentId, courseId_fk, assessmentName, dueDate, assessmentType);

        mAppRepository.insertAssessment(assessment);

        startActivity(intent);
    }
}