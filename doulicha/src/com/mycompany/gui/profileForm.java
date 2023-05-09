/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.entites.Utilisateur;

public class profileForm extends Form {
    private Utilisateur utilisateur;
    private TextField nomField;
    private TextField prenomField;
    private TextField emailField;
    private Button modifierButton;
    private Button retourButton;

    public profileForm(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("Profil");

        nomField = new TextField(utilisateur.getNom_user());
        prenomField = new TextField(utilisateur.getPrenom_user());
        emailField = new TextField(utilisateur.getEmail_user());
        
        modifierButton = new Button("Modifier");
        retourButton = new Button("Retour");

        modifierButton.addActionListener(e -> {
            // Récupérer les nouvelles informations saisies par l'utilisateur
            String nouveauNom = nomField.getText();
            String nouveauPrenom = prenomField.getText();
            String nouvelEmail = emailField.getText();

            // Mettre à jour les informations de l'utilisateur
            utilisateur.setNom_user(nouveauNom);
            utilisateur.setPrenom_user(nouveauPrenom);
            utilisateur.setEmail_user(nouvelEmail);

            // Afficher un message de succès ou effectuer d'autres actions
            Dialog.show("Succès", "Profil mis à jour", "OK", null);
        });

        retourButton.addActionListener(e -> {
            new HomePage().show();
        });

        add(nomField);
        add(prenomField);
        add(emailField);
        add(modifierButton);
        add(retourButton);
    }
}
