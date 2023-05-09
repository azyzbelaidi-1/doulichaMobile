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
import codingbeasts.doulicha.entities.participation_evenement;
import codingbeasts.doulicha.services.ServiceEvenement;
import static codingbeasts.doulicha.services.ServiceEvenement.instance;
import codingbeasts.doulicha.services.ServiceEvenementParticipation;
import java.util.Map;

public class UpdateParticipation extends SideMenu{

    private participation_evenement pe;
    private TextField phoneTF;
    private TextField placesTF;

    public UpdateParticipation(participation_evenement pe, Form previous) {
        this.pe = pe;
        
        setTitle("Modifier ma participation");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container c = new Container(new BorderLayout());
        c.setUIID("SignInForm");
        
        phoneTF = new TextField(pe.getNum_tel(), "Numéro de téléphone", 20, TextField.PHONENUMBER);
        placesTF = new TextField(String.valueOf(pe.getNombre_participation()), "Nombre de places", 20, TextField.NUMERIC);
        Button modifierBtn = new Button("Modifier");
        
        Container content = BoxLayout.encloseY(
                new Label("Modifiez les informations de votre participation :", "WelcomeTitle"),
                phoneTF,
                placesTF,
                modifierBtn
        );
        
           modifierBtn.addActionListener(e -> {
            // récupérer les nouvelles valeurs saisies dans les champs
            String phone = phoneTF.getText();
            int places = Integer.parseInt(placesTF.getText());
            
            // mettre à jour la participation dans la base de données
            ServiceEvenementParticipation.getInstance().modifierParticipationEvenement(pe.getID_participation(), phone, String.valueOf(places));
            
            // afficher un message de succès
            Dialog.show("Succès", "Votre participation a été modifiée avec succès !", "OK", null);
            Resources res = null;
            new ListParticipations(res).show();
            // retourner à la page précédente
          //  previous.showBack();
        });
        
        
    
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
  @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }   
    
}
    
    

