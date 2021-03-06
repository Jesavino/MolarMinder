//v0.2 -  8 January 2013

/*
 * Copyright (c) 2010, Shimmer Research, Ltd.
 * All rights reserved
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:

 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of Shimmer Research, Ltd. nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Jong Chern Lim
 * @date   October, 2013
 */

//Future updates needed
//- the handler should be converted to static 

package com.shimmerresearch.ShimmerExample;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.shimmerresearch.android.Shimmer;
import com.shimmerresearch.driver.FormatCluster;
import com.shimmerresearch.driver.ObjectCluster;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ShimmerExampleActivity extends Activity {
    /** Called when the activity is first created. */
	/*int arraySize = 500;
	double XAccelArray[] = new double[arraySize];
	int i = 0 ;
	*/
    Timer mTimer;
    private Shimmer mShimmerDevice1 = null;
    
    /**Create Queue**/
    Queue<String> qe=new LinkedList<String>();
    
    /**Create Toothbrushing Classifier**/
    ToothbrushingClassifier tbc = new ToothbrushingClassifier();
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
    }
    

    // in charge of connecting the shimmer to the application
    public void onConnectionButtonClick(View view) {
    	mShimmerDevice1 = new Shimmer(this, mHandler,"RightArm", 51.2, 0, 0, Shimmer.SENSOR_ACCEL|Shimmer.SENSOR_GYRO|Shimmer.SENSOR_MAG, false);
        String bluetoothAddress="00:06:66:6B:7C:37";
        
        mShimmerDevice1.connect(bluetoothAddress,"default"); 
        Log.d("ConnectionStatus","Trying");
    }
    
    public void onListenButtonClick(View view) {
    	setClassified(true);
    }
    
    public void setClassified(boolean state) {
    	TextView text = (TextView) findViewById(R.id.classification_text);
    	if (state) {
    		
    		text.setText("Brushing Teeth! Good Job!");
    	}
    	else {
    		text.setText(R.string.no_classification);
    	}
    }
    
 // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) { // handlers have a what identifier which is used to identify the type of msg
            case Shimmer.MESSAGE_READ:
            	if ((msg.obj instanceof ObjectCluster)){	// within each msg an object can be include, objectclusters are used to represent the data structure of the shimmer device
            	    ObjectCluster objectCluster =  (ObjectCluster) msg.obj; 
            	    Collection<FormatCluster> accelXFormats = objectCluster.mPropertyCluster.get("Accelerometer X");  // first retrieve all the possible formats for the current sensor device
					FormatCluster formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelXFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
						Log.d("CalibratedData",objectCluster.mMyName + " AccelX: " + formatCluster.mData + " "+ formatCluster.mUnits);
						
					}
					Collection<FormatCluster> accelYFormats = objectCluster.mPropertyCluster.get("Accelerometer Y");  // first retrieve all the possible formats for the current sensor device
					formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelYFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
						Log.d("CalibratedData",objectCluster.mMyName + " AccelY: " + formatCluster.mData + " "+formatCluster.mUnits);
					}
					Collection<FormatCluster> accelZFormats = objectCluster.mPropertyCluster.get("Accelerometer Z");  // first retrieve all the possible formats for the current sensor device
					formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelZFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
						Log.d("CalibratedData",objectCluster.mMyName + " AccelZ: " + formatCluster.mData + " "+formatCluster.mUnits);
					}


					accelXFormats = objectCluster.mPropertyCluster.get("Low Noise Accelerometer X");  // first retrieve all the possible formats for the current sensor device
					formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelXFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
						Log.d("CalibratedData",objectCluster.mMyName + " AccelLNX: " + formatCluster.mData + " "+ formatCluster.mUnits);

						//XAccelArray[i] = formatCluster.mData;
						qe.add(String.valueOf(formatCluster.mData));
						
					}
					accelYFormats = objectCluster.mPropertyCluster.get("Low Noise Accelerometer Y");  // first retrieve all the possible formats for the current sensor device
					formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelYFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
					//	Log.d("CalibratedData",objectCluster.mMyName + " AccelLNY: " + formatCluster.mData + " "+formatCluster.mUnits);
					//	if(formatCluster.mData > -12 && formatCluster.mData < -5){
					//		Log.d("Toothburshing", "Toothbrushing" );
					//	}
					}
					accelZFormats = objectCluster.mPropertyCluster.get("Low Noise Accelerometer Z");  // first retrieve all the possible formats for the current sensor device
					formatCluster = ((FormatCluster)ObjectCluster.returnFormatCluster(accelZFormats,"CAL")); // retrieve the calibrated data
					if (formatCluster!=null){
					//	Log.d("CalibratedData",objectCluster.mMyName + " AccelLNZ: " + formatCluster.mData + " "+formatCluster.mUnits);
					}
					if(qe.size() == 50){
						setClassified(tbc.betterClassifier(qe));
					}
					
            	}
                break;
                 case Shimmer.MESSAGE_TOAST:
                	Log.d("toast",msg.getData().getString(Shimmer.TOAST));
                	Toast.makeText(getApplicationContext(), msg.getData().getString(Shimmer.TOAST),
                            Toast.LENGTH_SHORT).show();
                break;

                 case Shimmer.MESSAGE_STATE_CHANGE:
                	 switch (msg.arg1) {
                     	case Shimmer.MSG_STATE_FULLY_INITIALIZED:
                    	    if (mShimmerDevice1.getShimmerState()==Shimmer.STATE_CONNECTED){
                    	        Log.d("ConnectionStatus","Successful");
                    	        mShimmerDevice1.startStreaming();
                    	        //shimmerTimer(1000); //Disconnect in 30 seconds
                    	     }
                    	    break;
	                    case Shimmer.STATE_CONNECTING:
	                    	Log.d("ConnectionStatus","Connecting");
                	        break;
	                    case Shimmer.STATE_NONE:
	                    	Log.d("ConnectionStatus","No State");
	                    	break;
                     }
                break;
                
            }
        }
    };



	  public synchronized void shimmerTimer(int seconds) {
	        mTimer = new Timer();
	        mTimer.schedule(new responseTask(), seconds*1000);
		}
	         
	    class responseTask extends TimerTask {
	        public void run() {
	        	mShimmerDevice1.stopStreaming(); 
	        	mShimmerDevice1.stop(); 
	        	mShimmerDevice1 = new Shimmer(ShimmerExampleActivity.this, mHandler,"RightArm", 51.2, 0, 0, Shimmer.SENSOR_ACCEL|Shimmer.SENSOR_GYRO|Shimmer.SENSOR_MAG, false);
	             String bluetoothAddress="00:06:66:6B:7C:37";
	             mShimmerDevice1.connect(bluetoothAddress,"default"); 
	        }
	    }
    }
    



    
    