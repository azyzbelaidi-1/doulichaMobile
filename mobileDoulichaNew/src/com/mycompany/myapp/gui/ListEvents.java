
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
import com.codename1.ui.Display;
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
import com.mycompany.myapp.services.ServiceEvenement;
import java.io.IOException;
import java.util.List;
import com.mycompany.myapp.services.ServiceEvenement;


public class ListEvents extends SideMenu{
    
    ListEvents instance;
    private List<evenement> listEvent;
     EncodedImage enc;
    private Form form ;
//    private Toolbar tb;
    private Container evenements;
    
//    public ListEvents(Form prev){
//        setTitle("Liste promotions");
//        
//        SpanLabel sp = new SpanLabel();
//        sp.setText("test");
//        sp.setText(PromotionService.getInstance().getAllpromo().toString());
//        
//        getToolbar().addMaterialCommandToLeftBar("Retour",FontImage.MATERIAL_ARROW_BACK, e->prev.showBack());
//    public void addToolBar(Toolbar tb){
//        tb.addMaterialCommandToOverflowMenu("Promotions Liste", FontImage.MATERIAL_HOME, (e)->{
//
//            ListEvents ev = new ListEvents(); 
//            ev.getForm().show();
//        });
//
//    }
//     public Form getForm(){
//        return form;
//    } 
//
//
//public void setForm(){    
//        form.setTitle("Promotions");
//        form.setLayout(new BorderLayout());
//        form.setScrollable(false);
//        tb = form.getToolbar();
//        addToolBar(tb);
//        evenements = new Container(BoxLayout.y());
//    }
    
    
    Form current;
    
    public ListEvents(Resources res) {
        instance=this;
//        super(BoxLayout.y());
        Toolbar tb = getToolbar();
//		tb.setUIID("Toolbar");
                
           setTitle("Liste des évènements");             
        
//        setTitle("Liste des promotions");
        
        
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
//        SpanLabel sp = new SpanLabel();
        List<evenement> list = ServiceEvenement.getInstance().getAllEvents();

       
for (evenement p : list){
    setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.y());
    Label SLnom = new Label(p.getNom_event());
    Label dateTxt1 = new Label("Date début: " + p.getDateDebut_event());
    Label dateTxt2 = new Label("Date fin: " + p.getDateFin_event());
    Label SLprix = new Label("Prix: " + Float.toString((float) p.getPrix_event())+" Dinars");
    Button SLvoir = new Button("Détails");
    
    EncodedImage enca = EncodedImage.createFromImage(Image.createImage(400, 400), true);
    String urla = "http://127.0.0.1:8000/uploads/images/"+p.getImage_event();
    ImageViewer imga = new ImageViewer(URLImage.createToStorage(enca, urla.substring(urla.lastIndexOf("/")+1, urla.length()), urla,URLImage.RESIZE_SCALE_TO_FILL));
    cnt1.add(imga);
        
    SLvoir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            new DetailEvent(p, instance).show();
        }
    });
        
    Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER ,1);
   
    cnt2.add(SLnom);
    cnt2.add(dateTxt1);
    cnt2.add(dateTxt2);
    cnt2.add(SLprix);
    cnt1.add(cnt2);
    cnt1.add(SLvoir);
    add(cnt1);
}


    }

     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }


    
}
