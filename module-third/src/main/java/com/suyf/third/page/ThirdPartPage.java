package com.suyf.third.page;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.suyf.third.R;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

import java.io.File;

public class ThirdPartPage extends TinyPage implements View.OnClickListener {
    public static final int CODE_CAMERA = 0x1002;
    public static final int CODE_PHOTO = 0x1004;
    private TextView mTvText;
    private Button mBtnOk;
    private Button mBtnOk2;
    private Uri mCameraImageUri;

    private void assignViews() {
        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk2 = (Button) findViewById(R.id.btn_ok_2);
    }

    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_third_part);
        assignViews();
        mTvText.setText(pageIntent.getString("data"));
        mBtnOk.setOnClickListener(this);
        mBtnOk2.setOnClickListener(this);
        findViewById(R.id.btn_ok_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPage(new PageIntent(DataBindingPage.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnOk) {
            checkCameraPermission();
        } else if (v == mBtnOk2) {
            checkPhotoPermission();
        }
    }

    private void checkPhotoPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0x1003);

        } else {
            openPhoto();
        }
    }

    private void openPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        getActivity().startActivityForResult(intent, CODE_PHOTO);
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    0x1001);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "photo.jpg";
        mCameraImageUri = FileProvider.getUriForFile(getActivity(),
                getActivity().getPackageName() + ".file.provider", new File(mTempPhotoPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        getActivity().startActivityForResult(intent, CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CODE_CAMERA:
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setImageURI(mCameraImageUri);
                break;
            case CODE_PHOTO:
                ImageView imageView2 = findViewById(R.id.image_view);
                imageView2.setImageURI(data.getData());
                break;
        }
    }

}
