package com.example.noteit.ModelClasses;

import androidx.fragment.app.Fragment;

public class FragmentsInit {
    private Fragment fragment;
    private String tag;

    public FragmentsInit(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
