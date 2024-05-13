package com.example.angendadeatividades.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angendadeatividades.R;
import com.example.angendadeatividades.activity.model.Usuario;
import com.example.angendadeatividades.activity.utilidades.configuracaoBD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    //objetos
    Usuario usuario;
    FirebaseAuth autentificacao;

    EditText campoNome, campoEmail, campoSenha;

    Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        usuario = new Usuario("", "", "");
        iniObjetos();
    }

    private void iniObjetos() {
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmailLogin);
        campoSenha = findViewById(R.id.editTextSenhaLogin);
        botaoCadastrar = findViewById(R.id.buttonCadastrarRegistro);
    }

    public void validaCampos(View v) {
        String nome = campoNome.getText().toString().trim();
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    cadastrarUsuario();
                } else {
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
    }



    private void cadastrarUsuario() {
        autentificacao = configuracaoBD.fireBaseAutentificador();
        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    String excecao = "";
                    try {
                        throw  task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite um senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Digite um email valido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já existe" ;
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar";
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}