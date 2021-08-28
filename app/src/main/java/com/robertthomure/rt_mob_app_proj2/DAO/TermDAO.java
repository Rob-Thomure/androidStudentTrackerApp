package com.robertthomure.rt_mob_app_proj2.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.robertthomure.rt_mob_app_proj2.Entity.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Delete
    void deleteTerm(TermEntity term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termId ASC")
    List<TermEntity> getAllTerms();
}
