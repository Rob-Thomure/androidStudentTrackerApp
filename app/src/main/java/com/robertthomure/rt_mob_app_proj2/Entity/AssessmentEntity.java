package com.robertthomure.rt_mob_app_proj2.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "assessment_table")

public class AssessmentEntity {
    @PrimaryKey
    private int assessmentId;

    private int courseId_fk;
    private String assessmentName;
    private LocalDate assessmentDueDate;
    private String assessmentType;

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentId=" + assessmentId +
                ", courseId_fk='" + courseId_fk + '\'' +
                ", assessment_name=" +assessmentName +
                ", assessmentDueDate=" + assessmentDueDate +
                ", assessmentType=" + assessmentType +
                '}';
    }

    public AssessmentEntity(int assessmentId, int courseId_fk, String assessmentName, LocalDate assessmentDueDate, String assessmentType) {
        this.assessmentId = assessmentId;
        this.courseId_fk = courseId_fk;
        this.assessmentName = assessmentName;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentType = assessmentType;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getCourseId_fk() {
        return courseId_fk;
    }

    public void setCourseId_fk(int courseId_fk) {
        this.courseId_fk = courseId_fk;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public LocalDate getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(LocalDate assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
}
