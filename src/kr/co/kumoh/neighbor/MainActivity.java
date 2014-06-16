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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import kr.co.kumoh.neighbor.R;


public class MainActivity extends FragmentActivity implements OnClickListener {

	final String TAG = "MainActivity";


	int mCurrentFragmentIndex;
	public final static int FRAGMENT_ONE = 0;
	public final static int FRAGMENT_TWO = 1;
	public final static int FRAGMENT_THREE = 2;
	public final static int FRAGMENT_FOUR = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ToggleButton bt_oneFragment = (ToggleButton) findViewById(R.id.bt_oneFragment);
		bt_oneFragment.setOnClickListener(this);
		ToggleButton bt_twoFragment = (ToggleButton) findViewById(R.id.bt_twoFragment);
		bt_twoFragment.setOnClickListener(this);
		ToggleButton bt_threeFragment = (ToggleButton) findViewById(R.id.bt_threeFragment);
		bt_threeFragment.setOnClickListener(this);
		
		
		mCurrentFragmentIndex = FRAGMENT_FOUR;

		fragmentReplace(mCurrentFragmentIndex);
		
		new HttpTask().execute();
	}
	

	
	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.ll_fragment, newFragment);

		// Commit the transaction
		transaction.commit();

	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;

		switch (idx) {
		case FRAGMENT_ONE:
			newFragment = new MapFragment();
			break;
		case FRAGMENT_TWO:
			newFragment = new HomeFragment();
			break;
		case FRAGMENT_THREE:
			newFragment = new RankFragment();
			break;
		case FRAGMENT_FOUR:
			newFragment = new StartGame();

		default:
			Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
	}
	

	@Override
	public void onClick(View v) {

		ToggleButton bt_oneFragment = (ToggleButton) findViewById(R.id.bt_oneFragment);
		bt_oneFragment.setChecked(false);
		ToggleButton bt_twoFragment = (ToggleButton) findViewById(R.id.bt_twoFragment);
		bt_twoFragment.setChecked(false);
		ToggleButton bt_threeFragment = (ToggleButton) findViewById(R.id.bt_threeFragment);
		bt_threeFragment.setChecked(false);
		switch (v.getId()) {

		case R.id.bt_oneFragment:
			mCurrentFragmentIndex = FRAGMENT_ONE;
			fragmentReplace(mCurrentFragmentIndex);
			bt_oneFragment.setChecked(true);
			break;
		case R.id.bt_twoFragment:
			mCurrentFragmentIndex = FRAGMENT_TWO;
			fragmentReplace(mCurrentFragmentIndex);
			bt_twoFragment.setChecked(true);
			break;
		case R.id.bt_threeFragment:
			mCurrentFragmentIndex = FRAGMENT_THREE;
			fragmentReplace(mCurrentFragmentIndex);
			bt_threeFragment.setChecked(true);
			break;
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			Toast.makeText(this,  "뒤로가기 버튼 눌림", Toast.LENGTH_SHORT).show();
			
			new AlertDialog.Builder(this)
			.setTitle("프로그램 종료")
			.setMessage("프로그램을 종료 하시겠습니까?")
			.setPositiveButton("예", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//프로세스 종료
					android.os.Process.killProcess(android.os.Process.myPid());
					
				}
			})
			.setNegativeButton("아니오", null)
			.show();
			
			break;
			
			default:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class HttpTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpPost request_profile = new HttpPost(
						"http://ec2-54-200-168-218.us-west-2.compute.amazonaws.com/m_insert.php"); // 서버
																									// 연결

				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

				// 로그인을 하고 게임이 끝난 후 첫페이지로 들어왔을때 테이블 생성

				nameValue.add(new BasicNameValuePair("name", userNameView
						.getText().toString()));
			
				nameValue.add(new BasicNameValuePair("ID", userID));

				Log.i("TAG", "userIDDDDDDD= " + userID);

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

}
