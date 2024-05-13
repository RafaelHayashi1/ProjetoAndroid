package com.example.angendadeatividades.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText campoEmail,campoSenha;
    Button botaoEntrar;
    FirebaseAuth autentificacao;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        autentificacao = configuracaoBD.fireBaseAutentificador();
        iniObjetos();
    }



    public void validaCampos(View v) {
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                Usuario usuario = new Usuario("","","");
                usuario.setEmail(email);
                usuario.setSenha(senha);
                logar(usuario);
            }else{
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }
    }

    private void logar(Usuario usuario) {
        autentificacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    enviarHome();
                }else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuario não cadastrado.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou Senha incorretos";
                    } catch(Exception e) {
                        excecao = "Erro ao Logar: " + e.getMessage();
                        Log.e("LoginActivity", excecao); // Adicionando log do erro
                    }

                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void enviarHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void cadastrarTela(View v){
        Intent i = new Intent(this, CadastroActivity.class); // Mudou de HomeActivity para CadastroActivity
        startActivity(i);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        FirebaseUser usuarioAuth = autentificacao.getCurrentUser();
        if(usuarioAuth !=null){
            enviarHome();
        }
    }

    private void iniObjetos() {
        campoEmail = findViewById(R.id.editTextEmailLogin);
        campoSenha = findViewById(R.id.editTextSenhaLogin);
        botaoEntrar = findViewById(R.id.buttonAcessarLogin);
    }

}