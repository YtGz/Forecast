package com.example.philipp.forecast;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Philipp on 09.08.2015.
 *
 * Fades out UI elements of old page and fades those of the new page in.
 * Background colour is blended from old to new colour utilizing a simple linear interpolation.
 */
public class BlendingPageTransformer implements ViewPager.PageTransformer {
    private Activity activity;
    public boolean swipeLeft = false;

    public BlendingPageTransformer(Activity a) {
        activity = a;
    }

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Fade old ui elements out and the new ones in
            ArrayList<View> childViews = getAllChildren(page);
            for(View v : childViews) {
                v.setAlpha(2*Math.max(0f, 0.5f-Math.abs(position)));
            }
            page.setAlpha(1); //set the background back to fully opaque

            //Prevent the default translation
            //page.setTranslationX(-1 * page.getWidth() * position);

            //blend the background colours together
            if(position < 0) {
                ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
                int[] fragmentIndices = getFragmentIndices(pager);
                MainActivityFragment prevFragment = (MainActivityFragment) pager.getAdapter().instantiateItem(pager, fragmentIndices[0]);  //note that pager.getCurrentItem() stays the same fragment until the next page has reached position 0!
                MainActivityFragment nextFragment = (MainActivityFragment) pager.getAdapter().instantiateItem(pager, fragmentIndices[1]);
                int blendedColor = getBlendedColor(position, prevFragment, nextFragment);
                page.setBackgroundColor(blendedColor);
            }
            else if(position > 0) {
                ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
                int[] fragmentIndices = getFragmentIndices(pager);
                MainActivityFragment prevFragment = (MainActivityFragment) pager.getAdapter().instantiateItem(pager, fragmentIndices[0]);  //note that pager.getCurrentItem() stays the same fragment until the next page has reached position 0!
                MainActivityFragment nextFragment = (MainActivityFragment) pager.getAdapter().instantiateItem(pager, fragmentIndices[1]);
                int blendedColor = getBlendedColor(position-1, prevFragment, nextFragment);
                page.setBackgroundColor(blendedColor);
            }
            else {
                ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
                page.setBackgroundColor(((MainActivityFragment) pager.getAdapter().instantiateItem(pager, pager.getCurrentItem())).getBackgroundColor());
            }

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
        }

    }

    private int[] getFragmentIndices(ViewPager pager) {
        int[] fragmentIndices = new int[2];
        if(swipeLeft) {
            fragmentIndices[0] = pager.getCurrentItem()-1;
            fragmentIndices[1] = pager.getCurrentItem();
        }
        else {
            fragmentIndices[0] = pager.getCurrentItem();
            fragmentIndices[1] = pager.getCurrentItem()+1;
        }
        return fragmentIndices;
    }

    private int getBlendedColor(float position, MainActivityFragment prevFragment, MainActivityFragment nextFragment) {
        float[] prevHSV = new float[3];
        float[] nextHSV = new float[3];
        Color.colorToHSV(prevFragment.getBackgroundColor(), prevHSV);
        Color.colorToHSV(nextFragment.getBackgroundColor(), nextHSV);
        float[] blendedHSV = {((1+position)*prevHSV[0]+Math.abs(position)*nextHSV[0])%360, Math.min(((1 + position) * prevHSV[1] + Math.abs(position) * nextHSV[1]), 1), Math.min(((1 + position) * prevHSV[2] + Math.abs(position) * nextHSV[2]), 1)};
        return Color.HSVToColor(blendedHSV);
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }
}
