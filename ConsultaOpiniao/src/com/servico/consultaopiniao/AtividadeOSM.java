package com.servico.consultaopiniao;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AtividadeOSM extends Activity {
	
	private MapView osm;
	private MapController mc;
	private LocationManager locationManager;
	LocationListener locationListener;
	int zoom = 12;
	int controle = 0;
	int controleEstabelecimento = 0;
	Button btMapaObterCoordenadas;
	String[] estabelecimento;
	String primeirolocal = "";
	
	

	//double latitude = -15.7077, longitude = -47.9491;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chamaTelaMapa();	
	}
	
	/* Utiliza o layout do openstreetmaps, obtem os estabelecimentos cadastrados no
	 * banco e obtem os dados do GPS do usuário */
	public void chamaTelaMapa(){
		setContentView(R.layout.atividadeosm);
		obtemEstabelecimentos();
		obtemMapaeControle();		
	}
	
	/* Busca todos os estabelecimentos no banco de dados central */
	public void obtemEstabelecimentos(){
		String respostaRetornada = null;
		String url = VariaveisFinais.urlEstabelecimentos;
		try {
			respostaRetornada = ConexaoHttpClient.executaHttpGet(url);
			
			//respostaRetornada = ConexaoHttpClient.executaHttpPost(url, parametrosPost);
			
			String resposta = respostaRetornada.toString();
			String resposta2 = resposta;
			
			//mensagem("Erro desejado", resposta);
			
			resposta = resposta.replaceAll("\\s+", " ");
			
			
			if (!resposta.contains("erro")){
				String[] estabelecimentos = resposta.split("AAA");
				String latitude = "";
				latitude = estabelecimentos[2];
				estabelecimento = estabelecimentos;
				Log.i("Conexao", "Teste da primeira latitude = "+estabelecimento[1]+"AA"+latitude);
				Log.i("Conexao", "Teste da primeira latitude = "+estabelecimento.length);
			}

	     }
        catch (Exception erro){
        Log.i("Conexao", "Erro na rotina do servidor = "+erro);
        }
	}
	
	/* Obtem todos os dados do GPS do usuário */
	public void obtemMapaeControle() {
		osm = (MapView) findViewById(R.id.mapaOSM);
		osm.setTileSource(TileSourceFactory.MAPNIK);
		osm.setBuiltInZoomControls(true);
		osm.setMultiTouchControls(true);
		
		mc = (MapController) osm.getController();
		try {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener();
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		//Log.i("GPS", "locationManager: "+locationManager);
		Location ultimaposicao = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//Log.i("GPS", "ultimaposicao: "+ultimaposicao);
		if (!ultimaposicao.equals(null)){
		VariaveisFinais.latitudeModificavel = ultimaposicao.getLatitude();
		VariaveisFinais.longitudeModificavel = ultimaposicao.getLongitude();
		//Log.i("GPS", "Latmodificada: "+ultimaposicao.getLatitude());
		} else {
			//Thread.sleep(5000);
			ultimaposicao = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (!ultimaposicao.equals(null)){
				VariaveisFinais.latitudeModificavel = ultimaposicao.getLatitude();
				VariaveisFinais.longitudeModificavel = ultimaposicao.getLongitude();
				//;//Log.i("GPS", "Latmodificadaaaaaaa: "+ultimaposicao.getLatitude());
				} else {
				VariaveisFinais.latitudeModificavel = -15.8172297;
				VariaveisFinais.longitudeModificavel = -47.9427556;
				}
			
		}
		} catch (Exception erro){	
		}
		//Log.i("GPS", "Latmodificada: "+VaraveisFinais.latitudeModificavel);
		GeoPoint center = new GeoPoint (VariaveisFinais.latitudeModificavel, VariaveisFinais.longitudeModificavel);
		mc.setZoom(zoom);
		mc.animateTo(center);
		addMarkerEscolha(center, "Você está aqui!", VariaveisFinais.USER, "0");
		
		//addMarker(center, "Nada");
		
		/*
		for (int i = 0; i<20; i++){
			GeoPoint ponto = new GeoPoint (VaraveisFinais.latLong[i][0], VaraveisFinais.latLong[i][1]);
			addMarker(ponto, "Escola " +String.valueOf(i));
		}
		*/
		String nomeLocal = "", tipoLocal, idLocal;
		GeoPoint f = new GeoPoint (Double.parseDouble(estabelecimento[controleEstabelecimento]), Double.parseDouble(estabelecimento[controleEstabelecimento+1]));
		while(controleEstabelecimento < estabelecimento.length){
			f = new GeoPoint (Double.parseDouble(estabelecimento[controleEstabelecimento]), Double.parseDouble(estabelecimento[controleEstabelecimento+1]));
			nomeLocal = estabelecimento[controleEstabelecimento+2];
			tipoLocal = estabelecimento[controleEstabelecimento+3];
			idLocal = estabelecimento[controleEstabelecimento+4];
			addMarkerEscolha(f, nomeLocal, Integer.parseInt(tipoLocal), idLocal);
			controleEstabelecimento++;
			controleEstabelecimento++;
			controleEstabelecimento++;
			controleEstabelecimento++;
			controleEstabelecimento++;
		}
	
		osm.getOverlays().add(new MapOverlay(this));
		osm.setMapListener(new MapListener() {
			
			@Override
			public boolean onZoom(ZoomEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onScroll(ScrollEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		
		/*
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		Location teste = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		VaraveisFinais.latitudeModificavel = teste.getLatitude();
		VaraveisFinais.longitudeModificavel = VaraveisFinais.longitude;
		*/
	}
	
	public void mensagem(String titulo, String msg) {
		
		AlertDialog.Builder mensagem = new AlertDialog.Builder(AtividadeOSM.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(msg);
		//mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
	
	public void addMarker(GeoPoint center, String valor){
		Marker marker = new Marker(osm);
		marker.setPosition(center);
		marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		marker.setTitle(valor);
		marker.setSnippet("Snippet Marker");
		marker.setSubDescription("SubDescription marker");
		
		
		marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0, MapView arg1) {
				Log.i("Marker", "Clicou no marker. Controle: " +controle);
				controle = 1;
				Log.i("Marker", "Clicou no marker. Controle: " +controle);
				arg0.showInfoWindow();
				return false;
			}
		});
		
		//osm.getOverlays().clear();
		// Controle para saber se está clicando em um Marker ou está clicando em outra parte do mapa sem Marker
		Log.i("Marker", "Antes do if do controle. Controle: " +controle);
		SystemClock.sleep(500);
		if (controle == 0){
		Log.i("Marker", "Primeira linha do if do controle. Controle: " +controle);
		osm.getOverlays().add(marker);
		osm.invalidate();
		Log.i("Marker", "Ultima linha do if do controle. Controle: " +controle);
		}
		Log.i("Marker", "Fora do if do controle. Controle: " +controle);
		controle = 0;
		Log.i("Marker", "Final do if do controle. Controle: " +controle);
		
	}
	
	public void addMarkerEscolha(GeoPoint center, String valor, final int tipo, final String idLocal){
		Marker marker = new Marker(osm);
		marker.setPosition(center);
		marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		
		
		if (tipo == VariaveisFinais.USER)
		marker.setIcon(getResources().getDrawable(R.drawable.iconlocal));
		else if (tipo == VariaveisFinais.HOSPITAL)
		marker.setIcon(getResources().getDrawable(R.drawable.iconhosp));
		else
		marker.setIcon(getResources().getDrawable(R.drawable.iconescol));
		
		marker.setTitle(valor);
		marker.setSnippet("Deseja selecionar esse lugar?");
		marker.setSubDescription("xx");
		//marker.setInfoWindow(infoWindow)
		marker.setInfoWindow(new CustomMarkerInfoWindow(osm));
			
		
		marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {		
			@Override
			public boolean onMarkerClick(Marker m, MapView arg1) {
				//controle = 1;
				//Log.i("Marker", "Clicou no marker. Controle: " +controle);
				
				
				
				if(tipo != 0) {
					m.showInfoWindow();
					VariaveisFinais.LOCALSEL = tipo;
					VariaveisFinais.idEstabelecimento = idLocal;
				}
				
				
				
				GeoPoint geo = m.getPosition();
				//mc.animateTo(geo);
				return false;
			}
		});
		
		
		
		//osm.getOverlays().clear();
		osm.getOverlays().add(marker);
		osm.invalidate();
		// Controle para saber se está clicando em um Marker ou está clicando em outra parte do mapa sem Marker
		/*
		if (controle == 0){
			Log.i("Marker", "Dentro do if (fora do marker). Controle: " +controle);
			osm.getOverlays().add(marker);
			osm.invalidate();
			Log.i("Marker", "Depois do invalidade. Controle: " +controle);
			}
			controle = 0;
			Log.i("Marker", "Zerou o controle. Controle: " +controle);*/
	}
	
	public class CustomMarkerInfoWindow extends MarkerInfoWindow{

		public CustomMarkerInfoWindow(MapView mapView) {
			super(R.layout.bonuspack_bubble, mapView);
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		public void onOpen(Object item){
			final Marker m = (Marker) item;
			
			ImageView iv = (ImageView) mView.findViewById(R.id.bubble_image);
			iv.setImageResource(R.drawable.ic_launcher);
						
			TextView title = (TextView) mView.findViewById(R.id.bubble_title);
			title.setText(m.getTitle());
			Log.i("Marker", "Titulo: "+m.getTitle());
			
			TextView description = (TextView) mView.findViewById(R.id.bubble_description);
			description.setText(m.getSnippet());
			description.setVisibility(View.VISIBLE);

			
			Button bt = (Button) mView.findViewById(R.id.bubble_moreinfo);
			bt.setVisibility(View.VISIBLE);
			bt.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View v) {
						if (distanciaCorreta(m.getPosition())) {
							VariaveisFinais.animarParaCentro = 1;
							locationManager.removeUpdates(locationListener);
							startActivity(new Intent(AtividadeOSM.this, AtividadeEstabelecimento.class));
							finish();	
						} else {
							mensagem("Erro de localização", "Desculpe mas você não está no local selecionado.");
						}
						
					
				}
			});
		}
		
		
		
	}
	
	public boolean distanciaCorreta(GeoPoint estabelecimento){

		Location locationA = new Location("Estabelecimento");
		locationA.setLatitude(estabelecimento.getLatitude());
		locationA.setLongitude(estabelecimento.getLongitude());
		Location locationB = new Location("Usuario");
		locationB.setLatitude(VariaveisFinais.latitudeModificavel);
		locationB.setLongitude(VariaveisFinais.longitudeModificavel);
		Log.i("Distancia", "Local A: " +locationA.toString());
		Log.i("Distancia", "Local B: " +locationB.toString());
		float distanciaUsuarioEstabelecimento = locationA.distanceTo(locationB);
		Log.i("Distancia", "Cálculo " +distanciaUsuarioEstabelecimento);
		if(distanciaUsuarioEstabelecimento < 100){
			return true;
		} else {
			return false;	
		}		
	}
