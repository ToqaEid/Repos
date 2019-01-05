package com.toqa.githubrepos.ui.home;

import com.toqa.githubrepos.model.pojo.Repo;
import com.toqa.githubrepos.ui.base.BaseView;

import java.util.List;

import ru.alexbykov.nopaginate.callback.PaginateView;

public interface HomeView extends BaseView, PaginateView {

    void addRepos(List<Repo> repos);
    void removeRepos();

}
