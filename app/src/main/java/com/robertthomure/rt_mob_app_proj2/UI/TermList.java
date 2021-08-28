package com.robertthomure.rt_mob_app_proj2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robertthomure.rt_mob_app_proj2.Database.AppRepository;
import com.robertthomure.rt_mob_app_proj2.R;

public class TermList extends AppCompatActivity {
    private AppRepository mAppRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TermDetails.returnTermId = -1;
        CourseDetails.returnCourseId = -1;
        AssessmentDetails.returnAssessmentId = -1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        mAppRepository = new AppRepository(getApplication());
        mAppRepository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.TL_recyclerView);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setWords(mAppRepository.getAllTerms());
    }


    public void addTermBtn(View view) {
        Intent intent = new Intent(TermList.this, TermAdd.class);
        startActivity(intent);
    }
}