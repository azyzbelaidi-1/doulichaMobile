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
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.reclamation;
import codingbeasts.doulicha.services.ServiceReclamation;
import java.util.ArrayList;
import java.util.List;
import codingbeasts.doulicha.views.AjoutReclamationForm;


/**
 *
 * @author HP
 */




public class ListReclamationForm extends SideMenu{

    private List<reclamation> listEvent;
    private EncodedImage enc;
    private Container cnt1;
private Resources theme;
    public ListReclamationForm(Resources res) {
        Toolbar tb = getToolbar();
        setTitle("Liste des réclamations"); 
        
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
 
        listEvent = ServiceReclamation.getInstance().affichageReclamations();
        enc = EncodedImage.createFromImage(Image.createImage(400, 400), true);

        //prepare field
        TextField searchField = new TextField("", "Rechercher...");
        searchField.getAllStyles().setFgColor(0x000000);
        add(searchField);
         Button btnRetour = new Button("Ajouter Réclamation");
    btnRetour.addActionListener(ev -> {
        // Retourner à la page de la liste des catégories
        
        new AjoutReclamationForm(theme).show();
    });
    add(btnRetour);

        cnt1 = new Container(BoxLayout.y());   
        add(cnt1);

        searchField.addDataChangeListener((i1, i2) -> {
            String text = searchField.getText();
            cnt1.removeAll();
            for (reclamation p : listEvent) {
                if (p.getContenu_reclamation().toLowerCase().contains(text.toLowerCase())) {
                    addcategorieContainer(p);
                }
            }
            revalidate();
        });
        
        for (reclamation p : listEvent){
            addcategorieContainer(p);
        }
    }

    private void addcategorieContainer(reclamation p) {
        Container cnt2 = new Container(BoxLayout.y());
        cnt2.getStyle().setBorder(Border.createLineBorder(2, 0x2f3033));
        cnt2.getStyle().setMargin(5, 5, 0, 0);
        cnt2.getStyle().setPadding(5, 5, 5, 5);
        
        TextArea texteReclamation = new TextArea(p.getContenu_reclamation());
texteReclamation.setUIID("reclamation-titre");
texteReclamation.setEditable(false);
texteReclamation.setRows(3); // set the number of visible rows
texteReclamation.setGrowByContent(true);
        Label etatTxt = new Label("etat : "+p.getEtat_reclamation(),"NewsTopLine1");
        Label dateTxt = new Label("date : "+p.getDate_reclamation(),"NewsTopLine1");
        
        if(p.getEtat_reclamation() == 0 ) {
            etatTxt.setText("non Traitée");
        }
        else 
            etatTxt.setText("Traitée");

        texteReclamation.setUIID("reclamation-titre");
        etatTxt.setUIID("reclamation-etat");
        dateTxt.setUIID("reclamation-date");
        
    
        cnt2.add(texteReclamation);
        cnt2.add(etatTxt);
        cnt2.add(dateTxt);
        cnt1.add(cnt2);
    }
    
    
    
     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}

/*
public class ListReclamationForm extends BaseForm {
    
    Form current;
    
    public ListReclamationForm(Resources res) {
        super("Liste des réclamations", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des réclamations");
        getContentPane().setScrollVisible(false);
        
        // Add the "Ajouter Réclamation" button to the toolbar
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
            new AjoutReclamationForm(res).show();
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        // Get the list of reclamations from the service
        ArrayList<reclamation> list = ServiceReclamation.getInstance().affichageReclamations();
        
        for (reclamation rec : list) {
            String urlImage = "back-logo.jpeg";
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            
            addButton(urlim, rec);
        }
    }

    private void addButton(Image img, reclamation rec) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        Label contenuTxt = new Label("Reclamation : "+rec.getContenu_reclamation(),"NewsTopLine2");
        Label etatTxt = new Label("etat : "+rec.getEtat_reclamation(),"NewsTopLine2");
        Label dateTxt = new Label("date : "+rec.getDate_reclamation(),"NewsTopLine2");
        
        if(rec.getEtat_reclamation() == 0 ) {
            etatTxt.setText("non Traitée");
        }
        else 
            etatTxt.setText("Traitée");
       
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(contenuTxt), BoxLayout.encloseX(etatTxt), BoxLayout.encloseX(dateTxt)));
        
        add(cnt);



//To change body of generated methods, choose Tools | Templates.
    }
    
    
}
*/

