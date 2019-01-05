package com.toqa.githubrepos.ui.home;

import com.toqa.githubrepos.model.datamanager.ReposDataManager;
import com.toqa.githubrepos.model.network.APIHelper;
import com.toqa.githubrepos.model.pojo.Repo;
import com.toqa.githubrepos.ui.base.BasePresenter;
import com.toqa.githubrepos.utils.NullUtils;

import java.util.List;

import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;

public class HomePresenter extends BasePresenter<HomeView> implements OnLoadMoreListener, ReposDataManager.ReposManagerCallbacks {

    private ReposDataManager reposDataManager;
    private int page;


    public HomePresenter(HomeView homeView, ReposDataManager reposDataManager) {
        super(homeView);
        this.reposDataManager = reposDataManager;
        this.page = 1;
    }

    public void getRepos() {
        if (view != null) {
            view.showPaginateError(false);
            view.showPaginateLoading(true);
            reposDataManager.getRepos(page, APIHelper.JAKE_REPOS_PER_PAGE_NUM, this);
        }
    }

    @Override
    public void onLoadMore() {
        getRepos();
    }

    @Override
    public void onSuccess(List<Repo> response) {
        if (NullUtils.isListNullOrEmpty(response)) {
            view.setPaginateNoMoreData(true);
        } else {
            page++;
            view.addRepos(response);
            view.showPaginateLoading(false);
        }
    }

    @Override
    public void onFailure(boolean resetData) {
        if (resetData) {
            page = 1;
            view.removeRepos();
        } else {
            view.showPaginateLoading(false);
            view.showPaginateError(true);
        }
    }
}
