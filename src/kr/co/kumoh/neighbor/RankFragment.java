package kr.co.kumoh.neighbor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.kumoh.neighbor.HomeFragment.HttpTask;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class RankFragment extends Fragment {
	
	private String url = "http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/getcount.php";
	private String jsonResult;
	private String url1 = "http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/getcount1.php";
	private String jsonResult1;
	TabHost tabHost;
	String getMyname, getMyage, getMycount, getMycount1 = "";
	String[] myresult = new String[5];
	EditText[] edit = new EditText[5];
	
	String[] myresult1 = new String[5];
	EditText[] edit1 = new EditText[5];
	int count;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.rankfragment, container, false);
		
		accessDatabase();  // DB에서 프로필 가져오기
		accessDatabase1();

		Button button = (Button) view.findViewById(R.id.button1);
		Button button1 = (Button) view.findViewById(R.id.button2);
	
		
		edit[0] = (EditText) view.findViewById(R.id.editText0);
		edit[1] = (EditText) view.findViewById(R.id.editText1);
		edit[2] = (EditText) view.findViewById(R.id.editText2);
		edit[3] = (EditText) view.findViewById(R.id.editText3);
		edit[4] = (EditText) view.findViewById(R.id.editText4);
		edit1[0] = (EditText) view.findViewById(R.id.editText5);
		edit1[1] = (EditText) view.findViewById(R.id.editText6);
		edit1[2] = (EditText) view.findViewById(R.id.editText7);
		edit1[3] = (EditText) view.findViewById(R.id.editText8);
		edit1[4] = (EditText) view.findViewById(R.id.editText9);
	
		


		button.setOnClickListener(new OnClickListener() { 
			
			public void onClick(View v) {
				for(int i=0; i<count; i++) {
					edit[i].setText(myresult[i]);
					
					edit[i].setEnabled(false);
				}
			}
		});
		
	button1.setOnClickListener(new OnClickListener() { 
			
			public void onClick(View v) {
				for(int i=0; i<count; i++) {
					edit1[i].setText(myresult1[i]);
					
					edit1[i].setEnabled(false);
				}

			}
		});

		return view;
	}
	
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
				
				count = jsonMainNode.length();

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

					getMyname = jsonChildNode.optString("name");
					getMyage = jsonChildNode.optString("age");
					getMycount = jsonChildNode.optString("count");
					
					myresult[i] = getMyname + "   " +getMyage+"세  " +getMycount+"번 얻어먹음";
					
					Log.i("TAG","result==  " + myresult[i]);

				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public void accessDatabase1() {
		JsonReadTask1 task = new JsonReadTask1();
		task.execute(new String[] { url1 });
	}

	@SuppressLint("UseValueOf")
	private class JsonReadTask1 extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult1 = inputStreamToString(
						response.getEntity().getContent()).toString(); // php가
																		// 뿌린
																		// 데이터를
																		// 받음.
				System.out.println("RESULT : " + jsonResult1);
				return jsonResult1;
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
				JSONObject jsonResponse = new JSONObject(jsonResult1);
				JSONArray jsonMainNode = jsonResponse.optJSONArray("test");
				
				count = jsonMainNode.length();

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

					getMyname = jsonChildNode.optString("name");
					getMyage = jsonChildNode.optString("age");
					getMycount = jsonChildNode.optString("count1");
					
					myresult1[i] = getMyname + "   " +getMyage+"세  " +getMycount+"번  사줌";
					
					Log.i("TAG","result==  " + myresult1[i]);

				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tabHost = (TabHost) this.getActivity().findViewById(
				android.R.id.tabhost);
		tabHost.setup();

		setupTab("운좋은놈");
		setupTab("운없는놈");
		tabHost.setCurrentTab(0);

		super.onActivityCreated(savedInstanceState);
	}

	public Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	private void setupTab(final String tag) {
		View tabview = createTabView(tabHost.getContext(), tag);
		// TabSpec은 공개된 생성자가 없으므로 직접 생성할 수 없으며, TabHost의 newTabSpec메서드로 생성
		TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview);

		if (tag.equals("운좋은놈")) {
			setContent.setContent(R.id.tab4);
		} else if (tag.equals("운없는놈"))
			setContent.setContent(R.id.tab5);

		tabHost.addTab(setContent);

	}

	// Tab에 나타날 View를 구성
	private static View createTabView(final Context context, final String text) {
		// layoutinflater를 이용해 xml 리소스를 읽어옴
		View view = LayoutInflater.from(context).inflate(R.layout.tab_widget,
				null);
		/*
		 * ImageView img;
		 * 
		 * 
		 * 
		 * img = (ImageView)view.findViewById(R.id.tabs_image);
		 * img.setImageResource(R.drawable.tab_widget_btn);
		 */

		TextView tv = (TextView) view.findViewById(R.id.tabs_text);
		tv.setText(text);
		return view;
	}
}