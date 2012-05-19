package com.alse.ambushe;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class mainpref extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mainpref);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		addPreferencesFromResource(R.xml.mainpref);
	}
}
