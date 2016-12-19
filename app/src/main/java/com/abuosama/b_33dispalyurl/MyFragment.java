package com.abuosama.b_33dispalyurl;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    Button button;
    TextView textView;





     public class  MyTask extends AsyncTask<String,Void,String>{

         URL myurl;
         HttpURLConnection connection;
         InputStream inputStream;
         InputStreamReader inputStreamReader;
         BufferedReader bufferedReader;

         String line;
         StringBuilder result;



         @Override
         protected String doInBackground(String... p1) {

             try {
                 myurl=new URL(p1[0]);



                 connection= (HttpURLConnection) myurl.openConnection();
                 //username and password is encryted mode  to this connection

                 String username=enCode("skillgumn");
                 String pass=enCode("palletech");
                 String pakagename=enCode("com.abuosama");


                 //set method

                 connection.setRequestProperty("username",username);
                 connection.setRequestProperty("password",pass);
                 connection.setRequestProperty("pakeagename",pakagename);

                 connection.connect();

                 inputStream=connection.getInputStream();
                 inputStreamReader=new InputStreamReader(inputStream);
                 bufferedReader=new BufferedReader(inputStreamReader);

                 result= new StringBuilder();
                 line=bufferedReader.readLine();

                 while(line!=null){

                     result.append(line);
                     line=bufferedReader.readLine();
                 }
                   return result.toString();


             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             finally {
                 if(connection!=null){

                     connection.disconnect();
                     if(inputStreamReader!=null){
                         try {
                             inputStreamReader.close();

                             if(bufferedReader!=null){

                                 bufferedReader.close();
                             }
                         } catch (IOException e) {
                             e.printStackTrace();
                         }


                     }
                 }
             }


             return null;
         }

         @Override
         protected void onPostExecute(String s) {

             if(s!=null)
                 textView.setText(s);

             else
              textView.setText("some thing wrong");


                 super.onPostExecute(s);

         }
     }

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my, container, false);

        textView = (TextView) v.findViewById(R.id.text);

        button = (Button) v.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkInternet()==true){

                    String subtopic_id="17";
                    MyTask m=new MyTask();
                    m.execute("http://techpalle.com/skillgun_App.svc/mobile/chapters/"+subtopic_id+"/app");


                }else
                {

                    Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });



        return  v;

    }

      public  boolean checkInternet(){

          ConnectivityManager con= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

          if(con!=null){

              NetworkInfo n=con.getActiveNetworkInfo();
              if(n!=null) {


                  if (n.isConnected() == false) {
                      Toast.makeText(getActivity(), "Ple enablr internet", Toast.LENGTH_SHORT).show();
                      return false;


                  }
              }}

          return  true;


      }

        //use for encode the code for encription

     public  String enCode(String input){

         byte[] bytes= Base64.encode(input.getBytes(),0);
         return  new String(bytes);
     }



}
