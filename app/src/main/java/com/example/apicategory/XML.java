package com.example.apicategory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Documented;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.XMLFormatter;

public class XML extends AppCompatActivity {
    TextView text_xml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_m_l);

        text_xml = findViewById(R.id.text_xml);
        ListView list_item_xml = findViewById(R.id.list_item_xml);


        String str = "123123123123213";
        String newStr = str.substring(0,4) +"****"+ str.substring(5);
        new getXML().execute();
    }

    private class getXML extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            text_xml.setText(s);

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }
    }
}