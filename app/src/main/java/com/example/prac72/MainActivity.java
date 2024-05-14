package com.example.prac72;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prac72.databinding.ActivityMainBinding;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.buttonDate.setOnClickListener(v -> {
            // Создание обработчика выбора даты
            DatePickerDialog.OnDateSetListener dateSetListener = new
                    DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month,
                                              int dayOfMonth) {
                            MainActivity.this.year = year;
                            MainActivity.this.month = month;
                            MainActivity.this.day = dayOfMonth;
                        }
                    };

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    dateSetListener,
                    year, // текущий год
                    month, // текущий месяц
                    day); // текущий день
            datePickerDialog.show();
        });

        binding.buttonTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int
                                hourOfDay, int minute) {
                            hour = hourOfDay;
                            MainActivity.this.minute = minute;
                        }
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        binding.buttonPlace.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = dialog.findViewById(R.id.editText);
                    latitude = editText.getText().toString();
                    editText = dialog.findViewById(R.id.editText2);
                    longitude = editText.getText().toString();
                    dialog.dismiss();
                }
            });
            dialog.show();
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Подтверждение");
            builder.setMessage("Проверьте введенные данные" +  "\n" +
                    "Дата: " + day + "." + month + "." + year + "\n" +
                    "Время: " + hour + ":" + minute + "\n" +
                    "Место: " + latitude + ", " + longitude);
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Рассчитываю натальную карту", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "А может все же хотите?", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        setContentView(binding.getRoot());
    }
}