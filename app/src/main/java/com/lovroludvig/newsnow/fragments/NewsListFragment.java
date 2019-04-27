package com.lovroludvig.newsnow.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovroludvig.newsnow.R;
import com.lovroludvig.newsnow.activities.MainActivity;
import com.lovroludvig.newsnow.adapters.NewsAdapter;
import com.lovroludvig.newsnow.entities.Article;
import com.lovroludvig.newsnow.networking.ApiService;
import com.lovroludvig.newsnow.networking.ApiServiceManager;
import com.lovroludvig.newsnow.networking.GenericResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsListFragment extends Fragment {
    private static final String ARG_PARAM1 = "newsType";
    private static final String ARG_PARAM2 = "categoryQuery";

    public static final String TOP_NEWS = "top-headlines";
    public static final String CATEGORY_NEWS = "category-news";
    public static final String SEARCH_NEWS = "search-news";

    //EDIT SOURCES IF NEEDED:
    public static final String SOURCES = "bbc-news,bbc-sport," +
            "business-insider,buzzfeed," +
            "daily-mail,espn,fox-news,google-news," +
            "ign,mtv-news,national-geographic," +
            "nbc-news,new-york-magazine,reddit-r-all,techradar" +
            ",the-lad-bible,the-new-york-times,the-telegraph,the-verge,the-wall-street-journal,time";

    public static final String CATEGORY_QUERY_SPORT = "sports";
    public static final String CATEGORY_QUERY_ENTERTAINMENT = "entertainment";
    public static final String CATEGORY_QUERY_TECH = "technology";
    public static final String CATEGORY_QUERY_NONE = "categoryNone";

    private String newsType;
    private String categoryQuery;

    private ApiService apiService;
    private RecyclerView newsRecycleView;
    private NewsAdapter newsAdapter;
    private TextView noResultsTextView;

    private Integer pageNumber = 1;


    public NewsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * news fragment using the provided parameters.
     * <p>
     * Displays news passed as parameters
     *
     * @param newsType      Top news or news by category.
     * @param categoryQuery Query word for category news.
     * @return A new instance of fragment NewsListFragment.
     */
    public static NewsListFragment newInstance(String newsType, String categoryQuery) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, newsType);
        args.putString(ARG_PARAM2, categoryQuery);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewsListFragment newInstance(String param1) {
        return NewsListFragment.newInstance(param1, CATEGORY_QUERY_NONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = ApiServiceManager.getApiService();

        newsRecycleView = view.findViewById(R.id.newsRecycleView);
        noResultsTextView = view.findViewById(R.id.noResultsText);
        newsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsAdapter = new NewsAdapter(new ArrayList<>());
        newsRecycleView.setAdapter(newsAdapter);

        getArticlesFromApi(pageNumber);

    }

    private void getArticlesFromApi(Integer pageNumber) {
        if (newsType.equals(TOP_NEWS)) {
            apiService.getTopNews(SOURCES, ApiServiceManager.API_KEY, "publishedAt", String.valueOf(10), String.valueOf(pageNumber)).enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful()) {
                        addArticles(response.body().getArticles());
                    } else {
                        ((MainActivity)getActivity()).showError("An error occurred, please try again later...");
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    ((MainActivity)getActivity()).showError("An error occurred, please try again later...");
                }
            });
        } else if (newsType.equals(CATEGORY_NEWS)){
            apiService.getNewsByCategory(categoryQuery, ApiServiceManager.API_KEY, "publishedAt", String.valueOf(10), String.valueOf(pageNumber)).enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful()) {
                        addArticles(response.body().getArticles());
                    } else {
                        ((MainActivity)getActivity()).showError("An error occurred, please try again later...");
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    ((MainActivity)getActivity()).showError("An error occurred, please try again later...");
                }
            });
        } else{
            apiService.getNewsByQuery(SOURCES, categoryQuery, ApiServiceManager.API_KEY, "publishedAt", String.valueOf(10), String.valueOf(pageNumber)).enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.isSuccessful()) {
                        addArticles(response.body().getArticles());
                    } else {
                        ((MainActivity)getActivity()).showError("An error occurred please try again later...");
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    ((MainActivity)getActivity()).showError("An error occurred please try again later...");
                }
            });
        }
    }

    private void addArticles(List<Article> articlesToAdd) {
        for (Article article : articlesToAdd) {
            ((NewsAdapter) newsRecycleView.getAdapter()).addArticle(article);
        }
        if (newsRecycleView.getAdapter().getItemCount()==0){
            noResultsTextView.setVisibility(View.VISIBLE);
        }else{
            setRecycleViewListener();
        }
    }

    private void setRecycleViewListener() {
        newsRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    getArticlesFromApi(++pageNumber);
                    recyclerView.removeOnScrollListener(this);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsType = getArguments().getString(ARG_PARAM1);
            categoryQuery = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }
}
