
package com.mycompany.myapp.gui;

import static com.codename1.charts.compat.Paint.Join.MITER;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.entities.participation_evenement;
import com.mycompany.myapp.services.ServiceEvenement;

import com.mycompany.myapp.services.ServiceEvenementParticipation;
import java.io.IOException;
import java.util.List;

public class ListParticipations extends SideMenu{
    
    ListParticipations instance;
    private List<participation_evenement> listParticipations;
    private Form form ;
     private Resources res;
      private List<participation_evenement> participations;

 public ListParticipations(Resources res) {
    instance = this;
    setTitle("Mes participations");
    Toolbar tb = getToolbar();
    tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> showOtherForm(res));
    
    List<participation_evenement> list = ServiceEvenementParticipation.getInstance().affichageParticipations();
    List<evenement> events = ServiceEvenement.getInstance().getAllEvents();
       evenement ev = new evenement();
 for (participation_evenement pe : list) {
    setLayout(new FlowLayout(CENTER));
    Container cnt = new Container(BoxLayout.y());
  
    // Créer la première ligne pour afficher le numéro de téléphone et le nombre de participation
    Container cnt1 = new Container(new BorderLayout());
    Label SLnum = new Label("" + pe.getNum_tel());
    Label SLnbP = new Label("Tickets : "+Math.round(pe.getNombre_participation()));
    //  Label SLnb = new Label(""+ev.getNom_event());
    cnt1.add(BorderLayout.WEST, SLnum);
    cnt1.add(BorderLayout.EAST, SLnbP);
    cnt.add(cnt1);
     
  
    // Créer la ligne pour les boutons Modifier et Supprimer
    Container cnt2 = new Container(new FlowLayout(CENTER));
    Button SLModifier = new Button("Modifier"); 
    Button delete = new Button("Supprimer");
    delete.getAllStyles().setBorder(RoundBorder.create().rectangle(true).color(0xFF0000).strokeColor(0).strokeOpacity(120).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        delete.getAllStyles().setFgColor(0xffffff);
    cnt2.add(SLModifier);
    cnt2.add(delete);
    cnt.add(cnt2);
   String qrCodeContent = "INFORMATIONS DE VOTRE PARTICIPATION : \n "+"Nom du participant : " + pe.getID_user()+ ", ID de la participation: " + pe.getID_participation() + ", Nom de l'évènement: " + pe.getID_event()+ ", Date de début: "  +", Nombre de tickets " + String.valueOf(Math.round(pe.getNombre_participation()));
   
  Image qrCodeImage = ServiceEvenementParticipation.getInstance().generateQRCodeImage(qrCodeContent, 200);

qrCodeImage = qrCodeImage.scaled(qrCodeImage.getWidth() * 2, qrCodeImage.getHeight() * 2);
Label qrCodeLabel = new Label(qrCodeImage);


    Container cnt3 = new Container(new FlowLayout(CENTER));
    cnt3.add(qrCodeLabel);
    cnt.add(cnt3);

    // Ajouter le bloc à la page
    add(cnt);
    
 
    
    delete.addActionListener(e -> {
         Dialog dig = new Dialog("Suppression");
    if (dig.show("Suppression", "Vous voulez supprimer ce Projet ?", "Oui", "Annuler")){
        ServiceEvenementParticipation.getInstance().deleteParticipationEvenement(pe.getID_participation());
        new ListParticipations(res).show();}
    });

    SLModifier.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            ServiceEvenementParticipation.getInstance().modifierParticipationEvenement(pe.getID_participation(), pe.getNum_tel(),String.valueOf(Math.round(pe.getNombre_participation())));
            new UpdateParticipation(pe, instance).show();
        }
    }); 
}

}
    
  /*  
     public ListParticipations(Resources res) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        
        setTitle("Mes participations");             
        Toolbar tb = getToolbar();
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        
        participations = ServiceEvenementParticipation.getInstance().affichageParticipations();
        for (participation_evenement pe : participations) {
            Container cnt = new Container(new BorderLayout());
            Label lblEvent = new Label(getEventName(pe.getID_event()));
            lblEvent.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            Label lblNumTel = new Label(pe.getNum_tel());
            lblNumTel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
            Label lblNbPart = new Label(Integer.toString(pe.getNombre_participation()));
            lblNbPart.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            
            Button btnModifier = new Button("Modifier");
            btnModifier.addActionListener(e -> {
                new UpdateParticipation(pe, this).show();
            });
            
            Button btnSupprimer = new Button("Supprimer");
            btnSupprimer.addActionListener(e -> {
                ServiceEvenementParticipation.getInstance().deleteParticipationEvenement(pe.getID_participation());
                participations.remove(pe);
                cnt.remove();
                revalidate();
            });
            
            Container cntBtn = new Container(new GridLayout(1, 2));
            cntBtn.add(btnModifier);
            cntBtn.add(btnSupprimer);
            
            cnt.add(BorderLayout.WEST, lblEvent);
            cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(lblNumTel, lblNbPart));
            cnt.add(BorderLayout.EAST, cntBtn);
            
            add(cnt);
        }
    }
    
    private String getEventName(int idEvent) {
        evenement ev = ServiceEvenement.getInstance().getEventById(idEvent);
        return ev != null ? ev.getNom_event() : "Inconnu";
    } */


    @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}

