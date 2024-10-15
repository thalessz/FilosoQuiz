package com.thalessz.filosoquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.thalessz.filosoquiz.api.ApiService;
import com.thalessz.filosoquiz.api.RetrofitClient;
import com.thalessz.filosoquiz.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Ranking extends AppCompatActivity {

    private TableLayout tblResultados;
    private ApiService apiService;
    private Button btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnVoltar = findViewById(R.id.btn_retorno);
        btnVoltar.setOnClickListener(v->retornarMenu());


        tblResultados = findViewById(R.id.tblResultados);
        apiService = RetrofitClient.getApiService();

        // Obter o nome do usuário da intent
        String nome = getIntent().getStringExtra("username");

        // Fazer a requisição à API
        fetchResultados(nome);
    }

    private void retornarMenu() {
        Intent intent = new Intent(Ranking.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchResultados(String nome) {
        Call<List<Result>> call = apiService.fetchResultados(nome);
        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (response.isSuccessful()) {
                    List<Result> resultados = response.body();
                    if (resultados!= null) {
                        preencherTabela(resultados);
                    } else {
                        Log.d("fetchResultados", "Nenhum resultado encontrado.");
                    }
                } else {
                    // Tratar erro
                    Log.d("fetchResultados", "Erro na requisição: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                // Tratar falha
                Log.d("fetchResultados", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    private void preencherTabela(List<Result> resultados) {
        // Adicionar linhas com dados
        for (Result resultado : resultados) {
            TableRow row = new TableRow(Ranking.this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Definir larguras das colunas
            int weightNome = 4;
            int weightPontuacao = 2;
            int weightResultado = 3;

            TextView tvNomeRow = new TextView(Ranking.this);
            tvNomeRow.setText(resultado.getNome());
            tvNomeRow.setTextColor(ContextCompat.getColor(Ranking.this, R.color.white));
            tvNomeRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Aumentar o tamanho da fonte
            tvNomeRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weightNome));
            row.addView(tvNomeRow);

            TextView tvPontuacaoRow = new TextView(Ranking.this);
            tvPontuacaoRow.setText(String.valueOf(resultado.getPontuacao()));
            tvPontuacaoRow.setTextColor(ContextCompat.getColor(Ranking.this, R.color.white));
            tvPontuacaoRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Aumentar o tamanho da fonte
            tvPontuacaoRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weightPontuacao));
            row.addView(tvPontuacaoRow);

            TextView tvResultadoRow = new TextView(Ranking.this);
            tvResultadoRow.setText(String.valueOf(resultado.getResultado()));
            tvResultadoRow.setTextColor(ContextCompat.getColor(Ranking.this, R.color.white));
            tvResultadoRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Aumentar o tamanho da fonte
            tvResultadoRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weightResultado));
            row.addView(tvResultadoRow);

            tblResultados.addView(row);
            Log.d("preencherTabela", "Adicionado resultado: " + resultado.getNome() + ", " + resultado.getPontuacao() + ", " + resultado.getResultado());
        }
    }

}
