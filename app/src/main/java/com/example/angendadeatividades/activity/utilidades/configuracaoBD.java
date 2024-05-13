package com.example.angendadeatividades.activity.utilidades;

import com.google.firebase.auth.FirebaseAuth;

public class configuracaoBD {
    private static FirebaseAuth auth;

    public static FirebaseAuth fireBaseAutentificador(){
        if (auth==null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
