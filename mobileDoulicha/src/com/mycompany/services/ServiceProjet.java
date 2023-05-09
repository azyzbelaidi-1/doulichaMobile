/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Projet;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
/**
 *
 * @author Admin
 */
public class ServiceProjet {
  
    
    //singleton 
    public static ServiceProjet instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceProjet getInstance() {
        if(instance == null )
            instance = new ServiceProjet();
        return instance ;
    }
    
    
    
    public ServiceProjet() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutProjet(Projet projet) {
        
        String url =Statics.BASE_URL+"/AjoutProjetmobile?nomProjet="+projet.getNomProjet()+"&descriptionProjet="+projet.getDescriptionProjet()+"&objectifProjet="+projet.getObjectifProjet()+"&etatProjet="+projet.getEtatProjet()+"&imageProjet="+projet.getImageProjet(); 
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    //affichage
    
   public ArrayList<Projet> affichageProjets() {
    ArrayList<Projet> result = new ArrayList<>();

   String url = Statics.BASE_URL + "/ListProjetmobile";
req.setUrl(url);

req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        JSONParser jsonp;
        jsonp = new JSONParser();

        try {
            Map<String, Object> mapProjet = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

            List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapProjet.get("root");

            for (Map<String, Object> obj : listOfMaps) {
                //JSONObject obj = listOfMaps.getJSONObject(i);
                Projet re = new Projet();

                //dima id fi codename one float 5outhouha
                float idProjet =Float.parseFloat(obj.get("idProjet").toString());
                String nomProjet = obj.get("nomProjet").toString();
                String descriptionProjet = obj.get("descriptionProjet").toString();
                float objectifProjet = Float.parseFloat(obj.get("objectifProjet").toString());
                float etatProjet = Float.parseFloat(obj.get("etatProjet").toString());
                String imageProjet = obj.get("imageProjet").toString();

                re.setIdProjet((int) idProjet);
                re.setNomProjet(nomProjet);
                re.setDescriptionProjet(descriptionProjet);
                re.setObjectifProjet((float)objectifProjet);
                re.setEtatProjet((int)etatProjet);
                re.setImageProjet(imageProjet);

               // String dateStr = obj.get("date").toString();
              //  Date date = new Date(Long.parseLong(dateStr) * 1000L);
              //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             //   sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
             //   String formattedDate = sdf.format(date);
               // re.setDate(formattedDate);

                //insert data into ArrayList result
                result.add(re);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
});

NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

return result;

}
   //Delete 
    public boolean deleteProjet(int idProjet ) {
        String url = Statics.BASE_URL +"/deleteProjetmobile/"+idProjet;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
  public boolean modifierProjet(Projet projet) {
    String url = Statics.BASE_URL +"/editProjetmobile?idProjet="+projet.getIdProjet()+"&nomProjet="+projet.getNomProjet()+"&descriptionProjet="+projet.getDescriptionProjet()+"&objectifProjet="+projet.getObjectifProjet()+"&etatProjet="+projet.getEtatProjet()+"&imageProjet="+projet.getImageProjet(); 
    req.setUrl(url);
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
            req.removeResponseListener(this);
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;  
}
public List<Projet> rechercherProjet(String searchTerm) {
    List<Projet> myList = new ArrayList<>();
    for (Projet p : myList) {
        if (p.getNomProjet().toLowerCase().contains(searchTerm.toLowerCase())) {
            myList.add(p);
        }
    }
    return myList;
}
}