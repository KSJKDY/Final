package kr.co.kumoh.neighbor;

import kr.co.kumoh.neighbor.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		DialogProgress();


		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				
				Intent intent = new Intent(LoadingActivity.this,FacebookLogin.class);
				startActivity(intent);
				finish();
				
			}
		},2000);
	}

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.login, menu);
	//	return true;
	//}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

	}
	
	private void DialogProgress(){
	       ProgressDialog dialog = ProgressDialog.show(LoadingActivity.this, "",
	                        "잠시만 기다려 주세요 ...", true);
	      // 창을 내린다.
	      // dialog.dismiss();
	}

}
