/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import codingbeasts.doulicha.entities.categorie_avis;
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
public class ServiceCatégorie {
        public static ServiceCatégorie instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceCatégorie getInstance() {
        if(instance == null )
            instance = new ServiceCatégorie();
        return instance ;
    }
    
    
    
    public ServiceCatégorie() {
        req = new ConnectionRequest();
        
    }
    
      public ArrayList<categorie_avis>affichageCategorie() {
        ArrayList<categorie_avis> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/categorie/list";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapCategorie = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCategorie.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        categorie_avis re = new categorie_avis();
                        
                        //dima id fi codename one float 5outhouha
                        
                        
                        float id = Float.parseFloat(obj.get("idCategorie").toString());
                        
                        String nomCategorieAvis = obj.get("nomCategorie").toString();
                        
                        
                        
                        re.setNom_categorie(nomCategorieAvis);
                        re.setID_categorie((int)id);
                        
                        
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
