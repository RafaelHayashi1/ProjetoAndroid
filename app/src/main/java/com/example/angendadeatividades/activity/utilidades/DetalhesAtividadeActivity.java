package com.example.angendadeatividades.activity.utilidades;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.angendadeatividades.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalhesAtividadeActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atividade);


        String uidAtividade = getIntent().getStringExtra("UID_ATIVIDADE");


        DatabaseReference atividadeRef = referencia.child("agenda_atividade").child(uidAtividade);


        TextView tituloTextView = findViewById(R.id.textViewTitulo);
        TextView dataTextView = findViewById(R.id.textViewData);
        TextView descricaoTextView = findViewById(R.id.textViewDescricao);
        TextView horaTextView = findViewById(R.id.textViewHora);
        TextView localizacaoTextView = findViewById(R.id.textViewLocalizacao);
        TextView participantesTextView = findViewById(R.id.textViewParticipantes);


        atividadeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Verificar se a atividade existe
                if (snapshot.exists()) {
                    // Obter os dados da atividade
                    String titulo = snapshot.child("titulo_atividade").getValue(String.class);
                    String data = snapshot.child("data_atividade").getValue(String.class);
                    String descricao = snapshot.child("descricao_atividade").getValue(String.class);
                    String hora = snapshot.child("hora_atividade").getValue(String.class);
                    String localizacao = snapshot.child("localizacao_atividade").getValue(String.class);
                    String participantes = snapshot.child("participantes_atividade").getValue(String.class);


                    tituloTextView.setText(titulo);
                    dataTextView.setText("Data da Atividade: " + data);
                    descricaoTextView.setText("Descrição: " + descricao);
                    horaTextView.setText("Hora: " + hora);
                    localizacaoTextView.setText("Localização: " + localizacao);
                    participantesTextView.setText("Participantes: " + participantes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    public void voltar(View view) {
        finish();
    }

}