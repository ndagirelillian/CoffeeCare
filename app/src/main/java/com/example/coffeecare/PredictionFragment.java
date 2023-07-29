package com.example.coffeecare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.coffeecare.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;

public class PredictionFragment extends Fragment {

    private ImageView imageView;
    private Button chooseImage, captureImage, predict, moreinfo;
    private TextView result;
    private Bitmap bitmap;
    private int imageSize = 224;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction, container, false);

        imageView = view.findViewById(R.id.imageView);
        chooseImage = view.findViewById(R.id.choose_image_btn);
        captureImage = view.findViewById(R.id.captureImage);
        predict = view.findViewById(R.id.predict);
        result = view.findViewById(R.id.result);
        moreinfo = view.findViewById(R.id.moreinfo);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                getPermission();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult( intent, 10);
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
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
                    getPrediction();
                } else {
                    Toast.makeText(requireContext(), "No Image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 11);
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
        if (requestCode == 10 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 12 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.getExtras() != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void getPrediction() {
        try {
            Model model = Model.newInstance(requireContext());
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Get the predicted class and its corresponding probability score
            int predictedClass = getMax(outputFeature0.getFloatArray());
            float confidence = outputFeature0.getFloatValue(predictedClass);

            // Ensure the confidence is between 0 and 100%
            float confidencePercentage = Math.min(Math.max(confidence * 100, 0.0f), 100.0f);

            if (predictedClass == 0) {
                result.setText("Healthy");
                moreinfo.setVisibility(View.GONE);
            } else if (predictedClass == 1) {
                // Display the confidence with two decimal places
                String confidenceString = String.format("%.2f", confidencePercentage);
                result.setText("Coffee Leaf Rust Detected with Confidence: " + confidenceString + "%");
                // result.setText("Coffee Leaf Rust Detected");
                moreinfo.setVisibility(View.VISIBLE);
                moreinfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), Help.class);
                        startActivity(intent);
                    }
                });
            } else if (predictedClass == 2) {
                result.setText("Not Coffee Leaf");
                moreinfo.setVisibility(View.GONE);
            } else {
                Toast.makeText(requireContext(), "Prediction Error", Toast.LENGTH_SHORT).show();
            }

            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

