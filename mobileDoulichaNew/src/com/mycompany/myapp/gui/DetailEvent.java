package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.evenement;
import com.mycompany.myapp.services.ServiceEvenement;
import static com.mycompany.myapp.services.ServiceEvenement.instance;
import java.util.Map;

public class DetailEvent extends Form{

    public DetailEvent(evenement p, Form previous) {
          ListEvents instance = null;
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(700, 460), true);
        String url = p.getImage_event();
        ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/")+1, url.length()), url,URLImage.RESIZE_SCALE_TO_FILL));
        setTitle(p.getNom_event());
        setLayout(new FlowLayout(CENTER));
        Container c= new Container(BoxLayout.y());
        Container v= new Container(BoxLayout.x());
//        System.out.println("nchalah-------"+article.getNom());
        c.add(new SpanLabel("Prix="+Float.toString((float) p.getPrix_event())+" Dt"));
         Button SLParticiper = new Button("Participer");
        c.add(img);
        c.add(new SpanLabel(p.getDescription_event()));
        
         
         SLParticiper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                        new AddParticipation(p, instance).show();
            }
        }); 
          c.add(SLParticiper);
         
        
        add(c);
//        add(v);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        });
    }

 
    
}
    
    

