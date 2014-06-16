package kr.co.kumoh.neighbor;


import static kr.co.kumoh.neighbor.ShowMap.temp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

//import com.example.location.B
public class MapDialog extends Activity implements OnClickListener {
	
	static String text;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
	                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	        setContentView(R.layout.mapppoup);
	        WebView webView = (WebView)findViewById(R.id.webPopup);
	        webView.setWebViewClient(new myWebViewClient());
	        WebSettings webSettings = webView.getSettings();
	        webSettings.setJavaScriptEnabled(true);
	        webSettings.setBuiltInZoomControls(true);
	        
	        webView.loadUrl("http://graph.facebook.com/"+temp+"/picture?type=large");
	        
	        Button submit =(Button)findViewById(R.id.submit_btn);
	  		 submit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					text = temp;
					 Intent intent = new Intent(MapDialog.this, ManActivity.class);

						MapDialog.this.startActivity(intent);
				
						///////////////////////////////
						//신청하는거다 신경써서 하삼
						////////////////////////////////////
					}
	      });
	  		 Button cancell =(Button)findViewById(R.id.button2);
	  		 cancell.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 
						finish() ;

					}
	      });
	        
	        
	        
	 
	    }
	    class myWebViewClient extends WebViewClient {
	 
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	            view.loadUrl(url);
	            return true;
	        }
	 
	    }
	    public void onClick(View arg0) {
	     
	    }
	} 