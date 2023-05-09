/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package codingbeasts.doulicha.views;

import static com.codename1.charts.compat.Paint.Join.MITER;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.Produit;
import codingbeasts.doulicha.services.ServiceCommande;
import java.io.IOException;
import java.util.List;
import codingbeasts.doulicha.services.ServiceProduit;
import java.util.ArrayList;
import codingbeasts.doulicha.views.panier;
import codingbeasts.doulicha.services.TwilioSMS;
/**
 *
 * @author aziz
 */
public class Listprods extends SideMenu {
    Listprods instance;
    private List<Produit> listprod;
     EncodedImage enc;
    private Form form ;
//    private Toolbar tb;
    private Container produits;
    Form current;





    
    public Listprods(Resources res) {
        instance=this;
//        super(BoxLayout.y());
        Toolbar tb = getToolbar();
//		tb.setUIID("Toolbar");
                
           setTitle("Liste des produits");   
           
        
//        setTitle("Liste des promotions");
        
        
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> new Home(res).show());
//        SpanLabel sp = new SpanLabel();
        List<Produit> list = ServiceProduit.getInstance().getAllprod();

        for (Produit p : list){
            System.out.println( "kkkkkkkk   "+p);
    setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.yCenter());
    int a = p.getID_produit();
    String b =p.getCategorie_produit();
    int c=p.getQuantite_produit();
    double d= p.getPrixUachat_produit();
    Label SLnom = new Label(p.getLibelle_produit());
    Label SLprix = new Label(Float.toString((float) p.getPrixUvente_produit())+" Dinars");
    Button SLvoir = new Button("ajouter au panier");
   
   
    SLvoir.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        Produit produit = p;// Récupérer le produit sélectionné
        panier.getInstance().ajouterProduit(p);
        Dialog.show("Produit ajouté au panier", "", "OK", null);
        panier.getInstance().majListe();
//        panier.getInstance().a();
        panier.getInstance().getProduits();
       
    }
    });
    
       EncodedImage enca = EncodedImage.createFromImage(Image.createImage(400, 400), true);
       String urla = "http://127.0.0.1:8000/img/"+p.getImage_produit();
       System.out.println(urla);
       ImageViewer imga = new ImageViewer(URLImage.createToStorage(enca, urla.substring(urla.lastIndexOf("/")+1, urla.length()), urla,URLImage.RESIZE_SCALE_TO_FILL));



//

//
// Ajouter le label pour le nom du produit
Label nomLabel = new Label(p.getLibelle_produit());
nomLabel.setUIID("Label"); // Optionnel, pour appliquer un style à l'étiquette


// Ajouter le label pour le prix du produit
Label prixLabel = new Label(Float.toString((float) p.getPrixUvente_produit())+" Dinars");
prixLabel.setUIID("Label"); // Optionnel, pour appliquer un style à l'étiquette

//

cnt2.add(imga);
cnt2.add(SLnom);
cnt2.add(SLprix);
cnt2.add(SLvoir);
//
cnt1.add(cnt2);
add(cnt1);
}


 


    
}
@Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
         
    }

}