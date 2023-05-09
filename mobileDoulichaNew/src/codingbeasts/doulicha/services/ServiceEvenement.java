/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;


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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import codingbeasts.doulicha.entities.evenement;
import codingbeasts.doulicha.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
            System.out.println("test"+json);
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
                 p.setLieu_event(obj.get("lieuEvent").toString());
                  p.setType_event(obj.get("typeEvent").toString());
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
        String url = Statics.BASE_URL+"/evenement/listEventmobile";
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
    
    
    
public Image generateQRCodeImageCAL(String text, int size) {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = null;
   int icalsize=250;
    try {
        bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, icalsize, icalsize);
    } catch (WriterException e) {
        e.printStackTrace();
    }
    int width = bitMatrix.getWidth();
    int height = bitMatrix.getHeight();
    int[] pixels = new int[width * height];
    for (int y = 0; y < height; y++) {
        int offset = y * width;
        for (int x = 0; x < width; x++) {
            pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
        }
    }
    Image image = Image.createImage(pixels, width, height);
    return image;
}
    

   public List<evenement> getEventsByDate(Date date) throws ParseException {
    try {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/events/bydatemobile/" + date);
        con.setHttpMethod("GET");
        con.setContentType("application/json");

        NetworkManager.getInstance().addToQueueAndWait(con);

        if (con.getResponseCode() == 200) {
            JSONParser parser = new JSONParser();
            Map<String, Object> eventsJson = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(con.getResponseData()), "UTF-8"));

            List<evenement> events = new ArrayList<>();
            List<Map<String, Object>> eventsListJson = (List<Map<String, Object>>) eventsJson.get("root");

            for (Map<String, Object> eventJson : eventsListJson) {
                evenement event = new evenement();
                event.setID_event((int) Float.parseFloat(eventJson.get("idvent").toString()));
                event.setNom_event(eventJson.get("nomEvent").toString());
                event.setDescription_event(eventJson.get("descriptionEvent").toString());
                event.setLieu_event(eventJson.get("lieuEvent").toString());
               
                
                  try {
     String dateDebutString = eventJson.get("datdebutEvent").toString();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date dateDebut = dateFormat.parse(dateDebutString);

    // Formater la date
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
    String dateString2 = dateFormat2.format(dateDebut);

    event.setDateDebut_event(dateString2);
} catch (ParseException e) {
    // Traiter l'erreur ici
    e.printStackTrace();
}
                  
                   try {
     String dateFinString = eventJson.get("datefinEvent").toString();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      Date dateFin = dateFormat.parse(dateFinString);

    // Formater la date
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
    String dateString2 = dateFormat2.format(dateFin);

    event.setDateDebut_event(dateString2);
} catch (ParseException e) {
    // Traiter l'erreur ici
    e.printStackTrace();
}
  
              
                event.setImage_event(eventJson.get("image").toString());
                event.setPrix_event((float) Float.parseFloat(eventJson.get("prix").toString()));

                events.add(event);
            }

            return events;
        }
    } catch (IOException ex) {
        System.out.println("test1");
    }

    return null;
}
   
      
}
