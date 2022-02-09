package com.example.memorization.Activities;

import static com.example.memorization.Constant.CAMERA_PERM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.memorization.databinding.ActivityScannerBarCodeBinding;
import com.google.zxing.Result;

public class ScannerBarCodeActivity extends AppCompatActivity {

    ActivityScannerBarCodeBinding binding;
    private CodeScanner scanner;
    private boolean CameraPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBarCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        scanner = new CodeScanner(this, binding.scanner);
        askPermission();
        if (CameraPermission) {
            binding.scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scanner.startPreview();
                }
            });

            scanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ScannerBarCodeActivity.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);

            } else {
                scanner.startPreview();
                CameraPermission = true;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERM) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                scanner.startPreview();
                CameraPermission = true;
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                    new AlertDialog.Builder(this)
                            .setTitle("Permission")
                            .setMessage("Please provide the camera permission for using all the features of the app")
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ActivityCompat.requestPermissions(ScannerBarCodeActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    }).create().show();

                } else {

                    new AlertDialog.Builder(this)
                            .setTitle("الصلاحية")
                            .setMessage("لقد رفضت الصلاحية. السماح بجميع الأذونات في [الإعدادات]> [الأذونات]")
                            .setPositiveButton("الاعدادات", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();


                                }
                            }).setNegativeButton("No, Exit app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CameraPermission) {
            scanner.releaseResources();
        }
    }
}