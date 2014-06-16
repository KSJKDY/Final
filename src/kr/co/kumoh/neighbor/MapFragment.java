package kr.co.kumoh.neighbor;

import kr.co.kumoh.neighbor.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MapFragment extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.mapfragment, container, false);

		Button button = (Button) view.findViewById(R.id.button1);
		button.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button1) {
			Intent intent = new Intent(getActivity(), ShowMap.class);
			getActivity().startActivity(intent);
		}

	}

}
