package com.salesianostriana.jldominguez.moveright;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    TextView tvDetailTitle, tvDetailPrice, tvDetailLoc, tvDetailRooms, tvDetailSize, tvDetailDesc;
    ImageButton ibDetailFab;
    ImageView ivDetailPhotos;
    Button bDelete, bEdit;
    private String propertyId;
    private PropertyViewModel viewModel;
    private Property property;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyId = getIntent().getStringExtra("id");
        if(!getIntent().getStringExtra("flag").isEmpty()){
            flag = getIntent().getStringExtra("flag");
        }

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);
        tvDetailLoc = findViewById(R.id.tvDetailLoc);
        tvDetailRooms = findViewById(R.id.tvDetailRooms);
        tvDetailSize = findViewById(R.id.tvDetailSize);
        ibDetailFab = findViewById(R.id.ibDetailFab);
        ivDetailPhotos = findViewById(R.id.ivDetailPhotos);
        tvDetailDesc = findViewById(R.id.tvDetailDesc);
        bDelete = findViewById(R.id.bDelete);
        bEdit = findViewById(R.id.bEdit);

        //PUEDE SER CAMBIADO A ADMIN SI NO SE QUIERE QUE LOS USUARIOS ELIMINEN Y EDITEN
        if(UtilToken.getUserRole(this) != "admin"){
            bEdit.setVisibility(View.GONE);
            bDelete.setVisibility(View.GONE);
        }

        launchViewModel();

        if(flag == "true"){
            ibDetailFab.setImageResource(R.drawable.ic_delete_forever_black_24dp);
        }

        ibDetailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == "true") {
                    showDialogDelete();
                } else if(UtilToken.getToken(PropertyDetailsActivity.this) != null) {
                    viewModel.setFavProperty(UtilToken.getToken(PropertyDetailsActivity.this), propertyId);
                    Toast.makeText(PropertyDetailsActivity.this, "Property added to favourites", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PropertyDetailsActivity.this, "You are not logged, please log in", Toast.LENGTH_LONG).show();
                }
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete();
            }
        });

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void launchViewModel(){
        viewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        viewModel.getPropertyDetailsFromCall(propertyId);
        viewModel.getPropertyDetails().observe(this, new Observer<Property>() {
            @Override
            public void onChanged(@Nullable Property newProperty) {
                setNewPropertyDetails(newProperty);

            }
        });

    }

    public void setNewPropertyDetails(Property newPropertyDetails){
        this.property = newPropertyDetails;
        tvDetailTitle.setText(property.getTitle());
        tvDetailDesc.setText(property.getDescription());
        tvDetailPrice.setText(Integer.toString(property.getPrice()));
        tvDetailLoc.setText(property.getAddress() + ", " +
                property.getZipcode()+ ", " +
                property.getCity() + ", " +
                property.getProvince());
        tvDetailRooms.setText(Integer.toString(property.getRooms()));
        tvDetailSize.setText(Integer.toString(property.getSize()));

        if(property.getPhotos() != null)
        Glide.with(PropertyDetailsActivity.this).load(property.getPhotos()[0]).into(ivDetailPhotos);
    }

    private void deleteProperty() {
        viewModel.deleteProperty(UtilToken.getToken(this));
    }

    private void showDialogDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PropertyDetailsActivity.this);


        builder.setMessage("All data will be deleted permanently")
                .setTitle("Delete property?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteProperty();
                        finish();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

