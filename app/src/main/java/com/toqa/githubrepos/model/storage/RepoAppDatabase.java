package com.toqa.githubrepos.model.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.toqa.githubrepos.model.pojo.Repo;

@Database(entities = {Repo.class}, version = 1)
public abstract class RepoAppDatabase extends RoomDatabase {

    private static volatile RepoAppDatabase INSTANCE;

    public static RepoAppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepoAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepoAppDatabase.class, "repo_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RepoDao repoDao();
}
