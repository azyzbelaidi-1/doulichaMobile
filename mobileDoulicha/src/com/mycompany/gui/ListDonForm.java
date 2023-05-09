/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Don;
import com.mycompany.entities.Projet;
import com.mycompany.services.ServiceDon;
import com.mycompany.services.ServiceProjet;
import java.util.List;

/**
 *
 * @author Admin
 */



public class ListDonForm extends BaseForm {
    
    ListDonForm instance;
    private List<Don> listDon;
    private EncodedImage enc;
    private Form form;
    private Container donsContainer;
    
    public ListDonForm(Resources res) {
        instance = this;
        Toolbar tb = getToolbar();
        setTitle("Liste des Dons");
        
        listDon = ServiceDon.getInstance().affichageDons();
        donsContainer = new Container(BoxLayout.y());
        
        for (Don p : listDon) {
            Container cnt1 = new Container(BoxLayout.y());   
            Container cnt2 = new Container(BoxLayout.x());
            Label dateDon = new Label(p.getDateDon());
            Label valeurDon = new Label("Valeur de don: " + Float.toString((float) p.getValeurDon()));
            Button SLModifier = new Button("Modifier un don");
             SLModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceDon.getInstance().modifierDon(p.getIdDon(),Float.toString((float) p.getValeurDon()));
               
                        new ModifierDonForm(p, instance).show();
            }
        }); 
            Button delete = new Button("Supprimer");
         System.out.println(p.getIdDon());

 delete.getAllStyles().setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xFF0000).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
            delete.getAllStyles().setFgColor(0xffffff);           
            delete.addActionListener(e -> {
                ServiceDon.getInstance().deleteDon(p.getIdDon());
                Dialog.show("Succès", "Votre suppression a été supprimer avec succès !", "OK", null);
                new ListDonForm(res).show();
            });
            
            Button SLPaye = new Button("paiement");
   SLPaye.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            ServiceDon.getInstance().modifierEtatPaiement(p.getIdDon(), String.valueOf(1));
                new Paiement().show();
            }
        }); 
            
             cnt1.add(dateDon);
    cnt1.add(valeurDon);
    cnt1.add(cnt2);
    cnt2.add(SLModifier);
    cnt2.add(delete);
    cnt2.add(SLPaye);
    //add(cnt1);
            cnt1.getStyle().setMarginTop(20);
            cnt1.getStyle().setMarginBottom(20);
            cnt2.getStyle().setMarginRight(20);
            donsContainer.add(cnt1);
        }
        
        add(donsContainer);
        
        
    }
    
}
