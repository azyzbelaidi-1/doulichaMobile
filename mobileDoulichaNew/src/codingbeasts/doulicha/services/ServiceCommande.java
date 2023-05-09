/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;

import codingbeasts.doulicha.entities.Commande;
import codingbeasts.doulicha.entities.Produit;
import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.utils.Statics;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import static com.codename1.ui.BrowserComponent.JSType.get;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

//import static com.codename1.ui.BrowserComponent.JSType.get;
//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 *
 * @author aziz
 */
public class ServiceCommande { //singleton
    public static ServiceCommande instance =null;

    //init conn request
    private ConnectionRequest rep;
    
    public static ServiceCommande getInstance(){
        if(instance == null)
            instance = new ServiceCommande();
        return instance;
    }   
     public ServiceCommande(){
        rep = new ConnectionRequest();

}  
 
public void ajoutercomm() {
    Commande cmd = new Commande(); // n'incluez pas l'ID de commande ici
    System.out.println("dkhaaaaaaaaaaaaaaaaaaaaaaaaaaalt");
    String url = Statics.BASE_URL + "/addcmdJSON/new"; // Ne pas inclure les paramètres ici

    // Créer la demande de connexion
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(true); // Méthode de demande GET
     Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
                int idUserConnecte=UserConnecte.getID_user();
                System.out.println(idUserConnecte);
                req.addArgument("idUser", String.valueOf(idUserConnecte));
   
    req.addArgument("etatCommande", "0"); // État de commande = 0
    req.addArgument("dateCommande", String.valueOf(new Date().getTime())); // Utilisez getTime() pour récupérer la date en millisecondes

    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        JSONParser parser = new JSONParser();
        try {
            Map<String, Object> json = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(str.getBytes())));
            int idCommande = ((Double)json.get("idCommande")).intValue();
            cmd.setID_commande(idCommande);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
}
}
