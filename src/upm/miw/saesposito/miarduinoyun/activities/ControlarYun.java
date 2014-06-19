package upm.miw.saesposito.miarduinoyun.activities;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class ControlarYun extends Activity{
	private static final int CONTROLARLED = 1; //constante que identifica a la actividad Controlar Led
	private static final int LEERTEMPERATURA = 2; //constante que identifica a la actividad Consultar Temperatura
	private static final int LEERSTATUSWIFI = 3;  //constante que identifica a la actividad  Status Wifi
	private Bundle extras; //para recoger los extras de la actividad anterior y pasarlos a la siguiente 

	//Sobreescribe el onCreate, ejecuta el de la superclase, despliega el layout 
	//y recoje los extras de la actividad principal 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controlar_yun);
		if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().permitNetwork().build());
          }
		extras = getIntent().getExtras();
	}
	
	
	//Arranca la actividad Controlar Led
	public void controlarLed(View v) {
		Intent i = new Intent(this, ControlarLed.class);
		i.putExtras(extras);
		startActivityForResult(i, CONTROLARLED);
		}
	
	//Arranca la actividad Consultar Temperatura
	public void leerTemperatura(View v) {
		Intent i = new Intent(this, ConsultarTemperatura.class);
		i.putExtras(extras);
		startActivityForResult(i, LEERTEMPERATURA);
		}
	
	//Arranca la actividad Consultar Status Wifi
	public void consultarStatusWifi(View v) {
		Intent i = new Intent(this, ConsultarStatusWifi.class);
		i.putExtras(extras);
		startActivityForResult(i, LEERSTATUSWIFI);
		}

}
