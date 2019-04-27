package com.lovroludvig.newsnow.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.lovroludvig.newsnow.R;
import com.lovroludvig.newsnow.fragments.NewsListFragment;
import com.lovroludvig.newsnow.fragments.PageViewerFragment;

public class MainActivity extends AppCompatActivity implements PageViewerFragment.OnFragmentInteractionListener {
    private FrameLayout fragmentFrame;
    private FragmentManager fragmentManager;
    private EditText searchBarEditText;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();

    private Runnable input_finish_checker = () -> {
        if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
            doSearch();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentFrame = findViewById(R.id.fragmentFrame);
        searchBarEditText = findViewById(R.id.searchBar);

        setInitFragment();
        setEditTextSearchListener();
    }

    private void doSearch() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentFrame, NewsListFragment.newInstance(NewsListFragment.SEARCH_NEWS, searchBarEditText.getText().toString()));
        if (!(fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1).getClass().getSimpleName().equals(NewsListFragment.class.getSimpleName()))) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void setEditTextSearchListener() {
        searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is empty
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {

                }
            }
        });
    }

    private void setInitFragment() {
        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragmentFrame) == null) {
            PageViewerFragment pageViewerFragment = PageViewerFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentFrame, pageViewerFragment);
            fragmentTransaction.commit();
        }
    }

    public void showError(String text) {
        new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setMessage(text)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    @Override
    public void onFragmentInteraction() {
        searchBarEditText.setText("");
        searchBarEditText.clearFocus();
    }
}
