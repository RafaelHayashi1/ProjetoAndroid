package com.example.angendadeatividades.activity.utilidades;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class usuarioAutentificado {
    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = configuracaoBD.fireBaseAutentificador();
        return usuario.getCurrentUser();
    }
}
