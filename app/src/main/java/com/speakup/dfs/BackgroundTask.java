package com.speakup.dfs;

import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;
    BackgroundTask(Context ctx){
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String logInURL = "http://10.0.2.2/SpeakUP/login.php";
        String regURL = "http://10.0.2.2/SpeakUP/register.php";
        if (type.equals("reg")){
            String username = strings[1];
            String name = strings[2];
            String email = strings[3];
            String mobile = strings[4];
            String address = strings[5];
            String password = strings[6];
            try{
                URL url = new URL(regURL);
                try{
                    //Toast.makeText(context, "reg Read and URL" + type, Toast.LENGTH_LONG).show();
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data =
                                    URLEncoder.encode("regUsername", "UTF-8") +"="+ URLEncoder.encode(username, "UTF-8") +"&"+
                                    URLEncoder.encode("regName", "UTF-8") +"="+ URLEncoder.encode(name, "UTF-8") +"&"+
                                    URLEncoder.encode("regEmail", "UTF-8") +"="+ URLEncoder.encode(email, "UTF-8") +"&"+
                                    URLEncoder.encode("regPhone_number", "UTF-8") +"="+ URLEncoder.encode(mobile, "UTF-8") +"&"+
                                    URLEncoder.encode("regAddress", "UTF-8") +"="+ URLEncoder.encode(address, "UTF-8") +"&"+
                                    URLEncoder.encode("regPassword", "UTF-8") +"="+ URLEncoder.encode(password, "UTF-8");
                    //Toast.makeText(context, insert_data, Toast.LENGTH_LONG).show();
                    bufferedWriter.write(insert_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //Toast.makeText(RegisterActivity,"register successfully", Toast.LENGTH_SHORT).show();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else if (type.equals("login")){
            String user_name = strings[1];
            String pass_word = strings[2];
            try{
                URL url = new URL(logInURL);
                try{
                    //Toast.makeText(context, "reg Read and URL" + type, Toast.LENGTH_LONG).show();
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data =
                                URLEncoder.encode("logInUsername", "UTF-8") +"="+ URLEncoder.encode(user_name, "UTF-8") +"&"+
                                URLEncoder.encode("logInPassword", "UTF-8") +"="+ URLEncoder.encode(pass_word, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //Toast.makeText(RegisterActivity,"register successfully", Toast.LENGTH_SHORT).show();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        //super.onPostExecute(s);
    }
}
