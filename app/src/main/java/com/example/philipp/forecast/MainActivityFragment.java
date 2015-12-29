package com.example.philipp.forecast;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int pageNumber;

    private int backgroundColor;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static MainActivityFragment create(int pageNumber) {
        MainActivityFragment fragment = new MainActivityFragment();
        int c;
        switch(pageNumber) {
            case 0:
                c = Color.CYAN;
                break;
            case 1:
                c = Color.BLUE;
                break;
            case 2:
                c = Color.YELLOW;
                break;
            case 3:
                c = Color.RED;
                break;
            case 4:
                c = Color.GRAY;
                break;
            case 5:
                c = Color.GREEN;
                break;
            default:
                c = Color.MAGENTA;
                break;
        }
        fragment.setBackgroundColor(c);
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_main, container, false);

        // Set the background colour
        rootView.setBackgroundColor(Color.BLUE);

        // Set the text view to show the page number.
        /*((TextView) rootView.findViewById(R.id.page)).setText(
                getString(R.string.page, pageNumber + 1));*/

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
