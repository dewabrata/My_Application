package com.juara.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.juara.myapplication.model.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll ;
    Button btnDelete ;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.layout);






        Inventory inventory = new Inventory();

        inventory.setName("Buku Beranak dalam Kardus");
        inventory.setPrice("100000");
        inventory.setStock(10);
        inventory.save();

        inventory = new Inventory();
        inventory.setName("KKN di desa Ponari");
        inventory.setPrice("50000");
        inventory.setStock(5);
        inventory.save();


        printScreen();



    }


    public void printScreen(){

        ll.removeAllViews();
        btnUpdate = new Button(MainActivity.this);
        btnUpdate.setText("Update");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inventory data = new Select().from(Inventory.class).where("name = 'Buku Beranak dalam Kardus'").executeSingle();
                data.setPrice("99999999");
                data.save();
                printScreen();
            }
        });
        btnDelete = new Button(MainActivity.this);
        btnDelete.setText("Delete");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cara pertama
                /*
                Inventory inventory = Inventory.load(Inventory.class,1);
                inventory.delete();
                printScreen();

                 */

                //cara kedua

               new Delete().from(Inventory.class).where("name = 'KKN di desa Ponari'").execute();

               printScreen();

            }
        });
        ll.addView(btnDelete);
        ll.addView(btnUpdate);
        List<Inventory> data = new Select().from(Inventory.class).execute();

        for(int x  = 0 ; x < data.size();x ++) {
            TextView txtData = new TextView(MainActivity.this);
            txtData.setText(data.get(x).getName()+" "+ data.get(x).getPrice()+" "+ data.get(x).getStock());
            ll.addView(txtData);
        }
    }

    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }
}
