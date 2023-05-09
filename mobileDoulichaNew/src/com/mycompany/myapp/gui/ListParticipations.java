
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
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.entities.participation_evenement;
import com.mycompany.myapp.services.ServiceEvenement;

import com.mycompany.myapp.services.ServiceEvenementParticipation;
import java.util.List;

public class ListParticipations extends SideMenu{
    
    ListParticipations instance;
    private List<participation_evenement> listParticipations;
    private Form form ;

    public ListParticipations(Resources res) {
    instance=this;
//        super(BoxLayout.y());
        Toolbar tb = getToolbar();
//		tb.setUIID("Toolbar");
                
           setTitle("Mes participations");             
        
//        setTitle("Liste des promotions");
        
        
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
//        SpanLabel sp = new SpanLabel();
        List<participation_evenement> list = ServiceEvenementParticipation.getInstance().affichageParticipations();

       
     for (participation_evenement pe : list){
    setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.x());
    Label SLnum = new Label(pe.getNum_tel());
    Label SLnbP = new Label(Float.toString((float) pe.getNombre_participation()));
    Button SLModifier = new Button("Modifier"); 
   Button delete = new Button("Supprimer");
         System.out.println(pe.getID_participation());

 delete.getAllStyles().setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xFF0000).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
            delete.getAllStyles().setFgColor(0xffffff);
          
           
            delete.addActionListener(e -> {
                ServiceEvenementParticipation.getInstance().deleteParticipationEvenement(pe.getID_participation());
                new ListParticipations(res).show();
            });
            
              SLModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceEvenementParticipation.getInstance().modifierParticipationEvenement(pe.getID_participation(), pe.getNum_tel(),Float.toString((float) pe.getNombre_participation()));
               
                        new UpdateParticipation(pe, instance).show();
            }
        }); 

    Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER ,1);
   
    cnt2.add(SLnum);
    cnt2.add(SLnbP);
    cnt1.add(cnt2);
    cnt2.add(SLModifier);
    cnt2.add(delete);
    add(cnt1);
}

        // Ajouter un bouton pour retourner à l'accueil
//        form.getToolbar().addCommandToSideMenu("Accueil", null, e -> showOtherForm(res));
    }

    @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }
}

