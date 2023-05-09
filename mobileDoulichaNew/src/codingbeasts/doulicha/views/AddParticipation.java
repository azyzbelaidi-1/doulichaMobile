package codingbeasts.doulicha.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.evenement;
import codingbeasts.doulicha.services.ServiceEvenement;
import static codingbeasts.doulicha.services.ServiceEvenement.instance;
import codingbeasts.doulicha.services.ServiceEvenementParticipation;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.util.Map;

public class AddParticipation extends Form{

     public AddParticipation(evenement p, Form previous) {
         
         
          ListEvents instance = null;
          
        setTitle("Je participe !");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container c = new Container(new BorderLayout());
        c.setUIID("SignInForm");
        
        TextField phoneTF = new TextField("", "Numéro de téléphone", 20, TextField.PHONENUMBER);
        TextField placesTF = new TextField("", "Nombre de places", 20, TextField.NUMERIC);
        Button participerBtn = new Button("Participer");
        
        Container content = BoxLayout.encloseY(
                new Label("Remplissez les informations de participation :", "WelcomeTitle"),
                phoneTF,
                placesTF,
                participerBtn
        );
        
     participerBtn.addActionListener(e -> {
   // récupérer les valeurs saisies dans les champs
String phone = phoneTF.getText();
String pf = placesTF.getText();

// vérifier que le champ "phoneTF" n'est pas vide
if (phone.isEmpty()) {
    Dialog.show("Erreur", "Veuillez saisir votre numéro de téléphone !", "OK", null);
    return;
}

// vérifier que le champ "placesTF" contient une valeur numérique valide
int places;
try {
    places = Integer.parseInt(pf);
} catch (NumberFormatException ex) {
    Dialog.show("Erreur", "Veuillez saisir une valeur numérique valide pour le nombre de places !", "OK", null);
    return;
}

// vérifier que le nombre de places est supérieur à zéro
if (places <= 0) {
    Dialog.show("Erreur", "Veuillez saisir un nombre de places supérieur à zéro !", "OK", null);
    return;
}

int id=p.getID_event();
int idUser=5;
// ajouter la participation à l'événement
ServiceEvenementParticipation.getInstance().ajoutParticipationEvenement(id, phone, String.valueOf(places), idUser);


 Dialog.show("Succès", "Votre participation a été enregistrée avec succès !", "OK", null);
            Resources res = null;
            new ListParticipations(res).show();



   // Envoyer un message de confirmation
                                
                                  String message = "Chère client(e), nous avons le plaisir de vous confirmer votre participation à l'évènement " + p.getNom_event() + ". Nous serons très ravis de vous accueillir. A très bientôt! ";
                                 System.out.println(phone);
                                    System.out.println("Envoi de message à " + phone+ " : " + message);
                                    envoiSMS(message, phone);
        
// afficher un message de succès
Dialog.show("Succès", "Votre participation a été enregistrée avec succès !", "OK",null);
      
});
     
     
        
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
     
      public static final String ACCOUNT_SID = "ACf110ae25bbef456bf8745ec2a3555b29"; 
    public static final String AUTH_TOKEN = "4421f040491a9fd05c4e9245d3423920"; 
 
    public void envoiSMS(String msg, String toNumber) { 
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
        Message message = Message.creator( 
                new com.twilio.type.PhoneNumber(toNumber), 
                new com.twilio.type.PhoneNumber("+12766378893"),  
               msg)      
            .create(); 
 
        System.out.println(message.getSid()); 
    } 
    
     
    AddParticipation(evenement p, ServiceEvenement instance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
    
    

