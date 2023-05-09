/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class ServiceEvenement {
  

    public static ServiceEvenement instance = null ;
    public static boolean resultOk = true;
    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceEvenement getInstance() {
        if(instance == null )
            instance = new ServiceEvenement();
        return instance ;
    }
    
      public ServiceEvenement() {
        req = new ConnectionRequest();
    }
      
          ArrayList<evenement> listEvent = new ArrayList<>();
    ArrayList<evenement> listprom = new ArrayList<>();
//    private List<String> artt;
    
  
    
    
    
    public ArrayList<evenement> liste(String json){
        ArrayList<evenement> lista = new ArrayList<>();
       
        try {
            
            JSONParser j = new JSONParser();
            Map<String, Object> evenements = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println("bechir"+json);
            System.out.println("Lista"+evenements);
            List<Map<String, Object>> list = (List<Map<String, Object>>) evenements.get("root");
  System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
  
            for (Map<String, Object> obj : list) {
               
                evenement p = new evenement();

                float id = Float.parseFloat(obj.get("idEvent").toString());
                p.setID_event((int) id);
                p.setNom_event(obj.get("nomEvent").toString());
                float prix = Float.parseFloat(obj.get("prixEvent").toString());
                p.setPrix_event((int) prix);
                p.setDescription_event(obj.get("descriptionEvent").toString());
                p.setImage_event(obj.get("imageEvent").toString());
                
    try {
    String dateString = obj.get("datedebutEvent").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    Date dateDebutEvent = dateFormat.parse(dateString);

    // Formater la date
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
    String dateString2 = dateFormat2.format(dateDebutEvent);

    p.setDateDebut_event(dateString2);
} catch (ParseException e) {
    // Traiter l'erreur ici
    e.printStackTrace();
}
    
      try {
    String dateString = obj.get("datefinEvent").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    Date dateFinEvent = dateFormat.parse(dateString);

    // Formater la date
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
    String dateString2 = dateFormat2.format(dateFinEvent);

    p.setDateFin_event(dateString2);
} catch (ParseException e) {
    // Traiter l'erreur ici
    e.printStackTrace();
}


                
                lista.add(p);
                    

            }
     
        } catch (IOException ex) {
            System.out.println("test1");
        }
        
        System.out.println("aman ekhdem"+lista);
    return lista;
    
    }
    

     
     /////////////////////
    public ArrayList<evenement> getAllEvents(){
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL+"/evenement/listEvent";
        System.out.println("mouch normal"+url);
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
        ServiceEvenement ps = new ServiceEvenement();
        listEvent = ps.liste(new String(con.getResponseData()));
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(con);
    return listEvent;
    }   
    
    
    

      
      /*    public ArrayList<evenement> parseEvenements(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> evenementsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) evenementsListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                evenement ev = new evenement();
            float ID_event = Float.parseFloat(obj.get("ID_event").toString());
                //get nom_event
                String nomEventText = obj.get("nomEvent").toString();
                StringTokenizer nomEventTokenizer = new StringTokenizer(nomEventText, "=");
                nomEventTokenizer.nextElement();
                String nom_event = nomEventTokenizer.nextElement().toString();
                ev.setNom_event(nom_event.substring(0, nom_event.length() - 1));
                //get description_event
                String descriptionEventText = obj.get("DescriptionEvent").toString();
                StringTokenizer descriptionEventTokenizer = new StringTokenizer(descriptionEventText, "=");
                descriptionEventTokenizer.nextElement();
                String description_event = descriptionEventTokenizer.nextElement().toString();
                 ev.setDescription_event(description_event.substring(0, description_event.length() - 1));
                
                ev.setLieu_event(obj.get("Lieu").toString());
                System.out.print(ev);
                //Ajouter la tâche extraite de la réponse Json à la liste
                events.add(ev);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return events;
    }
    
    

    public ArrayList<evenement> getAllEvents() {
        String url = Statics.BASE_URL + "/evenement/showEventsFront";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvenements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
    */
    
    
    /*
       //affichage
    
    public ArrayList<evenement>affichageEvenements() {
        ArrayList<evenement> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/evenement/showEventsFront";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapEvenements = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapEvenements.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        evenement re = new evenement();
                        
                        //dima id fi codename one float 5outhouha
                        int ID_event = Integer.parseInt(obj.get("ID_event").toString());
                           String nom_event = obj.get("nom_event").toString();
                        String description_event = obj.get("description_event").toString();
                        String lieu_event = obj.get("lieu_event").toString();
                        String type_event = obj.get("type_event").toString();
                        int capacite_event = Integer.parseInt(obj.get("capaciteEvent").toString());
                        String image_event = obj.get("imageEvent").toString();
                        float prix_event = Float.parseFloat(obj.get("prixEvent").toString());

                        
                        re.setID_event(ID_event);
                        re.setDescription_event(description_event);
                        re.setNom_event(nom_event);
                        re.setLieu_event(lieu_event);
                        re.setType_event(type_event);
                        re.setCapacite_event(capacite_event);
                        re.setImage_event(image_event);
                        re.setPrix_event(prix_event);
                        
                        
                        
                                                        //Date de début
                           String dateDebutConverter = obj.get("dateDebut_event").toString().substring(obj.get("dateDebut_event").toString().indexOf("timestamp") + 10, obj.get("dateDebut_event").toString().lastIndexOf("}"));
                           Date currentTime = new Date(Double.valueOf(dateDebutConverter).longValue() * 1000);

                           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                           String dateString = formatter.format(currentTime);

                           try {
                               Date dateDebut = formatter.parse(dateString);
                               re.setDateDebut_event(dateDebut);
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }

                           //Date de fin
                           String dateFinConverter = obj.get("dateFin_event").toString().substring(obj.get("dateFin_event").toString().indexOf("timestamp") + 10, obj.get("dateFin_event").toString().lastIndexOf("}"));
                           Date currentTime2 = new Date(Double.valueOf(dateFinConverter).longValue() * 1000);

                           String dateString2 = formatter.format(currentTime2);

                           try {
                               Date dateFin = formatter.parse(dateString2);
                               re.setDateFin_event(dateFin);
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }

                        
                        
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    } */
    
    /*
      //Delete 
    public boolean deleteEvenement(int ID_event ) {
        String url = Statics.BASE_URL +"/deleteEvent?id="+ID_event;
        
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
    
    
    /*
    //Update 
    public boolean modifierEvenement(evenement evenement) {
        String url = Statics.BASE_URL +"/editEvent?nomEvent="+evenement.getNom_event()+"&descriptionEvent="+evenement.getDescription_event()
                +"&lieuEvent="+evenement.getLieu_event()+"&typeEvent="+evenement.getType_event()
                +"&datedebutEvent="+evenement.getDateDebut_event()+"&datefinEvent="+evenement.getDateFin_event()
                +"&capaciteEvent="+evenement.getCapacite_event()+"&imageEvent="+evenement.getImage_event()
                +"&prixEvent="+evenement.getPrix_event();
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
