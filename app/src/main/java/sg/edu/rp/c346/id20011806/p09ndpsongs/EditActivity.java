package sg.edu.rp.c346.id20011806.p09ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText etSongID, etSongTitle, etSinger, etYears;
    RadioButton radio1,radio2,radio3,radio4,radio5;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etSongID = findViewById(R.id.etSongID);
        etSongTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSinger);
        etYears = findViewById(R.id.etYears);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        radio1 = findViewById(R.id.radioButton1e);
        radio2 = findViewById(R.id.radioButton2e);
        radio3 = findViewById(R.id.radioButton3e);
        radio4 = findViewById(R.id.radioButton4e);
        radio5 = findViewById(R.id.radioButton5e);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

       etSongID.setText(data.getId()+"");
        etSongTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYears.setText(data.getYear() +"");
       if (data.getStars() == 1) {
            radio1.setChecked(true);
        } else if (data.getStars() == 2) {
            radio2.setChecked(true);
        }else if (data.getStars() == 3) {
            radio3.setChecked(true);
        }else if (data.getStars() == 4) {
            radio4.setChecked(true);
        }else if (data.getStars() == 5) {
            radio5.setChecked(true);
        }

       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DBHelper dbh = new DBHelper(EditActivity.this);
               data.setSingers(etSinger.getText().toString());
               data.setTitle(etSongTitle.getText().toString());
               data.setYear(Integer.valueOf(etYears.getText().toString()));
               if (radio1.isChecked()) {
                   data.setStars(1);
               } else if (radio2.isChecked()) {
                   data.setStars(2);
               } else if (radio3.isChecked()) {
                   data.setStars(3);
               } else if (radio4.isChecked()) {
                   data.setStars(4);
               } else if (radio5.isChecked()) {
                   data.setStars(5);
               }
               dbh.updateSong(data);
               Toast.makeText(EditActivity.this, "Update successful",
                       Toast.LENGTH_SHORT).show();
               finish();
           }
       });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.getId());
                Toast.makeText(EditActivity.this, "Delete successful",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}