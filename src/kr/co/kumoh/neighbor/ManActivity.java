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

public class ManActivity extends Activity {

	 AsyncTask<?, ?, ?> regIDInsertTask;

	  TextView message;

	  ProgressDialog loagindDialog;

	  String regId ;

  String myResult ;

	 

  @Override

  public void onCreate(Bundle savedInstanceState){

      super.onCreate(savedInstanceState);
     setContentView(R.layout.activit_main);
      
      
      message=(TextView)findViewById(R.id.re_message);
        if(GCMIntentService.re_message!=null){

  	      message.setText(GCMIntentService.re_message);

        }else{

  	      registerGcm();

        }
      
		
		finish();

  }

  

  public void registerGcm() {

  	  GCMRegistrar.checkDevice(this);

  	  GCMRegistrar.checkManifest(this);




  	    regId = GCMRegistrar.getRegistrationId(this);




  	  if (regId.equals("")) {

  	   GCMRegistrar.register(this,"218397130189");

  	  } else {

  	   Log.e("reg_id", regId);

  	  }

	  sendAPIkey();

  	 }

  

	private void sendAPIkey() {

				String  pnum= kr.co.kumoh.neighbor.ShowMap.temp;
		
				
				regIDInsertTask = new regIDInsertTask().execute(regId, pnum);
				

			}




private class regIDInsertTask extends AsyncTask<String, Void, Void> {

		

		@Override

		protected void onPreExecute() {

			super.onPreExecute();

			//loagindDialog = ProgressDialog.show(ManActivity.this, "키 등록 중입니다..",

			//		"Please wait..", true, false);

		}




		@Override

		protected Void doInBackground(String... params) {

				HttpPostData(params[0] , params[1]);

			return null;

		}

		

		protected void onPostExecute(Void result) {

			//loagindDialog.dismiss();

	}

}




public void HttpPostData(String reg_id , String pnum) { 

  try { 

       URL url = new URL("http://54.200.168.218/gcm_send_message_test.php");       // URL 설정 

       HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속 

       //-------------------------- 

       //   전송 모드 설정 - 기본적인 설정이다 

       //-------------------------- 

       http.setDefaultUseCaches(false);                                            

       http.setDoInput(true);                        

       http.setDoOutput(true);                     

       http.setRequestMethod("POST");         




       http.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 

       StringBuffer buffer = new StringBuffer(); 

       buffer.append("reg_id").append("=").append(reg_id).append("&");       // 메세지 보내는 사람이 등록한 기기 ID

      			
       
       buffer.append("ID").append("=").append(pnum);		// 메세지 받는 사용자 아이디
      		// 내 ID
       buffer.append("type").append("=").append(kr.co.kumoh.neighbor.WebDialog.type);	// TYPE
       
       buffer.append("myID").append("=").append(kr.co.kumoh.neighbor.MainFragment.userID);
       
       Log.i("TAG","내기기아이디=  " + reg_id);
       Log.i("TAG","너아이디=  " + pnum);
       Log.i("TAG","type=  " + kr.co.kumoh.neighbor.WebDialog.type);
       Log.i("TAG","내아이디=  " + kr.co.kumoh.neighbor.MainFragment.userID);
       
       
       
       
       
       OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR"); 

       PrintWriter writer = new PrintWriter(outStream); 

       writer.write(buffer.toString()); 

       writer.flush(); 

       InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");  

       BufferedReader reader = new BufferedReader(tmp); 

       StringBuilder builder = new StringBuilder(); 

       String str; 

       while ((str = reader.readLine()) != null) {    

            builder.append(str + "\n");                 

 } 

       

        myResult = builder.toString();              

      

  } catch (MalformedURLException e) { 

         // 

  } catch (IOException e) { 

         //  

  } // try 

} // HttpPostData 




}
