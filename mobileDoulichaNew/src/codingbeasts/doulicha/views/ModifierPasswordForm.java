/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.SessionManager;
import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.services.ServicesUtilisateur;

/**
 *
 * @author Asus
 */
public class ModifierPasswordForm extends Form {

    public ModifierPasswordForm(Resources theme) {
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
           
             new Home(theme).show();
        });
        addAll(passactu, newpass, btnValider, retourButton);
    }

}
