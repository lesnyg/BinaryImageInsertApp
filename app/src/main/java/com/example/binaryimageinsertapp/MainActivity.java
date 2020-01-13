package com.example.binaryimageinsertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private AsyncTask<String, String, String> bnAsyncTask;
    private String mImage;
    private ImageView img_binery;

    private Bitmap mBitmap;
    private byte[] imageBytes;
    private ByteArrayOutputStream baos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_binery = findViewById(R.id.img_binery);
        mBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ad03);

        img_binery.setImageBitmap(mBitmap);
        BitmapToString(mBitmap);


        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnAsyncTask = new bnAsyncTask().execute();
            }
        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

    public class bnAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            query();
            return null;
        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void query() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Su_배너이미지(BLOBData) VALUES (?)");
            byte[] buf = imageBytes;
            ps.setBytes(1, buf);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void BitmapToString(Bitmap bitmap) {
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);    //bitmap compress
        imageBytes = baos.toByteArray();
//        mImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }
}
