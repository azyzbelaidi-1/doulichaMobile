/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.Don;
import codingbeasts.doulicha.services.ServiceDon;
import com.stripe.exception.StripeException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ListDonPaye extends SideMenu {
    
    private List<Don> listDon;
    private Container container;
    private Form current;
    
    public ListDonPaye(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        setTitle("Liste des Dons");
        
        listDon = ServiceDon.getInstance().affichageDonsPaye();
        
        container = new Container(BoxLayout.y());
        for (Don p : listDon){
            Container donContainer = new Container(new BorderLayout());
            Label dateDon = new Label(p.getDateDon());
            Label valeurDon = new Label("Valeur de don: " + Float.toString((float) p.getValeurDon()));
            Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER ,1);
           
            donContainer.add(BorderLayout.WEST, dateDon);
            donContainer.add(BorderLayout.EAST, valeurDon);
   
            container.add(donContainer);
        }

        add(BorderLayout.CENTER, container);
        
        // Ajouter un bouton "Retour" en dessous de la liste
        Container retourContainer = new Container(new FlowLayout(Component.CENTER));
        Button retourBtn = new Button("Retour");
        retourBtn.addActionListener((e) -> {
            new ListDonForm(res).show();
        });
        retourContainer.add(retourBtn);
        add(BorderLayout.SOUTH, retourContainer);
    }
    
    
    
     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}
