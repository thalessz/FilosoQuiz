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

    private void iniciarQuestoes() {
        perguntas.add(new Pergunta(
                "1. Imagine que você acabou de brigar com seus pais e agora está se perguntando sobre a real razão da briga. Ao tentar conversar com eles, você é tomado por uma raiva descontrolada. Neste contexto, como você lidaria com a situação?",
                new String[]{"A) Tentaria, com base em outros ataques de raiva na sua vida, buscar uma melhor forma de pensar e agir.",
                        "B) Sentiria tal raiva e expressaria para seus pais, mas procuraria entender o motivo por trás dela.",
                        "C) Buscaria o real motivo para aquela raiva inexplicável, analisando os eventos passados."},
                2, 1, 0));

        perguntas.add(new Pergunta(
                "2. Quando pensamos em conceitos como 'justiça' ou 'moralidade', como você acredita que eles se desenvolvem?",
                new String[]{"A) Estes conceitos são moldados de acordo com acontecimentos anteriores e experiências vividas.",
                        "B) Estes conceitos são universais e inatos, parte da nossa natureza humana.",
                        "C) Estes conceitos vêm de nossos ideais criados com lógica e interação com o mundo."},
                0, 1, 2));

        perguntas.add(new Pergunta(
                "3. Suponha que você precise mudar sua opinião sobre um tema importante. O que normalmente te convence a fazer essa mudança?",
                new String[]{"A) Evidências concretas e a oportunidade de experimentar algo novo.",
                        "B) Uma boa argumentação lógica e clara, que explique os pontos-chave do tema.",
                        "C) Exemplos práticos e novas informações que mostrem diferentes perspectivas."},
                2, 1, 0));

        perguntas.add(new Pergunta(
                "4. Quando você se encontra em um impasse emocional, como você geralmente lida com a situação?",
                new String[]{"A) Me concentro em como as emoções estão se manifestando, e busco entender-las antes de tirar uma conclusão.",
                        "B) Oscilo entre o sentir e refletir, tentando equilibrar o que estou vivendo com o que faz sentido para mim.",
                        "C) Tento organizar meus pensamentos e entender a causa por trás dos sentimentos, buscando uma solução lógica."},
                2, 1, 0));

        perguntas.add(new Pergunta(
                "5. Em relação à sua aprendizagem, qual abordagem você prefere adotar?",
                new String[]{"A) Prefere usar da lógica e raciocínio em primeiro lugar, antes de agir.",
                        "B) Tenta equilibrar experiência e razão, usando ambos os aspectos para aprender.",
                        "C) Prefere pôr a mão na massa e fazer experimentos para entender melhor."},
                0, 2, 1));

        perguntas.add(new Pergunta(
                "6. Quando alguém pede sua opinião sobre um assunto, como você geralmente responde?",
                new String[]{"A) Busco me basear em coisas que vivenciei e o que extraí delas, mas também considero novas informações.",
                        "B) Tento ajudar equilibrando experiências passadas e o que penso ser melhor hoje, considerando várias perspectivas.",
                        "C) Tento pensar de forma racional, evitando me prender a experiências passadas e focando no presente."},
                2, 1, 0));

        perguntas.add(new Pergunta(
                "7. Ao planejar algo importante, como você decide o caminho a seguir?",
                new String[]{"A) Gosto de começar a agir e ir ajustando meu plano durante o processo de acordo com os resultados do caminho.",
                        "B) Tendo a avaliar todas as opções, considerando os prós e contras de cada uma delas.",
                        "C) Tento criar uma boa estratégia deixando espaços para modificações durante o processo."},
                2, 0, 1));

        perguntas.add(new Pergunta(
                "8. Ao descobrir uma nova tecnologia, como você tenta aprender a usá-la?",
                new String[]{"A) Experimento ela diretamente e vou tentando descobrir como funciona durante o uso.",
                        "B) Costumo tentar entender o princípio de seu funcionamento antes de começar a usá-la, lendo documentação e tutoriais.",
                        "C) Leio um pouco sobre ela, mas também vou testando pra ver como funciona na prática."},
                2, 0, 1));

        perguntas.add(new Pergunta(
                "9. Quando se trata de entender a natureza da realidade, como você acredita que devemos abordar a busca pelo conhecimento?",
                new String[]{"A) Usando a razão e a lógica para deduzir princípios fundamentais, independentemente da experiência direta.",
                        "B) Através da observação e experimentação, verificando hipóteses e teorias por meio de evidências empíricas.",
                        "C) Considerando uma combinação de experiências práticas e reflexão teórica, buscando um equilíbrio entre ambos."},
                0, 2, 1));

        perguntas.add(new Pergunta(
        "10. Como você costuma refletir sobre o fim de uma relação?",
                new String[]{"A) Acabo voltando em situações específicas que marcaram o relacionamento, revendo o que vivi e como isso afetou tudo.",
                        "B) Me pego refletindo sobre como certas escolhas e decisões influenciaram o desfecho, gosto de examinar as razões por trás de cada acontecimento.",
                        "C) Tendo a oscilar entre lembrar os momentos bons e ruins, enquanto tento encontrar um equilíbrio entre o que senti e o que realmente aconteceu."},
                1, 0, 2));

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
