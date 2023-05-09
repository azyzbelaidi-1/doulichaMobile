/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.entities.participation_evenement;
import com.mycompany.myapp.utils.Statics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ServiceEvenementParticipation {
    
     //singleton 
    public static ServiceEvenementParticipation instance = null ;
    public static boolean resultOk = true;
    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceEvenementParticipation getInstance() {
        if(instance == null )
            instance = new ServiceEvenementParticipation();
        return instance ;
    }
    
      public ServiceEvenementParticipation() {
        req = new ConnectionRequest();
    }
      
      
      
       //ajout 
    public void ajoutParticipationEvenement(int idEvent, String numTel, String nombreParticipation, int idUser) {
    
String url = Statics.BASE_URL+"/evenement/participer/"+idEvent;
        req.setUrl(url);
        idUser=5;
        req.addArgument("idUser",String.valueOf(idUser));
        req.addArgument("numTel",String.valueOf(numTel));
        req.addArgument("nombreParticipation",String.valueOf(nombreParticipation));
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        req.setPost(true);
                
                req.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    
    
       //affichage
    
    
    public ArrayList<participation_evenement>affichageParticipations() {
        ArrayList<participation_evenement> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/evenement/showParticipations";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapParticipationsEvenement = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapParticipationsEvenement.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        participation_evenement re = new participation_evenement();
                       
                        
                       float id = Float.parseFloat(obj.get("idParticipation").toString());
                re.setID_participation((int) id);
                        float nombreParticipation = Float.parseFloat(obj.get("nombreParticipation").toString());
                         String numTel = obj.get("numTel").toString();
                        
                        
                        re.setNombre_participation((int) nombreParticipation);
                       
                        re.setNum_tel(numTel);
                        
                      
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
    
    
    
      //Delete 
   /* public boolean deleteParticipationEvenement(int IdParticipation ) {
        String url = Statics.BASE_URL +"/deleteParticipation/"+IdParticipation;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    } */
    
       public void deleteParticipationEvenement(int idParticipation) {
        String url = Statics.BASE_URL + "/deleteParticipation/"+idParticipation;
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
  
    public void modifierParticipationEvenement(int idParticipation, String numTel, String nombreParticipation) {

    String url = Statics.BASE_URL + "/editParticipation/" + idParticipation;
    req.setUrl(url);
    
    req.addArgument("numTel", numTel);
    req.addArgument("nombreParticipation", nombreParticipation);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
    });
    
    req.setHttpMethod("PUT");
    req.setFailSilently(true);
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    
}

    
    
    //Update 
  /*  public boolean modifierParticipationEvenement(participation_evenement participation_evenement, int ID_participation) {
      String url = Statics.BASE_URL+"/editParticipation?ID_participation="+ID_participation+"&numTel="+participation_evenement.getNum_tel()+"&nombreParticipation="+participation_evenement.getNombre_participation();

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    } */
    
    
}
