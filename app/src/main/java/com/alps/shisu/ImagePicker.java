package com.alps.shisu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;


import com.alps.shisu.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImagePicker extends DialogFragment {

        GetImage getImage;
        public ImagePicker(GetImage getImage, boolean allowMultiple) {
            this.getImage = getImage;
        }
        File cameraImage;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.bottom_sheet_imagepicker, container, false);
            view.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {@
                    Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {
                          Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
                    }, WRITE_STORAGE_PERMISSION_CODE);
                }
                else {
                    captureFromCamera();
                }
            }
            });
            view.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, READ_STORAGE_PERMISSION_CODE);
                }
                else {
                    startGallery();
                }
            }
            });
            return view;
        }

    private final int READ_STORAGE_PERMISSION_CODE = 100;
    private final int WRITE_STORAGE_PERMISSION_CODE = 200;
    private final int CAMERA_PERMISSION_CODE = 300;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == READ_STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                   && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
                Log.e("GalleryResult--","Permission Granted");
            } else {
                Log.e("GalleryResult--","Permission DENIED");

            }
        }
        else if(requestCode == WRITE_STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                captureFromCamera();
                Log.e("GalleryResult--","Permission Granted");
            } else {
                Log.e("GalleryResult--","Permission DENIED");

            }
        }

    }
        public interface GetImage {
            void setGalleryImage(Uri imageUri);
            void setCameraImage(String filePath, File cameraImage);
            void setImageFile(File file);
        }@
                Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == Activity.RESULT_OK) {
                if(requestCode == 1000) {
                    Uri returnUri = data.getData();
                    getImage.setGalleryImage(returnUri);
                    Bitmap bitmapImage = null;
                }
                if(requestCode == 1002) {
                    if(cameraImage != null) {
                        getImage.setImageFile(cameraImage);
                    }
                    getImage.setCameraImage(cameraFilePath,cameraImage);

                }
            }
        }
        private void startGallery() {
        try {
            Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            cameraIntent.setType("image/*");
            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(cameraIntent, 1000);
            }
            else{
                Log.e("Error", "null");
            }
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        }
        private String cameraFilePath;
        private File createImageFile() throws IOException {

            File imageFile = null;
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                boolean pictureDirectoryExists = storageDir.exists();
                if (!pictureDirectoryExists) {
                    pictureDirectoryExists = storageDir.mkdirs();
                }
                if (pictureDirectoryExists) {
                    imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
                    cameraFilePath = imageFile.getAbsolutePath();
                    cameraImage = imageFile;}

            } catch (Exception exc) {
//                LOGGER.error("E", exc);
            }
            return imageFile;

//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String imageFileName = "JPEG_" + timeStamp + "_";
//                File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
//                File image = File.createTempFile(imageFileName, /* prefix */ ".jpg", /* suffix */ storageDir /* directory */);
//                cameraFilePath = "file://" + image.getAbsolutePath();
//                cameraImage = image;
//                return image;


        }
        private void captureFromCamera() {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),  "com.alps.shisu.provider", createImageFile()));

                startActivityForResult(intent, 1002);
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
}
