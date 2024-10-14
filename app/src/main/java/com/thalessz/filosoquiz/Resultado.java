package com.thalessz.filosoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resultado extends AppCompatActivity {
    private TextView resultTextView;
    private ImageView imgResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultTextView = findViewById(R.id.txtExplicacao);
        imgResultado = findViewById(R.id.imgResultado);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        String userName = getIntent().getStringExtra("USER_NAME");
        int empiristScore = getIntent().getIntExtra("EMPIRIST_SCORE", 0);
        int rationalistScore = getIntent().getIntExtra("RATIONALIST_SCORE", 0);

        String resultMessage;

        // Verifica os resultados e define a mensagem e a imagem
       if (empiristScore > rationalistScore) {
            resultMessage = getString(R.string.resultado_empirista);
            imgResultado.setImageResource(R.drawable.hume); // Imagem do David Hume
        } else if (rationalistScore > empiristScore) {
            resultMessage = getString(R.string.resultado_racionalista);
            imgResultado.setImageResource(R.drawable.descartes); // Imagem do Descartes
        } else {
            resultMessage = getString(R.string.resultado_aristotelico);
            imgResultado.setImageResource(R.drawable.aristoteles); // Imagem para perfil misto, se necessário
        }

        // Exibe o resultado formatado
        resultTextView.setText(Html.fromHtml(resultMessage));

        // Configura o botão Voltar
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Resultado.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza a activity atual
        });
    }

}
