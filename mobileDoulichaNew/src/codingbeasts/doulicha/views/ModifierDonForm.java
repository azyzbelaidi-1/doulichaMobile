/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

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
import codingbeasts.doulicha.entities.Don;
import codingbeasts.doulicha.services.ServiceDon;

/**
 *
 * @author Admin
 */
public class ModifierDonForm extends Form {

    private Don p;
    private TextField ValeurDonTF;
  

    public ModifierDonForm(Don p, Form previous) {
        this.p = p;
        
        setTitle("Modifier ma Don");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container c = new Container(new BorderLayout());
        c.setUIID("SignInForm");
        ValeurDonTF = new TextField(String.valueOf(p.getValeurDon()), "Valeur de don", 20, TextField.NUMERIC);
        ValeurDonTF.getAllStyles().setFgColor(0x000000);
        Button modifierBtn = new Button("Modifier");
        Container content = BoxLayout.encloseY(
                new Label("Modifiez les informations de votre Don :", "WelcomeTitle"),
                ValeurDonTF,
                modifierBtn
        );
        modifierBtn.addActionListener(e -> {
            // valider la saisie de la valeur de don
            String valeurDonString = ValeurDonTF.getText();
            if (valeurDonString.isEmpty()) {
                Dialog.show("Erreur", "Veuillez saisir une valeur de don", "OK", null);
                return;
            }
            int valeurDon = Integer.parseInt(valeurDonString);
            if (valeurDon <= 0) {
                Dialog.show("Erreur", "Veuillez saisir une valeur de don positive", "OK", null);
                return;
            }
            // mettre à jour la participation dans la base de données
            ServiceDon.getInstance().modifierDon(p.getIdDon(), String.valueOf(valeurDon));
            // afficher un message de succès
            Dialog.show("Succès", "Votre don a été modifiée avec succès !", "OK", null);

            Resources res = null;
            new ListDonForm(res).show();
            // retourner à la page précédente
          //  previous.showBack();
        });
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
