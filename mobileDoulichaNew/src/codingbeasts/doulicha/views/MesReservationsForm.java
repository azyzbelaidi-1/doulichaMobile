/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import codingbeasts.doulicha.entities.Logement;
import codingbeasts.doulicha.entities.Reservation_logement;
import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.services.ServicesUtilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import codingbeasts.doulicha.services.serviceLogement;
import com.codename1.io.ConnectionRequest;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Calendar;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.util.Resources;











/**
 *
 * @author marie
 */
public class MesReservationsForm extends SideMenu {
    public MesReservationsForm(Resources res) {
        
        // Titre de la vue
        super("Mes réservations", BoxLayout.y());
          Toolbar tb = getToolbar();
      tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        int idUser= 4;
         String url = "http://127.0.0.1:8000/reservation_logement/afficherMesReservationsMobile/"+ idUser;
        MultipartRequest request = new MultipartRequest();
        request.setUrl(url);
        Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
                int idUserConnecte=UserConnecte.getID_user();
        
         request.addArgument("idUser", String.valueOf(idUserConnecte));
        request.setPost(true);
        request.addResponseListener((e) -> {
            try {
                byte[] data = request.getResponseData();
                if (data != null) {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> reservations = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                    if (reservations.size() == 0) {
            this.add(new Label("Vous n'avez aucune réservation."));
        } else {
                 
                     Container logementsContainer = new Container(BoxLayout.y());
          for (Map<String, Object> reservation : (ArrayList<Map<String, Object>>) reservations.get("mesReservations")) {
      // Créer un conteneur pour chaque logement
            Container reservationContainer = new Container(BoxLayout.y());
              // Nom de logement
              
             Object logement = reservation.get("idLogement");
             Map<String, Object> logementMap = (Map<String, Object>) logement;
             Object[] logementArray = logementMap.entrySet().toArray();
             Map.Entry<String, Object> deuxiemePaie = (Map.Entry<String, Object>) logementArray[1];
             Object nomLogement = deuxiemePaie.getValue();
          
              
               Label nomLogementLabel= new Label("Logement: " + nomLogement);
               System.out.println(nomLogementLabel);
                reservationContainer.add(nomLogementLabel);
                
                // Dates de départ et d'arrivée
                String datearriveeReservationStringA=(String) reservation.get("datearriveeReservation");
SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Date datearriveeA = null;
                try {
                    datearriveeA = dateFormat8.parse(datearriveeReservationStringA);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

// Formater la date
SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-dd");
String datearriveeString3 = dateFormat9.format(datearriveeA);
                Label dateArriveeLabel= new Label("Date d'arrivée: " + datearriveeString3);
                
                System.out.println(dateArriveeLabel);
                reservationContainer.add(dateArriveeLabel);
                
                //DATE DE DEPART
                String datedepartReservationStringA=(String) reservation.get("datedepartReservation");
SimpleDateFormat dateFormat10 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Date datedepartA = null;
                try {
                    datedepartA = dateFormat10.parse(datedepartReservationStringA);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

// Formater la date
SimpleDateFormat dateFormat11 = new SimpleDateFormat("yyyy-MM-dd");
String datedepartString3 = dateFormat11.format(datedepartA);
               Label dateDepartLabel= new Label("Date de départ: " + datedepartString3);
               reservationContainer.add(dateDepartLabel);
                
                // Montant total
               Label totalLabel= new Label("Total: " + (Double) reservation.get("montanttotalReservation") +" DT");
               reservationContainer.add(totalLabel);
                
                // Numéro de téléphone et nombre de personnes
                Label numTelLabel = new Label("Tél: " + (String) reservation.get("numTel"));
                reservationContainer.add(numTelLabel);
                int nbpersonnes = (int) Math.round((double) reservation.get("nbpersonnesReservation"));
                Label PersonnesLabel = new Label ("Personnes: " + nbpersonnes + " Personnes");
                reservationContainer.add(PersonnesLabel);
               

//////////*******************
/* Button PdfButton = new Button("Télécharger ma réservation");
reservationContainer.add(PdfButton);
PdfButton.addActionListener(ev->{
String fileName = "Résercation N°" + reservation.get("idReservation") + ".pdf";
String pdfPath = "src/PDF/" + fileName;


com.itextpdf.text.Document document = new com.itextpdf.text.Document();
PdfWriter writer;
                try {
                    writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                    
                } catch (Exception ex) {
                    ex.printStackTrace();}
                
document.open();
                try {
                 
                     
    com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 20,com.itextpdf.text.Font.BOLDITALIC | com.itextpdf.text.Font.UNDERLINE, new BaseColor(76, 187, 217));
    Paragraph title = new Paragraph("Détails de votre réservation", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);
                    
                } catch (Exception exx) {
                    exx.printStackTrace();
                }
                try {
                    document.add(new Paragraph("Nom du logement : " +nomLogement));
                } catch (Exception ex2) {
                   ex2.printStackTrace();
                }
                try {
                    document.add(new Paragraph("Date d'arrivée : " + datearriveeString3));
                } catch (Exception ex3) {
                    ex3.printStackTrace();
                }
                try {
                    document.add(new Paragraph(" Date de départ : " + datedepartString3 ));
                } catch (Exception ex4) {
                    ex4.printStackTrace();
                }
                try {
                    document.add(new Paragraph(" Nombre de personnes : " + reservation.get("nbpersonnesReservation") ));
                } catch (Exception ex5) {
                   ex5.printStackTrace();
                }
                try {
                    document.add(new Paragraph(" *******************************Montant total à payer : " + reservation.get("montanttotalReservation")+" DT *******************************" ));
                } catch (Exception ex6) {
                   ex6.printStackTrace();
                }


document.close();
});*/
                // Bouton pour annuler la réservation
                Button annulerButton = new Button("Annuler");
                annulerButton.addActionListener(eee -> {
                      int idR = (int) Math.round((double) reservation.get("idReservation"));
    String url1 = "http://127.0.0.1:8000/supprimerReservationMobile/" + idR;
    System.out.println(url1);
    MultipartRequest request1 = new MultipartRequest();
    
    request1.setUrl(url1); // set the correct URL here
    request1.setPost(false);
     
    NetworkManager.getInstance().addToQueue(request1);
        
new MesReservationsForm(res).show();
 
                });
                
                reservationContainer.add(annulerButton);
                
                // Bouton pour modifier la réservation
                Button modifierButton = new Button("Modifier");
                modifierButton.addActionListener(eeee -> {
                    // Créer un nouveau formulaire pour la modification de réservation
Form modificationForm = new Form("Modifier réservation", BoxLayout.y());

String datearriveeReservationString=(String) reservation.get("datearriveeReservation");
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Date datearrivee = null;
                try {
                    datearrivee = dateFormat.parse(datearriveeReservationString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

// Formater la date
SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
String datearriveeString2 = dateFormat2.format(datearrivee);
 //Label date = new Label(datearriveeString2);
// Ajouter les champs au formulaire
        //DATE D'ARRIVEE
         Button choisirDateArriveeButton = new Button("Choisir la date d'arrivée");
        Label dateArriveeLabelM=  new Label();
        TextField dateArriveeField= new TextField("", "", 20, TextField.ANY);
        dateArriveeField.setText(datearriveeString2);
        Container dateArriveeContainer =  new Container(new BoxLayout(BoxLayout.X_AXIS));
        dateArriveeContainer.add(dateArriveeLabelM);
        dateArriveeContainer.add(dateArriveeField);
        modificationForm.add(dateArriveeContainer);
        modificationForm.add(choisirDateArriveeButton);
        choisirDateArriveeButton.addActionListener(dd -> {
    // Afficher le calendrier
    Form calendarArriveeForm = new Form("Calendrier d'arrivée");

    Calendar calendarArrivee = new Calendar();
 

    calendarArriveeForm.setLayout(new BorderLayout());
    calendarArriveeForm.addComponent(BorderLayout.CENTER, calendarArrivee);
     Button confirmerDateDepartButton = new Button("Confirmer la date d'arrivée");
    calendarArriveeForm.addComponent(BorderLayout.SOUTH, confirmerDateDepartButton);
    calendarArriveeForm.show();
    calendarArrivee.addActionListener(ev -> {
       

        Date selectedDate = calendarArrivee.getDate();
    SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd"); 
    String selectedDateStr = dateFormat3.format(selectedDate);
        System.out.println(selectedDateStr);
    dateArriveeField.setText(selectedDateStr);
        System.out.println(dateArriveeField.getText());
        });
        confirmerDateDepartButton.addActionListener(evt-> { 
            modificationForm.show();
        });
});
        //DATE DE DEPART
        String datedepartReservationString=(String) reservation.get("datedepartReservation");
SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Date datedepart = null;
                try {
                    datedepart = dateFormat5.parse(datedepartReservationString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

// Formater la date
SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy-MM-dd");
String datedepartString2 = dateFormat6.format(datedepart);
        Button choisirDateDepartButton = new Button("Choisir la date de départ");
        Label dateDepartLabelM=  new Label();
        TextField dateDepartField= new TextField("", "", 20, TextField.ANY);
        dateDepartField.setText(datedepartString2);
        Container dateDepartContainer =  new Container(new BoxLayout(BoxLayout.X_AXIS));
        dateDepartContainer.add(dateDepartLabelM);
        dateDepartContainer.add(dateDepartField);
        modificationForm.add(dateDepartContainer);
         modificationForm.add(choisirDateDepartButton);
        choisirDateDepartButton.addActionListener(da -> {
    // Afficher le calendrier
    Form calendarDepartForm = new Form("Calendrier de départ");
    Calendar calendarDepart = new Calendar();
    calendarDepartForm.setLayout(new BorderLayout());
    calendarDepartForm.addComponent(BorderLayout.CENTER, calendarDepart);
    Button confirmerDateDepartButton = new Button("Confirmer la date de départ");
    calendarDepartForm.addComponent(BorderLayout.SOUTH, confirmerDateDepartButton);
    calendarDepartForm.show();
    
    calendarDepart.addActionListener(ev -> {
       

        Date selectedDate = calendarDepart.getDate();
    SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd"); 
    String selectedDateStr = dateFormat4.format(selectedDate);
        System.out.println(selectedDateStr);
    dateDepartField.setText(selectedDateStr);
        System.out.println(dateDepartField.getText());
        });
        confirmerDateDepartButton.addActionListener(evt-> { 
            modificationForm.show();
        });
  
});



Label numPersonnesLabelM = new Label("Nombre de personnes:");
TextField numPersonnesField = new TextField("", "");
 int nbpersonnes2 = (int) Math.round((double) reservation.get("nbpersonnesReservation"));
numPersonnesField.setText( String.valueOf(nbpersonnes2));
modificationForm.add(numPersonnesLabelM);
modificationForm.add(numPersonnesField);

Label numTelLabelM = new Label("Numéro de téléphone:");
TextField numTelField = new TextField("", "");
numTelField.setText((String) reservation.get("numTel"));
modificationForm.add(numTelLabelM);
modificationForm.add(numTelField);



// Ajouter un bouton de validation pour enregistrer les modifications
Button validerButton = new Button("Modifier");
modificationForm.add(validerButton);
Button annulerModButton = new Button("Annuler");
modificationForm.add(annulerModButton);
annulerModButton.addActionListener(an-> { 
new MesReservationsForm(res).show();
});
modificationForm.show();
validerButton.addActionListener(m ->{ 
 // Récupérer les valeurs saisies dans les champs
            String dateArrivee = dateArriveeField.getText();
           
            String dateDepart = dateDepartField.getText();
            
            int nombrePersonnes = Integer.parseInt(numPersonnesField.getText());
            String numeroTelephone = numTelField.getText();

            // Vérifier que les champs obligatoires sont remplis
            if (dateArrivee.isEmpty() || dateDepart.isEmpty() || numeroTelephone.isEmpty()) {
                Dialog.show("Erreur", "Veuillez remplir tous les champs obligatoires", "OK", null);
            } else {
                int idR = (int) Math.round((double) reservation.get("idReservation"));
                // Envoyer les données de réservation au serveur
                String url2 = "http://127.0.0.1:8000/modifierReservationMobile/"+idR;
                MultipartRequest request2 = new MultipartRequest();
                request.setUrl(url2);
               
                request.addArgument("idLogement", String.valueOf(idR));
                request.addArgument("idUser", String.valueOf(19));
                request.addArgument("datearriveeReservation", dateArrivee);
                request.addArgument("datedepartReservation", dateDepart);
                request.addArgument("nbpersonnesReservation", String.valueOf(nombrePersonnes));
                request.addArgument("numTel", numeroTelephone);
                
                
                
                request.addResponseListener(response -> {
                    Dialog.show("Confirmation", "La réservation a été modifiée avec succès", "OK", null);
                // Rediriger vers la vue AfficherLogementsForm
                
               new MesReservationsForm(res).show();

                });
                request.setPost(true);
                
                request.setFailSilently(true);
                
                NetworkManager.getInstance().addToQueue(request);
            }


});

// Créer un dialog Codename One et y ajouter le formulaire de modification
/*Dialog modificationDialog = new Dialog("Modifier réservation");
modificationDialog.setLayout(new BorderLayout());
modificationDialog.add(BorderLayout.CENTER, modificationForm);*/

// Ajouter un listener pour le bouton "Modifier"
annulerButton.addActionListener(eee -> {
    // Afficher le dialog de modification
    new MesReservationsForm(res).show();
});
                });
                
                reservationContainer.add( modifierButton);
                
                this.add(reservationContainer);
            
           
            
            
            
           

          }
}
            }
        
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        request.setFailSilently(true);
        request.setDuplicateSupported(true);
        request.setTimeout(20000);
        NetworkManager.getInstance().addToQueue(request);
    }
    
   @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }  
}
