package com.rachcode.peykman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.config.General;
import com.rachcode.peykman.home.MainActivity;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.user.UserDataResponseJson;
import com.rachcode.peykman.signUp.SignUpActivity;
import com.rachcode.peykman.signUp.VerificationActivity;
import com.rachcode.peykman.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentWallet extends Fragment {


    // editText price
    @BindView(R.id.editText3)
    EditText edtTextPrice;

    // button price custom
    @BindView(R.id.btnWallet10)
    TextView btnWallet10Toman;
    // button price custom
    @BindView(R.id.txtBalance)
    TextView txtBalance;
    @BindView(R.id.btnWallet20)
    TextView btnWallet20Toman;
    @BindView(R.id.btnWallet50)
    TextView btnWallet50Toman;


     @BindView(R.id.button5)
     Button button_p;
    @BindView(R.id.imageView18)
    ImageView increase;
    // btn price --
    @BindView(R.id.imageView10)
    ImageView reduce;
    UserData userLogin;
        private Boolean isFirst = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
         updateBalance();

        selectorBtnWallet10Toman();


        btnWallet10Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet10Toman();
            }
        });

        btnWallet20Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet20Toman();
            }
        });

        btnWallet50Toman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorBtnWallet50Toman();
            }
        });
            button_p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
        isFirst=true;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://peykman.com/utaxi/api.php/UserInventory/request?price="+edtTextPrice.getText().toString()+"&phone="+userLogin.getPhone()));
                    startActivity(browserIntent);
                }
            });
        // price ++
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int price = 5000 + Integer.parseInt(edtTextPrice.getText().toString());
                edtTextPrice.setText(String.valueOf(price));
            }
        });



        // price --
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PriceX = Integer.parseInt(edtTextPrice.getText().toString());
                if (PriceX != 0 && PriceX > 5000) {
                    int price = Integer.parseInt(edtTextPrice.getText().toString()) - 5000;

                    edtTextPrice.getText().clear();
                    edtTextPrice.setText(String.valueOf(price));
                }
            }
        });
    }
 private void updateBalance(){
     userLogin = GoTaxiApplication.getInstance(getContext()).getLoginUserD();
     txtBalance.setText(formatMony(userLogin.getBalance()));
 }


    private void selectorBtnWallet10Toman(){
        btnWallet10Toman.setSelected(true);
        btnWallet20Toman.setSelected(false);
        btnWallet50Toman.setSelected(false);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("10000");
    }

    private void selectorBtnWallet20Toman(){
        btnWallet10Toman.setSelected(false);
        btnWallet20Toman.setSelected(true);
        btnWallet50Toman.setSelected(false);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("20000");
    }

    private void selectorBtnWallet50Toman(){
        btnWallet10Toman.setSelected(false);
        btnWallet20Toman.setSelected(false);
        btnWallet50Toman.setSelected(true);

        edtTextPrice.getText().clear();
        edtTextPrice.setText("50000");
    }

    @Override
    public void onResume() {
        super.onResume();


        updateBalance();

    }


    public String formatMony(String price) {
        String formattedText = price + " " + General.MONEY;

        return formattedText;
    }
    public static void saveUser(Context context, UserData user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(UserData.class);
        realm.copyToRealm(user);
        realm.commitTransaction();

        GoTaxiApplication.getInstance(context).setLoginUserD(user);
    }
}
