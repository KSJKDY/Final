package kr.co.kumoh.neighbor;

import static kr.co.kumoh.neighbor.MainFragment.userNameView;
import static kr.co.kumoh.neighbor.MainFragment.userID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.navdrawer.SimpleSideDrawer;

public class ShowMap extends FragmentActivity implements
		OnMyLocationChangeListener, LocationListener {

	private String url = "http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/json.php";
	private String jsonResult;

	private GoogleMap mMap;
	Location l;
	LocationManager l1;
	LatLng my_location1 = null;
	LatLng users_location = null;
	String latitude, longitude;
	Criteria criteria;
	Marker my_loc;
	Marker my_last_loc;
	Marker Marker1;
	Marker Marker2;
	Marker[] Marker3 = new Marker[30];
	Marker[] Marker4 = new Marker[30];
	// int count = 0;
	double a1 = 0;
	double my_locationLat = 0; // 현재 경도
	double my_locationLog = 0; // 현재 위도
	double last_my_location_lat = 1; // 업데이트 되기전 경도
	double last_my_location_log = 1; // 업데이트 되기전 위도
	String name; // DB에서 받아온 사용자 이름
	int users_count = 0; // 주위 사용자 수

	String[] lat = new String[30];
	String[] log = new String[30];
	long end = 0;
	double userLa = 36.142955;
	double userLo = 128.394409;
	int c1 = 0;
	private SimpleSideDrawer mNav;
	static String m;
	static String get_userID = null;
	static String markerID = null;
	public static String temp = null;
	AsyncTask<?, ?, ?> regIDInsertTask;
	String name1= kr.co.kumoh.neighbor.MainFragment.userID;
	TextView message;

	ProgressDialog loagindDialog;

	String regId;

	String myResult;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showmap);
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);

		LocationManager l1;
		l1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		String provider = l1.getBestProvider(criteria, true);

		l1.requestLocationUpdates(provider, 1000, 0, this);
		if (provider == null)
			provider = "network";

		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);

		// 내위치 찾기
		mMap.setOnMyLocationChangeListener(ShowMap.this);
		// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location1,16));
		// 다른사용자 위치 띄우기
		mNav = new SimpleSideDrawer(this);
		mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);

		Button list = (Button) findViewById(R.id.bt_left);
		list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mNav.toggleRightDrawer();
			}
		});


			registerGcm();
	}

	public void registerGcm() {

		GCMRegistrar.checkDevice(this);

		GCMRegistrar.checkManifest(this);

		regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {

			GCMRegistrar.register(this, "218397130189");

		} else {

			Log.e("reg_id", regId);

		}

		sendAPIkey();

	}

	private void sendAPIkey() {

		String myNum = name1;

		regIDInsertTask = new regIDInsertTask().execute(regId, myNum);

	}

	public class regIDInsertTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			loagindDialog = ProgressDialog.show(ShowMap.this, "키 등록 중입니다..",

			"Please wait..", true, false);

		}

		@Override
		protected Void doInBackground(String... params) {

			HttpPostData(params[0], params[1]);

			return null;

		}

		protected void onPostExecute(Void result) {

			loagindDialog.dismiss();

		}

	}

	public void HttpPostData(String reg_id, String pnum) {

		try {

			URL url = new URL("http://54.200.168.218/gcm_reg_insert_test.php"); // URL
																				// 설정

			HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속

			// --------------------------

			// 전송 모드 설정 - 기본적인 설정이다

			// --------------------------

			http.setDefaultUseCaches(false);

			http.setDoInput(true);

			http.setDoOutput(true);

			http.setRequestMethod("POST");

			http.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");

			StringBuffer buffer = new StringBuffer();

			buffer.append("reg_id").append("=").append(reg_id).append("&"); // php
																			// 변수에
																			// 값
																			// 대입

			buffer.append("ID").append("=").append(pnum);
			
			Log.i("TAG","reg_id=  " + reg_id);
			Log.i("TAG", "ID=  " + pnum);

			OutputStreamWriter outStream = new OutputStreamWriter(
					http.getOutputStream(), "EUC-KR");

			PrintWriter writer = new PrintWriter(outStream);

			writer.write(buffer.toString());

			writer.flush();

			InputStreamReader tmp = new InputStreamReader(
					http.getInputStream(), "EUC-KR");

			BufferedReader reader = new BufferedReader(tmp);

			StringBuilder builder = new StringBuilder();

			String str;

			while ((str = reader.readLine()) != null) {

				builder.append(str + "\n");

			}

			myResult = builder.toString();

		} catch (MalformedURLException e) {

			//

		} catch (IOException e) {

			//

		} // try

	} // HttpPostData

	public void show_users_profile() {
		Intent intent = new Intent(ShowMap.this, MapDialog.class);

		ShowMap.this.startActivity(intent);

	}

	// ///////////////////////////다른 사용자 좌표 받아오기///////////////////////////////
	public void accessDatabase() {
		JsonReadTask task = new JsonReadTask();
		task.execute(new String[] { url });
	}

	@SuppressLint("UseValueOf")
	private class JsonReadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString(); // php가
																		// 뿌린
																		// 데이터를
																		// 받음.
				System.out.println("RESULT : " + jsonResult);
				return jsonResult;
			} catch (HttpHostConnectException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(),
						"Error...! : " + e.toString(), Toast.LENGTH_LONG)
						.show();
			}
			return answer;
		}

		@SuppressLint("UseValueOf")
		@Override
		protected void onPostExecute(String result) {

			try {
				// TextView tv = (TextView)findViewById(R.id.tvTest);
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("test");

				users_count = jsonMainNode.length();
				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

					name = jsonChildNode.optString("name"); // 사용자 이름
					lat[i] = jsonChildNode.optString("lat"); // 사용자 경도
					log[i] = jsonChildNode.optString("log"); // 사용자 위도
					get_userID = jsonChildNode.optString("ID"); // 사용자 아이디
					double users_lat = new Double(lat[i]);
					double users_log = new Double(log[i]);

					Log.i("TAG", "ID= " + userID);
					
					if(!userID.equals(get_userID)) {
						searchusers(i, users_lat, users_log);
						users_count = users_count -1;
					}

					
					// DB에서 받아온
					// 좌표 지도에 찍음

					// tv.append("이름 : " +name+ "   위도 : " +lat+ "    경도: "
					// +log+ "\n");
				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}


	public void onMyLocationChange(Location location) {

		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker marker) {

				temp = marker.getSnippet();
				if (temp != null) {
					Toast.makeText(getApplicationContext(), temp,
							Toast.LENGTH_LONG).show();

					Intent intent = new Intent(ShowMap.this, MapDialog.class);
					ShowMap.this.startActivity(intent);

				}

				return false;
			}
		});

		
		// TODO Auto-generated method stub

		my_locationLat = location.getLatitude();
		my_locationLog = location.getLongitude();

		new HttpTask().execute(); // 자신의 위치정보 비디로 보냄

		if (end == 0 || System.currentTimeMillis() > end + 30000) {
			// location table속의 사용자 찾기

			if (end != 0) {
				for (int c2 = 0; c2 < users_count; c2++) {
					Marker4[c2].remove();
				}
			}

			// count = 1 + (int) (Math.random() * 20);
			accessDatabase(); // DB에서 사용자 좌표 받아옴


			end = System.currentTimeMillis();

		}
		if ((my_locationLat > last_my_location_lat + 0.00005 && my_locationLog > last_my_location_log + 0.00005)
				|| (my_locationLat > last_my_location_lat + 0.00005 && my_locationLog < last_my_location_log - 0.00005)
				|| (my_locationLat < last_my_location_lat - 0.00005 && my_locationLog > last_my_location_log + 0.00005)
				|| (my_locationLat < last_my_location_lat - 0.00005 && my_locationLog < last_my_location_log - 0.00005)
				|| last_my_location_lat == 0) {
			last_my_location_lat = my_locationLat;
			last_my_location_log = my_locationLog;

			if (my_location1 != null) {

				my_last_loc.remove();

			}

			// 마커 표시
			Bitmap marker_bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.aa);
			my_location1 = new LatLng(my_locationLat, my_locationLog);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location1, 16));

			my_loc = mMap.addMarker(new MarkerOptions().position(my_location1));

			my_last_loc = my_loc;
		}

	}

	public void searchusers(int user_count, double users_lat, double users_log) {

		users_location = new LatLng(users_lat, users_log);
		Marker3[user_count] = mMap.addMarker(new MarkerOptions().position(
				users_location).snippet(get_userID));
		Marker4[user_count] = Marker3[user_count];

	}

	class HttpTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpPost request = new HttpPost(
						"http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/m_location.php"); // 좌표
																										// 테이블
																										// 서버로
																										// 연결

				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

				nameValue.add(new BasicNameValuePair("locationLat", Double
						.toString(my_locationLat))); // 경도 보내기
				nameValue.add(new BasicNameValuePair("locationLog", Double
						.toString(my_locationLog))); // 위도 보내기
				nameValue.add(new BasicNameValuePair("name", userNameView
						.getText().toString())); // 이름 보내기

				HttpEntity enty = new UrlEncodedFormEntity(nameValue,
						HTTP.UTF_8);

				request.setEntity(enty);

				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(request);
				// 웹서버에서 값 받기

				HttpEntity entityResponse = res.getEntity();
				InputStream im = entityResponse.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(im, HTTP.UTF_8));

				String total = "";
				String tmp = "";
				// 버퍼에 있는거 전부 더해주기
				// readLine -> 파일내용을 줄 단위로 읽기

				while ((tmp = reader.readLine()) != null) {
					if (tmp != null) {
						total += tmp;
					}

				}

				im.close();

				return total;

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 오류시 null 반환
			return null;

		}

		protected void onPostExecute(String value) {
			super.onPostExecute(value);
			// result.setText(value);

		}

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

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

	}
}
