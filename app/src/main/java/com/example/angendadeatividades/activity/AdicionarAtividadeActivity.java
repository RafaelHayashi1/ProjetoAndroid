package com.example.angendadeatividades.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        // Obter os valores dos campos de entrada
        String titulo = campoAtividade.getText().toString().trim();
        String descricao = campoDesc.getText().toString().trim();
        String data = campoData.getText().toString().trim();
        String hora = campoHora.getText().toString().trim();
        String localizacao = campoLoca.getText().toString().trim();
        String participantes = campPart.getText().toString().trim();
        String uidUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Verificar se todos os campos estão preenchidos
        if (!titulo.isEmpty() && !descricao.isEmpty() && !data.isEmpty() && !hora.isEmpty()
                && !localizacao.isEmpty() && !participantes.isEmpty()) {

            String chave = referencia.child("agenda_atividade").push().getKey();

            // Criar um objeto de atividade com os valores dos campos
            Atividade atividade = new Atividade();
            atividade.setUid_atividade(chave); // Definir o UID da atividade
            atividade.setUid_usuario(uidUsuario);
            atividade.setTitulo_atividade(titulo);
            atividade.setDescricao_atividade(descricao);
            atividade.setData_atividade(data);
            atividade.setHora_atividade(hora);
            atividade.setLocalizacao_atividade(localizacao);
            atividade.setParticipantes_atividade(participantes);

            // Inserir os dados no banco de dados Firebase usando o identificador único gerado
            referencia.child("agenda_atividade").child(chave).setValue(atividade);

            // Mostrar uma mensagem de sucesso
            Toast.makeText(this, "Atividade adicionada com sucesso!", Toast.LENGTH_SHORT).show();

            // Limpar os campos de entrada após adicionar a atividade
            limparCampos();
        } else {
            // Caso algum campo esteja vazio, mostrar uma mensagem de erro
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

    public class Atividade {
        private String uid_atividade; // ID único da atividade
        private String uid_usuario; // UID do usuário
        private String titulo_atividade;
        private String descricao_atividade;
        private String data_atividade;
        private String hora_atividade;
        private String localizacao_atividade;
        private String participantes_atividade;

        // Construtor
        public Atividade() {
            // Construtor vazio necessário para Firebase
        }

        // Getters e Setters
        public String getUid_atividade() {
            return uid_atividade;
        }

        public void setUid_atividade(String uid_atividade) {
            this.uid_atividade = uid_atividade;
        }

        public String getUid_usuario() {
            return uid_usuario;
        }

        public void setUid_usuario(String uid_usuario) {
            this.uid_usuario = uid_usuario;
        }

        public String getTitulo_atividade() {
            return titulo_atividade;
        }

        public void setTitulo_atividade(String titulo_atividade) {
            this.titulo_atividade = titulo_atividade;
        }

        public String getDescricao_atividade() {
            return descricao_atividade;
        }

        public void setDescricao_atividade(String descricao_atividade) {
            this.descricao_atividade = descricao_atividade;
        }

        public String getData_atividade() {
            return data_atividade;
        }

        public void setData_atividade(String data_atividade) {
            this.data_atividade = data_atividade;
        }

        public String getHora_atividade() {
            return hora_atividade;
        }

        public void setHora_atividade(String hora_atividade) {
            this.hora_atividade = hora_atividade;
        }

        //teste
        public String getLocalizacao_atividade() {
            return localizacao_atividade;
        }

        public void setLocalizacao_atividade(String localizacao_atividade) {
            this.localizacao_atividade = localizacao_atividade;
        }

        public String getParticipantes_atividade() {
            return participantes_atividade;
        }

        public void setParticipantes_atividade(String participantes_atividade) {
            this.participantes_atividade = participantes_atividade;
        }
    }
}
