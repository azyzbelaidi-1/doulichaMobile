/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.services.ServicesUtilisateur;
import com.codename1.ui.Command;

/**
 *
 * @author Asus
 */
public class OubliePass extends Form {

    public OubliePass() {
        setTitle("Mot de passe oublié");
        setLayout(BoxLayout.y());
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_LEFT, ev -> { previous.showBack();});
        TextField email = new TextField("", "Email");
        Button envoyer = new Button("Envoyer");
        envoyer.addActionListener((evt) -> {
            if (ServicesUtilisateur.getInstance().oubliepassword(email.getText()) == true) {
                Dialog.show("Success", "Email envoyé", new Command("OK"));
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });
         Button retourButton = new Button("Retour");
        retourButton.addActionListener((evt) -> {
            LoginForm loginForm = new LoginForm();
            loginForm.show();
        });

       
       

        addAll(email, envoyer, retourButton);
    }
   
}
