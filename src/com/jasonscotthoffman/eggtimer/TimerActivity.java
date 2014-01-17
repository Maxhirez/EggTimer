package com.jasonscotthoffman.eggtimer;

import java.io.IOException;

import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class TimerActivity extends FragmentActivity implements OnClickListener {

 private CountDownTimer countDownTimer;
 private boolean timerHasStarted = false;
 private Button startB;
 public TextView text;
 public long startTime;// = 5 * 1000;
 private int myInt;
 private final long interval = 1 * 1000;
 public String myEdit;


 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_timer);
  startB = (Button) this.findViewById(R.id.button);
  startB.setOnClickListener(this);
  text = (TextView) this.findViewById(R.id.timer);
  countDownTimer = new MyCountDownTimer(startTime, interval);
  text.setText(text.getText() + String.valueOf(startTime / 1000));
 }

 private long myGetCountdownTime() {
	 //get countdown interval from user
	 EditText myEdit = (EditText) findViewById(R.id.time);
	 //convert edit text to string 
	 String numString = myEdit.getText().toString();
	 //convert string to integer
   myInt = Integer.parseInt(numString);
   //convert integer to long number of milliseconds
   long localStartTime = (Long.valueOf(myInt) * 1000);
   return (localStartTime);
}

@Override
 public void onClick(View v) {
	startTime = myGetCountdownTime();
  if (!timerHasStarted) {
   countDownTimer.start();
   timerHasStarted = true;
   startB.setText("STOP");
  } else {
   countDownTimer.cancel();
   timerHasStarted = false;
   startB.setText("RESTART");
  }
 }

 public class MyCountDownTimer extends CountDownTimer {
  public MyCountDownTimer(long startTime, long interval) {
   super(startTime, interval);
   //assign the alarm volume
   setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
  }



  @Override
  public void onFinish() {
   text.setText("Time's up!");
   //Trigger the system alarm sound when it's done.
   try {
       Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
       r.play();
   } catch (Exception e) {}
  }

  @Override
  public void onTick(long millisUntilFinished) {
   text.setText("" + millisUntilFinished / 1000);
   
  }
 
 }

}