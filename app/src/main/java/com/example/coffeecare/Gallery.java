package com.example.coffeecare;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coffeecare.ml.Model;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;


public class Gallery extends AppCompatActivity {

    android.widget.ImageView ImageView;
    Button chooseImage, captureImage, predict, moreinfo;
    TextView result;
    Bitmap bitmap;
    DisplayMetrics displayMetrics;

//    private static final  int IMAGE_PICK_CODE = 1000;
//    private static final  int PERMISSION_CODE = 1001;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(Gallery.this, LoginFarmer.class)});
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageView = findViewById(R.id.imageView);
        chooseImage = findViewById(R.id.choose_image_btn);
        captureImage = findViewById(R.id.captureImage);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);
        moreinfo = findViewById(R.id.moreinfo);



        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                getPermission();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
                getPrediction();
            }
        });




    }
    int imageSize = 224;


    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check if the camera permission is granted
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request the camera permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }


    private int getMax(float[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[max]) max = i;
        }
        return max;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ImageView.setImageBitmap(bitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void getPrediction() {
        try {
            Model model = Model.newInstance(Gallery.this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());
                // Runs model inference and gets result.
                Model.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                // Releases model resources if no longer used.
                int results = getMax(outputFeature0.getFloatArray());
                if (results == 0) {
                    result.setText("Healthy");
                } else if (results == 1) {
                    result.setText("Coffee Leaf Rust Detected");
                    moreinfo.setVisibility(View.VISIBLE);
                    moreinfo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), DiseaseInformation.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else if (results == 2) {
                    result.setText("Not Coffee Leaf");

                } else {
                    Toast.makeText(Gallery.this, "No Image seleted", Toast.LENGTH_SHORT).show();
                }
                model.close();

            } else {
                Toast.makeText(Gallery.this, "No Image seleted", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            // TODO Handle the exception
        }


    }}