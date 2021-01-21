package com.oktydeniz.zar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int[] imageArray = new int[]{R.drawable.dice, R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three, R.drawable.dice_four,
            R.drawable.dice_five, R.drawable.dice_six};

    ImageView imageView1, imageView2;
    TextView textView1, textView2, textView3;
    Button button1, button2;
    ListView listView;

    List<String> listString;
    ArrayAdapter<String> listAdapter;

    Random random;
    CountDownTimer countDownTimer;
    int toplam, can, sure, rnd1, rnd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewTanimlamalari();
    }

    public void Sure(int sure) {
        countDownTimer = new CountDownTimer(sure, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView3.setText(String.valueOf((millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                button1.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Süreniz Bitti", Toast.LENGTH_SHORT).show();
                button2.setEnabled(true);
            }
        };
    }

    private void ViewTanimlamalari() {
        imageView1 = findViewById(R.id.image_view_1);
        imageView2 = findViewById(R.id.image_view_2);
        textView1 = findViewById(R.id.text_view_1);
        textView2 = findViewById(R.id.text_view_2);
        textView3 = findViewById(R.id.text_view_3);
        listView = findViewById(R.id.list_view);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        Olustur();
    }

    private void Adapter(String str) {
        listString.add(str);
        listAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listString);
        listView.setAdapter(listAdapter);
    }

    private void Olustur() {
        sure = 20000;
        Sure(sure);
        countDownTimer.start();
        random = new Random();
        listString = new ArrayList<>();
        can = 3;
        toplam = 0;
        textView1.setText(String.valueOf(toplam));
        textView2.setText(String.valueOf(can));
        imageView1.setImageResource(imageArray[0]);
        imageView2.setImageResource(imageArray[0]);
        button1.setEnabled(true);
        button2.setEnabled(false);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                Basla();
                break;
            case R.id.button_2:
                Olustur();
                break;
        }
    }

    private void Basla() {
        rnd1 = random.nextInt(6) + 1;
        rnd2 = random.nextInt(6) + 1;
        imageView1.setImageResource(imageArray[rnd1]);
        imageView2.setImageResource(imageArray[rnd2]);
        toplam = rnd1 + rnd2;
        textView1.setText(String.valueOf(toplam));
        String durum = "İlk : " + rnd1 + "  İkinci : " + rnd2 + "  Toplam : " + toplam;
        Adapter(durum);
        Kontrol(toplam);
    }

    private void Kontrol(int toplam) {
        if (toplam == 7 || toplam == 11) {
            Toast.makeText(this, "Kazandınız !", Toast.LENGTH_SHORT).show();
            button1.setEnabled(false);
            button2.setEnabled(true);
            countDownTimer.cancel();
        } else if (toplam == 2 || toplam == 3 || toplam == 12) {
            can--;
            textView2.setText(String.valueOf(can));
            if (can <= 0) {
                button1.setEnabled(false);
                button2.setEnabled(true);
                Toast.makeText(this, "Oyun Bitti ! Kaybettin ", Toast.LENGTH_SHORT).show();
                countDownTimer.cancel();
            }
        }
    }
}