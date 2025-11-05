package com.iot.imageclassifiers;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.Locale;

public class GalleryActivity extends AppCompatActivity {

    private ClassifierWithModel cls;
    private ImageView imageView;
    private TextView textView;
    public static final int GALLERY_IMAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery);

        Button selectBtn = findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(v->getImageFromGallery());
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        cls = new ClassifierWithModel(this);

        try{
            cls.init();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK&&
        requestCode == GALLERY_IMAGE_REQUEST_CODE){
            if(data == null){
                return;
            }

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {
                if(Build.VERSION.SDK_INT >= 29){
                    ImageDecoder.Source src =
                            ImageDecoder.createSource(getContentResolver(), selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(src);
                }else{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
            }catch (IOException ioe){
                Log.e(TAG, "Failed to read Image", ioe);
            }

            if(bitmap != null) {
                if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
                    // ARGB_8888로 복사/변환
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                }
                Pair<String, Float> output = cls.classify(bitmap);
                String resultStr = String.format(Locale.ENGLISH,
                        "class: %s, prob : %2f%%",
                        output.first, output.second*100);

                imageView.setImageBitmap(bitmap);
                textView.setText(resultStr);
            }
        }
    }
}