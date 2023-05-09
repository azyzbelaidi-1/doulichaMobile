
package codingbeasts.doulicha.views;

import static com.codename1.charts.compat.Paint.Join.MITER;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;

import com.codename1.ui.Component;
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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.evenement;
import codingbeasts.doulicha.services.ServiceEvenement;
import java.io.IOException;
import java.util.List;
import codingbeasts.doulicha.services.ServiceEvenement;
import codingbeasts.doulicha.services.ServiceEvenementParticipation;
import com.codename1.ui.TextArea;
import java.util.Date;


public class ListEvents extends SideMenu{
    
    ListEvents instance;
    private List<evenement> listEvent;
     EncodedImage enc;
    private Form form ;
    private Container evenements;
    Form current;
    
    public ListEvents(Resources res) {
        instance=this;
//        super(BoxLayout.y());
        Toolbar tb = getToolbar();
//		tb.setUIID("Toolbar");
                
           setTitle("Liste des évènements");   
        
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
       /*  Picker datePicker = new Picker();
    datePicker.setType(Display.PICKER_TYPE_DATE);
    datePicker.setDate(new Date());
    add(datePicker);*/
   
//        SpanLabel sp = new SpanLabel();
        List<evenement> list = ServiceEvenement.getInstance().getAllEvents();

      
  

//prepare field
TextField searchField;
searchField = new TextField("", "Rechercher...");
add(searchField);

searchField.addDataChangedListener((i1, i2) -> {
    String text = searchField.getText();
    removeAll();
    for (evenement p : list) {
        if (p.getNom_event().toLowerCase().contains(text.toLowerCase())) {
           
setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.y());
    Label SLnom = new Label(p.getNom_event());
    Label dateTxt1 = new Label("Date de début: " + p.getDateDebut_event());
    Label dateTxt2 = new Label("Date de fin: " + p.getDateFin_event());
    
    Label dateTxt3 = new Label("Lieu: " + p.getLieu_event());
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
    cnt2.add(dateTxt3);
    cnt2.add(SLprix);
    cnt1.add(cnt2);
    cnt1.add(SLvoir);


            add(cnt1);
        }
    }
    revalidate();
});


for (evenement p : list){
      
    setLayout(new FlowLayout(CENTER));
    Container cnt1 = new Container(BoxLayout.y());   
    Container cnt2 = new Container(BoxLayout.y());
    TextArea SLnom = new TextArea(p.getNom_event());
    Label dateTxt1 = new Label("Date de début: " + p.getDateDebut_event());
    Label dateTxt2 = new Label("Date de fin: " + p.getDateFin_event());
     
     Label dateTxt3 = new Label("Lieu: " + p.getLieu_event());
    Label SLprix = new Label("Prix: " + Float.toString((float) p.getPrix_event())+" Dinars");
    Button SLvoir = new Button("Détails");
    
    EncodedImage enca = EncodedImage.createFromImage(Image.createImage(400, 400), true);
    String urla = "http://127.0.0.1:8000/uploads/images/"+p.getImage_event();
    ImageViewer imga = new ImageViewer(URLImage.createToStorage(enca, urla.substring(urla.lastIndexOf("/")+1, urla.length()), urla,URLImage.RESIZE_SCALE_TO_FILL));
    cnt1.add(imga);
    
  /*  // Créer un lien iCalendar avec les dates de début et de fin de l'évènement
String icalData = "BEGIN:VCALENDAR\n" +
                  "VERSION:2.0\n" +
                  "BEGIN:VEVENT\n" +
        
                  "SUMMARY:" + p.getNom_event() + "\n" +
                  "DTSTART:" + p.getDateDebut_event()+ "\n" +
                  "DTEND:" + p.getDateFin_event() + "\n" +
                  "LOCATION:" + p.getLieu_event() + "\n" +
                  "DESCRIPTION:" + p.getDescription_event()+ "\n" +
        
                  "END:VEVENT\n" +
                  "END:VCALENDAR";

String icalLink = "data:text/calendar;base64," + icalData;
        Image qrCodeImage = ServiceEvenement.getInstance().generateQRCodeImageCAL(icalLink, 200);

qrCodeImage = qrCodeImage.scaled(qrCodeImage.getWidth() * 2, qrCodeImage.getHeight() * 2);
Label qrCodeLabel = new Label(qrCodeImage);
cnt1.add(qrCodeLabel); */

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
    
    cnt2.add(dateTxt3);
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
