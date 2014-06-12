package upm.miw.saesposito.miarduinoyun.activities;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class ControlarYun extends Activity{
	private static final int CONTROLARLED = 1;
	private static final int LEERTEMPERATURA = 2;
	private static final int LEERSTATUSWIFI = 3;
	private Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controlar_yun);
		if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().permitNetwork().build());
          }
		extras = getIntent().getExtras();
	}
	
	
	
	public void controlarLed(View v) {
		Intent i = new Intent(this, ControlarLed.class);
		i.putExtras(extras);
		startActivityForResult(i, CONTROLARLED);
		}
	
	public void leerTemperatura(View v) {
		Intent i = new Intent(this, ConsultarTemperatura.class);
		i.putExtras(extras);
		startActivityForResult(i, LEERTEMPERATURA);
		}
	
	public void consultarStatusWifi(View v) {
		Intent i = new Intent(this, ConsultarStatusWifi.class);
		i.putExtras(extras);
		startActivityForResult(i, LEERSTATUSWIFI);
		}

}
