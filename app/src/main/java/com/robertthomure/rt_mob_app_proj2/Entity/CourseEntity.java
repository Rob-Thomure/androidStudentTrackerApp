package com.robertthomure.rt_mob_app_proj2.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = TermEntity.class,
                parentColumns = "termId",
                childColumns = "termId_fk"
        )
)
public class CourseEntity {
    @PrimaryKey
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

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseId=" + courseId +
                ", termId_fk='" + termId_fk + '\'' +
                ", courseName=" + courseName +
                ", courseStart=" + courseStart +
                ", courseEnd=" + courseEnd +
                ", courseStatus=" + courseStatus +
                ", courseMentor=" + courseMentor +
                ", mentorPhone=" + mentorPhone +
                ", mentorEmail=" + mentorEmail +
                ", courseNotes=" + courseNotes +
                '}';
    }

    public CourseEntity(int courseId, int termId_fk, String courseName, LocalDate courseStart, LocalDate courseEnd,
                        String courseStatus, String courseMentor, String mentorPhone, String mentorEmail,
                        String courseNotes) {
        this.courseId = courseId;
        this.termId_fk = termId_fk;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.courseNotes = courseNotes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId_fk() {
        return termId_fk;
    }

    public void setTermId_fk(int termId_fk) {
        this.termId_fk = termId_fk;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDate courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDate getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDate courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseMentor() {
        return courseMentor;
    }

    public void setCourseMentor(String courseMentor) {
        this.courseMentor = courseMentor;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }
}