/*
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
		mc.animateTo(center);
		addMarkerEscolha(center, "Você está aqui!", VaraveisFinais.USER);
		VaraveisFinais.latitudeModificavel = location.getLatitude();
		VaraveisFinais.longitudeModificavel = location.getLongitude();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}*/
	public void locationListener()	{
	locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      // Called when a new location is found by the network location provider.
			GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
			
			//Animar para o centro apenas a primeira vez que entrar aqui
			if (VariaveisFinais.animarParaCentro == 1){
			mc.animateTo(center);
			}
			VariaveisFinais.animarParaCentro = 0;
			
			addMarkerEscolha(center, "Você está aqui!", VariaveisFinais.USER, "0");
			VariaveisFinais.latitudeModificavel = location.getLatitude();
			VariaveisFinais.longitudeModificavel = location.getLongitude();
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {}

	    public void onProviderEnabled(String provider) {}

	    public void onProviderDisabled(String provider) {}
	  };
}

	class MapOverlay extends Overlay{

		public MapOverlay(Context ctx) {
			super(ctx);
		}

		@Override
		protected void draw(Canvas arg0, MapView arg1, boolean arg2) {
		}
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent me, MapView mv){
			Projection p = osm.getProjection();
			GeoPoint gp = (GeoPoint) p.fromPixels((int) me.getX(), (int) me.getY());
			//mc.animateTo(gp);
			//addMarkerEscolha(gp, "Clicou em algum lugar", 2);
			//VaraveisFinais.latitudeModificavel = gp.getLatitude();
			//VaraveisFinais.longitudeModificavel = gp.getLongitude();
			return false;
		} 
		
	}

}