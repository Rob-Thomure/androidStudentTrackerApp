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
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AppRepository mAppRepository;

    private int courseId;
    private int termId_fk;
    private String courseName;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private String courseStatus;
    private String courseMentor;
    private String mentorPhone;
    private String mentorEmail;
    private String courseNotes;

    private int termId;
    private String termName;
    private LocalDate termStart;
    private LocalDate termEnd;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    private EditText startDateText;
    private EditText endDateText;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;

    private EditText editTextCourseName;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Spinner  spinnerCourseStatus;
    private EditText editTextCourseMentor;
    private EditText editTextMentorPhone;
    private EditText editTextMentorEmail;
    private EditText editTextCourseNotes;

    private String spinnerTextCourseStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        termId = getIntent().getIntExtra("termId", -1);
        termName = getIntent().getStringExtra("termName");
        termStart = (LocalDate) getIntent().getSerializableExtra("termStart");
        termEnd = (LocalDate) getIntent().getSerializableExtra("termEnd");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        spinnerCourseStatus = (Spinner) findViewById(R.id.spinnerCourseStatus);
        editTextCourseMentor = findViewById(R.id.editTextCourseMentor);
        editTextMentorPhone = findViewById(R.id.editTextPhone);
        editTextMentorEmail = findViewById(R.id.editTextEmail);
        editTextCourseNotes = findViewById(R.id.editTextNotes);



        startDateText = findViewById(R.id.editTextStartDate);
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
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseAdd.this, startDateListener, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateText = findViewById(R.id.editTextEndDate);
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndLabel();
            }
        };

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseAdd.this, endDateListener, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAppRepository = new AppRepository(getApplication());

        // spinner Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCourseStatus.setAdapter(adapter);
        spinnerCourseStatus.setOnItemSelectedListener(this);
    }

    public void saveNewCourseBtn(View view) {
        CourseEntity course;

        Intent intent = new Intent(CourseAdd.this, TermDetails.class);

        courseName = editTextCourseName.getText().toString();
        courseStartDate = DateConverters.stringToLocalDate(editTextStartDate.getText().toString());
        courseEndDate = DateConverters.stringToLocalDate(editTextEndDate.getText().toString());
        courseStatus = spinnerTextCourseStatus;
        courseMentor = editTextCourseMentor.getText().toString();
        mentorPhone = editTextMentorPhone.getText().toString();
        mentorEmail = editTextMentorEmail.getText().toString();
        courseNotes = editTextCourseNotes.getText().toString();

        List<CourseEntity> allCourses = mAppRepository.getAllCourses();

        if(allCourses.size() > 0) {
            courseId = allCourses.get(allCourses.size()-1).getCourseId();
        } else {
            courseId = 0;
        }

        termId_fk = getIntent().getIntExtra("termId", -1);

        course = new CourseEntity(++courseId, termId_fk, courseName, courseStartDate, courseEndDate, courseStatus,
                courseMentor, mentorPhone, mentorEmail, courseNotes);

        mAppRepository.insertCourse(course);

        intent.putExtra("termId", termId);
        intent.putExtra("termName", termName);
        intent.putExtra("termStart", termStart);
        intent.putExtra("termEnd", termEnd);

        startActivity(intent);
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

    //for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerTextCourseStatus = parent.getItemAtPosition(position).toString();
    }

    // for spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}