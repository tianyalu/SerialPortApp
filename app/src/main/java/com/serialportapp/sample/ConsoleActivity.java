/*
 * Copyright 2009 Cedric Priscal
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
import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.serialportapp.R;

import android_serialport_api.Converter;

public class ConsoleActivity extends SerialPortActivity {

	EditText mReception;
	EditText Emission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.console);

		mReception = (EditText) findViewById(R.id.EditTextReception);
		Emission = (EditText) findViewById(R.id.EditTextEmission);
		Emission.setText("@01张三，张三");
		final Button buttonsend = (Button) findViewById(R.id.ButtonSent1);
		buttonsend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				CharSequence t = Emission.getText();

				if (t.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入数据",
							Toast.LENGTH_SHORT).show();
					return;
				}
				char[] text = new char[t.length()];
				for (int i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					/*
					 * byte[] b=new byte[]{0x40,0x30,0x31, (byte) 0xD5,(byte)
					 * 0xC5,(byte)0xC8,(byte)0xFD, 0x2C, (byte) 0xD5,(byte)
					 * 0xC5,(byte)0xC8,(byte)0xFD}; mOutputStream.write(b);
					 */
					byte[] info = new String(text).getBytes("GB2312");
					mOutputStream.write(info);
					System.out.println(Converter.getHexString(info, info.length));
					// mOutputStream.write('\n');

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				// if (mReception != null) {
				try {
					String data=new String(buffer, 0, size,"GB2312");
					Log.d(TAG,"onDataReceived-->data="+data);
					mReception.append(data);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("mReception=" + new String(buffer, 0, size));
				// }
			}
		});
	}
}
