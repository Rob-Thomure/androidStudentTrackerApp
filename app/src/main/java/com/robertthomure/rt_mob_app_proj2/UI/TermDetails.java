package com.robertthomure.rt_mob_app_proj2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.Database.DateConverters;
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;
import com.robertthomure.rt_mob_app_proj2.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    static int returnTermId;
    static int numCourses;

    private AppRepository mAppRepository;
    private int termId;
    private String termName;
    private LocalDate termStartDate;
    private LocalDate termEndDate;

    TextView textViewTermName;
    TextView textViewTermStartDate;
    TextView textViewTermEndDate;

    TermEntity currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        mAppRepository = new AppRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.TD_recyclerView);

        List<TermEntity> allTerms = mAppRepository.getAllTerms();
        for(TermEntity term:allTerms) {
            if(term.getTermId() == returnTermId) {
                currentTerm = term;
            }
        }
        termId = returnTermId;
        termName = currentTerm.getTermName();
        termStartDate = currentTerm.getTermStart();
        termEndDate = currentTerm.getTermEnd();

        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//  to filter out unrelated courses
        List<CourseEntity> filteredCourses = new ArrayList<>();
        for(CourseEntity course:mAppRepository.getAllCourses()) {
            if(course.getTermId_fk() == termId) {
                filteredCourses.add(course);
            }
        }
        adapter.setWords(filteredCourses);

        numCourses = filteredCourses.size();

        textViewTermName = findViewById(R.id.textViewTermName);
        textViewTermStartDate = findViewById(R.id.textViewStartDate);
        textViewTermEndDate = findViewById(R.id.textViewEndDate);

        textViewTermName.setText(termName);
        textViewTermStartDate.setText(DateConverters.localDateToString(termStartDate));
        textViewTermEndDate.setText(DateConverters.localDateToString(termEndDate));
    }

    public void editTermBtn(View view) {
        Intent intentEditTermBtn = new Intent(TermDetails.this, TermEdit.class);
        intentEditTermBtn.putExtra("termId", termId);
        intentEditTermBtn.putExtra("termName", termName);
        intentEditTermBtn.putExtra("termStart", termStartDate);
        intentEditTermBtn.putExtra("termEnd", termEndDate);

        startActivity(intentEditTermBtn);
    }

    public void addCourseBtn(View view) {
        Intent intentAddCourseBtn = new Intent(TermDetails.this, CourseAdd.class);
        intentAddCourseBtn.putExtra("termId", termId);
        intentAddCourseBtn.putExtra("termName", termName);
        intentAddCourseBtn.putExtra("termStart", termStartDate);
        intentAddCourseBtn.putExtra("termEnd", termEndDate);

        startActivity(intentAddCourseBtn);
    }


}