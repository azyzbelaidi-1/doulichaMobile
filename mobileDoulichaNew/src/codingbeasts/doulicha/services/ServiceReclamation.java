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
import codingbeasts.doulicha.entities.reclamation;
import codingbeasts.doulicha.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author HP
 */
public class ServiceReclamation {
        //singleton 
    public static ServiceReclamation instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceReclamation getInstance() {
        if(instance == null )
            instance = new ServiceReclamation();
        return instance ;
    }
    
    
    
    public ServiceReclamation() {
        req = new ConnectionRequest();
        
    }
        public void ajoutReclamation(reclamation reclamation) {
        
          Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
          int idUserConnecte=UserConnecte.getID_user();
    
        String url =Statics.BASE_URL+"/reclamation/add?id="+idUserConnecte+"&contenuReclamation="+reclamation.getContenu_reclamation(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
        
        public ArrayList<reclamation>affichageReclamations() {
        ArrayList<reclamation> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/liste/reclamations";
         req.setUrl(url);
          Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
          int idUserConnecte=UserConnecte.getID_user();
          req.addArgument("idUser", String.valueOf(idUserConnecte));
        req.setPost(true);

     
    NetworkManager.getInstance().addToQueue(req);
       
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        reclamation re = new reclamation();
                        
                        //dima id fi codename one float 5outhouha
                        
                        
                        
                        
                        String contenuReclamation = obj.get("contenuReclamation").toString();
                        float etat = Float.parseFloat(obj.get("etatReclamation").toString());
                        
                        
                        re.setContenu_reclamation(contenuReclamation);
                        re.setEtat_reclamation((int)etat);
                        
                        //Date 
                        //String DateConverter =  obj.get("dateReclamation").toString().substring(obj.get("dateReclamation").toString().indexOf("timestamp") + 10 , obj.get("dateReclamation").toString().lastIndexOf("}"));
                        
                        //Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                        
                        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        //String dateString = formatter.format(currentTime);
                        //re.setDate_reclamation(dateString);
                        
                        //insert data into ArrayList result
String dateString = obj.get("dateReclamation").toString();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
Date dateReclamation = dateFormat.parse(dateString);

// Formater la date
SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
String dateString2 = dateFormat2.format(dateReclamation);

re.setDate_reclamation(dateString2);
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
    
}
