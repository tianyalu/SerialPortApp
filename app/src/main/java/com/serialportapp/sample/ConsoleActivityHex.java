package com.serialportapp.sample;

import java.io.IOException;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.serialportapp.R;

import android_serialport_api.Converter;

public class ConsoleActivityHex extends SerialPortActivity {

	EditText mReception;
	EditText Emission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consolehex);

		mReception = (EditText) findViewById(R.id.EditTextReception);
		Emission = (EditText) findViewById(R.id.EditTextEmission);
		final Button buttonsend = (Button) findViewById(R.id.ButtonSent1);
		buttonsend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CharSequence t = Emission.getText();

				if (t.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入数据",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				try {
					
					byte[] info = Converter.hexStringToBytes(t.toString());
					mOutputStream.write(info);
					System.out.println(Converter.getHexString(info, info.length));

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
				String hexString=Converter.bytesToHexString(buffer, size);
				mReception.append(hexString);
			}
		});
	}
}
