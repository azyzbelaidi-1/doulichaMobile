/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;
import codingbeasts.doulicha.entities.Utilisateur;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import codingbeasts.doulicha.entities.avis;
import codingbeasts.doulicha.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codename1.io.ConnectionRequest;

/**
 *
 * @author HP
 */
public class ServiceAvis {
         //singleton 
    public static ServiceAvis instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceAvis getInstance() {
        if(instance == null )
            instance = new ServiceAvis();
        return instance ;
    }
    
    
    
    public ServiceAvis() {
        req = new ConnectionRequest();
        
    }
    
    
public ArrayList<avis> affichageAvis(int idCategorie) {
    ArrayList<avis> result = new ArrayList<>();

    String url = Statics.BASE_URL + "/afficher/categorie/" + idCategorie + "/avis";
    req.setUrl(url);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp;
            jsonp = new JSONParser();

            try {
                Map<String,Object> mapAvis = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapAvis.get("root");

                for(Map<String, Object> obj : listOfMaps) {
                    avis av = new avis();
                    Object user = obj.get("idUser");
                    System.out.println("test1"+user);
                    Map<String, Object> userMap = (Map<String, Object>) user;
             Object[] userArray = userMap.entrySet().toArray();
             Map.Entry<String, Object> deuxiemePaie = (Map.Entry<String, Object>) userArray[0];
             Object valeur= userMap.get("idUser");
                    System.out.println(valeur);
             //Object idUser = valeur.getValue();
                 //   System.out.println("test2"+idUser);
                    Float idUser2=Float.parseFloat(valeur.toString());
                    System.out.println("test3"+idUser2);
                    int idUser3= ((int)Math.round(idUser2));
                    System.out.println(idUser3);
                    float id = Float.parseFloat(obj.get("idAvis").toString());
                    String contenuAvis = obj.get("contenuAvis").toString();
                    float note = Float.parseFloat(obj.get("noteAvis").toString());
                    String typeAvis = obj.get("typeAvis").toString();

                    
                    av.setContenu_avis(contenuAvis);
                    av.setNote_avis((int)note);
                    av.setType_avis(typeAvis);
                    av.setID_avis((int)id);
                    av.setID_user(idUser3);
                    //System.out.println("testFinael"+idUser2);

                    result.add(av);
                }

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return result;
}
    public void ajouterAvis(int idCategorie, int noteAvis, String contenuAvis, String typeAvis) {
    // Construction de l'URL de la requête POST
    String url = Statics.BASE_URL + "/"+idCategorie+"/avis";
    
    // Création d'un objet ConnectionRequest pour gérer la requête HTTP
    ConnectionRequest request = new ConnectionRequest(url, false);
    
    // Ajout des paramètres de la requête (correspondant aux champs de l'objet Avis)
    Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
    int idUserConnecte=UserConnecte.getID_user();
    request.addArgument("id", Integer.toString(idUserConnecte));
    request.addArgument("note", Integer.toString(noteAvis));
    request.addArgument("contenu", contenuAvis);
    request.addArgument("type", typeAvis);
    request.addArgument("idLogement", Integer.toString(52));
    request.addArgument("idEvent", Integer.toString(10));
    
    // Ajout d'un listener pour gérer la réponse du serveur
    request.addResponseListener((e) -> {
        if (request.getResponseCode() == 200) {
            // Si la réponse est 200 OK, la requête a réussi
            String str = new String(request.getResponseData());
            System.out.println("Ajout d'avis réussi : " + str);
        } else {
            // Si la réponse est autre que 200 OK, il y a eu une erreur
            System.err.println("Erreur lors de l'ajout d'avis : " + request.getResponseErrorMessage());
        }
    });
    
    // Envoi de la requête HTTP
    NetworkManager.getInstance().addToQueueAndWait(request);
}
    
public boolean supprimerAvis(int idAvis) {
    // Construction de l'URL de la requête DELETE
    String url = Statics.BASE_URL + "/avissupprimer/" + idAvis;
    
    
    req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
    
    // Envoi de la requête HTTP
    NetworkManager.getInstance().addToQueueAndWait(req);
    return  resultOk;
}
public void modifierAvis(int idavis, String contenuAvis) {
    String url = Statics.BASE_URL + "/modifieravis/" + idavis; 
    ConnectionRequest req = new ConnectionRequest(url, false);
    
    req.addArgument("contenuAvis", contenuAvis);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
    });
    
    req.setHttpMethod("PUT");
    req.setFailSilently(true);
    
    NetworkManager.getInstance().addToQueueAndWait(req);
}



}
