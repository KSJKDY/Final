package kr.co.kumoh.neighbor;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import static kr.co.kumoh.neighbor.ShowMap.temp;
//import com.example.location.B
public class gcmpop extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                */
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        layoutParams.dimAmount = 0.7f;

        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.gcmpop);
        WebView webView = (WebView)findViewById(R.id.popup1);
        webView.setWebViewClient(new myWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        
    
          webView.loadUrl("http://54.200.168.218/upload/untitled.png");
 
          Button list =(Button)findViewById(R.id.button1);
  		 list.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String text = "확인하러 고고슁";
				 Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                .show();
				
				 Intent intent = new Intent(Intent.ACTION_VIEW);
				 Uri u = Uri.parse("http://www.facebook.com/"+temp+"/");
				 intent.setData(u);
				 startActivity(intent);
				 /////페이스북으로 가는 코드 구현 해라~~~`

				}
      });
  	
  		 Button list11 =(Button)findViewById(R.id.button2);
  		 list11.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish() ;

			

				}
      });
          
          
    }
    

    	
    
    class myWebViewClient extends WebViewClient {
 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
 
    }



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
} 

