package com.thalessz.filosoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thalessz.filosoquiz.api.ApiService;
import com.thalessz.filosoquiz.api.RetrofitClient;
import com.thalessz.filosoquiz.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resultado extends AppCompatActivity {
    private TextView resultTextView;
    private TextView txtResultado;
    private ImageView imgResultado;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);

        // Adiciona listener para ajustar o padding de acordo com as barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os componentes da interface
        resultTextView = findViewById(R.id.txtExplicacao);
        txtResultado = findViewById(R.id.txtResultado);
        imgResultado = findViewById(R.id.imgResultado);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        Button btnRanking = findViewById(R.id.btnRanking);

        // Verifica se os componentes da interface foram encontrados
        if (resultTextView == null || txtResultado == null || imgResultado == null || btnVoltar == null || btnRanking == null) {
            Toast.makeText(this, "Erro ao encontrar componentes da interface", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Obtem os dados da intenção
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName == null) {
            userName = ""; // Valor padrão se não for fornecido
        }

        int empiristScore = getIntent().getIntExtra("EMPIRIST_SCORE", 0);
        int rationalistScore = getIntent().getIntExtra("RATIONALIST_SCORE", 0);

        String resultMessage;

        // Verifica os resultados e define a mensagem e a imagem
        if (empiristScore > rationalistScore) {
            txtResultado.setText("Empirista");
            resultMessage = getString(R.string.resultado_empirista);
            imgResultado.setImageResource(R.drawable.hume);
            score = empiristScore;
        } else if (rationalistScore > empiristScore) {
            txtResultado.setText("Racionalista");
            resultMessage = getString(R.string.resultado_racionalista);
            imgResultado.setImageResource(R.drawable.descartes);
            score = rationalistScore;
        } else {
            txtResultado.setText("Aristotélico...?");
            resultMessage = getString(R.string.resultado_aristotelico);
            imgResultado.setImageResource(R.drawable.aristoteles);
            score = 0;
        }

        // Define o texto da explicação
        if (resultTextView!= null) {
            resultTextView.setText(Html.fromHtml(resultMessage));
        }

        // Adiciona listener para o botão de voltar
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Resultado.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Cria o objeto de resultado
        if (userName!= null && txtResultado!= null) {
            Result resultado = new Result(userName, score, txtResultado.getText().toString().trim());
            btnRanking.setOnClickListener(v -> submit_resultado(resultado));
        } else {
            Toast.makeText(this, "Erro ao criar objeto de resultado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void submit_resultado(Result resultado) {
        ApiService apiService = RetrofitClient.getApiService();
        if (apiService!= null) {
            Call<Result> call = apiService.submit_resultado(resultado);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Resultado.this, "Você foi inserido no Ranking, Parabéns!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Resultado", "Erro na requisição: " + response.code());
                    }
                    Intent intent = new Intent(Resultado.this, Ranking.class);
                    intent.putExtra("username", resultado.getNome());
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("Resultado", "Falha na requisição: " + t.getMessage());
                }
            });
        } else {
            Log.e("Resultado", "ApiService instance is null");
        }
    }
}
