package com.toqa.githubrepos.model.network;

import com.toqa.githubrepos.model.pojo.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepoService {

    @GET("JakeWharton/repos")
    Call<List<Repo>> getJakeWhartonRepos(@Query("page") int page, @Query("per_page") int perPageNum);

}
