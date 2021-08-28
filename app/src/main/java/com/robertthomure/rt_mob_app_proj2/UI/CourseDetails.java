package com.robertthomure.rt_mob_app_proj2.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.Database.DateConverters;
import com.robertthomure.rt_mob_app_proj2.Entity.AssessmentEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    static int returnCourseId;
    static int numAssessments;
    public static int numAlert;

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

    private TextView textViewCourseName;
    private TextView textViewCourseStart;
    private TextView textViewCourseEnd;
    private TextView textViewCourseStatus;
    private TextView textViewCourseMentor;
    private TextView textViewMentorPhone;
    private TextView textViewMentorEmail;
    private TextView textViewCourseNotes;

    CourseEntity currentCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        mAppRepository = new AppRepository(getApplication());
        mAppRepository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.CD_recyclerView);

        List<CourseEntity> allCourses = mAppRepository.getAllCourses();
        for (CourseEntity course:allCourses) {
            if(course.getCourseId() == returnCourseId) {
                currentCourse = course;
            }
        }
        courseId = returnCourseId;
        termId_fk = currentCourse.getTermId_fk();
        courseName = currentCourse.getCourseName();
        courseStart = currentCourse.getCourseStart();
        courseEnd = currentCourse.getCourseEnd();
        courseStatus = currentCourse.getCourseStatus();
        courseMentor = currentCourse.getCourseMentor();
        mentorPhone = currentCourse.getMentorPhone();
        mentorEmail = currentCourse.getMentorEmail();
        courseNotes = currentCourse.getCourseNotes();


        textViewCourseName = findViewById(R.id.textViewCD_courseName);
        textViewCourseStart = findViewById(R.id.textViewCD_startDate);
        textViewCourseEnd = findViewById(R.id.textViewCD_endDate);
        textViewCourseStatus = findViewById(R.id.textViewCD_status);
        textViewCourseMentor = findViewById(R.id.textViewCD_courseMentor);
        textViewMentorPhone = findViewById(R.id.textViewCD_mentorPhone);
        textViewMentorEmail = findViewById(R.id.textViewCD_MentorEmail);
        textViewCourseNotes = findViewById(R.id.textViewCD_courseNotes);

        textViewCourseName.setText(courseName);
        textViewCourseStart.setText(DateConverters.localDateToString(courseStart));
        textViewCourseEnd.setText(DateConverters.localDateToString(courseEnd));
        textViewCourseStatus.setText(courseStatus);
        textViewCourseMentor.setText(courseMentor);
        textViewMentorPhone.setText(mentorPhone);
        textViewMentorEmail.setText(mentorEmail);
        textViewCourseNotes.setText(courseNotes);

        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // gets a list of assessments that are assigned to the course
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for (AssessmentEntity assessment:mAppRepository.getAllAssessments()) {
            if(assessment.getCourseId_fk() == courseId) {
                filteredAssessments.add(assessment);
            }
        }
        adapter.setWords(filteredAssessments);

        numAssessments = filteredAssessments.size();

    }

    public void editCourseBtn(View view) {
        Intent intent = new Intent(CourseDetails.this, CourseEdit.class);

        intent.putExtra("courseId" , courseId);
        intent.putExtra("termId_fk", termId_fk);
        intent.putExtra("courseName", courseName);
        intent.putExtra("courseStart", courseStart);
        intent.putExtra("courseEnd", courseEnd);
        intent.putExtra("courseStatus", courseStatus);
        intent.putExtra("courseMentor", courseMentor);
        intent.putExtra("mentorPhone", mentorPhone);
        intent.putExtra("mentorEmail", mentorEmail);
        intent.putExtra("courseNotes", courseNotes);
        startActivity(intent);
    }

    public void addAssessmentbtn(View view) {
        Intent intent = new Intent(CourseDetails.this, AssessmentAdd.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void shareBtn(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes);
        //sendIntent.putExtra(Intent.EXTRA_TITLE, "send message title");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.startDateAlarm) {
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            String message = courseName + " starts " + DateConverters.localDateToString(courseStart);
            intent.putExtra("notificationMessage", message);
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Long courseStartEpochMillis = DateConverters.localDateToLongEpochMilli(courseStart);
            alarmManager.set(AlarmManager.RTC_WAKEUP, courseStartEpochMillis, sender );
            return true;
        }

        if (id == R.id.endDateAlarm) {
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            String message = courseName + " Ends " + DateConverters.localDateToString(courseEnd);
            intent.putExtra("notificationMessage", message);
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Long courseEndEpochMillis = DateConverters.localDateToLongEpochMilli(courseEnd);
            alarmManager.set(AlarmManager.RTC_WAKEUP, courseEndEpochMillis, sender );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}