package com.freedomfighter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	private EditText editText_password;
	private Button button_submit;
	int counter = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initialize();
	}

	private void initialize() {
		editText_password = (EditText) findViewById(R.id.editText_password);
		button_submit = (Button) findViewById(R.id.button_submit);
		button_submit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("com.freedomfighter.ABOUT");
			startActivity(i);
			break;
		case R.id.paranoid:
			Uri packageUri = Uri.parse("package:com.freedomfighter");
			Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,packageUri);
			startActivity(uninstallIntent);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		String pass = readFromFile();
		String passMd5 = md5(editText_password.getText().toString());
		if (pass.contentEquals(passMd5)) {
			Toast.makeText(getApplicationContext(), "Redirecting...",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Wrong password",
					Toast.LENGTH_SHORT).show();
			editText_password.setText("");
			counter--;
			if (counter == 0) {
				button_submit.setEnabled(false);
				editText_password.setEnabled(false);
			}
		}
	}

	public String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * private void writeToFile(String data) { try { OutputStreamWriter
	 * outputStreamWriter = new OutputStreamWriter( openFileOutput("pass.txt",
	 * Context.MODE_PRIVATE)); outputStreamWriter.write(data);
	 * outputStreamWriter.close(); } catch (IOException e) { Log.e("Exception",
	 * "File write failed: " + e.toString()); } }
	 */

	private String readFromFile() {

		String ret = "";

		try {
			InputStream inputStream = openFileInput("pass.txt");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}
}
