package com.mycompany.myapp.gui;

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
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.services.ServiceEvenement;
import static com.mycompany.myapp.services.ServiceEvenement.instance;
import com.mycompany.myapp.services.ServiceEvenementParticipation;
import java.util.Map;

public class AddParticipation extends Form{

     public AddParticipation(evenement p, Form previous) {
        
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
    int places = Integer.parseInt(placesTF.getText());
    int id=p.getID_event();
    int idUser=5;
         System.out.println(id);
    // ajouter la participation à l'événement
    ServiceEvenementParticipation.getInstance().ajoutParticipationEvenement(id, phone,String.valueOf(places), idUser);
    
    // afficher un message de succès
    Dialog.show("Succès", "Votre participation a été enregistrée avec succès !", "OK", null);
    
    // retourner à la page précédente
    previous.showBack();
});
        
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
     
    AddParticipation(evenement p, ServiceEvenement instance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
    
    

