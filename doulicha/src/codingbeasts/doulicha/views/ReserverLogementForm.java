/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import codingbeasts.doulicha.views.afficherLogementsForm;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;

import com.codename1.ui.Display;
import static com.codename1.ui.events.ActionEvent.Type.Calendar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import codingbeasts.doulicha.services.SmsSender;
import java.util.Date;


/**
 *
 * @author marie
 */
public class ReserverLogementForm extends Form {
    public ReserverLogementForm(int idd)
    {
        super("Réservation de logement", BoxLayout.y());
        Container ReservationContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Form ReservationForm = new Form();
        ReservationForm.add(ReservationContainer);

        // Créer les champs de saisie pour la réservation
        TextField nombrePersonnesField = new TextField("", "Nombre de personnes", 20, TextField.NUMERIC);
        TextField numeroTelephoneField = new TextField("", "Numéro de téléphone", 20, TextField.ANY);
     
      

        // Ajouter les champs au formulaire
         Button choisirDateArriveeButton = new Button("Choisir la date d'arrivée");
        Label dateArriveeLabel =  new Label();
        TextField dateArriveeField = new TextField("", "Date d'arrivée", 20, TextField.ANY);
        Container dateArriveeContainer =  new Container(new BoxLayout(BoxLayout.X_AXIS));
        dateArriveeContainer.add(dateArriveeLabel);
        dateArriveeContainer.add(dateArriveeField);
        
choisirDateArriveeButton.addActionListener(e -> {
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    String selectedDateStr = dateFormat.format(selectedDate);
        System.out.println(selectedDateStr);
    dateArriveeField.setText(selectedDateStr);
        System.out.println(dateArriveeField.getText());
        });
        confirmerDateDepartButton.addActionListener(evt-> { 
            this.showBack();
        });
});
//Date Depart
       
       Button choisirDateDepartButton = new Button("Choisir la date de départ");
        Label dateDepartLabel =  new Label();
        TextField dateDepartField = new TextField("", "Date de départ", 20, TextField.ANY);
        Container dateDepartContainer =  new Container(new BoxLayout(BoxLayout.X_AXIS));
        dateDepartContainer.add(dateDepartLabel);
        dateDepartContainer.add(dateDepartField);
        
choisirDateDepartButton.addActionListener(e -> {
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    String selectedDateStr = dateFormat.format(selectedDate);
        System.out.println(selectedDateStr);
    dateDepartField.setText(selectedDateStr);
        System.out.println(dateDepartField.getText());
        });
        confirmerDateDepartButton.addActionListener(evt-> { 
            this.showBack();
        });
  
});



        ReservationContainer.add(dateArriveeContainer);
        ReservationContainer.add(choisirDateArriveeButton);
        
        ReservationContainer.add(dateDepartContainer);
        ReservationContainer.add(choisirDateDepartButton);
        ReservationContainer.add(nombrePersonnesField);
        ReservationContainer.add(numeroTelephoneField);
        
       
/*Form reserverLogementForm = new Form("Réserver un logement");
reserverLogementForm.setLayout(new BorderLayout());
reserverLogementForm.addComponent(BorderLayout.CENTER,dateArriveeContainer);
reserverLogementForm.addComponent(BorderLayout.SOUTH, choisirDateButton);
ReservationContainer.add(reserverLogementForm);
        
        Label labelDateArrivee = new Label("Date d'arrivée :");
        Calendar calendarA = new Calendar();
        Container containerDateArrivee = new Container(new BorderLayout());
        containerDateArrivee.add(BorderLayout.WEST, labelDateArrivee);
        containerDateArrivee.add(BorderLayout.SOUTH, calendarA);
        ReservationContainer.add(containerDateArrivee);*/
     
/*Label labelDateArrivee = new Label("Date d'arrivée :");
TextField tfDateArrivee = new TextField();
Picker datePickerArrivee = new Picker();
datePickerArrivee.setType(Display.PICKER_TYPE_CALENDAR);
datePickerArrivee.setDate(new Date());
datePickerArrivee.setUIID("TextField");
datePickerArrivee.addActionListener((evt) -> {
    Date date = datePickerArrivee.getDate();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    tfDateArrivee.setText(dateFormat.format(date));
});
Container containerDateArrivee = new Container(new BorderLayout());
containerDateArrivee.add(BorderLayout.WEST, labelDateArrivee);
containerDateArrivee.add(BorderLayout.CENTER, tfDateArrivee);
containerDateArrivee.setLeadComponent(datePickerArrivee);
ReservationContainer.add(containerDateArrivee); */




        // Ajouter le bouton de réservation
        Button reserverButton = new Button("Réserver");
        reserverButton.addActionListener(e -> {
            // Récupérer les valeurs saisies dans les champs
            String dateArrivee = dateArriveeField.getText();
           
            String dateDepart = dateDepartField.getText();
            
            
            String nombrePersonnesString = nombrePersonnesField.getText();
            String numeroTelephone = numeroTelephoneField.getText();

            // Vérifier que les champs obligatoires sont remplis
            if (dateArrivee.isEmpty() || dateDepart.isEmpty() || numeroTelephone.isEmpty()|| nombrePersonnesString.isEmpty() ) {
                Dialog.show("Erreur", "Veuillez remplir tous les champs obligatoires", "OK", null);
            } else {
                int nombrePersonnes = Integer.parseInt(nombrePersonnesField.getText());
                int idUserConnecte= 1;
                // Envoyer les données de réservation au serveur
                String url = "http://127.0.0.1:8000/reservation_logement/reserverLogementMobile/"+idd;
                MultipartRequest request = new MultipartRequest();
                request.setUrl(url);
                request.addArgument("idUser", String.valueOf(idUserConnecte));
                request.addArgument("idLogement", String.valueOf(idd));
                //request.addArgument("idUser", String.valueOf(19));
                request.addArgument("datearriveeReservation", dateArrivee);
                request.addArgument("datedepartReservation", dateDepart);
                request.addArgument("nbpersonnesReservation", String.valueOf(nombrePersonnes));
                request.addArgument("numTel", numeroTelephone);
                
                
                
                request.addResponseListener(response -> {
                    Dialog.show("Confirmation", "La réservation a été ajoutée avec succès", "OK", null);
                // Rediriger vers la vue AfficherLogementsForm
                
                afficherLogementsForm afficherLogementsForm = new afficherLogementsForm();
                afficherLogementsForm.show();

                });
                request.setPost(true);
                
                request.setFailSilently(true);
                
                NetworkManager.getInstance().addToQueue(request);
      //SMS DE CONFIRMATION
                String toNumber= "+216"+ numeroTelephone;
                String messageBody = "Votre réservation de logement du " +dateArrivee+ " au " + dateDepart+ "  a été bien validée. Merci de votre confiance !";
                SmsSender.sendSms(toNumber, messageBody);
            }
        });
        ReservationContainer.add(reserverButton);
        
        Button annulerButton = new Button("Annuler");
        ReservationContainer.add(annulerButton);
        annulerButton.addActionListener(an-> {
        new afficherLogementsForm().show();
        
        });
        
        // Ajouter le conteneur de la liste de logements au formulaire
        add(ReservationForm);
        

    }
        }

    

