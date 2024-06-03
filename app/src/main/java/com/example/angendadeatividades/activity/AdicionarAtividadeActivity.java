package com.example.angendadeatividades.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.angendadeatividades.activity.model.Atividade;
import com.example.angendadeatividades.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarAtividadeActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    EditText campoAtividade, campoDesc, campoData, campoHora, campoLoca, campPart;
    Button adicionarAtividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_atividade);
        iniObjetos();
    }

    public void adicionar(View v) {
        String titulo = campoAtividade.getText().toString().trim();
        String descricao = campoDesc.getText().toString().trim();
        String data = campoData.getText().toString().trim();
        String hora = campoHora.getText().toString().trim();
        String localizacao = campoLoca.getText().toString().trim();
        String participantes = campPart.getText().toString().trim();
        String uidUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (!titulo.isEmpty() && !descricao.isEmpty() && !data.isEmpty() && !hora.isEmpty()
                && !localizacao.isEmpty() && !participantes.isEmpty()) {

            String chave = referencia.child("agenda_atividade").push().getKey();

            Atividade atividade = new Atividade();
            atividade.setUid_atividade(chave);
            atividade.setUid_usuario(uidUsuario);
            atividade.setTitulo_atividade(titulo);
            atividade.setDescricao_atividade(descricao);
            atividade.setData_atividade(data);
            atividade.setHora_atividade(hora);
            atividade.setLocalizacao_atividade(localizacao);
            atividade.setParticipantes_atividade(participantes);

            referencia.child("agenda_atividade").child(chave).setValue(atividade);

            // Mostrar uma mensagem de sucesso
            Toast.makeText(this, "Atividade adicionada com sucesso!", Toast.LENGTH_SHORT).show();

            limparCampos();
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limparCampos() {
        campoAtividade.setText("");
        campoDesc.setText("");
        campoData.setText("");
        campoHora.setText("");
        campoLoca.setText("");
        campPart.setText("");
    }

    private void iniObjetos() {
        campoAtividade = findViewById(R.id.editTextTituloAtividade);
        campoDesc = findViewById(R.id.editTextDescricaoAtividade);
        campoData = findViewById(R.id.editTextDataAtividade);
        campoHora = findViewById(R.id.editTextHoraAtividade);
        campoLoca = findViewById(R.id.editTextLocalizacaoAtividade);
        campPart = findViewById(R.id.editTextParticipantesAtividade);
        adicionarAtividade = findViewById(R.id.buttonAdicionarAtividade);
    }

    public void voltar(View view) {
        finish();
    }

}
