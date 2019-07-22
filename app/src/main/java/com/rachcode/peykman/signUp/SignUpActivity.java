package com.rachcode.peykman.signUp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.model.UserData;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import com.rachcode.peykman.R;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.model.FirebaseToken;
import com.rachcode.peykman.model.json.user.RegisterRequestJson;
import com.rachcode.peykman.model.json.user.RegisterResponseJson;
import com.rachcode.peykman.utils.DialogActivity;
import com.rachcode.peykman.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Androgo on 10/12/2018.
 */

public class SignUpActivity extends DialogActivity implements Validator.ValidationListener {

    public static final int SIGNUP_ID = 110;
    public static final String USER_KEY = "UserKey";
    private static final String TAG = "SignUpActivity";

    public static final String NomorHP = "no_hp";
    @NotEmpty
    @BindView(R.id.signUp_firstName)
    EditText textFirstName;

    @NotEmpty
    @BindView(R.id.signUp_lastName)
    EditText textLastName;


    @BindView(R.id.signUp_signUpButton)
    Button buttonSignUp;

    private String nohp;
    FirebaseToken token;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private Validator validator;

    private void updateLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
//        textDateOfBirth.setText(sdf.format(calendar.getTime()));
    }

    private void showDatePicker() {
        new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        if (General.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        Intent getDataOrder = getIntent();
        nohp = getDataOrder.getStringExtra(NomorHP);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

    }

    private void onSignUpClick() {
        String firstName = textFirstName.getText().toString();
        String lastName = textLastName.getText().toString();
        String phone = getIntent().getStringExtra("phone");

        showProgressDialog(R.string.dialog_loading);
        UserData request = new UserData();
        //request.setFirstName(textFirstName.getText().toString());
        //request.setLastName(textLastName.getText().toString());
        //request.setPhone(nohp);

        /*request.setEmail(textEmail.getText().toString());
        request.setPassword(textPassword.getText().toString());

        request.setAlamat(textAddress.getText().toString());*/


//        request.setTempatLahir(textPlaceOfBirth.getText().toString());
//        request.setTglLahir(textDateOfBirth.getText().toString());

        Realm realm = Realm.getDefaultInstance();
        token = realm.where(FirebaseToken.class).findFirst();
        String reg_id = null;
        if (token != null) {
               reg_id = String.valueOf(token.getTokenId());
         }

        /*test*/
        Log.i(TAG, "param021: "+"firstName:"+firstName+";"+"lastName:"+lastName+";"+"reg_id:"+reg_id+";"+"phone:"+phone+";");
        UserService service = ServiceGenerator.createService(UserService.class, request.getEmail(), request.getPassword());
        service.register(firstName,lastName,reg_id,phone).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(Call<RegisterResponseJson> call, Response<RegisterResponseJson> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        /*Intent retIntent = getIntent();
                        retIntent.putExtra(USER_KEY, response.body().getData().get(0));
                        //setResult(Activity.RESULT_OK, retIntent);
                        startActivity(retIntent);
                        finish();*/
                        UserData user = response.body().getData().get(0);
                        Utils.saveUser(SignUpActivity.this,user);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        System.exit(1);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Your account registration has failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "System error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseJson> call, Throwable t) {
                Log.e(TAG, "onFailure021: "+t.getMessage() );
                hideProgressDialog();
                t.printStackTrace();
                Toast.makeText(SignUpActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        onSignUpClick();
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

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(FirebaseToken.class);
        realm.copyToRealm(response);
        realm.commitTransaction();
    }
}