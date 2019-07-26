package com.rachcode.peykman;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.home.submenu.setting.UpdateProfileActivity;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.user.UpdateProfileRequestJson;
import com.rachcode.peykman.model.json.user.UpdateProfileResponseJson;
import com.rachcode.peykman.splash.SplashActivity;
import com.rachcode.peykman.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfile extends AppCompatActivity {
    @BindView(R.id.editText4)
    EditText firstName;
    @BindView(R.id.editText5)
    EditText lastName;
    @BindView(R.id.textView59)
    TextView exit;

    @BindView(R.id.imageView34)
    de.hdodenhof.circleimageview.CircleImageView profile_pic;
    @BindView(R.id.textView61)
    TextView saveProfile;
    @BindView(R.id.btn_logo)
    ImageView backimg;
    private Bitmap bitmap;
    private ProgressDialog dialog;
    private boolean imageSelected = false;
    private byte[] imageBytes0;
    private static int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        final UserData user = GoTaxiApplication.getInstance(this).getLoginUserD();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        loadImageFromStorage();


        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ActivityProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    profile_pic.setEnabled(false);
                    ActivityCompat.requestPermissions(ActivityProfile.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMG);
            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileRequestJson request = new UpdateProfileRequestJson();
                request.id = user.getId();
                request.first_name = firstName.getText().toString();
                request.last_name = lastName.getText().toString();
                request.phone = user.getPhone();
                if (imageSelected) {

                    String cop = compressJSON(bitmap);
                    request.profile_picture = cop;
                } else {
                    request.profile_picture = null;
                }

                if (request.profile_picture == null && firstName.getText().toString().equals(user.getFirstName()) && lastName.getText().toString().equals(user.getLastName())) {
                    Toast.makeText(ActivityProfile.this, "لطفا در اطلاعات خود تغیراتی ایجاد کنید", Toast.LENGTH_SHORT).show();
                return;
                }


                showProgressDialog("درحال بروز رسانی ...");
                UserService service = ServiceGenerator.createService(UserService.class, user.getEmail(), user.getPassword());
                service.updateProfile(request).enqueue(new Callback<UpdateProfileResponseJson>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResponseJson> call, Response<UpdateProfileResponseJson> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body().message.equals("success")) {
                                if (imageSelected) {
                                    saveToInternalStorage(bitmap);
                                }
                                Realm realm = GoTaxiApplication.getInstance(ActivityProfile.this).getRealmInstance();
                                realm.beginTransaction();
                                GoTaxiApplication.getInstance(ActivityProfile.this).getLoginUserD().setFirstName(firstName.getText().toString());
                                GoTaxiApplication.getInstance(ActivityProfile.this).getLoginUserD().setLastName(lastName.getText().toString());
                                realm.commitTransaction();
                                Toast.makeText(ActivityProfile.this, "ok", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ActivityProfile.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfileResponseJson> call, Throwable t) {
                        hideProgressDialog();
                        t.printStackTrace();
                        Toast.makeText(ActivityProfile.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Realm realm = GoTaxiApplication.getInstance(getApplication()).getRealmInstance();
                realm.beginTransaction();
                realm.delete(UserData.class);
                realm.commitTransaction();
                GoTaxiApplication.getInstance(getApplication()).setLoginUserD(null);
                startActivity(new Intent(getApplication(), SplashActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    public void showProgressDialog(String message) {
        dialog = ProgressDialog.show(this, "", message, false, false);
    }

    public String compressJSON(Bitmap bmp) {
        ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos0);
        imageBytes0 = baos0.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
        return encodedImage;
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);

            bitmap = decodeFile(imgDecodableString, 200);
            profile_pic.setImageBitmap(bitmap);
            imageSelected = true;

            Toast.makeText(this, "تصویر انتخاب شد!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveToInternalStorage(Bitmap bitmapImage) {

        deleteImageFromStorage();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("photoDriver", Context.MODE_PRIVATE);
        String nameFoto = "profile.jpg";
        File mypath = new File(directory, nameFoto);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Bitmap decodeFile(final String path, final int thumbnailSize) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, o);
        if ((o.outWidth == -1) || (o.outHeight == -1)) {
            bitmap = null;
        }

        int originalSize = (o.outHeight > o.outWidth) ? o.outHeight
                : o.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }

    private void showPopupHold(String message) {
        final AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
        popupBuilder.setCancelable(false);
        View viewPopup = getLayoutInflater().inflate(R.layout.popup, null);

        TextView title = viewPopup.findViewById(R.id.textView48);
        title.setText(message);

        popupBuilder.setView(viewPopup);


        final AlertDialog popup = popupBuilder.create();
        popup.show();
        TextView ok = viewPopup.findViewById(R.id.txt_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });


    }

    private void loadImageFromStorage() {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("photoDriver", Context.MODE_PRIVATE);
        File f = new File(directory, "profile.jpg");
        Bitmap tryDec = decodeFile(f.toString(), 200);
        profile_pic.setImageBitmap(tryDec);

    }

    private boolean deleteImageFromStorage() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("photoDriver", Context.MODE_PRIVATE);
        File f = new File(directory, "profile.jpg");
        boolean del = f.delete();
//        Log.d("isDel", String.valueOf(del));
        return del;
    }
}
