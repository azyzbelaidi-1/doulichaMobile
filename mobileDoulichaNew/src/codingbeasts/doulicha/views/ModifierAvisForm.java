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
import com.codename1.ui.FontImage;
/**
 *
 * @author HP
 */
public class ModifierAvisForm extends Form {

    private avis pe;
    private TextField contenuAvis;

    public ModifierAvisForm(avis pe, Form previous) {
        this.pe = pe;
        
        setTitle("Modifier mon avis");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container c = new Container(new BorderLayout());
        c.setUIID("SignInForm");
        
        contenuAvis = new TextField(pe.getContenu_avis(), "Contenu avis", 20, TextField.ANY);
        contenuAvis.getAllStyles().setFgColor(0x000000);
        Button modifierBtn = new Button("Modifier");
        
        Container content = BoxLayout.encloseY(
                new Label("Modifiez votre avis :", "WelcomeTitle"),
                contenuAvis,
                modifierBtn
        );
        
           modifierBtn.addActionListener(e -> {
            // récupérer les nouvelles valeurs saisies dans les champs
            String contenu = contenuAvis.getText();
            
            // mettre à jour la avis dans la base de données
            ServiceAvis.getInstance().modifierAvis(pe.getID_avis(), contenu);
            
            // afficher un message de succès
            Dialog.show("Succès", "Votre avis a été modifiée avec succès !", "OK", null);
            Resources res = null;
            new ListCategorieForm(res).show();
            // retourner à la page précédente
          //  previous.showBack();
        });
        
        
    
        c.addComponent(BorderLayout.CENTER, content);
        add(c);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}


