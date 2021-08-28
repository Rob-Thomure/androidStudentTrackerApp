package com.robertthomure.rt_mob_app_proj2.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

public class AssessmentDetails extends AppCompatActivity {

    static int returnAssessmentId;
    AssessmentEntity currentAssessment;
    CourseEntity currentCourse;

    private AppRepository mAppRepository;

    private int assessmentId;
    private int courseId_fk;
    private String assessmentName;
    private LocalDate dueDate;
    private String assessmentType;
    private String courseNotes;

    private EditText editTextAssessmentName;
    private EditText editTextDueDate;
    private EditText editTextAssessmentType;
    private EditText editTextCourseNotes;

    private LocalDate startDate;
    private EditText editTextStartDate;
    private EditText startDateText;
    DatePickerDialog.OnDateSetListener startDateListener;
    final Calendar startDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        mAppRepository = new AppRepository(getApplication());
        //mAppRepository.getAllAssessments();

        List<AssessmentEntity> allAssessments = mAppRepository.getAllAssessments();
        for (AssessmentEntity assessment:allAssessments) {
            if(assessment.getAssessmentId()  == returnAssessmentId) {
                currentAssessment = assessment;
            }
        }

        List<CourseEntity> allCourses = mAppRepository.getAllCourses();
        for (CourseEntity course:allCourses) {
            if (course.getCourseId() == CourseDetails.returnCourseId) {
                currentCourse = course;
            }
        }

        assessmentId = returnAssessmentId;
        courseId_fk = currentAssessment.getCourseId_fk();
        assessmentName = currentAssessment.getAssessmentName();
        dueDate = currentAssessment.getAssessmentDueDate();
        assessmentType = currentAssessment.getAssessmentType();
        courseNotes = currentCourse.getCourseNotes();

        editTextAssessmentName = findViewById(R.id.editTextAD_assessmentName);
        editTextDueDate = findViewById(R.id.editTextAD_dueDate);
        editTextAssessmentType = findViewById(R.id.editTextAD_assessmentType);
        editTextCourseNotes = findViewById(R.id.editTextAD_CourseNotes);

        editTextAssessmentName.setText(assessmentName);
        editTextDueDate.setText(DateConverters.localDateToString(dueDate));
        editTextAssessmentType.setText(assessmentType);
        editTextCourseNotes.setText(courseNotes);

        editTextStartDate = findViewById(R.id.editTextDateAD_startDate);
        startDateText = findViewById(R.id.editTextDateAD_startDate);
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, month);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateStartDateLabel();
            }
        };

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetails.this, startDateListener, startDateCalendar
                .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    public void editAssessmentBtn(View view) {
        Intent intent = new Intent(AssessmentDetails.this, AssessmentEdit.class);
        intent.putExtra("assessmentId", assessmentId);
        intent.putExtra("courseId_fk", courseId_fk);
        intent.putExtra("assessmentName", assessmentName);
        intent.putExtra("dueDate", dueDate);
        intent.putExtra("assessmentType", assessmentType);
        startActivity(intent);
    }

    public void setAssessmentDueDateAlarm(View view) {
        Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
        String message = assessmentName + " is due " + DateConverters.localDateToString(dueDate);
        intent.putExtra("notificationMessage", message);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++CourseDetails.numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Long assessmentDueDateEpochMillis = DateConverters.localDateToLongEpochMilli(dueDate);
        alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentDueDateEpochMillis, sender);
    }

    public void setAssessmentStartDateAlarm(View view) {

        System.out.println("editTextStartDate = " + editTextStartDate.getText());

        try {
            startDate = DateConverters.stringToLocalDate(editTextStartDate.getText().toString());
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            String message = assessmentName + "starts " + DateConverters.localDateToString(startDate);
            intent.putExtra("notificationMessage", message);
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++CourseDetails.numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Long assessmentStartDateEpochMillis = DateConverters.localDateToLongEpochMilli(startDate);
            alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentStartDateEpochMillis, sender);
        }
        catch (java.time.format.DateTimeParseException e) {
            Context context = getApplicationContext();
            CharSequence message = "Enter a start date before setting a start date alarm";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    private void updateStartDateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editTextStartDate.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }
}