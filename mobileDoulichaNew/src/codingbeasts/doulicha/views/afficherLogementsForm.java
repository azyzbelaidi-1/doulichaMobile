/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import codingbeasts.doulicha.entities.Logement;

import com.codename1.components.ImageViewer;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;


import com.codename1.maps.Coord;
import com.codename1.maps.MapComponent;


import com.codename1.maps.layers.PointLayer;
import com.codename1.maps.providers.OpenStreetMapProvider;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import codingbeasts.doulicha.services.MapService;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import java.util.Timer;
import java.util.TimerTask;



/**
 *
 * @author marie
 */
public class afficherLogementsForm extends SideMenu {
    public afficherLogementsForm(Resources res){
    
     super("Liste des Logements", BoxLayout.y());
      Toolbar tb = getToolbar();
      tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
     // Récupération des données JSON depuis l'URL
        String url = "http://127.0.0.1:8000/logement/afficherLogementsMobile";
        MultipartRequest request = new MultipartRequest();
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener((NetworkEvent e) -> {
            try {
                byte[] data = request.getResponseData();
                if (data != null) {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> logements = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                 if (logements != null) {
        
       /* // Créer une liste de logements factice pour cet exemple
        ArrayList<Logement> logements = new ArrayList<>();
        logements.add(new Logement("DARTEST","Appartement en bord de mer", "123 Rue de la Plage", 75,4, "Appartement", "appartement.jpg"));
        logements.add(new Logement("AAA","Appartement en bord de mer", "123 Rue de la Plage", 75,4, "Appartement", "appartement.jpg"));*/
        
        
        // Créer un conteneur pour la liste de logements
        Container logementsContainer = new Container(BoxLayout.y());
        //****************
        //prepare field
        Button retourrButton= new Button("Retour");
            logementsContainer.add(retourrButton);
retourrButton.addActionListener(ret->
{this.showBack();
        });
TextField searchField;
searchField = new TextField("", "Rechercher...");
logementsContainer.add(searchField);
    
searchField.addDataChangedListener((i1, i2) -> {
    Timer timer = new Timer();
     timer.schedule(new TimerTask() {
        @Override
        public void run() {
    Button retourButton= new Button("Retour");
retourButton.addActionListener(ret->
{ new afficherLogementsForm(res).show();

        });
    System.out.println("aaaaaaaaaaaaaaaa");
    Form rechercheForm = new Form();
    Container logementsContainerR = new Container();
    logementsContainerR.add(retourButton);
    String text = searchField.getText();
    //removeAll();
    logementsContainer.removeAll();
     for (Map<String, Object> logement : (ArrayList<Map<String, Object>>) logements.get("logements")) {
        if (((String)logement.get("nomLogement")).toLowerCase().contains(text.toLowerCase())) {
            System.out.println(logement);
        //****************
         
      
            // Créer un conteneur pour chaque logement
            Container logementContainerR = new Container(BoxLayout.y());
            
           
            
            // Ajouter l'image du logement
                        String imageUrl = "http://localhost:8000/assets/images/Logement/" + (String) logement.get("imageLogement");
                        
                        // Création de l'image à partir de l'URL
                        EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);

                        URLImage urlImage = URLImage.createToStorage(placeHolder, (String) logement.get("imageLogement"), imageUrl);

                        EncodedImage scaledImage = urlImage.scaledEncoded(500, 500);

                        ImageViewer imageViewer = new ImageViewer(scaledImage);
                        Container imageContainer = new Container(new BorderLayout());
                        imageContainer.add(BorderLayout.CENTER, imageViewer);
                        logementContainerR.add(imageContainer);
            
           

             // Ajouter le nom du logement
            Label nomLabel = new Label((String) logement.get("nomLogement"));
            System.out.println(nomLabel);
            nomLabel.getStyle().setFgColor(0x000000);
            nomLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            
            logementContainerR.add(nomLabel);
            // Ajouter l'adresse du logement
            TextArea adresseTextArea= new TextArea((String) logement.get("adresseLogement"));
            logementContainerR.add(adresseTextArea);
            
            // Ajouter le prix du logement
            Label prixLabel = new Label("Prix: " + (Double)logement.get("prixnuiteeLogement") + " DT");
            logementContainerR.add(prixLabel);
            
            // Ajouter le type de logement
            Label typeLabel = new Label("Type: " + (String) logement.get("typeLogement"));
            logementContainerR.add(typeLabel);
            
            // Ajouter la capacité du logement
            Label capaciteLabel = new Label("Capacité: " + (Double) logement.get("capaciteLogement") + " personnes");
            logementContainerR.add(capaciteLabel);
           
            
            // Ajouter un bouton pour voir les détails du logement
            Button detailButton = new Button("Voir les détails");
            int id = (int) Math.round((double) logement.get("idLogement"));
            DetailLogementForm DetailLogementForm= new DetailLogementForm(id, res);
            detailButton.addActionListener(e1 ->  DetailLogementForm.show());
                logementContainerR.add(detailButton);
            //detailButton.addActionListener(e -> new DetailLogementForm(logement).show());
         
            
            // Ajouter le conteneur du logement au conteneur principal
            logementsContainerR.add(logementContainerR);
            
        }
        
       
        
                 }
     rechercheForm.add(logementsContainerR);
            rechercheForm.show();
            logementsContainerR.add(new Label("")); // espace vide pour la séparation
            logementsContainerR.revalidate();
            }
    }, 500);
    
});
//***************
 for (Map<String, Object> logement : (ArrayList<Map<String, Object>>) logements.get("logements")) {
       
        
        //****************
         
      
            // Créer un conteneur pour chaque logement
            Container logementContainer = new Container(BoxLayout.y());


           
            
            // Ajouter l'image du logement
                        String imageUrl = "http://localhost:8000/assets/images/Logement/" + (String) logement.get("imageLogement");
                        
                        // Création de l'image à partir de l'URL
                        EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);

                        URLImage urlImage = URLImage.createToStorage(placeHolder, (String) logement.get("imageLogement"), imageUrl);

                        EncodedImage scaledImage = urlImage.scaledEncoded(500, 500);

                        ImageViewer imageViewer = new ImageViewer(scaledImage);
                        Container imageContainer = new Container(new BorderLayout());
                        imageContainer.add(BorderLayout.CENTER, imageViewer);
                        logementContainer.add(imageContainer);
            
           

             // Ajouter le nom du logement
            Label nomLabel = new Label((String) logement.get("nomLogement"));
            nomLabel.getStyle().setFgColor(0x000000);
            nomLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            
            logementContainer.add(nomLabel);
            // Ajouter l'adresse du logement
            TextArea adresseTextArea= new TextArea((String) logement.get("adresseLogement"));
            logementContainer.add(adresseTextArea);
            
            // Ajouter le prix du logement
            Label prixLabel = new Label("Prix: " + (Double)logement.get("prixnuiteeLogement") + " DT");
            logementContainer.add(prixLabel);
            
            // Ajouter le type de logement
            Label typeLabel = new Label("Type: " + (String) logement.get("typeLogement"));
            logementContainer.add(typeLabel);
            
            // Ajouter la capacité du logement
            Label capaciteLabel = new Label("Capacité: " + (Double) logement.get("capaciteLogement") + " personnes");
            logementContainer.add(capaciteLabel);
           

            
            ////
            
            // Ajouter un bouton pour voir les détails du logement
            Button detailButton = new Button("Voir les détails");
            int id = (int) Math.round((double) logement.get("idLogement"));
            DetailLogementForm DetailLogementForm= new DetailLogementForm(id, res);
            detailButton.addActionListener(e1 ->  DetailLogementForm.show());
                logementContainer.add(detailButton);
            //detailButton.addActionListener(e -> new DetailLogementForm(logement).show());
         
            
            // Ajouter le conteneur du logement au conteneur principal
            logementsContainer.add(logementContainer);
        }
        
        // Ajouter le conteneur de la liste de logements au formulaire
        add(logementsContainer);
        
                 
//*****************

                 }}
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
});
        request.setFailSilently(true);
        request.setDuplicateSupported(true);
        request.setTimeout(20000);
        NetworkManager.getInstance().addToQueue(request);
                }
    
    
     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}
