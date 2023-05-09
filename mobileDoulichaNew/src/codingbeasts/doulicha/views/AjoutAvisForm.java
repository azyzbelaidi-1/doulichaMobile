/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.avis;
import codingbeasts.doulicha.entities.categorie_avis;
import codingbeasts.doulicha.services.ServiceAvis;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author HP
 */
public class AjoutAvisForm extends Form{
    private Resources theme;
    public AjoutAvisForm(Resources res, categorie_avis rec) {
        super("Ajout d'avis", BoxLayout.y());
        
        // Création des champs pour entrer les informations de l'avis
        TextField noteField = new TextField("", "Note (sur 5)");
        noteField.getAllStyles().setFgColor(0x000000);
        TextField contenuField = new TextField("", "Contenu");
        contenuField.getAllStyles().setFgColor(0x000000);
        TextField typeField = new TextField("", "Type");
        typeField.getAllStyles().setFgColor(0x000000);
        /*TextField logementField = new TextField("", "ID Logement");
        logementField.getAllStyles().setFgColor(0x000000);
        TextField eventField = new TextField("", "ID Événement");
        eventField.getAllStyles().setFgColor(0x000000);*/
        
        // Bouton pour ajouter l'avis
        Button ajouterButton = new Button("Ajouter");
        
        // Bouton de retour
    Button btnRetour = new Button("Retour");
    btnRetour.addActionListener(ev -> {
        // Retourner à la page de la liste des catégories
        
        new ListCategorieForm(theme).show();
    });
        
        // Ajout des champs et du bouton à la fenêtre
        add(noteField);
        add(contenuField);
        add(typeField);
        //add(logementField);
        //add(eventField);
        add(ajouterButton);
        add(btnRetour);
        
        // Action lors du clic sur le bouton "Ajouter"
        // Action lors du clic sur le bouton "Ajouter"
ajouterButton.addActionListener((e) -> {
    // Vérification que les champs sont remplis
    if (noteField.getText().isEmpty() || contenuField.getText().isEmpty() ||
        typeField.getText().isEmpty()  ) {
        Dialog.show("Erreur", "Veuillez remplir tous les champs", "OK", null);
        return;
    }
    
    // Vérification que les valeurs saisies sont correctes
    try {
        int note = Integer.parseInt(noteField.getText());
        if (note < 0 || note > 5) {
            Dialog.show("Erreur", "La note doit être comprise entre 0 et 5", "OK", null);
            return;
        }
        
        /*int idLogement = Integer.parseInt(logementField.getText());
        if (idLogement <= 0) {
            Dialog.show("Erreur", "L'ID du logement doit être supérieur à 0", "OK", null);
            return;
        }
        
        int idEvent = Integer.parseInt(eventField.getText());
        if (idEvent <= 0) {
            Dialog.show("Erreur", "L'ID de l'événement doit être supérieur à 0", "OK", null);
            return;
        }*/
    } catch (NumberFormatException ex) {
        Dialog.show("Erreur", "Les champs 'Note', 'ID Logement' et 'ID Événement' doivent être des nombres entiers", "OK", null);
        return;
    }
            // Récupération des valeurs entrées par l'utilisateur
            int note = Integer.parseInt(noteField.getText());
            String contenu = contenuField.getText();
            String type = typeField.getText();
            //int idLogement = Integer.parseInt(logementField.getText());
            //int idEvent = Integer.parseInt(eventField.getText());
            int id=rec.getID_categorie();
            System.out.println(id);
            // Appel de la méthode pour ajouter l'avis
            ServiceAvis.getInstance().ajouterAvis(id, note, contenu, type);
            
            // Affichage d'un message de confirmation
            Dialog.show("Succès", "L'avis a été ajouté avec succès", "OK", null);
            
            // Retour à la liste des avis
            new ListCategorieForm(theme).show();
        });
    }
    
}
