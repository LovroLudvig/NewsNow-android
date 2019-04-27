package com.lovroludvig.newsnow.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovroludvig.newsnow.R;
import com.lovroludvig.newsnow.adapters.MyPagerAdapter;

public class PageViewerFragment extends Fragment {
    private MyPagerAdapter myPagerAdapter;
    private PageViewerFragment.OnFragmentInteractionListener mListener;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NewsListFragment.TOP_NEWS)); //index 0
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NewsListFragment.CATEGORY_NEWS, NewsListFragment.CATEGORY_QUERY_SPORT)); //index 1
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NewsListFragment.CATEGORY_NEWS, NewsListFragment.CATEGORY_QUERY_ENTERTAINMENT)); //index 2
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NewsListFragment.CATEGORY_NEWS, NewsListFragment.CATEGORY_QUERY_TECH)); //index 3
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
    }

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance() {
        PageViewerFragment fragment = new PageViewerFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_viewer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageViewerFragment.OnFragmentInteractionListener) {
            mListener = (PageViewerFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }


}
