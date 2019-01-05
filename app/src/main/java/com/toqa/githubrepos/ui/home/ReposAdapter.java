package com.toqa.githubrepos.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toqa.githubrepos.R;
import com.toqa.githubrepos.model.pojo.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<Repo> repoList;
    private boolean isLoadingAdded = false;

    public ReposAdapter() {
        repoList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_repo, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        holder.fillData(repoList.get(position));
    }

    @Override
    public int getItemCount() {
        return repoList == null ? 0 : repoList.size();
    }

    public void addItems(List<Repo> repos) {
        this.repoList.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear() {
        final int size = repoList.size();
        repoList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView textView_name;

        @BindView(R.id.textView_full_name)
        TextView textView_fullName;

        @BindView(R.id.textView_language)
        TextView textView_language;

        public ReposViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillData(Repo repo) {
            textView_name.setText(repo.getName());
            textView_fullName.setText(repo.getFullName());
            textView_language.setText(repo.getLanguage());
        }
    }
}
