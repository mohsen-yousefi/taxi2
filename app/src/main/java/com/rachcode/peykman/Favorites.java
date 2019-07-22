package com.rachcode.peykman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.rachcode.peykman.api.ServiceGenerator;
import com.rachcode.peykman.api.service.UserService;
import com.rachcode.peykman.mSend.SendActivity;
import com.rachcode.peykman.model.FavoriteAddress;
import com.rachcode.peykman.model.UserData;
import com.rachcode.peykman.model.json.book.GetFavoriteAddressResponseJson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorites extends AppCompatActivity {

    @BindView(R.id.recyclerviewAddress)
    RecyclerView recyclerviewAddress;

    @BindView(R.id.editText2)
    EditText editText;

    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;

     private GoogleMap mMap;
     private boolean isMapReady = false;
    String address;
    SupportMapFragment mapFragment;
    LatLng latLngAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);

        // get favoriteAddress from server
        requestGetAddress();


        // set
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")){
                    setContentView(R.layout.favorite_map);


                    Intent intent = new Intent(Favorites.this,FavoriteMap.class);
                    intent.putExtra("favorite_title",editText.getText().toString());

                    startActivity(intent);

                }else{
                    Toast.makeText(Favorites.this, "متن وارد کن داداچ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void requestGetAddress() {
        UserData loginUser = GoTaxiApplication.getInstance(Favorites.this).getLoginUserD();
        UserService service = ServiceGenerator.createService(UserService.class);

         service.getFavoriteAddress(loginUser.getId()).enqueue(new Callback<GetFavoriteAddressResponseJson>() {
            @Override
            public void onResponse(Call<GetFavoriteAddressResponseJson> call, Response<GetFavoriteAddressResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        // list address favorite
                        List<FavoriteAddress> favoriteAddress = response.body().getData();

                        // set adapter recyclerview
                        recyclerviewAddress.setLayoutManager(new GridLayoutManager(Favorites.this, 2, LinearLayoutManager.VERTICAL, false));
                        recyclerviewAddress.setAdapter(new RecyclerViewAddressAdapter(favoriteAddress));

                     } else {
                        Toast.makeText(Favorites.this, "متاسفم،شما آدرس منتخب ندارید.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFavoriteAddressResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(Favorites.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }





    class RecyclerViewAddressAdapter extends RecyclerView.Adapter<RecyclerViewAddressViewHolder> {
        private List<FavoriteAddress> favoriteAddresses;

        public RecyclerViewAddressAdapter(List<FavoriteAddress> favoriteAddresses){
            this.favoriteAddresses = favoriteAddresses;
        }

        @Override
        public RecyclerViewAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Favorites.this).inflate(R.layout.item_address, parent, false);
            return new RecyclerViewAddressViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewAddressViewHolder holder, final int position) {
            holder.tv.setText(favoriteAddresses.get(position).getAddress());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Favorites.this, SendActivity.class);

               intent.putExtra("search_location",new LatLng(Double.parseDouble(favoriteAddresses.get(position).getLatitude()),Double.parseDouble(favoriteAddresses.get(position).getLongitude())));

           startActivity(intent);finish();
            }
       });
        }

        @Override
        public int getItemCount() {
            return favoriteAddresses.size();
        }
    }

    class RecyclerViewAddressViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public RecyclerViewAddressViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}
