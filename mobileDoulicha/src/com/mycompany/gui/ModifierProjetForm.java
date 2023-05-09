/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Projet;
import com.mycompany.services.ServiceProjet;
/**
 *
 * @author Admin
 */
public class ModifierProjetForm extends BaseForm {
    
    Form current;
    public ModifierProjetForm(Resources res , Projet r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField NomProjet = new TextField(r.getNomProjet() , "NomProjet" , 20 , TextField.ANY);
        TextField DescriptionProjet = new TextField(r.getDescriptionProjet() , "DescriptionProjet" , 20 , TextField.ANY);
        TextField ImageProjet = new TextField(r.getImageProjet() , "ImageProjet" , 20 , TextField.ANY);
        TextField ObjectifProjet = new TextField(String.valueOf(r.getObjectifProjet()) , "ObjectifProjet" , 20 , TextField.ANY);
        TextField EtatProjet = new TextField(String.valueOf(r.getEtatProjet()) , "EtatProjet" , 20 , TextField.ANY);

 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        ComboBox etatCombo = new ComboBox();
        
        etatCombo.addItem("Non Traiter");
        
        etatCombo.addItem("Traiter");
        
        if(r.getEtatProjet() == 0 ) {
            etatCombo.setSelectedIndex(0);
        }
        else 
            etatCombo.setSelectedIndex(1);
        
        
        
        
        
        NomProjet.setUIID("NewsTopLine");
        DescriptionProjet.setUIID("NewsTopLine");
        ImageProjet.setUIID("NewsTopLine");
        ObjectifProjet.setUIID("NewsTopLine");
        EtatProjet.setUIID("NewsTopLine");
        
        NomProjet.setSingleLineTextArea(true);
        DescriptionProjet.setSingleLineTextArea(true);
        ImageProjet.setSingleLineTextArea(true);
        ObjectifProjet.setSingleLineTextArea(true);
        EtatProjet.setSingleLineTextArea(true);

        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setNomProjet(NomProjet.getText());
           r.setDescriptionProjet(DescriptionProjet.getText());
           r.setImageProjet(ImageProjet.getText());
           r.setObjectifProjet(Float.parseFloat(ObjectifProjet.getText())); 


           if(etatCombo.getSelectedIndex() == 0 ) {
               r.setEtatProjet(0);
           }
           else 
               r.setEtatProjet(1);
      
       
           //appel fonction modfier reclamation men service
           if(ServiceProjet.getInstance().modifierProjet(r) == true) {
                new ListProjetForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListProjetForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(NomProjet),
                createLineSeparator(),
                new FloatingHint(DescriptionProjet),
                createLineSeparator(),
                new FloatingHint(ImageProjet),
                createLineSeparator(),
                new FloatingHint(ObjectifProjet),
                createLineSeparator(),
                new FloatingHint(EtatProjet),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
}
