/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Projet;
import com.mycompany.services.ServiceDon;

/**
 *
 * @author Admin
 */
public class AjoutDonForm extends Form{

     public AjoutDonForm(Projet p, Form previous) {
        setTitle("J ajout un Don !");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container c = new Container(new BorderLayout());
        c.setUIID("SignInForm");
        TextField ValeurDon = new TextField("", "Valeur de don", 20, TextField.ANY);
        ValeurDon.getAllStyles().setFgColor(0x000000); // couleur noire
        Button participerBtn = new Button("Participer");
        Container content = BoxLayout.encloseY(
                new Label("Remplissez les informations de Don :", "WelcomeTitle"),
                ValeurDon,
                participerBtn
        );
        participerBtn.addActionListener(e -> {
// récupérer les valeurs saisies dans les champs
String valeurStr = ValeurDon.getText();
if (valeurStr.isEmpty()) {
// afficher un message d'erreur si le champ est vide
Dialog.show("Erreur", "Veuillez saisir une valeur de don.", "OK", null);
return;
}
int valeur;
try {
valeur = Integer.parseInt(valeurStr);
if (valeur <= 0) {
// afficher un message d'erreur si la valeur saisie n'est pas un entier positif
Dialog.show("Erreur", "Veuillez saisir une valeur de don positive.", "OK", null);
return;
}
} catch (NumberFormatException ex) {
// afficher un message d'erreur si la valeur saisie n'est pas un entier
Dialog.show("Erreur", "Veuillez saisir une valeur de don valide.", "OK", null);
return;
}
int idProjet = p.getIdProjet();
int idUser = 5;
int etatPaiement = 0;
System.out.println(idProjet);
// ajouter la participation à l'événementint idProjet, String valeurDon,int idUser,int etatPaiement
ServiceDon.getInstance().ajoutDon(idProjet, String.valueOf(valeur), idUser, etatPaiement);
// afficher un message de succès
Dialog.show("Succès", "Votre participation a été enregistrée avec succès !", "OK", null);
// retourner à la page précédente
Resources res = null;
new ListDonForm(res).show();
});
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
