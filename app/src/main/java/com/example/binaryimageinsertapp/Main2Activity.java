package com.example.binaryimageinsertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ImageView img_result;
    private ImageView img_result2;
    private ImageView img_result3;
    private ImageView img_result4;
    private AsyncTask<String, String, String> mTask;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img_result = findViewById(R.id.img_result);
        img_result2 = findViewById(R.id.img_result2);
        img_result3 = findViewById(R.id.img_result3);
        img_result4 = findViewById(R.id.img_result4);
        mTask = new MySyncTask().execute();

    }

    public class MySyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            listQuery();
            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void listQuery() {
        Connection connection;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select TOP 1* from Su_직원출퇴근정보 order by id DESC");

            byte b[];
            byte b2[];
            byte b3[];
            byte b4[];
            while (resultSet.next()) {
                Blob blob = resultSet.getBlob(9);
                b = blob.getBytes(1, (int) blob.length());
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                Blob blob2 = resultSet.getBlob(13);
                b2 = blob2.getBytes(1, (int) blob2.length());
                bitmap2 = BitmapFactory.decodeByteArray(b2, 0, b2.length);

                Blob blob3 = resultSet.getBlob(12);
                b3 = blob3.getBytes(1, (int) blob3.length());
                bitmap3 = BitmapFactory.decodeByteArray(b3, 0, b3.length);

               

            }
            ResultSet rs = statement.executeQuery("select TOP 1* from Su_수급자서명정보 order by id DESC");
            while (rs.next()){
                Blob blob4 = rs.getBlob(3);
                b4 = blob4.getBytes(1, (int) blob4.length());
                bitmap4 = BitmapFactory.decodeByteArray(b4, 0, b4.length);

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    img_result.setImageBitmap(bitmap);
                    img_result2.setImageBitmap(bitmap2);
                    img_result3.setImageBitmap(bitmap3);
                    img_result4.setImageBitmap(bitmap4);


                }
            });
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
