package edu.cmu.officient.ui.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.cmu.officient.R;
import edu.cmu.officient.ui.MainActivity;
import edu.cmu.officient.ui.courses.CoursesList;

public class SuccessfulRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);

        Button start = findViewById(R.id.start_application);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulRegistration.this, CoursesList.class);
                startActivity(intent);
            }
        });
    }
}
