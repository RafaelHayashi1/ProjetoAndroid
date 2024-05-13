package com.example.angendadeatividades.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.angendadeatividades.R;
import com.example.angendadeatividades.activity.utilidades.DetalhesAtividadeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        DatabaseReference minhasAtividades = referencia.child("agenda_atividade");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        Query atividadesQuery = minhasAtividades.orderByChild("uid_usuario").equalTo(auth.getCurrentUser().getUid());

        atividadesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                linearLayout.removeAllViews();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uidAtividade = dataSnapshot.getKey(); // Obter o UID da atividade
                    String tituloAtividade = dataSnapshot.child("titulo_atividade").getValue(String.class);
                    String dataAtividade = dataSnapshot.child("data_atividade").getValue(String.class);
                    String descricaoAtividade = dataSnapshot.child("descricao_atividade").getValue(String.class);
                    String horaAtividade = dataSnapshot.child("hora_atividade").getValue(String.class);
                    String localizacaoAtividade = dataSnapshot.child("localizacao_atividade").getValue(String.class);
                    String participantesAtividade = dataSnapshot.child("participantes_atividade").getValue(String.class);
                    addCard(linearLayout, uidAtividade, tituloAtividade, dataAtividade, descricaoAtividade, horaAtividade, localizacaoAtividade, participantesAtividade);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Erro:" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCard(LinearLayout linearLayout, String uidAtividade, String tituloAtividade, String dataAtividade, String descricaoAtividade,
                         String horaAtividade, String localizacaoAtividade, String participantesAtividade) {

        CardView cardView = new CardView(HomeActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(16, 16, 16, 16); // Margens para o card
        cardView.setLayoutParams(layoutParams);


        LinearLayout cardContentLayout = new LinearLayout(HomeActivity.this);
        cardContentLayout.setOrientation(LinearLayout.VERTICAL);
        cardContentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cardContentLayout.setPadding(16, 16, 16, 16);


        TextView titleTextView = new TextView(HomeActivity.this);
        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        titleTextView.setText(tituloAtividade);
        titleTextView.setTextSize(18);
        titleTextView.setTypeface(null, Typeface.BOLD);
        cardContentLayout.addView(titleTextView);

        addInfoTextView(cardContentLayout, "Data da Atividade: ", dataAtividade);
        addInfoTextView(cardContentLayout, "Descrição da Atividade: ", descricaoAtividade);
        addInfoTextView(cardContentLayout, "Hora da Atividade: ", horaAtividade);
        addInfoTextView(cardContentLayout, "Localização da Atividade: ", localizacaoAtividade);
        addInfoTextView(cardContentLayout, "Participantes da Atividade: ", participantesAtividade);


        cardView.addView(cardContentLayout);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, DetalhesAtividadeActivity.class);
                // Passar o uidAtividade como um extra para a próxima atividade
                intent.putExtra("UID_ATIVIDADE", uidAtividade);
                // Iniciar a nova atividade
                startActivity(intent);
            }
        });

        // Adiciona o CardView ao LinearLayout principal
        linearLayout.addView(cardView);
    }



    private void addInfoTextView(LinearLayout linearLayout, String label, String value) {
        // Cria um novo TextView para exibir uma informação da atividade
        TextView textView = new TextView(HomeActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 0, 8); // Margens para cada TextView
        textView.setLayoutParams(layoutParams);
        textView.setText(label + value);
        linearLayout.addView(textView);
    }

    public void adicionarAtividade(View v) {
        Intent i = new Intent(this, AdicionarAtividadeActivity.class);
        startActivity(i);
    }

    public void deslogarUsuario(View v) {
        try {
            auth.signOut();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }
}
