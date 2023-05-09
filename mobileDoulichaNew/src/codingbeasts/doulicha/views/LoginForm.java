package codingbeasts.doulicha.views;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.services.ServicesUtilisateur;

public class LoginForm extends Form {
    private TextField emailField;
    private TextField passwordField;
    private Resources theme;

    public LoginForm() {
        this(Resources.getGlobalResources());
    }

    public LoginForm(Resources theme) {
        setTitle("connexion");
        setLayout(BoxLayout.y());
        this.theme = theme;

        emailField = new TextField("", "Email", 20, TextField.EMAILADDR);
        passwordField = new TextField("", "Mot de passe", 20, TextField.PASSWORD);

        Button loginButton = new Button("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                boolean success = ServicesUtilisateur.getInstance().connexion(email, password);

                if (success) {
                    
                    /* Home homePage = new Home();
                      homePage.show();*/
                      
                       new Home(theme).show();
//                    Dialog.show("success", "hello", "OK", null);
                    // Connexion réussie, affichez votre écran principal ou effectuez d'autres actions
                } else {
                    Dialog.show("Erreur de connexion", "Identifiants de connexion incorrects", "OK", null);
                    // Connexion échouée, affichez un message d'erreur ou effectuez des actions appropriées
                }
            }
        });
       
          Button registerButton = new Button("Inscrivez-vous");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                InscriptionForm inscriptionForm = new InscriptionForm(theme);
                inscriptionForm.show();
            }
        });
          Button btn1 = new Button("oublie-mot de passe");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              
               OubliePass o1 = new OubliePass();
                o1.show();
            }
        });
       

      
        addAll(emailField,passwordField,loginButton,registerButton,btn1);
      
    }
   
 
}
