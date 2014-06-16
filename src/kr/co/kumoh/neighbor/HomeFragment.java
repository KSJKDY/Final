package kr.co.kumoh.neighbor;

import static kr.co.kumoh.neighbor.MainFragment.userID;
import static kr.co.kumoh.neighbor.MainFragment.userNameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class HomeFragment extends Fragment {
	TabHost tabHost;
	String myName, myAge, mySex, myPhone, myAddress = "";
	private ProfilePictureView profilePictureView;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private String url = "http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/json_get_profile.php";
	private String jsonResult;
	String getMyname, getMyage, getMysex, getMyphone, getMyaddress = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.homefragment, container, false);

		final EditText name = (EditText) view.findViewById(R.id.editText1); // 이름
																			// 받아옴
		final EditText age = (EditText) view.findViewById(R.id.editText2); // 나이
																			// 받아옴
		final EditText sex = (EditText) view.findViewById(R.id.editText3); // 성별
																			// 받아옴
		final EditText phone = (EditText) view.findViewById(R.id.editText4); // 연락처
																				// 받아옴
		final EditText address = (EditText) view.findViewById(R.id.editText5); // 주소
																				// 받아옴
		final Button submit_button = (Button) view.findViewById(R.id.button5); // 버튼
																				// 받아옴
		final Button show_profile = (Button) view
				.findViewById(R.id.showprofile);
		
		final Button modify = (Button) view.findViewById(R.id.modify_btn);

		accessDatabase();  // DB에서 프로필 가져오기

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) view
				.findViewById(R.id.selection_profile_picture);
		profilePictureView.setCropped(true);

		// Find the user's name view
		// Find the user's name view
		// userNameView = (TextView)
		// view.findViewById(R.id.selection_user_names);
		// userNameView = (TextView)
		// view.findViewById(R.id.selection_user_name);

		submit_button.setOnClickListener(new OnClickListener() { // 확인 버튼을 클릭할
																	// 경우
					public void onClick(View v) {
						myName = name.getText().toString();
						myAge = age.getText().toString();
						mySex = sex.getText().toString();
						myPhone = phone.getText().toString();
						myAddress = address.getText().toString();

						new HttpTask().execute(); // 프로필을 디비 서버로 보냄

						
						name.setEnabled(false);
						age.setEnabled(false);
						phone.setEnabled(false);
						sex.setEnabled(false);
						address.setEnabled(false);

					}
				});

		show_profile.setOnClickListener(new OnClickListener() { // 프로필 정보 보기 버튼을 우르면
					// 경우
					public void onClick(View v) {
						accessDatabase();
						
						name.setText(getMyname);			// DB에서 가져온 문자열을 EditView에 삽입
						age.setText(getMyage);
						phone.setText(getMyphone);
						sex.setText(getMysex);
						address.setText(getMyaddress);

						Log.i("TAG", "name= " + getMyname);
						Log.i("TAG", "age= " + getMyage);

						name.setEnabled(false);
						age.setEnabled(false);
						phone.setEnabled(false);
						sex.setEnabled(false);
						address.setEnabled(false);

					}
				});
		
		modify.setOnClickListener(new OnClickListener() { // 확인 버튼을 클릭할
			// 경우
			public void onClick(View v) {

				name.setEnabled(true);
				age.setEnabled(true);
				phone.setEnabled(true);
				sex.setEnabled(true);
				address.setEnabled(true);

			}
		});

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}



		return view;
	}



	// ///////////////////////////내프로필 받아오기///////////////////////////////
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

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

					getMyname = jsonChildNode.optString("name");
					getMyage = jsonChildNode.optString("age");
					getMysex = jsonChildNode.optString("sex");
					getMyphone = jsonChildNode.optString("phone");
					getMyaddress = jsonChildNode.optString("address");

					if (getMyname.equals(userNameView.getText().toString())) {

						Log.i("TAG", "name= " + getMyname);
						Log.i("TAG", "age= " + getMyage);
						break;
					}

				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	// 확인 버튼 눌렀을때 서버로 프로필 보내기
	class HttpTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpPost request_profile = new HttpPost(
						"http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/profile_modify.php"); // 서버
																									// 연결

				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

				// 이름, 나이, 성별, 연락처, 주소

				nameValue.add(new BasicNameValuePair("name", userNameView
						.getText().toString()));
				nameValue.add(new BasicNameValuePair("sex", mySex));
				nameValue.add(new BasicNameValuePair("phone", myPhone));
				nameValue.add(new BasicNameValuePair("address", myAddress));
				nameValue.add(new BasicNameValuePair("age", myAge));
				nameValue.add(new BasicNameValuePair("ID", userID));

				Log.i("TAG", "userIDDDDDDD= " + userID);
				Log.i("TAG", "myPhone= " + myPhone);
				Log.i("TAG", "myAddress= " + myAge);

				HttpEntity enty = new UrlEncodedFormEntity(nameValue,
						HTTP.UTF_8);

				request_profile.setEntity(enty);

				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(request_profile);

				HttpEntity entityResponse = res.getEntity();
				InputStream im = entityResponse.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(im, HTTP.UTF_8));

				String total = "";
				String tmp = ""; // 버퍼에 있는거 전부 더해주기
				// readLine -> 파일내용을 줄 단위로 읽기

				while ((tmp = reader.readLine()) != null) {
					if (tmp != null) {
						total += tmp;
					}

				}

				im.close();

				// return total;

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
	public void onActivityCreated(Bundle savedInstanceState) {
		tabHost = (TabHost) this.getActivity().findViewById(
				android.R.id.tabhost);
		tabHost.setup();

		setupTab("프로필");
		setupTab("알림");
		setupTab("설정");
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

		if (tag.equals("프로필")) {
			setContent.setContent(R.id.tab1);

		} else if (tag.equals("알림"))
			setContent.setContent(R.id.tab2);
		else if (tag.equals("설정"))
			setContent.setContent(R.id.tab3);

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

	public void onSetting(View v) {
		// LinearLayout
		// homeLayout=(LinearLayout)this.getActivity().findViewById(R.id.homeLayout);
		// homeLayout.set
	}

	public void onAlarm(View v) {

	}

	public void onProfile(View v) {

	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								// Set the id for the ProfilePictureView
								// view that in turn displays the profile
								// picture.
								profilePictureView.setProfileId(user.getId());
								Log.i("TAG", "User ID" + user.getId());
								// Set the Textview's text to the user's name.
								// userNameView.setText(user.getName());
								userID = user.getId();
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}

				});
		request.executeAsync();
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

}
