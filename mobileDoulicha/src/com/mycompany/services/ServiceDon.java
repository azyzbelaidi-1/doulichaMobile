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
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Don;
import com.mycompany.entities.Projet;
import com.mycompany.gui.Paiement;
import static com.mycompany.services.ServiceProjet.resultOk;
import com.mycompany.utils.Statics;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.TokenCreateParams;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
 
/**
 *
 * @author Admin
 */
public class ServiceDon {
      //singleton 
    public static ServiceDon instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceDon getInstance() {
        if(instance == null )
            instance = new ServiceDon();
        return instance ;
    }
    
    
    
    public ServiceDon() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
   public void ajoutDon(int idProjet, String valeurDon, int idUser, int etatPaiement) {
 
    String url = Statics.BASE_URL + "/AjoutDonmobile/" + idProjet + "?valeurDon=" + valeurDon;

    ConnectionRequest req = new ConnectionRequest(url);
    req.setPost(true);
    req.addArgument("idProjet", String.valueOf(idProjet));
    req.addArgument("valeurDon", valeurDon);
    req.addArgument("idUser", String.valueOf(idUser));
    req.addArgument("etatPaiement", String.valueOf(etatPaiement));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = formatter.format(new Date());
    req.addArgument("date", date);
    req.setFailSilently(true);
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
        System.out.println("data == " + str);
    });
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
}
   
   
 public ArrayList<Don>affichageDons() {
        ArrayList<Don> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/ListDonmobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapDon = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapDon.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        Don re = new Don();
                       float id = Float.parseFloat(obj.get("idDon").toString());
                       re.setIdDon((int) id);
                        float valeurDon = Float.parseFloat(obj.get("valeurDon").toString());
                         re.setValeurDon((int) valeurDon);
                         String dateString = obj.get("dateDon").toString();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
Date dateDon = dateFormat.parse(dateString);

// Formater la date
SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
String dateString2 = dateFormat2.format(dateDon);

re.setDateDon(dateString2);
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
 public void modifierDon(int idDon, String valeurDon) {
    String url = Statics.BASE_URL + "/ModifierDonmobile/" + idDon; 
    ConnectionRequest req = new ConnectionRequest(url, false);
    
    req.addArgument("valeurDon", valeurDon);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
    });
    
    req.setHttpMethod("PUT");
    req.setFailSilently(true);
    
    NetworkManager.getInstance().addToQueueAndWait(req);
}
 public void deleteDon(int idDon) {
        String url = Statics.BASE_URL + "/SupprimerDon/"+idDon;
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
 
 public ArrayList<Don>affichageDonsPaye() {
        ArrayList<Don> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/ListDonPayemobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapDon = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapDon.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        Don re = new Don();
                       float id = Float.parseFloat(obj.get("idDon").toString());
                       re.setIdDon((int) id);
                        float valeurDon = Float.parseFloat(obj.get("valeurDon").toString());
                         re.setValeurDon((int) valeurDon);
                          String dateString = obj.get("dateDon").toString();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
Date dateDon = dateFormat.parse(dateString);

// Formater la date
SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
String dateString2 = dateFormat2.format(dateDon);

re.setDateDon(dateString2);
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
 public void modifierEtatPaiement(int idDon, String etatPaiement) {
    String url = Statics.BASE_URL + "/ModifierDonnmobile/" + idDon;
    ConnectionRequest req = new ConnectionRequest(url, false);
    
    req.addArgument("etatPaiement", etatPaiement);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
    });
    
    req.setHttpMethod("PUT");
    req.setFailSilently(true);
    
    NetworkManager.getInstance().addToQueueAndWait(req);
}

}

    
