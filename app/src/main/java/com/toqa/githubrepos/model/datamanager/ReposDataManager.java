package com.toqa.githubrepos.model.datamanager;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.toqa.githubrepos.model.network.RepoService;
import com.toqa.githubrepos.model.network.ServiceGenerator;
import com.toqa.githubrepos.model.pojo.Repo;
import com.toqa.githubrepos.model.storage.RepoAppDatabase;
import com.toqa.githubrepos.model.storage.RepoDao;
import com.toqa.githubrepos.utils.NetworkUtils;
import com.toqa.githubrepos.utils.NullUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposDataManager {

    public interface ReposManagerCallbacks{
        void onSuccess(List<Repo> repoList);
        void onFailure(boolean resetData);
    }

    private RepoDao repoDao;
    private Context context;
    private boolean isPreviousStatusNetwork = false;
    private boolean firstRun = true;

    public ReposDataManager(Context context) {
        this.context = context;
        RepoAppDatabase repoAppDatabase = RepoAppDatabase.getInstance(context);
        repoDao = repoAppDatabase.repoDao();
    }

    public void getRepos(int page, int perPageNum, ReposManagerCallbacks callback) {
        if (NetworkUtils.isNetworkConnected(context)) {
            getNetworkRepos(page, perPageNum, callback);
        } else {
            if (!isPreviousStatusNetwork) {
                getLocalRepos(page - 1, perPageNum, callback);
            } else {
                callback.onFailure(false);
            }
        }
    }

    private void getLocalRepos(int page, int perPageNum, final ReposManagerCallbacks callback) {

        AsyncTask.execute(() -> {
            List<Repo> repoList = repoDao.loadRepos(perPageNum, page*perPageNum);

            ((Activity)context).runOnUiThread(() -> {
                if (NullUtils.isListNullOrEmpty(repoList)) {    //if list is empty or null call onFailure
                    callback.onFailure(false);
                } else {
                    callback.onSuccess(repoList);
                    isPreviousStatusNetwork = false;
                }
            });

        });

        if (firstRun) {
            firstRun = false;
        }
    }

    private void getNetworkRepos(final int page, final int perPageNum, final ReposManagerCallbacks callback) {
        RepoService repoService = ServiceGenerator.createService(RepoService.class);
        Call<List<Repo>> reposCall = repoService.getJakeWhartonRepos(page, perPageNum);
        reposCall.enqueue(new Callback<List<Repo>>() {

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                getLocalRepos(page - 1, perPageNum, callback);
            }

            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                if (response.isSuccessful()) {

                    //1- clear data from db and ui
                    if (!isPreviousStatusNetwork && !firstRun) {
                        AsyncTask.execute(() -> {
                            repoDao.deleteAll();
                            ((Activity)context).runOnUiThread(() -> callback.onFailure(true));
                        });
                    }

                    isPreviousStatusNetwork = true;

                    //2- show new data
                    callback.onSuccess(response.body());

                    //3- insert in db
                    AsyncTask.execute(() -> repoDao.insertAllRepos(response.body()));
                } else {
                    getLocalRepos(page - 1, perPageNum, callback);
                }

                if (firstRun) {
                    firstRun = false;
                }
            }
        });
    }
}
