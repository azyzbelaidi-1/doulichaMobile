/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import codingbeasts.doulicha.services.MapService;
import com.codename1.components.ImageViewer;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


/**
 *
 * @author marie
 */
public class DetailLogementForm extends Form {
    
    public DetailLogementForm(int id, Resources res){
  
    super("Détails du logement");
        
        // Récupération des données JSON depuis l'URL
        String url = "http://127.0.0.1:8000/logement/detailLogementMobile/" + id;
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener((e) -> {
            try {
                byte[] data = request.getResponseData();
                if (data != null) {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> logement = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                    if(logement!= null)
                    {
                     logement= (Map<String, Object>) logement.get("logement");
                                        
                    // Créer un conteneur pour les détails du logement
                    Container detailsContainer = new Container(BoxLayout.y());
                    
                    
                    
                    // Ajouter l'image du logement
                    String imageUrl = "http://localhost:8000/assets/images/Logement/" + (String) logement.get("imageLogement");
                    System.out.println(imageUrl);
                    // Création de l'image à partir de l'URL
                    EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);
                    URLImage urlImage = URLImage.createToStorage(placeHolder, (String) logement.get("imageLogement"), imageUrl);
                     EncodedImage scaledImage = urlImage.scaledEncoded(500, 500);
                    ImageViewer imageViewer = new ImageViewer(scaledImage);
                    detailsContainer.add(imageViewer);
                    
                    // Ajouter le nom du logement
                    String nom = (String) logement.get("nomLogement");
                    Label nomLabel = new Label(nom);
                    nomLabel.getStyle().setFgColor(0x000000);
                    nomLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
                    detailsContainer.add(nomLabel);
                    // Ajouter la description du logement
                    String description = (String) logement.get("descriptionLogement");
                    TextArea descriptionTextArea = new TextArea(description);
                    detailsContainer.add(descriptionTextArea);
                    
                    // Ajouter l'adresse du logement
                    String adresse = (String) logement.get("adresseLogement");
                    TextArea adresseTextArea = new TextArea(adresse);
                    detailsContainer.add(adresseTextArea);
                    
                    // Ajouter le type de logement
                    String type = (String) logement.get("typeLogement");
                    Label typeLabel = new Label("Type: " + type);
                    detailsContainer.add(typeLabel);
                    
                    // Ajouter le prix du logement
                    double prix = (Double) logement.get("prixnuiteeLogement");
                    Label prixLabel = new Label("Prix par nuitée: " + prix + " DT");
                    detailsContainer.add(prixLabel);
                    
                    // Ajouter la capacité du logement
                    double capacite = (Double) logement.get("capaciteLogement");
                    Label capaciteLabel = new Label("Capacité: " + capacite + " personne(s)");
                    detailsContainer.add(capaciteLabel);
                    
                     //MAP
            // Récupérer l'adresse du logement à afficher
String adresseLogement = (String) logement.get("adresseLogement");
MapService mapservice = new MapService();
// Obtenir les coordonnées géographiques à partir de l'adresse
Double[] coords = mapservice.getCoordinatesFromAddress(adresseLogement);
System.out.println("LatitudeA: " + coords[0] + ", LongitudeA: " + coords[1]);
// Créer la carte
// Créer un objet Coord avec les coordonnées géographiques
Coord coord = new Coord(coords[0], coords[1]);
     System.out.println("AAAAAA"+coord);

// Créer un objet MapContainer en utilisant les coordonnées géographiques
MapContainer map = new MapContainer();
map.setCameraPosition(coord);
int screenWidth = Display.getInstance().getDisplayWidth();
int screenHeight = Display.getInstance().getDisplayHeight();
     System.out.println(screenHeight);
     System.out.println(screenWidth);
int mapWidth = screenWidth /10;
int mapHeight = screenHeight /10;
     System.out.println(mapWidth);
     System.out.println(mapHeight);


map.setSize(new Dimension(mapWidth, mapHeight));
map.zoom(coord, 15);
// Ajouter un marqueur pour l'emplacement du logement
// Charger l'image du marqueur
Image markerImage = Image.createImage("/marker.png");
markerImage.scaled(10, 10);
// Créer un ImageViewer à partir de l'image du marqueur


ImageViewer markerImageViewer = new ImageViewer(markerImage.scaled(100, 100));

map.addMarker(
   markerImageViewer,
    coord    
    );
   Container containerMap = new Container(new BoxLayout(BoxLayout.Y_AXIS));
containerMap.setHeight(110);
containerMap.setWidth(240);

// Ajouter la map dans le container
containerMap.add(map);
detailsContainer.add(containerMap);


                    
                    // Ajouter un bouton pour réserver le logement
            Button reserverButton = new Button("Réserver");
             Button annulerButton = new Button("Retour");
             int idd = (int) Math.round((double) logement.get("idLogement"));
            reserverButton.addActionListener(e1 -> new ReserverLogementForm(idd, res).show());
            annulerButton.addActionListener(e1 -> new afficherLogementsForm(res).show());
           
            //reserverButton.addActionListener(e1 -> new DetailLogementForm(id).show());
                detailsContainer.add(reserverButton);
                detailsContainer.add(annulerButton);
                    
                    // Ajouter le conteneur des détails au formulaire
                    add(detailsContainer);
                }
                }      
        } catch (IOException ex) {
         ex.printStackTrace();
        }
        });
        request.setFailSilently(true);
        request.setDuplicateSupported(true);
        request.setTimeout(20000);
        NetworkManager.getInstance().addToQueue(request);
        
     
    }
   
}
