/*
 * Copyright 2011 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.serialportapp.sample;

import java.io.IOException;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.serialportapp.R;

import android_serialport_api.Converter;

public class SendingActivity extends SerialPortActivity {

	private TextView text;
	private Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sending);
		text = (TextView) findViewById(R.id.textView1);
		text.setMovementMethod(ScrollingMovementMethod.getInstance());
		send = (Button) findViewById(R.id.start);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mSerialPort != null) {
					toSend = !toSend;
					if (toSend) {
						sendThread.start();
					}

				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		toSend = false;
		super.onDestroy();
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				String hexString = Converter.bytesToHexString(buffer, size);
				if (hexString.endsWith("0b0d")) {
					hexString = hexString + "\n";
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				text.append(hexString);
				int offset = text.getLineCount() * text.getLineHeight();
				if (offset > text.getHeight()) {
					text.scrollTo(0, offset - text.getHeight());
				}
			}
		});
	}

	byte[] sendByte = new byte[] { (byte) 0xA5, 0x00, 0x0D, 0x01, 0x52,
			(byte) 0xD7, 0x0B, 0x0D };
	boolean toSend = false;
	private Thread sendThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (toSend) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (mOutputStream != null) {
					try {
						mOutputStream.write(sendByte);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	});
}
