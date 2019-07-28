package com.rachcode.peykman.signUp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rachcode.peykman.GoTaxiApplication;
import com.rachcode.peykman.R;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.model.FirebaseToken;
import com.rachcode.peykman.model.Status;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.user.UserDataResponseJson;
import com.rachcode.peykman.signIn.SignInActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mukesh.OtpView;
import com.rachcode.peykman.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rachcode.peykman.signUp.SignUpActivity.NomorHP;


public class VerificationActivity extends AppCompatActivity implements OnClickListener, Validator.ValidationListener {

    public static final int SIGNUP_ID = 110;
    //Variable Untuk Komponen-komponen Yang Diperlukan
    //private EditText SetCode;
    //private Button login;
    //AppCompatButton verifi;
    //TextView Resend;
    Validator validator;

    // Button test;


    //Variables Needed for Authentication
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verifiID;
    private String No_Telepon;

    private TimerClass timerClass;


    /********** find view **********/
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.parent_phone)
    ConstraintLayout parent_phone;

    @BindView(R.id.parent_code)
    ConstraintLayout parent_code;

    @BindView(R.id.btn_send_phone)
    Button btn_send_phone;

    @BindView(R.id.btn_send_code)
    Button btn_send_code;

    @BindView(R.id.btn_empty_phone)
    Button btn_empty_phone;

    @BindView(R.id.btn_empty_code)
    Button btn_empty_code;

    @NotEmpty
    @BindView(R.id.phone)
    OtpView edt_phone;

    @BindView(R.id.code)
    OtpView edt_code;

    @BindView(R.id.timer)
    TextView timer;

    @BindView(R.id.btn_change_phone)
    Button btn_change_phone;

    private SmsVerifyCatcher smsVerifyCatcher;
    String phone;
    String code;
    FirebaseToken token;


    /*********** OnCreate ************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        // using smsVerifyCatcher library for auto reading verify code from messeges
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                if(parent_code.getVisibility() == View.VISIBLE) {
                    String code = parseCode(message);//Parse verification code
                    edt_code.setText(code);//set code in edit text
                    //then you can send verification code to server
                    onActivationCheck();
                }

            }
        });



        validator = new Validator(this);
        validator.setValidationListener(this);


        showPhone();
        // if view phone showing
        btn_empty_phone.setVisibility(View.VISIBLE);
        btn_send_phone.setVisibility(View.INVISIBLE);
        btn_send_phone.setOnClickListener(this);
        btn_empty_phone.setOnClickListener(this);

        // if view code showing
        btn_empty_code.setVisibility(View.VISIBLE);
        btn_send_code.setVisibility(View.INVISIBLE);
        btn_send_code.setOnClickListener(this);
        btn_empty_code.setOnClickListener(this);
        btn_change_phone.setOnClickListener(this);


        /*********** Change Phone ***************/
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* if edt_phone ==11 show ButtonSend else show ButtonEmpty */
                if (charSequence.length() == 11) {
                    phone = charSequence.toString();
                    btn_empty_phone.setVisibility(View.INVISIBLE);
                    btn_send_phone.setVisibility(View.VISIBLE);
                } else {
                    btn_empty_phone.setVisibility(View.VISIBLE);
                    btn_send_phone.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /*********** Change Code ***************/
        edt_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* if edt_phone ==11 show ButtonSend else show ButtonEmpty */
                if (charSequence.length() == 4) {
                    title.setText("خوش آمدید!");
                    code = charSequence.toString();
                    btn_empty_code.setVisibility(View.INVISIBLE);
                    btn_send_code.setVisibility(View.VISIBLE);
                } else {
                    title.setText("تقریبا تمومه!");
                    btn_empty_code.setVisibility(View.VISIBLE);
                    btn_send_code.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /////////////////////////////

        //timer = (TextView) findViewById(R.id.timer);
        //login = (Button) findViewById(R.id.login);
        //login.setOnClickListener(this);
        //verifi = (AppCompatButton) findViewById(R.id.verifi);
        //verifi.setOnClickListener(this);
        //Resend = (TextView) findViewById(R.id.resend);
        //Resend.setOnClickListener(this);
        //Resend.setEnabled(false);


        timerClass = new TimerClass(60000 * 1, 1000);
        showPhone();
    }

    private void send() {
        Intent intent = new Intent(VerificationActivity.this, ListCountryActivity.class);
        startActivityForResult(intent, ListCountryActivity.LIST_COUNTRY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ListCountryActivity.LIST_COUNTRY) {
            if (resultCode == Activity.RESULT_OK) {
                String code = data.getStringExtra("key");
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onSendInClick() {
        onActivationAdd();
        //Resend.setEnabled(true);
        timerClass.start();
    }

    private String parseCode(String messges) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(messges);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
    /*******************  send code *******************/
    private void onActivationAdd() {
        UserService userService = ServiceGenerator.createService(UserService.class);
        String phone_number = edt_phone.getText().toString();
        userService.activationAdd(phone_number).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if (status.getStatus().equals("success")) {
                    Toast.makeText(getApplicationContext(), "ارسال شد", Toast.LENGTH_SHORT).show();
                    showCode();
                } else {
                    Toast.makeText(getApplicationContext(), "خطا", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "خطای اینترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*******************  check code *******************/
    private void onActivationCheck() {

        UserService userService = ServiceGenerator.createService(UserService.class);
        userService.activationCheck(edt_phone.getText().toString(), edt_code.getText().toString(), FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<UserDataResponseJson>() {
            @Override
            public void onResponse(Call<UserDataResponseJson> call, Response<UserDataResponseJson> response) {
                UserDataResponseJson responseUser = response.body();
                if (responseUser.getStatus().equals("user_not_exist")) {

                    Intent intent = new Intent(VerificationActivity.this, SignUpActivity.class);
                    intent.putExtra("phone", edt_phone.getText().toString());
                    startActivity(intent);
                    finish();
                } else if (responseUser.getStatus().equals("user_exist")) {
                    //SaveUser(responseUser.getData().get(0));
                    UserData user = response.body().getData().get(0);
                    if (response.body().getData().get(0).getProfilepicture() == null){
                    }else{




                        Picasso.with(VerificationActivity.this).load(user.getProfilepicture()).into(target);




                    }

                    Utils.saveUser(VerificationActivity.this,user);

                    Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (responseUser.getStatus().equals("active_codes_dont_match")) {
                    Toast.makeText(getApplicationContext(), "کد اشتباه است.", Toast.LENGTH_SHORT).show();

                } else if (responseUser.getStatus().equals("BANNED")) {
                    Toast.makeText(getApplicationContext(), "مشکلی برای اکانت شما پیش آمده لطفا با پشتیبانی تماس حاصل فرمائید.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserDataResponseJson> call, Throwable t) {

            }
        });
    }

    //Make InnerClass for Countdown Time configuration
    public class TimerClass extends CountDownTimer {

        TimerClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //This method runs when the time / timer changes
        @Override
        public void onTick(long millisUntilFinished) {
            //Time Format configuration used
            String test = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

            //Display it on TexView
            timer.setText("(" + test + ")");
        }

        @Override
        public void onFinish() {
            //Resend.setVisibility(View.VISIBLE);
            timer.setVisibility(View.GONE);
            btn_send_phone.setClickable(true);

            ///Walk when the time has finished or stop
            showPhone();
        }
    }

    @Override
    public void onValidationSucceeded() {
        onSendInClick();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    /*private void show() {
        timer.setVisibility(View.VISIBLE);
        pleaseInputCode.setVisibility(View.VISIBLE);
        SetCode.setVisibility(View.VISIBLE);
        verifi.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
         NoTelepon.setVisibility(View.GONE);
    }

    private void hidden() {
        timer.setVisibility(View.GONE);
        pleaseInputCode.setVisibility(View.GONE);
        SetCode.setVisibility(View.GONE);
        verifi.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
         NoTelepon.setVisibility(View.VISIBLE);

    }*/

    /**************************************************************************************************************************/


    /************* Show Views ****************/
    public void showPhone() {
        title.setText("خوش آمدید!");
        parent_phone.setVisibility(View.VISIBLE);
        parent_code.setVisibility(View.GONE);
        edt_phone.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt_phone, InputMethodManager.SHOW_IMPLICIT);
    }

    public void showCode() {
        title.setText("تقریبا تمومه!");
        parent_code.setVisibility(View.VISIBLE);
        parent_phone.setVisibility(View.GONE);
    }


    /************* Clear Code ****************/
    private void clearCode() {
        edt_code.setText("");
        code = "";
    }

    /************* Click View ****************/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_phone:
                btn_send_phone.setClickable(false);
                validator.validate();
                break;

            case R.id.btn_empty_phone:
                Toast.makeText(this, "شماره کامل نیست", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_send_code:
                onActivationCheck();
                break;

            case R.id.btn_empty_code:
                Toast.makeText(this, "کد کامل نیست", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_change_phone:
                showPhone();
                clearCode();
                btn_send_phone.setClickable(true);
                break;
        }
    }
    public Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Bitmap circleBitmap = bitmap;
            saveToInternalStorage(circleBitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }  ;

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("photoDriver", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }




    /******* Back *********/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
