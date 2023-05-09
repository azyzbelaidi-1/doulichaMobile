package codingbeasts.doulicha.views;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.services.ServicesUtilisateur;

public class InscriptionForm extends Form {
   public InscriptionForm(Resources res) {
    super(new BorderLayout());
    Toolbar tb = new Toolbar(true);
    setToolbar(tb);
    tb.setUIID("Container");
    getTitleArea().setUIID("Container");
    Form previous = Display.getInstance().getCurrent();
    tb.setBackCommand("", e -> previous.showBack());

    TextField nom = new TextField("", "nom", 20, TextField.ANY);
    TextField prenom = new TextField("", "prenom", 20, TextField.ANY);
    TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
    TextField motDePass = new TextField("", "mot de passe", 20, TextField.PASSWORD);


    nom.setSingleLineTextArea(false);
    prenom.setSingleLineTextArea(false);
    email.setSingleLineTextArea(false);
    motDePass.setSingleLineTextArea(false);

    
    Button signUpButton = new Button("SignUp");
    Button signIn = new Button("Sign In");
    signUpButton.addActionListener((ActionEvent e) -> {
        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.getText().length() == 0 || prenom.getText().length() == 0 ||
            email.getText().length() == 0 || motDePass.getText().length() == 0 
           ) {
            Label alertLabel = new Label("Veuillez remplir les champs");
            Dialog.show("Alert", alertLabel, new Command("OK"));
        } else if (!email.getText().contains("@") || !email.getText().contains(".")) {
            Label alertLabel = new Label("Please enter a valid email address");
            Dialog.show("Alert", alertLabel, new Command("OK"));
        }
        else {
            
           Utilisateur u = new Utilisateur(email.getText().toString(),nom.getText().toString(), prenom.getText().toString(),  motDePass.getText().toString());
           
            
            if(ServicesUtilisateur.getInstance().inscription(u)){
            Dialog.show("Success","Utilisateur ajouter","OK",null);
            }
            
    }
      
    });
     add(BorderLayout.CENTER, BoxLayout.encloseY(
            new Label("Sign Up", "LogoLabel"),
            nom,
            prenom,
            email,
            motDePass,
       
            signUpButton
    ));
}

    public InscriptionForm() {
        // Ne rien faire ici
    }
}
