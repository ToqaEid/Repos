package com.toqa.githubrepos.model.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.toqa.githubrepos.model.pojo.Repo;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllRepos(List<Repo> repoList);

    @Query("SELECT * FROM Repo ORDER BY name ASC LIMIT :limit OFFSET :offset ")
    List<Repo> loadRepos(int limit, int offset);

    @Query("DELETE FROM Repo")
    void deleteAll();


}
