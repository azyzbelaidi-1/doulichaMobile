/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Projet;
import com.mycompany.entities.Projet;
import com.mycompany.services.ServiceProjet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class AjoutProjetForm extends BaseForm {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lenovo
 */
    private Resources res;
    
    Form current;
    public AjoutProjetForm(Resources res  ) {
        
        
        super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    this.res = res;
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        tb.addSearchCommand(e ->  {
        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Reclamations", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            refreshTheme();
        });
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));
        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        TextField nomProjet = new TextField("", "entrer Nom!!");
        nomProjet.setUIID("TextFieldBlack");
        addStringValue("NomProjet",nomProjet);
        TextField descriptionProjet = new TextField("", "entrer description!!");
        descriptionProjet.setUIID("TextFieldBlack");
        addStringValue("DescriptionProjet",descriptionProjet);
        TextField objectifProjet = new TextField("", "entrer objectif!!");
        objectifProjet.setUIID("TextFieldBlack");
        addStringValue("ObjectifProjet",objectifProjet);
        TextField etatProjet = new TextField("", "entrer etat!!");
        etatProjet.setUIID("TextFieldBlack");
        addStringValue("EtatProjet",etatProjet);
        TextField imageProjet = new TextField("", "entrer image!!");
        imageProjet.setUIID("TextFieldBlack");
        addStringValue("ImageProjet",imageProjet);
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        btnAjouter.addActionListener((e) -> {
            try {
                if(nomProjet.getText().equals("") || descriptionProjet.getText().equals("")|| objectifProjet.getText().equals("")|| etatProjet.getText().equals("")|| imageProjet.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                final Dialog iDialog = ip.showInfiniteBlocking();
           Projet r = new Projet(
    nomProjet.getText().toString(),
    descriptionProjet.getText().toString(),
    imageProjet.getText().toString(),
    Float.valueOf(objectifProjet.getText()),
    Integer.parseInt(etatProjet.getText())
   );                            
                    System.out.println("data  projet == "+r);
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceProjet.getInstance().ajoutProjet(r);
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    //ba3d ajout net3adaw lel ListREclamationForm
                 //ba3d ajout net3adaw lel ListREclamationForm
                    new ListProjetForm(res).show();
                    refreshTheme();//Actualisation
                }
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
        });
    }
private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }
 private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
}

 