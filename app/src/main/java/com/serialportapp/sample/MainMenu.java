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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.serialportapp.R;

public class MainMenu extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button buttonSetup = (Button)findViewById(R.id.ButtonSetup);
        buttonSetup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainMenu.this, SerialPortPreferences.class));
			}
		});

        final Button buttonConsole = (Button)findViewById(R.id.ButtonConsole);
        buttonConsole.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainMenu.this, ConsoleActivity.class));
			}
		});
        final Button buttonConsoleHex = (Button)findViewById(R.id.ButtonSendingHEX);
        buttonConsoleHex.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		startActivity(new Intent(MainMenu.this, ConsoleActivityHex.class));
        	}
        });

        final Button buttonSending = (Button)findViewById(R.id.ButtonSending);
        buttonSending.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainMenu.this, SendingActivity.class));
			}
		});

        final Button buttonQuit = (Button)findViewById(R.id.ButtonQuit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MainMenu.this.finish();
			}
		});
    }
}
