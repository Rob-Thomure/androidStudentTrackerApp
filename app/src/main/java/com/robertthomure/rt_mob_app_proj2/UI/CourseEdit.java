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
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AppRepository mAppRepository;

    private int courseId;
    private int termId_fk;
    private String courseName;
    private LocalDate courseStart;
    private LocalDate courseEnd;
    private String courseStatus;
    private String courseMentor;
    private String mentorPhone;
    private String mentorEmail;
    private String courseNotes;

    private EditText editTextCourseName;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Spinner spinnerCourseStatus;
    private EditText editTextCourseMentor;
    private EditText editTextMentorPhone;
    private EditText editTextMentorEmail;
    private EditText editTextCourseNotes;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    private EditText startDateText;
    private EditText endDateText;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    private String spinnerTextCourseStatus;

    CourseEntity currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        courseId = getIntent().getIntExtra("courseId", -1);
        termId_fk = getIntent().getIntExtra("termId_fk", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStart = (LocalDate) getIntent().getSerializableExtra("courseStart");
        courseEnd = (LocalDate) getIntent().getSerializableExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
        courseMentor = getIntent().getStringExtra("courseMentor");
        mentorPhone = getIntent().getStringExtra("mentorPhone");
        mentorEmail = getIntent().getStringExtra("mentorEmail");
        courseNotes = getIntent().getStringExtra("courseNotes");

        editTextCourseName = findViewById(R.id.editTextCE_courseName);
        editTextStartDate = findViewById(R.id.editTextCE_startDate);
        editTextEndDate = findViewById(R.id.editTextCE_endDate);
        spinnerCourseStatus = (Spinner) findViewById(R.id.spinnerCE_courseStatus);
        editTextCourseMentor = findViewById(R.id.editTextCE_courseMentor);
        editTextMentorPhone = findViewById(R.id.editTextCE_mentorPhone);
        editTextMentorEmail = findViewById(R.id.editTextCE_mentorEmail);
        editTextCourseNotes = findViewById(R.id.editTextCE_courseNotes);

        editTextCourseName.setText(courseName);
        editTextStartDate.setText(DateConverters.localDateToString(courseStart));
        editTextEndDate.setText(DateConverters.localDateToString(courseEnd));
        editTextCourseMentor.setText(courseMentor);
        editTextMentorPhone.setText(mentorPhone);
        editTextMentorEmail.setText(mentorEmail);
        editTextCourseNotes.setText(courseNotes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseStatus.setAdapter(adapter);
        spinnerCourseStatus.setOnItemSelectedListener(this);

        // set the initial spinner selection
        int adapterPosition = adapter.getPosition(courseStatus);
        spinnerCourseStatus.setSelection(adapterPosition);

        startDateText = findViewById(R.id.editTextCE_startDate);
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateStartLabel();
            }
        };

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseEdit.this, startDateListener, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateText = findViewById(R.id.editTextCE_endDate);
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
                new DatePickerDialog(CourseEdit.this, endDateListener, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAppRepository = new AppRepository(getApplication());

        List<CourseEntity> allCourses = mAppRepository.getAllCourses();
        for (CourseEntity course:allCourses) {
            if(course.getCourseId() == courseId) {
                currentCourse = course;
            }
        }
    }

    // for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerTextCourseStatus = parent.getItemAtPosition(position).toString();
    }

    // for spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextStartDate.setText(simpleDateFormat.format(startCalendar.getTime()));
    }

    private void updateEndLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextEndDate.setText(simpleDateFormat.format(endCalendar.getTime()));
    }


    public void saveEditedCourseBtn(View view) {
        CourseEntity course;
        Intent intent = new Intent(CourseEdit.this, CourseDetails.class);

        courseName = editTextCourseName.getText().toString();
        courseStart = DateConverters.stringToLocalDate(editTextStartDate.getText().toString());
        courseEnd = DateConverters.stringToLocalDate(editTextEndDate.getText().toString());
        courseStatus = spinnerTextCourseStatus;
        courseMentor = editTextCourseMentor.getText().toString();
        mentorPhone = editTextMentorPhone.getText().toString();
        mentorEmail = editTextMentorEmail.getText().toString();
        courseNotes = editTextCourseNotes.getText().toString();

        course = new CourseEntity(courseId, termId_fk, courseName, courseStart, courseEnd, courseStatus,
                courseMentor, mentorPhone, mentorEmail, courseNotes);

        mAppRepository.insertCourse(course);


        startActivity(intent);
    }

    public void deleteCourseBtn(View view) {
        Intent intent = new Intent(CourseEdit.this, TermDetails.class);

        if(CourseDetails.numAssessments > 0) {

            // delete assessments that are assigned to course
            for (AssessmentEntity assessment:mAppRepository.getAllAssessments()) {
                if(assessment.getCourseId_fk() == courseId) {

                    mAppRepository.deleteAssessment(assessment);
                }
            }
        }
        mAppRepository.deleteCourse(currentCourse);
        startActivity(intent);
    }
}