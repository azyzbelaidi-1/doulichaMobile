/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;

import codingbeasts.doulicha.entities.Utilisateur;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.ui.Image;
import static com.codename1.ui.events.ActionEvent.Type.Response;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Callback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import codingbeasts.doulicha.entities.evenement;
import codingbeasts.doulicha.entities.participation_evenement;
import codingbeasts.doulicha.utils.Statics;
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
    
String url = Statics.BASE_URL+"/evenement/participerMobile/"+idEvent;
        req.setUrl(url);
        Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
                int idUserConnecte=UserConnecte.getID_user();
        //int idUserConnecte= 1;
            req.addArgument("idUser", String.valueOf(idUserConnecte));
        
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
        
        String url = Statics.BASE_URL+"/evenement/showParticipationsMobile";
        req.setUrl(url);
        Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
        int idUserConnecte=UserConnecte.getID_user();
        req.addArgument("idUser", String.valueOf(idUserConnecte));
        req.setPost(true);
                
        req.setFailSilently(true);
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
                
        
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
        String url = Statics.BASE_URL + "/deleteParticipationMobile/"+idParticipation;
        //req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        //req.setHttpMethod("GET");
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
       
/*   
public void generateQrCode(int idParticipation, evenement ev, participation_evenement part) {
       // Générer le QR code
    String codeData = "Nom du participant : " + part.getID_user() + ", ID de la participation: " + idParticipation + ", Nom de l'évènement: " + part.getID_event() + ", Date de début: " + ev.getDateDebut_event()+ ", Date de fin: " + ev.getDateFin_event();
    int size = 250;
    try {
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
            // Encodez la chaîne de texte en une matrice de bits
            BitMatrix bitMatrix = qrCodeWriter.encode(codeData, BarcodeFormat.QR_CODE, 200, 200);
 Image qrCodeImage = new Image();
            // Créez une image BufferedImage à partir de la matrice de bits
            //BufferedImage qrCodeImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
    } catch (WriterException e) {
        e.printStackTrace();
    }
    
    // Créer un lien iCalendar avec les dates de début et de fin de l'évènement
String icalData = "BEGIN:VCALENDAR\n" +
                  "VERSION:2.0\n" +
                  "BEGIN:VEVENT\n" +
                  "SUMMARY:" + part.getID_event() + "\n" +
                  "DTSTART:" + "\n" +
                  "DTEND:" + "\n" +
                  "LOCATION:" + ev.getLieu_event() + "\n" +
                  "END:VEVENT\n" +
                  "END:VCALENDAR";

String icalLink = "data:text/calendar;base64," + icalData;

// Générer le QR code avec le lien iCalendar
String icalFilePath = "src/qrcode/ical_qrcode.png";
int icalSize = 250;
try {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(icalLink, BarcodeFormat.QR_CODE, icalSize, icalSize);
   // MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(icalFilePath));
} catch (WriterException e) {
    e.printStackTrace();
}
}
  */  
  
    public void modifierParticipationEvenement(int idParticipation, String numTel, String nombreParticipation) {

    String url = Statics.BASE_URL + "/editParticipationMobile/" + idParticipation;
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
 /*   
 public List<String> getNumTelsByEvent(int idEvent) throws IOException {
    List<String> numTels = new ArrayList<>();
    String url = Statics.BASE_URL + "/events/numstels/" + idEvent;
    ConnectionRequest con = new ConnectionRequest();
    con.setUrl(url);
    con.setPost(false);
    con.addResponseListener((e) -> {
        try {
            JSONParser parser = new JSONParser();
            Map<String, Object> response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(con.getResponseData()), "UTF-8"));
            ArrayList<String> numTelsArray = (ArrayList<String>) response.get("root");
            numTels.addAll(numTelsArray);
        } catch (IOException ex) {
            System.out.println("Erreur lors de la récupération des numéros de téléphone de l'événement: " + ex.getMessage());
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(con);
    return numTels;
}
*/
    
    
public Image generateQRCodeImage(String text, int size) {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = null;
    try {
        bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
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

    
    
}