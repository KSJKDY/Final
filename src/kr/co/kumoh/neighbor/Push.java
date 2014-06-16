package kr.co.kumoh.neighbor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import static kr.co.kumoh.neighbor.MainFragment.userNameView;
import kr.co.kumoh.neighbor.R;

import com.google.android.gcm.GCMRegistrar;

public class Push extends Activity {

	AsyncTask<?, ?, ?> regIDInsertTask;

	TextView message;

	ProgressDialog loagindDialog;

	String regId;

	String myResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activit_main);

		message = (TextView) findViewById(R.id.re_message);
		if (GCMIntentService.re_message != null) {

			message.setText(GCMIntentService.re_message);

		} else {

			registerGcm();

		}
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

		String yname = kr.co.kumoh.neighbor.ShowMap.m;

		regIDInsertTask = new regIDInsertTask().execute(regId, yname);

	}

	private class regIDInsertTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			loagindDialog = ProgressDialog.show(Push.this, "키 등록 중입니다..",

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
			/*
			int i = 0;
			if (i == 0) {

				URL url = new URL("http://54.200.168.218/gcm_send_message_after_reg_pay.php"); // URL
																				// 설정

				HttpURLConnection http = (HttpURLConnection) url
						.openConnection(); // 접속

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

				buffer.append("pnum").append("=").append(pnum); // 클릭한 사용자 이름

				buffer.append("name").append("=").append(userNameView); // 보내는
																		// 사용자
																		// 이름

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
			}
*/
			//else {
				URL url = new URL("http://54.200.168.218/gcm_reg_insert_after_reg.php"); // URL
				// 설정

				HttpURLConnection http = (HttpURLConnection) url
						.openConnection(); // 접속

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

				buffer.append("yname").append("=").append(pnum); // 클릭한 사용자 이름
				
				Log.i("TAG", "User name= " + pnum);
				Log.i("TAG", "User ID= " + reg_id);

			

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
			//}

		} catch (MalformedURLException e) {

			//

		} catch (IOException e) {

			//

		} // try

	} // HttpPostData
}
