package com.thalessz.filosoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thalessz.filosoquiz.models.Pergunta;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {
    private TextView txtPergunta;
    private RadioGroup radioGroup;
    private Button btnProxima;

    private List<Pergunta> perguntas = new ArrayList<>();
    private int numPergunta = 0;
    private String nome;

    private int empiristaScore = 0;
    private int racionalistaScore = 0;
    private int neutro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtPergunta = findViewById(R.id.txtPergunta);
        radioGroup = findViewById(R.id.radioGroup);
        btnProxima = findViewById(R.id.btnProxima);

        iniciarQuestoes();
        getNome();
        mostrarPergunta();

        btnProxima.setOnClickListener(v->nextQuestion());
    }

    private void nextQuestion(){
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            showAlert("Por favor, selecione uma resposta antes de continuar.");
        } else {
            calculateScore();
            if(numPergunta < perguntas.size() - 1){
                numPergunta++;
                mostrarPergunta();
            } else {
                Intent intent = new Intent(Quiz.this, Resultado.class);
                intent.putExtra("USER_NAME", nome);
                intent.putExtra("EMPIRIST_SCORE", empiristaScore);
                intent.putExtra("RATIONALIST_SCORE", racionalistaScore);
                intent.putExtra("NEUTRAL_SCORE", neutro);
                startActivity(intent);
            }
        }
    }

    private void iniciarQuestoes(){
        perguntas.add(new Pergunta(
                "1. Tente se imaginar nesta situação: Você acabou de brigar com seus pais e está se perguntando a real razão da briga ter começado. Ao tentar conversar com eles, você é pego em uma raiva descontrolada. Neste contexto, você:",
                new String[]{"A) Sentiria tal raiva e expressaria para seus pais mesmo que de forma controlada",
                        "B) Buscaria o real motivo para aquela raiva inexplicável para que não se repetisse",
                        "C) Tentaria, com base em outros ataques de raiva na sua vida, buscar uma melhor forma de pensar e agir"},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "2. Como você explica o funcionamento de conceitos como 'justiça' ou 'moralidade'?",
                new String[]{"A) Estes conceitos estão dentro de nós",
                        "B) Estes conceitos vêm da nossa interação com o mundo",
                        "C) Estes conceitos são moldados de acordo com acontecimentos anteriores"},
                0,
                1,
                2));

        perguntas.add(new Pergunta(
                "3. Você acha que devemos aprender sobre o mundo:",
                new String[]{"A) Usando a lógica e raciocínio dedutivo",
                        "B) Através de reflexão e experiência prática",
                        "C) Experimentando e observando o mundo ao nosso redor"},
                0,
                1,
                2));

        perguntas.add(new Pergunta(
                "4. Como você acredita que o conhecimento se desenvolve ao longo da vida?",
                new String[]{"A) Através da lógica, reflexão e compreensão dos princípios fundamentais",
                        "B) Através de uma combinação de experiências e aprendizados teóricos",
                        "C) Através da vivência e contato com novas experiências"},
                0,
                1,
                2));

        perguntas.add(new Pergunta(
                "5. Em relação à sua aprendizagem, você:",
                new String[]{"A) Tenta equilibrar experiência e razão",
                        "B) Prefere pôr a mão na massa e fazer experimentos",
                        "C) Prefere usar a lógica e raciocínio em primeiro lugar"},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "6. Quando pedem minha opinião sobre algo eu:",
                new String[]{"A) Tento ajudar equilibrando experiências passadas e o que penso ser melhor hoje",
                        "B) Busco me basear em coisas que vivenciei e o que extraí delas",
                        "C) Tento pensar de forma racional, evitando me prender a experiências passadas"},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "7. Quando te pedem para fazer algo que você não faz há um tempo você:",
                new String[]{"A) Tenta fazer com base em experiências e resultados passados",
                        "B) Tenta usar a lógica para achar uma forma de fazer",
                        "C) Tenta usar tanto experiências passadas quanto a lógica para fazer o que foi pedido"},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "8. Como você considera que o conhecimento é adquirido?",
                new String[]{"A) O conhecimento é adquirido principalmente através das experiências vividas e da observação do mundo ao nosso redor.",
                        "B) O conhecimento pode ser adquirido tanto por meio da experiência quanto pela reflexão teórica; ambos têm seu papel.",
                        "C) O conhecimento é inato e deve ser acessado através da razão."},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "9. Qual a relação entre experiência e verdade no processo de conhecimento?",
                new String[]{"A) A verdade é estabelecida pela verificação empírica; somente aquilo que pode ser observado e testado é considerado verdadeiro.",
                        "B) A relação entre experiência e verdade é complexa; a experiência pode ajudar a validar teorias, mas não garante a verdade absoluta.",
                        "C) A verdade deve ser alcançada através da razão; pois as experiências podem ser enganosas."},
                2,
                1,
                0));

        perguntas.add(new Pergunta(
                "10. Um objeto é solto no ar. Você sabe que ele irá cair em direção ao chão por causa:",
                new String[]{"A) Da lógica da física onde a gravidade atrai os objetos para o chão",
                        "B) Da lógica e de experiências anteriores que você viu objetos caírem em direção ao chão",
                        "C) De experiências anteriores onde todos os objetos soltos eram atraídos para o chão"},
                0,
                1,
                2));
    }



    private void mostrarPergunta() {
        Pergunta currentQuestion = perguntas.get(numPergunta);

        txtPergunta.setText(currentQuestion.getTextoPergunta());

        radioGroup.clearCheck(); // Limpa a seleção anterior
        ((RadioButton) radioGroup.getChildAt(0)).setText(currentQuestion.getAlternativas()[0]);
        ((RadioButton) radioGroup.getChildAt(1)).setText(currentQuestion.getAlternativas()[1]);
        ((RadioButton) radioGroup.getChildAt(2)).setText(currentQuestion.getAlternativas()[2]);

        // Muda o texto do botão se for a última pergunta
        btnProxima.setText(numPergunta == perguntas.size() - 1 ? "Mostrar Resultados" : "Próxima Pergunta");
    }
    private void calculateScore() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.txtResposta1) {
            if (perguntas.get(numPergunta).getIndiceEmpirista() == 0) {
                empiristaScore += 10;
            } else if (perguntas.get(numPergunta).getIndiceRacionalista() == 0) {
                racionalistaScore += 10;
            } else {
                neutro +=10;
            }

        } else if (selectedId == R.id.txtResposta2) {
            if (perguntas.get(numPergunta).getIndiceEmpirista() == 1) {
                empiristaScore +=10;
            } else if (perguntas.get(numPergunta).getIndiceRacionalista() == 1) {
                racionalistaScore +=10;
            } else {
                neutro +=10;
            }

        } else if (selectedId == R.id.txtResposta3) {
            if (perguntas.get(numPergunta).getIndiceEmpirista() == 2) {
                empiristaScore +=10;
            } else if (perguntas.get(numPergunta).getIndiceRacionalista() ==2 ) {
                racionalistaScore +=10;
            } else {
                neutro +=10;
            }

        }

        radioGroup.clearCheck();
    }
    private void getNome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Para iniciarmos, insira seu nome:");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> nome = input.getText().toString());

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.cancel();
            Toast.makeText(Quiz.this, "Bobão, acha que vai passar sem nome?", Toast.LENGTH_SHORT).show();
            finish();
        });

        builder.show();
    }
    private void showAlert(String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
