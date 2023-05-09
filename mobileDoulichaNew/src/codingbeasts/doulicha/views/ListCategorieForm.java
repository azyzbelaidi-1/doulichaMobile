/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import codingbeasts.doulicha.entities.Utilisateur;
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
import codingbeasts.doulicha.entities.categorie_avis;
import codingbeasts.doulicha.services.ServiceCatégorie;
import java.util.ArrayList;
import codingbeasts.doulicha.entities.avis;
import codingbeasts.doulicha.entities.translator;
import codingbeasts.doulicha.services.ServiceAvis;
import codingbeasts.doulicha.services.ServicesUtilisateur;
import codingbeasts.doulicha.views.AjoutAvisForm;
import java.util.List;

/**
 *
 * @author HP
 */

/*
public class ListCategorieForm extends BaseForm{
    
    private List<categorie_avis> listEvent;
    private EncodedImage enc;
    private Container cnt1;

    public ListCategorieForm(Resources res) {
        Toolbar tb = getToolbar();
        setTitle("Liste des évènements");             
 
        listEvent = ServiceCatégorie.getInstance().affichageCategorie();
        enc = EncodedImage.createFromImage(Image.createImage(400, 400), true);

        //prepare field
        TextField searchField = new TextField("", "Rechercher...");
        add(searchField);

        cnt1 = new Container(BoxLayout.y());   
        add(cnt1);

        searchField.addDataChangeListener((i1, i2) -> {
            String text = searchField.getText();
            cnt1.removeAll();
            for (categorie_avis p : listEvent) {
                if (p.getNom_categorie().toLowerCase().contains(text.toLowerCase())) {
                    addcategorieContainer(p);
                }
            }
            revalidate();
        });
        
        for (categorie_avis p : listEvent){
            addcategorieContainer(p);
        }
    }

    private void addcategorieContainer(categorie_avis p) {
        Container cnt2 = new Container(BoxLayout.y());
        Label titreLabel = new Label(p.getNom_categorie());

        Button SLvoir = new Button("Ajouter un don");
    
        
        cnt2.add(titreLabel);
        cnt1.add(cnt2);
    }
}
*/



