package upm.miw.saesposito.miarduinoyun.activities;

import upm.miw.saespositi.miarduinoyun.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferencias extends PreferenceActivity {
	  @SuppressWarnings("deprecation")
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      addPreferencesFromResource(R.xml.preferencias);
	  }
	  
	  @Override
		public void onBackPressed() {
			Intent i = new Intent();
			setResult(RESULT_OK, i);
			super.onBackPressed();
		}
	} 
