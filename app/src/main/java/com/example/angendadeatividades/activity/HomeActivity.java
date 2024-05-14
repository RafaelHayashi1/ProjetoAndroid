package com.example.angendadeatividades.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.angendadeatividades.activity.model.AtividadeHome;
import com.example.angendadeatividades.R;
import com.example.angendadeatividades.activity.utilidades.DetalhesAtividadeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editTextFiltro;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        DatabaseReference minhasAtividades = referencia.child("agenda_atividade");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        editTextFiltro = findViewById(R.id.editTextFiltro);
        Query atividadesQuery = minhasAtividades.orderByChild("uid_usuario").equalTo(auth.getCurrentUser().getUid());

        atividadesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                linearLayout.removeAllViews();

                // Lista original de todas as atividades
                List<AtividadeHome> atividades = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Obter os dados da atividade
                    String uidAtividade = dataSnapshot.getKey();
                    String tituloAtividade = dataSnapshot.child("titulo_atividade").getValue(String.class);
                    String dataAtividade = dataSnapshot.child("data_atividade").getValue(String.class);
                    String descricaoAtividade = dataSnapshot.child("descricao_atividade").getValue(String.class);
                    String horaAtividade = dataSnapshot.child("hora_atividade").getValue(String.class);
                    String localizacaoAtividade = dataSnapshot.child("localizacao_atividade").getValue(String.class);
                    String participantesAtividade = dataSnapshot.child("participantes_atividade").getValue(String.class);

                    // Adicionar a atividade à lista original
                    AtividadeHome atividade = new AtividadeHome(uidAtividade, tituloAtividade, dataAtividade, descricaoAtividade, horaAtividade, localizacaoAtividade, participantesAtividade);
                    atividades.add(atividade);
                }

                // Verificar se há filtro aplicado
                String filtro = editTextFiltro.getText().toString().toLowerCase(Locale.getDefault());

                for (AtividadeHome atividade : atividades) {
                    // Se não houver filtro ou o título da atividade corresponder ao filtro, adicione o card
                    if (filtro.isEmpty() || atividade.getTitulo().toLowerCase(Locale.getDefault()).contains(filtro)) {
                        addCard(linearLayout, atividade.getUid(), atividade.getTitulo(), atividade.getData(), atividade.getDescricao(), atividade.getHora(), atividade.getLocalizacao(), atividade.getParticipantes());
                    }
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

    public void exibirCaixaDeTexto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar Atividade");

        // Criando um EditText programaticamente
        final EditText editTextFiltro = new EditText(this);
        editTextFiltro.setHint("Digite o filtro...");
        builder.setView(editTextFiltro);

        // Adicionando o botão "Aplicar Filtro"
        builder.setPositiveButton("Aplicar Filtro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String filtro = editTextFiltro.getText().toString().toLowerCase(Locale.getDefault());

                // Atualizar a exibição das atividades com base no filtro
                atualizarAtividades(filtro);
            }
        });

        // Adicionando o botão "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Criando e exibindo o diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void atualizarAtividades(String filtro) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.removeAllViews();

        Query atividadesQuery = referencia.child("agenda_atividade")
                .orderByChild("uid_usuario")
                .equalTo(auth.getCurrentUser().getUid());

        atividadesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uidAtividade = dataSnapshot.getKey();
                    String tituloAtividade = dataSnapshot.child("titulo_atividade").getValue(String.class);
                    String dataAtividade = dataSnapshot.child("data_atividade").getValue(String.class);
                    String descricaoAtividade = dataSnapshot.child("descricao_atividade").getValue(String.class);
                    String horaAtividade = dataSnapshot.child("hora_atividade").getValue(String.class);
                    String localizacaoAtividade = dataSnapshot.child("localizacao_atividade").getValue(String.class);
                    String participantesAtividade = dataSnapshot.child("participantes_atividade").getValue(String.class);

                    // Verificar se o título da atividade corresponde ao filtro
                    if (tituloAtividade != null && tituloAtividade.toLowerCase(Locale.getDefault()).contains(filtro)) {
                        // Se corresponder, adicione o card
                        // Você pode obter os outros detalhes da atividade aqui, se necessário
                        addCard(linearLayout, uidAtividade, tituloAtividade, dataAtividade, descricaoAtividade, horaAtividade, localizacaoAtividade, participantesAtividade);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Erro:" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
