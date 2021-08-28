package com.robertthomure.rt_mob_app_proj2.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "term_table")
public class TermEntity {
    @PrimaryKey
    private int termId;

    private String termName;
    private LocalDate termStart;
    private LocalDate termEnd;



    @Override
    public String toString() {
        return "TermEntity{ " +
                "termId" + termId +
                ", termName=" + termName +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
    }

    public TermEntity(int termId, String termName, LocalDate termStart, LocalDate termEnd) {
        this.termId = termId;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public LocalDate getTermStart() {
        return termStart;
    }

    public void setTermStart(LocalDate termStart) {
        this.termStart = termStart;
    }

    public LocalDate getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(LocalDate termEnd) {
        this.termEnd = termEnd;
    }
}
