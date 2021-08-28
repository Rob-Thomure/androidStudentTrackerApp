package com.robertthomure.rt_mob_app_proj2.Database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.robertthomure.rt_mob_app_proj2.DAO.AssessmentDAO;
import com.robertthomure.rt_mob_app_proj2.DAO.CourseDAO;
import com.robertthomure.rt_mob_app_proj2.DAO.TermDAO;
import com.robertthomure.rt_mob_app_proj2.Entity.AssessmentEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, TermEntity.class}, version = 5)

@TypeConverters(DateConverters.class)

public abstract class AppDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService appDatabaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database.db")
                            .fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();

                    appDatabaseWriteExecutor.execute(()->{
                        AssessmentDAO mAssessmentDAO = INSTANCE.assessmentDAO();
                        CourseDAO mCourseDAO = INSTANCE.courseDAO();
                        TermDAO mTermDAO = INSTANCE.termDAO();

                        LocalDate date1 = LocalDate.of(2020, 01, 01);
                        LocalDate date2 = LocalDate.of(2020,02,01);
                        LocalDate date3 = LocalDate.of(2020, 03, 01);
                        LocalDate date4 = LocalDate.of(2020, 04, 01);

                        TermEntity term = new TermEntity(1, "Term 1", date1, date2);
                        mTermDAO.insertTerm(term);
                        term = new TermEntity(2, "Term 2", date3, date4);
                        mTermDAO.insertTerm(term);

                        CourseEntity course = new CourseEntity(1, 1, "Course 1",
                                date1, date2, "non-active", "Steve Rogers", "1234567890",
                                "steverogers@wgu","course notes here");
                        mCourseDAO.insertCourse(course);

                        AssessmentEntity assessment = new AssessmentEntity(1, 1,
                                "Assessment 1", date1, "objective");
                        mAssessmentDAO.insertAssessment(assessment);
                    });
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
