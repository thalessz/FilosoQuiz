package com.thalessz.filosoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView txt1, txt2, txt3, txt4, txt5, txtTitulo;
    private Button btnRacionalismo, btnEmpirismo, btnQuiz;
    private ImageView imgFilosofo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Declaração dos botões e dos txts
        btnRacionalismo = findViewById(R.id.btnRacionalismo);
        btnEmpirismo = findViewById(R.id.btnEmpirismo);
        btnQuiz = findViewById(R.id.btnQuiz);
        txtTitulo = findViewById(R.id.txtTitulo);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        imgFilosofo = findViewById(R.id.imgFilosofo);
        txt1.setText("");
        loadRacionalismo();

        btnEmpirismo.setOnClickListener(v->loadEmpirismo());
        btnRacionalismo.setOnClickListener(v->loadRacionalismo());
        btnQuiz.setOnClickListener(v->loadQuiz());

    }
    private void loadRacionalismo(){
        txtTitulo.setText(getString(R.string.str_racionalismo0));
        txt1.setText(getString(R.string.str_racionalismo1));
        txt2.setText(getString(R.string.str_racionalismo2));
        txt3.setText(getString(R.string.str_racionalismo3));
        txt4.setText(getString(R.string.str_racionalismo4));
        txt5.setText(getString(R.string.str_racionalismo5));
        imgFilosofo.setImageResource(R.drawable.descartes);
    }

    private void loadEmpirismo(){
        txtTitulo.setText(getString(R.string.str_empirismo0));
        txt1.setText(getString(R.string.str_empirismo1));
        txt2.setText(getString(R.string.str_empirismo2));
        txt3.setText(getString(R.string.str_empirismo3));
        txt4.setText(getString(R.string.str_empirismo4));
        txt5.setText(getString(R.string.str_empirismo5));
        imgFilosofo.setImageResource(R.drawable.hume);
    }

    private void loadQuiz(){
        Intent intent = new Intent(MainActivity.this, Quiz.class);
        startActivity(intent);
    }

}