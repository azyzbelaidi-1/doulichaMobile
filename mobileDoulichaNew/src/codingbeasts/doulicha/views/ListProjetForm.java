/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import static com.codename1.io.Log.p;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Stroke;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.Projet;
import codingbeasts.doulicha.services.ServiceProjet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class ListProjetForm extends SideMenu{
    
    private List<Projet> listEvent;
    private EncodedImage enc;
    private Container cnt1;

    public ListProjetForm(Resources res) {
        Toolbar tb = getToolbar();
        setTitle("Liste des Projets");             
 
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        listEvent = ServiceProjet.getInstance().affichageProjets();
        enc = EncodedImage.createFromImage(Image.createImage(400, 400), true);

        //prepare field
        TextField searchField = new TextField("", "Rechercher...");
        searchField.getAllStyles().setFgColor(0x000000);

        add(searchField);

        cnt1 = new Container(BoxLayout.y());   
        add(cnt1);

        searchField.addDataChangeListener((i1, i2) -> {
            String text = searchField.getText();
            cnt1.removeAll();
            for (Projet p : listEvent) {
                if (p.getNomProjet().toLowerCase().contains(text.toLowerCase())) {
                    addProjectContainer(p);
                }
            }
            revalidate();
        });
        
        for (Projet p : listEvent){
            addProjectContainer(p);
        }
    }

    private void addProjectContainer(Projet p) {
        Container cnt2 = new Container(BoxLayout.y());
        Label nomProjet = new Label(p.getNomProjet());
        Label descriptionProjet = new Label("Description: " + p.getDescriptionProjet());
        Label objectifProjet = new Label("Prix: " + Float.toString((float) p.getObjectifProjet()));
        Label etatProjet = new Label("Etat: " + Integer.toString(p.getEtatProjet()));
        Button SLvoir = new Button("Ajouter un don");
    
        String urla = "http://127.0.0.1:8000/uploads/images/"+p.getImageProjet();
        ImageViewer imga = new ImageViewer(URLImage.createToStorage(enc, urla.substring(urla.lastIndexOf("/")+1, urla.length()), urla,URLImage.RESIZE_SCALE_TO_FILL));
        cnt2.add(imga);
        
        SLvoir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AjoutDonForm(p, ListProjetForm.this).show();
            }
        });
        
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER ,1);
        cnt2.add(nomProjet);
        cnt2.add(descriptionProjet);
        cnt2.add(objectifProjet);
        cnt2.add(etatProjet);
        cnt2.add(SLvoir);
        cnt1.add(cnt2);
    }
    
    
    
     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}


     



