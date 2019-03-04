package com.salesianostriana.jldominguez.moveright;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.salesianostriana.jldominguez.moveright.dto.PropertyDTO;
import com.salesianostriana.jldominguez.moveright.interfaces.FavPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.interfaces.MyPropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.interfaces.PropertyInteractionListener;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PropertyInteractionListener, MyPropertyInteractionListener, FavPropertyInteractionListener {

    MenuItem navPropertiesManage, navFavManage, navMyProManage, navLoginManage, navSignupManage, navLogoutManage;
    TextView tvUserName, tvUserEmail;
    ImageView ivUserPict;

    Fragment fProperty;
    Fragment fMyProperty;
    Fragment fFavProperty;

    PropertyViewModel propertyViewModel;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment temp = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(temp instanceof PropertyFragment || temp instanceof FavPropertyFragment){
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                } else if (temp instanceof MyPropertyFragment) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                    builder.setView(inflater.inflate(R.layout.dialog_create_property, null))

                            .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //TODO RECUPERAR LAS COSAS DEL DIALOGO Y HACER UNA PETICION CON VIEWMODEL
                                    propertyViewModel = ViewModelProviders.of(MainActivity.this).get(PropertyViewModel.class);
                                    propertyViewModel.createProperty(UtilToken.getToken(MainActivity.this), new PropertyDTO());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create();

                }

            }


        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);

        tvUserEmail = hView.findViewById(R.id.tvEmail);
        tvUserName = hView.findViewById(R.id.tvUserName);
        ivUserPict = hView.findViewById(R.id.ivUserPicture);

        navPropertiesManage = navigationView.getMenu().findItem(R.id.nav_properties);
        navFavManage = navigationView.getMenu().findItem(R.id.nav_favproperties);
        navMyProManage = navigationView.getMenu().findItem(R.id.nav_myproperties);
        navLoginManage = navigationView.getMenu().findItem(R.id.nav_login);
        navSignupManage = navigationView.getMenu().findItem(R.id.nav_signup);
        navLogoutManage = navigationView.getMenu().findItem(R.id.nav_logout);

        if(UtilToken.getToken(this)==null){
            navPropertiesManage.setVisible(true);
            navFavManage.setVisible(false);
            navMyProManage.setVisible(false);
            navLoginManage.setVisible(true);
            navSignupManage.setVisible(true);
            navLogoutManage.setVisible(false);
        } else {
            navPropertiesManage.setVisible(true);
            navFavManage.setVisible(true);
            navMyProManage.setVisible(true);
            navLoginManage.setVisible(false);
            navSignupManage.setVisible(false);
            navLogoutManage.setVisible(true);
            if(UtilToken.getUsername(this)!=null && UtilToken.getUserEmail(this) != null && UtilToken.getPict(this) != null ) {

                String tempUsername = UtilToken.getUsername(this);
                String tempEmail = UtilToken.getUserEmail(this);
                String tempPic = UtilToken.getPict(this);

                tvUserName.setText(tempUsername);
                tvUserEmail.setText(tempEmail);
                Glide.with(this).load(tempPic).into(ivUserPict);
            }
        }




        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);

        fProperty = new PropertyFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fProperty, "properties")
                .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_properties) {
            fProperty = new PropertyFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fProperty, "properties")
                    .commit();
            fab.setImageResource(R.drawable.ic_map_black_24dp);
        } else if (id == R.id.nav_myproperties) {
            fMyProperty = new MyPropertyFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fMyProperty, "myProperties")
                    .commit();
            fab.setImageResource(R.drawable.ic_add_black_24dp);
        } else if (id == R.id.nav_favproperties) {
            fFavProperty = new FavPropertyFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fFavProperty, "myProperties")
                    .commit();
            fab.setImageResource(R.drawable.ic_map_black_24dp);
        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signup) {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_logout){
            SharedPreferences preferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //METODO DE PROPERTY
    @Override
    public void onClickFav(String propertyId) {
        if(UtilToken.getToken(this) != null){
            propertyViewModel.setFavProperty(UtilToken.getToken(MainActivity.this), propertyId);
            Toast.makeText(MainActivity.this, "Property added to favourites", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "You are not logged, please log in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClickView(String propertyId) {
        Intent intent = new Intent(MainActivity.this, PropertyDetailsActivity.class);
        intent.putExtra("id", propertyId);
        startActivity(intent);
    }

    //METODOS DE FAV PROPERTY
    @Override
    public void onClickDeleteFav(String id) {
        propertyViewModel.deleteFavProperty(UtilToken.getToken(MainActivity.this), id);
        Toast.makeText(MainActivity.this, "Property deleted of favourites", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickFavView(String id) {
        Intent intent = new Intent(MainActivity.this, PropertyDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    //METODOS DE MY PROPERTY
    @Override
    public void onClickMyView(String id) {
        Intent intent = new Intent(MainActivity.this, PropertyDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
