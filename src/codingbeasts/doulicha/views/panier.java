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
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
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
import codingbeasts.doulicha.entities.Produit;
import codingbeasts.doulicha.services.ServiceProduit;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.util.List;
import codingbeasts.doulicha.services.ServiceCommande;
import codingbeasts.doulicha.services.TwilioSMS;
import java.util.ArrayList;

/**
 *
 * @author aziz
 */
public class panier extends SideMenu {
    private Resources theme;
    
    private static panier instance ;
    private static List<Produit> panier = new ArrayList<>();
    private Container produitsContainer;
    

    private panier() {}

public static panier getInstance() {
    if (instance == null) {
        instance = new panier();
    }
        return instance;
}

public panier(Resources res) {
    super(new BorderLayout());
    setTitle("Panier");
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    getToolbar().addCommandToRightBar("Retour", null, e -> new Listprods(res).show());

    majListe();
}

public void ajouterProduit(Produit produit) {
    panier.add(produit);
    majListe();
}

//public void a() {
//    System.out.println(panier);
//}

public void supprimerProduit(Produit produit) {
    panier.remove(produit);
    majListe();
}

public void vider() {
    panier.clear();
    majListe();
}

public List<Produit> getProduits() {
    return panier;
}


public void majListe() {
    System.out.println("ena moch lenaaaaaa " + getProduits());
    
        for (Produit produit : panier) {
            
            setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.yCenter());
    int a = produit.getID_produit();
    String b =produit.getCategorie_produit();
    int c=produit.getQuantite_produit();
    double d= produit.getPrixUachat_produit();
    Label SLnom = new Label(produit.getLibelle_produit());
    Label SLprix = new Label(Float.toString((float) produit.getPrixUvente_produit())+" Dinars");
    Button SLsup = new Button("supprimer du panier");
   SLsup.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      
        Produit p = produit;// Récupérer le produit sélectionné
        supprimerProduit(p);
        cnt1.revalidate();
        new panier(theme).show();
        
        
        
    }
    });
   
    
    
    EncodedImage enca = EncodedImage.createFromImage(Image.createImage(400, 400), true);
        String urla = "http://127.0.0.1:8000/img/"+produit.getImage_produit();
         System.out.println(urla);
       ImageViewer imga = new ImageViewer(URLImage.createToStorage(enca, urla.substring(urla.lastIndexOf("/")+1, urla.length()), urla,URLImage.RESIZE_SCALE_TO_FILL));



//

//
// Ajouter le label pour le nom du produit
Label nomLabel = new Label(produit.getLibelle_produit());
nomLabel.setUIID("Label"); // Optionnel, pour appliquer un style à l'étiquette


// Ajouter le label pour le prix du produit
Label prixLabel = new Label(Float.toString((float) produit.getPrixUvente_produit())+" Dinars");
prixLabel.setUIID("Label"); // Optionnel, pour appliquer un style à l'étiquette

//

cnt2.add(imga);
cnt2.add(SLnom);
cnt2.add(SLprix);
cnt2.add(SLsup);
//
cnt1.add(cnt2);
add(cnt1);



        
        
        // Rafraîchir l'affichage
        cnt1.revalidate();
    }
        Container cnt3 = new Container(new FlowLayout(CENTER));
    Button validerCommandeButton = new Button("Valider la commande");
    Button map = new Button("MAP");
    cnt3.add(validerCommandeButton);
    cnt3.add(map);
    validerCommandeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            // Ajouter ici le code pour valider la commande
            ServiceCommande sc = new ServiceCommande(); 
            sc.ajoutercomm();
            //TwilioSMS.send("+21655983309","Hello votre commande est validé. On vous contactera pour plus d'information le plus tot possible.");
            panier.clear();
            Dialog.show("Commande validé", "", "OK", null);
            new panier(theme).show();
        }
    });
    map.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            new MapForm();
        }
    });
    add(cnt3);
        
   
       
}



@Override
protected void showOtherForm(Resources res) {
    new Home(res).show();
}
}