package com.robertthomure.rt_mob_app_proj2.Database;

import android.app.Application;

import com.robertthomure.rt_mob_app_proj2.DAO.AssessmentDAO;
import com.robertthomure.rt_mob_app_proj2.DAO.CourseDAO;
import com.robertthomure.rt_mob_app_proj2.DAO.TermDAO;
import com.robertthomure.rt_mob_app_proj2.Entity.AssessmentEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.CourseEntity;
import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;

import java.util.List;

public class AppRepository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private List<AssessmentEntity> mAllAssessments;
    private List<CourseEntity> mAllCourses;
    private List<TermEntity> mAllTerms;
    private int assessmentId;
    private int courseId;
    private int TermId;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AssessmentEntity> getAllAssessments() {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<CourseEntity> getAllCourses() {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAllCourses();
        });try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<TermEntity> getAllTerms() {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insertAssessment(AssessmentEntity assessmentEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAssessmentDAO.insertAssessment(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertCourse(CourseEntity courseEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mCourseDAO.insertCourse(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertTerm(TermEntity termEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mTermDAO.insertTerm(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssessment(AssessmentEntity assessmentEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAssessmentDAO.deleteAssessment(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(CourseEntity courseEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mCourseDAO.deleteCourse(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAssessments() {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mAssessmentDAO.deleteAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteTerm(TermEntity termEntity) {
        AppDatabase.appDatabaseWriteExecutor.execute(()->{
            mTermDAO.deleteTerm(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
