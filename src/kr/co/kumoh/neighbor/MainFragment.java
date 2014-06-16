package kr.co.kumoh.neighbor;

import kr.co.kumoh.neighbor.HomeFragment.HttpTask;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class MainFragment extends Fragment implements OnClickListener{

	
	public static Fragment fragment;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	public static TextView userNameView;
	public static String userID;
	//private ProfilePictureView profilePictureView;  ////////////////

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.mainfragment, container, false);

		Button show_home = (Button) view.findViewById(R.id.showhome);
		
		// Find the user's profile picture custom view
		//profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		//profilePictureView.setCropped(true);

		// find user's name
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		fragment = MainFragment.this;
		
		
		//TextView textview = (TextView) view.findViewById(R.id.textView1);
		//textview.setSelected(true);

		// Check for an open session
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}
		
		String text = "화면을 터치 하면 다음 화면으로 넘어 갑니다";
		Toast.makeText(getActivity().getApplicationContext(), text,
				Toast.LENGTH_SHORT).show();
		
		show_home.setOnClickListener(this);
		return view;

	}
	
private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

public void onClick(View view){
		
		if(view.getId() == R.id.showhome)
		{
		Intent intent = new Intent(getActivity(), WebDialog.class);
		getActivity().startActivity(intent);
		
		}
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
								
								// view that in turn displays the profile picture.
			                  //  profilePictureView.setProfileId(user.getId());
								userID = user.getId();
								Log.i("TAG", "User ID" + user.getId());
								// Set the Textview's text to the user's name.
								userNameView.setText(user.getName());
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



