/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.SessionManager;
import com.mycompany.entites.Utilisateur;
import com.mycompany.services.ServicesUtilisateur;

/**
 *
 * @author Asus
 */
public class ModifierPasswordForm extends Form {

    public ModifierPasswordForm() {
        setTitle("Modifier mot de passe");
        setLayout(BoxLayout.y());

        TextField passactu = new TextField("", "Mot de passe actuel");
        TextField newpass = new TextField("", "Nouveau mot de passe");
        Button btnValider = new Button("Valider");
        btnValider.addActionListener((evt) -> {
          Utilisateur utilisateurConnecte = SessionManager.getLoggedInUser();
            ServicesUtilisateur.getInstance().updatePass(utilisateurConnecte,passactu.getText().toString(), newpass.getText().toString());
        });
         Button retourButton = new Button("Retour");
        retourButton.addActionListener((evt) -> {
            HomePage loginForm = new HomePage();
            loginForm.show();
        });
        addAll(passactu, newpass, btnValider, retourButton);
    }

}
