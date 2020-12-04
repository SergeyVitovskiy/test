package com.example.apicategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.InflaterOutputStream;

public class MainActivity extends AppCompatActivity {

    Button updateCategory_button;
    Button deleteCategory_button;
    Button addCategory_button;
    TextView text_time;
    ListView category_list;

    CategoryAdapter mCategoryAdapter;
    List<Category> mCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView link = findViewById(R.id.link);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        // Присвоение элементов
        text_time = findViewById(R.id.text_time);
        addCategory_button = findViewById(R.id.addCategory_button);
        updateCategory_button = findViewById(R.id.updateCategory_button);
        deleteCategory_button = findViewById(R.id.deleteCategory_button);
        category_list = findViewById(R.id.category_list);
        // Присвоение адаптера
        mCategoryAdapter = new CategoryAdapter(mCategoryList, MainActivity.this);
        category_list.setAdapter(mCategoryAdapter);

        new getJsonCategory().execute(); // Получить категории

        Button cam_button = findViewById(R.id.cam_button);
        cam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CamActivity.class);
                startActivity(intent);
            }
        });

        Button regex_button = findViewById(R.id.regex_button);
        regex_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegEx.class);
                startActivity(intent);
            }
        });

        Button dialog_button = findViewById(R.id.dialog_button);
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(fragmentManager, "MyDialog");
            }
        });
        time();
        buttonOnClick();
    }

    private void time() {
        Timer timer = new Timer();
        timer.schedule(new UpdateTime(), 0, 100);
    }

    private void buttonOnClick() {
        // Добавить категорию
        addCategory_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addCategory().execute();
                new getJsonCategory().execute();
            }
        });
        // Обновление категории
        updateCategory_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Обновление");
                dialog.setContentView(R.layout.category_dialog_update);
                final EditText title_editText = dialog.findViewById(R.id.title_editText);
                final EditText image_editText = dialog.findViewById(R.id.image_editText);
                CheckBox displayed_checkBox = dialog.findViewById(R.id.displayed_checkBox);
                Button update_button = dialog.findViewById(R.id.update_button);
                Button cancel_button = dialog.findViewById(R.id.cancel_button);

                final Spinner category_spinner = dialog.findViewById(R.id.category_spinner);
                List<String> mCategoryString = new ArrayList<>();
                for (int i = 0; i < mCategoryList.size(); i++) {
                    mCategoryString.add(mCategoryList.get(i).getId() + " - "
                            + mCategoryList.get(i).getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, mCategoryString);
                category_spinner.setAdapter(adapter);

                category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(MainActivity.this, "Позиция:" + i , Toast.LENGTH_SHORT).show();
                        title_editText.setText(mCategoryList.get(i).getTitle());
                        image_editText.setText(mCategoryList.get(i).getImage());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                update_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new updateCategory().execute(); // Обновить категорию
                        new getJsonCategory().execute(); // Получить категории
                        dialog.cancel();
                    }
                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        // Удаление категории
        deleteCategory_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteCategory().execute();
                new getJsonCategory().execute();
            }
        });


        Button xml_button = findViewById(R.id.xml_button);
        xml_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, XML.class);
                startActivity(intent);
            }
        });
    }

    // Добавить категорию
    private class addCategory extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("title", "23453245")
                    .appendQueryParameter("image", "124134234")
                    .appendQueryParameter("displayed", "hrtrth");
            String out = builder.build().getEncodedQuery();
            try {
                URL url = new URL("http://anndroidankas.h1n.ru/categories");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setReadTimeout(2000);
                connection.setConnectTimeout(2000);
                connection.setRequestMethod("POST");
                connection.connect();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write("title=title1&image=image1&displayed=123123123");
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null)
                    result.append(line);

                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    // Удалить категорию
    private class deleteCategory extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://anndroidankas.h1n.ru/categories/22");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.connect();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null)
                    result.append(line);
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    // Обновление категории
    private class updateCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {


                JSONObject jsonObject = new JSONObject("{\r\n    \"title\": \"erge\",\r\n    \"image\": \"erg\",\r\n    \"displayed\": \"1\"\r\n}");

                URL url = new URL("http://anndroidankas.h1n.ru/categories/30");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/raw");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(connection.getOutputStream()));
                writer.append(jsonObject.toString());
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null)
                    result.append(line);
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // Класс получения категорий
    private class getJsonCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://anndroidankas.h1n.ru/categories");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (connection.getInputStream()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null)
                    result.append(line);
                return result.toString(); // Возвращаем строку с JSON
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mCategoryList.clear(); // Очистить лист с категориями
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Category category = new Category(
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("image"),
                            jsonObject.getInt("displayed"));
                    mCategoryList.add(category);
                }
                mCategoryAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateTime extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
                    text_time.setText(dateFormat.format(date));
                }
            });
        }
    }
}