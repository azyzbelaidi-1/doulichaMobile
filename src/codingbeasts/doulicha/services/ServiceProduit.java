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
import codingbeasts.doulicha.entities.Produit;
import codingbeasts.doulicha.utils.statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import codingbeasts.doulicha.views.Listprods;
/**
 *
 * @author aziz
 */
public class ServiceProduit {
    
    //singleton
    public static ServiceProduit instance =null;

    //init conn request
    private ConnectionRequest rep;
    
    public static ServiceProduit getInstance(){
        if(instance == null)
            instance = new ServiceProduit();
        return instance;
    }   

    public ServiceProduit(){
        rep = new ConnectionRequest();

}  
 ArrayList<Produit> listprod = new ArrayList<>();
    ArrayList<Produit> listprom = new ArrayList<>();
    
    //ajouuuuuuuuuuuuuuuuuuuut PRODUIT
    public void ajouterProduit(Produit produit){
        
        String url = statics.BASE_URL+"/addprodJSON/new?libelleProduit="+produit.getLibelle_produit()+
        "&quantiteProduit="+produit.getQuantite_produit()+
        "&prixuventeProduit="+produit.getPrixUvente_produit()+
        "&prixuachatProduit="+produit.getPrixUachat_produit()+
        "&categorieProduit="+produit.getCategorie_produit()+
        "&imageProduit="+produit.getImage_produit();

        rep.setUrl(url);
        rep.addResponseListener((e) -> {
        
            String str = new String(rep.getResponseData());
            System.out.println("data == "+str);
        });
        
       
NetworkManager.getInstance().addToQueueAndWait(rep);

    }

    //afficherrr PRODUIT
  public ArrayList<Produit> afficherProduit() {
    ArrayList<Produit> result = new ArrayList<>();
    String url = statics.BASE_URL + "/afficheProd";
    rep.setUrl(url);
    
    rep.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String,Object> mapProd = jsonp.parseJSON(new CharArrayReader(new String(rep.getResponseData()).toCharArray()));
                List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapProd.get("root");
                
                for(Map<String, Object> obj : listOfMaps) {
                    Produit prod = new Produit();
                    prod.setID_produit(Integer.parseInt(obj.get("ID_produit").toString()));
                    prod.setLibelle_produit(obj.get("libelle_produit").toString());
                    prod.setQuantite_produit(Integer.parseInt(obj.get("quantite_produit").toString()));
                    prod.setPrixUachat_produit(Double.parseDouble(obj.get("prixUachat_produit").toString()));
                    prod.setPrixUvente_produit(Double.parseDouble(obj.get("prixUvente_produit").toString()));
                    prod.setCategorie_produit(obj.get("categorie_produit").toString());
                    prod.setImage_produit(obj.get("image_produit").toString());
                    
                    result.add(prod);
                }
                
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(rep);
    return result;
}

    //detail PRODUIIIIIIIIIIT
    public Produit DetailProduit(int id) {

    Produit produit = new Produit();

    String url = statics.BASE_URL + "/afficheProd/" + id;
    rep.setUrl(url);

    rep.addResponseListener((evt) -> {
        JSONParser jsonp = new JSONParser();
        try {
            Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(rep.getResponseData()).toCharArray()));
            produit.setID_produit((int) obj.get("id"));
            produit.setLibelle_produit(obj.get("libelle_produit").toString());
            produit.setQuantite_produit((int) obj.get("quantite_produit"));
            produit.setPrixUachat_produit(Double.parseDouble(obj.get("prixUachat_produit").toString()));
            produit.setPrixUvente_produit(Double.parseDouble(obj.get("prixUvente_produit").toString()));
            produit.setCategorie_produit(obj.get("categorie_produit").toString());
            produit.setImage_produit(obj.get("image_produit").toString());
        } catch (IOException ex) {
            System.out.println("error related to sql :( " + ex.getMessage());
        }

        System.out.println("data === " + rep.getResponseData());
    });

    NetworkManager.getInstance().addToQueueAndWait(rep);

    return produit;
}

//    //Delete PRODUIIIIIIIIIIIT
//    public boolean deleteProduit(int id ) {
//        String url = statics.BASE_URL +"/deleteStudentJSON/"+id;
//        
//        rep.setUrl(url);
//        
//        rep.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                    
//                    rep.removeResponseCodeListener(this);
//            }
//        });
//        
//        NetworkManager.getInstance().addToQueueAndWait(rep);
//        return resultOk;
//    }
//
//
//    //Update 
//    public boolean modifierProduit(Produit produit) {
//        String url = statics.BASE_URL +"/updateReclamation?id="+produit.getID_produit()+
//        "libelleProduit="+produit.getLibelle_produit()+
//        "&quantiteProduit="+produit.getQuantite_produit()+
//        "&prixuventeProduit="+produit.getPrixUvente_produit()+
//        "&prixuachatProduit="+produit.getPrixUachat_produit()+
//        "&categorieProduit="+produit.getCategorie_produit()+
//        "&imageProduit="+produit.getImage_produit();
//        rep.setUrl(url);
//        
//        rep.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOk = rep.getResponseCode() == 200 ;  // Code response Http 200 ok
//                rep.removeResponseListener(this);
//            }
//        });
//        
//    NetworkManager.getInstance().addToQueueAndWait(rep);//execution ta3 request sinon yet3ada chy dima nal9awha
//    return resultOk;
//        
//    }

        public ArrayList<Produit> getAllprod(){
        ConnectionRequest con = new ConnectionRequest();
        String url = statics.BASE_URL+"/afficheProd";
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
        ServiceProduit ps = new ServiceProduit();
        listprod = ps.liste(new String(con.getResponseData()));
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(con);
              System.out.println("listiniiiiiiiiii "+listprod);
    return listprod;
    }  

 public ArrayList<Produit> liste(String json){
        ArrayList<Produit> lista = new ArrayList<>();
       
        try {
            
            JSONParser j = new JSONParser();
            Map<String, Object> Produits = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println("bechir"+json);
            System.out.println("Lista"+Produits);
            List<Map<String, Object>> list = (List<Map<String, Object>>) Produits.get("root");
  System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
  
            for (Map<String, Object> obj : list) {
               
                Produit p = new Produit();


        p.setID_produit((int) Float.parseFloat(obj.get("idProduit").toString()));
        p.setLibelle_produit(obj.get("libelleProduit").toString());
        p.setQuantite_produit((int) Float.parseFloat(obj.get("quantiteProduit").toString()));
        p.setPrixUachat_produit(Double.parseDouble(obj.get("prixuachatProduit").toString()));
        p.setPrixUvente_produit(Double.parseDouble(obj.get("prixuventeProduit").toString()));
        p.setCategorie_produit(obj.get("categorieProduit").toString());
        p.setImage_produit(obj.get("imageProduit").toString());

                
                lista.add(p);
                
                        

            }
     
        } catch (IOException ex) {
            System.out.println("test1");
        }
        
        System.out.println("MAHA aman ekhdem"+lista);
    return lista;
    
    }
 
    

}