public class ListCategorieForm extends SideMenu{
    ListCategorieForm instance;
    private Resources theme;
        Form current;
public ListCategorieForm(Resources res) {
    super("Catégories", BoxLayout.y());
    Toolbar tb = new Toolbar(true);
    current = this;
    setToolbar(tb);
    getTitleArea().setUIID("Container");
    //setTitle("Ajout Reclamation");
    //getContentPane().setScrollVisible(false);
    //tb.addSearchCommand(e -> {});

    Tabs swipe = new Tabs();
    Label s1 = new Label();
    Label s2 = new Label();

    ArrayList<categorie_avis> list = ServiceCatégorie.getInstance().affichageCategorie();
    for (categorie_avis rec : list) {
        String urlImage = "back-logo.jpeg";
        Image placeHolder = Image.createImage(120, 90);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
        URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

        addButton(urlim, rec);
    }
}

private void addButton(Image img, categorie_avis rec) {
    int height = Display.getInstance().convertToPixels(1f);
    int width = Display.getInstance().convertToPixels(1f);

    Button image = new Button(img.fill(width, height));
    image.setUIID("Label");
    Container cnt = BorderLayout.west(image);

    Label nomTxt = new Label("Catégorie : " + rec.getNom_categorie(), "NewsTopLine2");
    //Label idTxt = new Label("Nom Catégorie : " + rec.getID_categorie(), "NewsTopLine2");

  Button btnAfficher = new Button("Afficher Avis");
btnAfficher.addActionListener(e -> {
    // Appeler la méthode affichageAvis() pour récupérer la liste des avis
    System.out.println(rec.getID_categorie());
    ArrayList<avis> listeAvis = ServiceAvis.getInstance().affichageAvis(rec.getID_categorie());
    
    // Créer un nouveau formulaire pour afficher la liste des avis
    Form listeAvisForm = new Form("Liste des Avis", BoxLayout.y());
    
    // Ajouter un élément de liste pour chaque avis retourné
    for (avis a : listeAvis) {
        Container c = new Container(BoxLayout.y());
        c.getStyle().setMarginBottom(100);
        // Titre de l'avis : le contenu de l'avis
        Label titreLabel = new Label(a.getContenu_avis());
        titreLabel.setUIID("NewsTopLine");
        c.add(titreLabel);
        
                Button traduireBtn = new Button("Traduire");
        traduireBtn.addActionListener(evt -> {
          
                String texteTraduit = translator.translate("en", "fr", a.getContenu_avis());
                titreLabel.setText(texteTraduit);
           
        });
        c.add(traduireBtn);
        
        // Note de l'avis
    Container noteContainer = new Container(new FlowLayout());
    int noteAvis = a.getNote_avis();
    String etoiles = "";
    for (int i = 0; i < noteAvis; i++) {
        etoiles += "\u2605"; // Ajouter une étoile à la chaîne
    }
    Label noteLabel = new Label(etoiles);
    noteContainer.add(noteLabel);
    c.add(noteContainer);
        
        Label idLabel = new Label(a.getType_avis());
        
        idLabel.setUIID("NewsTopLine");
        c.add(idLabel);
        /*
        Label idLabel2 = new Label(Integer.toString(a.getID_user()));
        
        idLabel2.setUIID("NewsTopLine");
        c.add(idLabel2);*/
        
        // Bouton supprimer l'avis
    Button supprimerBtn = new Button("Supprimer");
    System.out.println(a.getID_user());
     Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
          int idUserConnecte=UserConnecte.getID_user();
if (a.getID_user() == idUserConnecte) { // Vérifier si l'ID de l'utilisateur est égal à 1
    supprimerBtn.addActionListener(evt -> {
        Dialog dig = new Dialog("Suppression");
        if (dig.show("Suppression", "Vous voulez supprimer ce reclamation ?", "Annuler", "Oui")) {
            dig.dispose();
        } else {
            dig.dispose();
        }
        //n3ayto l suuprimer men service Reclamation
        if (ServiceAvis.getInstance().supprimerAvis(a.getID_avis())) {
            new ListCategorieForm(theme).show();
        }
    });
    c.add(supprimerBtn);
    


    Button modifierBtn = new Button("Modifier");
     modifierBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
                ServiceAvis.getInstance().modifierAvis(a.getID_avis(), a.getContenu_avis());
               
                        new ModifierAvisForm(a, instance).show();
            }
        }); 
     c.add(modifierBtn);
    
}       
       
        
        listeAvisForm.add(c);
    }
    // Bouton de retour
    Button btnRetour = new Button("Retour");
    btnRetour.addActionListener(ev -> {
        // Retourner à la page de la liste des catégories
        
        new ListCategorieForm(theme).show();
    });
    listeAvisForm.add(btnRetour);
    
    // Utiliser un renderer pour l'affichage des éléments de liste avec un style défini dans le fichier theme.res
    listeAvisForm.getContentPane().setUIID("ListeAvisRenderer");
    listeAvisForm.getContentPane().setScrollVisible(false);
    listeAvisForm.getContentPane().setScrollableY(true);
    listeAvisForm.show();
});

    Button ajouterAvisBtn = new Button("Ajouter un avis");
ajouterAvisBtn.addActionListener(e -> {
    // Création de l'instance de la fenêtre d'ajout d'avis en passant la ressource et l'objet categorie_avis
    AjoutAvisForm ajoutAvisForm = new AjoutAvisForm(theme, rec);
    // Affichage de la fenêtre d'ajout d'avis
    ajoutAvisForm.show();
});

    cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
            BoxLayout.encloseX(nomTxt),
            BoxLayout.encloseX(btnAfficher, ajouterAvisBtn)
    ));

    add(cnt);
}


     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }

}

