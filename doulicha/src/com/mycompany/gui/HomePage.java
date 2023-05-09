/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entites.Utilisateur;
import com.mycompany.services.ServicesUtilisateur;

public class HomePage extends Form {

    public HomePage() {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Accueil");

        Label helloLabel = new Label("Hello");
        add(helloLabel);

        Button profileButton = new Button("Profil");
        profileButton.addActionListener(evt -> {
            Utilisateur utilisateur = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
            if (utilisateur != null) {
                profileForm profileForm = new profileForm(utilisateur);
                profileForm.show();
            } else {
                Dialog.show("Erreur", "Aucun utilisateur connecté", "OK", null);
            }
        });
        Button pass = new Button("Modifier mot de passe");
        pass.addActionListener(e -> new ModifierPasswordForm().show());

        Button logoutButton = new Button("Déconnexion");
        logoutButton.addActionListener(evt -> {
            ServicesUtilisateur.getInstance().logout(); // Appel de la méthode logout
            LoginForm loginForm = new LoginForm();
            loginForm.show();
        });

        add(profileButton);
        add(pass);
        add(logoutButton);
    }
}
