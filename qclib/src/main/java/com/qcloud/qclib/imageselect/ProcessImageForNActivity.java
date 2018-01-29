package com.qcloud.qclib.imageselect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.qcloud.qclib.R;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 类说明：图片处理界面   兼容Android N（7.0）
 * Author: iceBerg
 * Date: 2017/12/18 14:58.
 */
public class ProcessImageForNActivity extends AppCompatActivity {
    private final int TAKE_PHOTO = 110; //取图片
    private static final int CROP_PHOTO = 111;//剪切照片
    private Uri imageUri;       //图片Uri
    public static File tempFile; //图片文件
    private int CODE_FOR_CAMERA_PERMISSION = 0x00000235;
    private int CODE_FOR_WRITE_PERMISSION = 0x00001234; // 读取外置sd卡权限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_img2);
        openCamera(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                } else {
                    finish();
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {

                        if (tempFile != null) {
                            Intent intent = new Intent();
                            ArrayList<String> images = new ArrayList<>();
                            images.add(tempFile.getPath());
                            intent.putStringArrayListExtra(ImageSelectUtil.SELECT_RESULT, images);
                            setResult(RESULT_OK, intent);
                        } else {
                            ToastUtils.ToastMessage(this, getResources().getString(R.string.toast_file_error));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        finish();
                    }
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * 打开相机
     *
     * @param activity
     */
    public void openCamera(Activity activity) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //获取系统版本
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            // 激动相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 判断存储卡是否可以用，可用进行存储
            if (hasSdcard()) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        filename + ".jpg");
                if (currentapiVersion < 24) {
                    // 从文件中创建uri
                    imageUri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    //兼容android7.0 使用共享文件的形式
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                    //检查是否有存储权限，以免崩溃
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ToastUtils.ToastMessage(this, getResources().getString(R.string.toast_permission_tip));
                        return;
                    }
                    imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                }
            }
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            activity.startActivityForResult(intent, TAKE_PHOTO);
        } else {
            ActivityCompat.requestPermissions(ProcessImageForNActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_FOR_CAMERA_PERMISSION);
        }
    }

    /**
     * 跳转页面，这些传值没用，之后可能用得上，所以保留了
     *
     * @param context
     * @param requestCode
     * @param type
     * @param isCut
     * @param cutType
     * @param width
     * @param height
     */
    public static void openThisActivity(Activity context, int requestCode, int type, boolean isCut, int cutType, int width, int height) {
        Intent intent = new Intent(context, ProcessImageForNActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isCut", isCut);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("cutType", cutType);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_FOR_CAMERA_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用CAMERA
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PHOTO);
            } else {
                //用户不同意，向用户展示该权限作用
                finish();
            }
        } else if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(this);
            } else {
                ToastUtils.ToastMessage(this, "获取图片失败");
                finish();
            }
        }
    }
}
