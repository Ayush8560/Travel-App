package com.canadore.travelplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {

    private EditText cityName;
    private Button insertTrip;
    private DatabaseReference databaseReference;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Trips");

        cityName = findViewById(R.id.cityname);
        insertTrip = findViewById(R.id.inserttrip);

        insertTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTripData();
            }
        });

        imageButton = findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void insertTripData() {
        String city = cityName.getText().toString().trim();

        if (!city.isEmpty()) {
            // Generate a unique key for each trip
            String tripId = databaseReference.push().getKey();

            // Create a Trip object
            Trip trip = new Trip(tripId, city);

            // Insert the trip into Firebase under the "Trips" node
            if (tripId != null) {
                databaseReference.child(tripId).setValue(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FIREBASE_SUCCESS", "Trip inserted successfully");
                            Toast.makeText(InsertActivity.this, "Trip inserted successfully", Toast.LENGTH_SHORT).show();
                            cityName.setText("");
                            //onBackPressed();
                        } else {
                            Log.e("FIREBASE_ERROR", "Failed to insert trip", task.getException());
                            Toast.makeText(InsertActivity.this, "Failed to insert trip", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }
        } else {
            Toast.makeText(this, "Please enter a trip name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}