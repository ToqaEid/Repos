package com.toqa.githubrepos.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.toqa.githubrepos.R;
import com.toqa.githubrepos.model.datamanager.ReposDataManager;
import com.toqa.githubrepos.model.pojo.Repo;
import com.toqa.githubrepos.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class HomeActivity extends BaseActivity<HomePresenter>  implements HomeView{

    @BindView(R.id.recycleView_repos)
    RecyclerView recycleView_repos;

    private ReposAdapter reposAdapter;
    private HomePresenter presenter;
    private NoPaginate noPaginate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this, new ReposDataManager(this));
        setupPagination();
        presenter.getRepos();
    }

    @Override
    protected int getViewResourceID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        reposAdapter = new ReposAdapter();
        recycleView_repos.setAdapter(reposAdapter);
        recycleView_repos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupPagination() {
        noPaginate = NoPaginate.with(recycleView_repos)
                .setOnLoadMoreListener(presenter)
                .setLoadingTriggerThreshold(3)
                .build();
    }

    @Override
    public void addRepos(@NonNull List<Repo> repos) {
        reposAdapter.addItems(repos);
    }

    @Override
    public void removeRepos() {
        reposAdapter.clear();
    }

    @Override
    public void showPaginateLoading(boolean isPaginateLoading) {
        noPaginate.showLoading(isPaginateLoading);
    }

    @Override
    public void showPaginateError(boolean isPaginateError) {
        noPaginate.showError(isPaginateError);
    }

    @Override
    public void setPaginateNoMoreData(boolean isNoMoreItems) {
        noPaginate.setNoMoreItems(isNoMoreItems);
    }

    @Override
    public void onDestroy() {
        noPaginate.unbind();
        super.onDestroy();
    }

}
