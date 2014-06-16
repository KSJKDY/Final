package kr.co.kumoh.neighbor;

import static kr.co.kumoh.neighbor.MainFragment.userID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

import kr.co.kumoh.neighbor.R.drawable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebDialog extends Activity implements OnClickListener {

	private static MediaPlayer music;
	static String text;

	MainFragment fragment = (MainFragment) MainFragment.fragment;
	private Button[] mButton = new Button[10];
	int num = 0;
	public static String type = "0";
	int chance = 0; // ��ư Ŭ�� Ƚ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.webpopup);
		WebView webView = (WebView) findViewById(R.id.webPopup);
		webView.setWebViewClient(new myWebViewClient());
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		// webView.loadUrl("http://www.google.com");
		
		music = MediaPlayer.create(this, R.raw.gamesong);
		music.setLooping(true);
		music.start();

		Random random = new Random();
		num = random.nextInt(10); // 1���� 9���� ���� �߻�
		if (num == 0) {
			num = 1;
		}
		System.out.println(num);

		mButton[1] = (Button) findViewById(R.id.button1);
		mButton[2] = (Button) findViewById(R.id.button2);
		mButton[3] = (Button) findViewById(R.id.button3);
		mButton[4] = (Button) findViewById(R.id.button4);
		mButton[5] = (Button) findViewById(R.id.button5);
		mButton[6] = (Button) findViewById(R.id.button6);
		mButton[7] = (Button) findViewById(R.id.button7);
		mButton[8] = (Button) findViewById(R.id.button8);
		mButton[9] = (Button) findViewById(R.id.button9);

		mButton[1].setOnClickListener(this);
		mButton[2].setOnClickListener(this);
		mButton[3].setOnClickListener(this);
		mButton[4].setOnClickListener(this);
		mButton[5].setOnClickListener(this);
		mButton[6].setOnClickListener(this);
		mButton[7].setOnClickListener(this);
		mButton[8].setOnClickListener(this);
		mButton[9].setOnClickListener(this);

		AlertDialog.Builder alert = new AlertDialog.Builder(WebDialog.this);
		alert.setPositiveButton("START!!",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss(); // �ݱ�

					}
				});
		alert.setMessage("�� 3���� ��ȸ�� ���ڸ� ����ٸ� ����� ���� ����� �Ѵ�. ������ ���Ѵٸ� ����� ����!! ���� ��� ���� �� �ִ�");
		alert.show();
		
		

	}

	public void searchnumber(View v) {
		Button newButton = (Button) v;

		for (int i = 1; i < 10; i++) {
			if (newButton == mButton[i]) {
				if (i == num) { // ////////////////////////////////// ������
								// ��ư�� ���� ���
					type = "0"; // ���߾��� ������ ���� ���ִ� �ǹ��� ���� 0�� �ο�
					new HttpTask().execute();
					change_correct_button(i); // ������ ��ư�� �� ����

					AlertDialog.Builder alert = new AlertDialog.Builder(
							WebDialog.this);
					alert.setPositiveButton("Ȯ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(WebDialog.this,
											MainActivity.class);
									startActivity(intent);
									fragment.getActivity().finish();
									finish();
									music.stop();
									dialog.dismiss(); // �ݱ�
									

								}
							});
					alert.setMessage("����!! ���� �� ��� ���̱���...T.T");
					alert.show();

					System.out.println("����");

				}

				else if (i < num) { // /////////////////////////////// ������
									// ��ư�� ���� ���
					int j = i;

					System.out.println("����");

					for (int k = 1; k < j + 1; k++) {
						changebutton(k);

						if (chance == 2) { // 3���� ��ȸ���� ������ ���� ���
							type = "1"; // ������ ���ϸ� ���� ��� ���� �� �ִ� �ǹ��� ���� 1�� �ο�
							new HttpTask().execute(); // db�� Ÿ�� �� �ѱ�
							AlertDialog.Builder alert = new AlertDialog.Builder(
									WebDialog.this);
							alert.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													WebDialog.this,
													MainActivity.class);
											startActivity(intent);
											music.stop();
											dialog.dismiss(); // �ݱ�
											fragment.getActivity().finish();
											finish();
										}
									});
							alert.setMessage("����!! ���� ��� ���� �� �־��^^");
							alert.show();
						}

					}
					String text = "UP����";
					Toast.makeText(getApplicationContext(), text,
							Toast.LENGTH_SHORT).show();
					break;
				} else if (i > num) { // ///////////////////////////// ������
										// ��ư�� Ŭ ���
					for (; i < 10; i++) {
						changebutton(i);
						if (chance == 2) { // 3���� ��ȸ�� ������ ���� ���
							type = "1"; // ������ ���ϸ� ���� ��� ���� �� �ִ� �ǹ��� ���� 1�� �ο�
							new HttpTask().execute();
							AlertDialog.Builder alert = new AlertDialog.Builder(
									WebDialog.this);
							alert.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													WebDialog.this,
													MainActivity.class);
											startActivity(intent);
											fragment.getActivity().finish();
											finish();
											music.stop();
											dialog.dismiss(); // �ݱ�
										}
									});
							alert.setMessage("����!! ���� ��� ���� �� �־��^^");
							alert.show();

							break;
						}

					}
					System.out.println("ŭ");
					String text = "DOWN����";
					Toast.makeText(getApplicationContext(), text,
							Toast.LENGTH_SHORT).show();
					break;
				}

			}
		}

	}

	class myWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}

	}

	public void onClick(View v) {

			searchnumber(v);
			chance = chance + 1;

		}


	public void change_correct_button(int i) // ������ ���
	{
		switch (i) {

		case 1:
			mButton[i].setBackgroundResource(drawable.button_yellow1);
			break;
		case 2:
			mButton[i].setBackgroundResource(drawable.button_yellow2);
			break;
		case 3:
			mButton[i].setBackgroundResource(drawable.button_yellow3);
			break;
		case 4:
			mButton[i].setBackgroundResource(drawable.button_yellow4);
			break;
		case 5:
			mButton[i].setBackgroundResource(drawable.button_yellow5);
			break;
		case 6:
			mButton[i].setBackgroundResource(drawable.button_yellow6);
			break;
		case 7:
			mButton[i].setBackgroundResource(drawable.button_yellow7);
			break;
		case 8:
			mButton[i].setBackgroundResource(drawable.button_yellow8);
			break;
		case 9:
			mButton[i].setBackgroundResource(drawable.button_yellow9);
			break;

		}

	}

	public void changebutton(int i) // ������ ��ư�� �۰ų� Ŭ ���
	{
		switch (i) {

		case 1:
			mButton[i].setBackgroundResource(drawable.button_red1);
			break;
		case 2:
			mButton[i].setBackgroundResource(drawable.button_red2);
			break;
		case 3:
			mButton[i].setBackgroundResource(drawable.button_red3);
			break;
		case 4:
			mButton[i].setBackgroundResource(drawable.button_red4);
			break;
		case 5:
			mButton[i].setBackgroundResource(drawable.button_red5);
			break;
		case 6:
			mButton[i].setBackgroundResource(drawable.button_red6);
			break;
		case 7:
			mButton[i].setBackgroundResource(drawable.button_red7);
			break;
		case 8:
			mButton[i].setBackgroundResource(drawable.button_red8);
			break;
		case 9:
			mButton[i].setBackgroundResource(drawable.button_red9);
			break;

		}

	}

	// Ȯ�� ��ư �������� ������ ������ ������
	class HttpTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpPost request_profile = new HttpPost(
						"http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/type_select.php"); // ����
																										// ����

				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

				// �̸�, ����, ����, ����ó, �ּ�

				nameValue.add(new BasicNameValuePair("ID", userID));
				nameValue.add(new BasicNameValuePair("type", type));
				
				Log.i("TAG", "id= " + userID);
				Log.i("TAG", "TYPE= " + type);
				

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
				String tmp = ""; // ���ۿ� �ִ°� ���� �����ֱ�
				// readLine -> ���ϳ����� �� ������ �б�

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

			// ������ null ��ȯ
			return null;

		}

		protected void onPostExecute(String value) {
			super.onPostExecute(value);
			// result.setText(value);

		}

	}

}