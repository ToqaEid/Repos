package com.toqa.githubrepos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.toqa.githubrepos.model.datamanager.ReposDataManager;
import com.toqa.githubrepos.model.pojo.Repo;
import com.toqa.githubrepos.model.storage.RepoAppDatabase;
import com.toqa.githubrepos.model.storage.RepoDao;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.toqa.githubrepos", appContext.getPackageName());
    }


    private ReposDataManager reposDataManager ;

    Context context;

    RepoDao repoDao;
    RepoAppDatabase mDatabase;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
//        reposDataManager = new ReposDataManager()

        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RepoAppDatabase.class)
                .allowMainThreadQueries()
                .build();
        repoDao = mDatabase.repoDao();
    }


    @Test
    public void testGetRepos() {
//        WifiManager wifiManager = .getSystemService(Context.WIFI_SERVICE);

        Repo repo = new Repo(3070104, "abs.io", "JakeWharton/abs.io", "JavaScript");
        Repo repo1 = new Repo(1451060, "ActionBarSherlock", "JakeWharton/ActionBarSherlock", "Java");

        List<Repo> repoApiList = new ArrayList<>();
        repoApiList.add(repo);
        repoApiList.add(repo1);


        Repo repoDb = new Repo(3070104, "abs.io", "JakeWharton/abs.io", "JavaScript");
        Repo repoDb1 = new Repo(1451060, "ActionBarSherlock", "JakeWharton/ActionBarSherlock", "Java");
        Repo repoDb2 = new Repo(1451060, "ActionBarSherlock", "JakeWharton/ActionBarSherlock", "Java");

        List<Repo> repoDBList = new ArrayList<>();
        repoDBList.add(repoDb);
        repoDBList.add(repoDb1);
        repoDBList.add(repoDb2);

//        when(repoDao.loadRepos(any(),any())).thenReturn(repoDBList);
        // testing calling teh db if the network switched off
//        wifiManager.setWifiEnabled(false);
//        AsyncTask.execute(() -> repoDao.insertAllRepos(repoDBList));
        reposDataManager.getRepos(1, 3, new ReposDataManager.ReposManagerCallbacks() {
            @Override
            public void onSuccess(List<Repo> repoList) {
                assertThat(repoList.size(), CoreMatchers.is(0));
            }

            @Override
            public void onFailure(boolean resetData) {
            }
        });
        //testing if the network is on returns the associated list
//        wifiManager.setWifiEnabled(true);
//        reposDataManager.getRepos(any(), any(), new ReposDataManager.ReposManagerCallbacks() {
//            @Override
//            public void onSuccess(List<Repo> repoList) {
//                assertThat(repoList.size(), is(2));
//            }
//
//            @Override
//            public void onFailure(boolean resetData) {
//            }
//        });


    }


}
