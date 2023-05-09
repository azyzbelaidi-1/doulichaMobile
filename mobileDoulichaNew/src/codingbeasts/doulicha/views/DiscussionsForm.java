package codingbeasts.doulicha.views;

import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.services.PerspectiveService;
import codingbeasts.doulicha.services.ServicesUtilisateur;
import com.codename1.components.ImageViewer;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.ConnectionRequest;

import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class DiscussionsForm extends SideMenu {

    public DiscussionsForm(Resources res) {

        super("Liste des Discussions", BoxLayout.y());

        this.setScrollableY(true);
         Toolbar tb = getToolbar();
    tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> showOtherForm(res));
          Utilisateur UserConnecte = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
                int idUserConnecte=UserConnecte.getID_user();
                String idUserConnecteString=String.valueOf(idUserConnecte);

        Container nouvelleDiscussionContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label ajouterLabel= new Label("Ajouter une discussion: ");
        ajouterLabel.getStyle().setMargin(40, 40, 20, 20);
           ajouterLabel.getStyle().setFgColor(0x3fc4ed);
            ajouterLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
        Label titreLabel = new Label("Titre");
        TextField titreField = new TextField();
        nouvelleDiscussionContainer.add(ajouterLabel);
        nouvelleDiscussionContainer.add(titreLabel);
        nouvelleDiscussionContainer.add(titreField);

// Contenu
        Label contenuLabel = new Label("Contenu");
        TextArea contenuArea = new TextArea();
        nouvelleDiscussionContainer.add(contenuLabel);
        nouvelleDiscussionContainer.add(contenuArea);

// Image
        Label imageLabel = new Label("Image");
        Button selectImageButton = new Button("Choisir une image");
        final EncodedImage[] selectedImage = {null};

        selectImageButton.addActionListener(e -> {
            ActionListener callback = e2 -> {
                if (e2 != null && e2.getSource() != null) {
                    try {
                        Image image = Image.createImage((String) e2.getSource());
                        selectedImage[0] = EncodedImage.createFromImage(image, false);
                        System.out.println(selectedImage[0]);
                        // Set the selected image in the selectedImage variable
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            FileChooser.showOpenDialog(".png,.jpg,.jpeg,.gif", callback);
        });
        nouvelleDiscussionContainer.add(imageLabel);
        nouvelleDiscussionContainer.add(selectImageButton);

// Bouton Ajouter
        Button ajouterButton = new Button("Ajouter");
        ajouterButton.getStyle().setMarginTop(50);
        ajouterButton.addActionListener(e -> {
            // Get the values of the form fields
            String titre = String.valueOf(titreField.getText()).toString();
            String contenu = String.valueOf(contenuArea.getText()).toString();

            String base64EncodedImage = "";

            if (selectedImage[0] != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ImageIO.getImageIO().save(selectedImage[0], baos, ImageIO.FORMAT_JPEG, 0.9f);
                } catch (IOException ex) {
                }
                byte[] imageData = baos.toByteArray();
                base64EncodedImage = Base64.encodeNoNewline(imageData);
            }

            // Make an HTTP POST request to the server
            MultipartRequest request = new MultipartRequest();
            PerspectiveService ser = new PerspectiveService();
            float i = 0;
            float j = 0;
            try {
                i = ser.getToxicity(titre);
                j = ser.getToxicity(contenu);
            } catch (IOException ex) {
            } catch (GeneralSecurityException ex) {
            }
            if (i >= 0.7) {
                Dialog.show("Error", "Titre très toxique !", "OK", null);
            } else if (j >= 0.7) {
                Dialog.show("Error", "Contenu très toxique !", "OK", null);
            } else if ((titre.isEmpty()) || (contenu.isEmpty())) {
                Dialog.show("Error", "Veuillez remplir tous les champs !", "OK", null);
            } else {
                request.addArgumentNoEncoding("titreDiscussion", titre);
                request.addArgumentNoEncoding("contenuDiscussion", contenu);
                                                        request.addArgumentNoEncoding("idUser", idUserConnecteString);

                

                System.out.println(base64EncodedImage);

                if (!base64EncodedImage.isEmpty()) {
                    // Add the base64-encoded image to the request
                    request.addArgumentNoEncoding("imageDiscussion", base64EncodedImage);
                }

                request.setUrl("http://127.0.0.1:8000/discussionsJSON/new");
                request.setPost(true);
                request.setFailSilently(true); // Optional: Suppress errors
                NetworkManager.getInstance().addToQueue(request);
                                Dialog.show("Ajout de discussion", "Discussion ajoutée avec succès !", "OK", null);

                DiscussionsForm h = new DiscussionsForm(res);
                h.show();
            }
        });
        nouvelleDiscussionContainer.getStyle().setMargin(50, 0, 30, 30);
        nouvelleDiscussionContainer.add(ajouterButton);
        add(nouvelleDiscussionContainer);

        TextField searchField = new TextField();
        searchField.setHint("rechercher...");
        add(searchField);

        // Récupération des données JSON depuis l'URL
        String url = "http://127.0.0.1:8000/AllDiscussionsJSON";
        MultipartRequest request = new MultipartRequest();
        request.setUrl(url);
        request.setPost(false);
        request.setFailSilently(true);
        request.setDuplicateSupported(true);
        request.setTimeout(20000);
        NetworkManager.getInstance().addToQueue(request);
        request.addResponseListener((e) -> {
            try {
                byte[] data = request.getResponseData();
                if (data != null) {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> discussions = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                    if (discussions != null) {
                        // Création des éléments de l'interface graphique
                        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                        //recherche
                        searchField.addDataChangedListener((i1, i2) -> {
                            String text = searchField.getText();

                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    Button retourButton = new Button("Retour");
                                    retourButton.getStyle().setMarginTop(50);
                                    retourButton.addActionListener(ret
                                            -> {
                                        new DiscussionsForm(res).show();

                                    });
                                    System.out.println("aaaaaaaaaaaaaaaa");
                                    Form rechercheForm = new Form();
                                    Container discussionsContainerR = new Container();
                                    discussionsContainerR.add(retourButton);
                                    //removeAll();
                                    container.removeAll();
                                    for (Map<String, Object> discussion : (ArrayList<Map<String, Object>>) discussions.get("discussions")) {
                                        if ((((String) discussion.get("titreDiscussion")).toLowerCase().contains(text.toLowerCase())) || (((String) discussion.get("contenuDiscussion")).toLowerCase().contains(text.toLowerCase()))) {
                                            Container buttonsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

                                            Container discontainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                                            // Bouton "visiter"
                                            Button visitButton = new Button("Visiter");
                                            visitButton.getStyle().setMarginRight(30);
                                            visitButton.addActionListener((evt) -> {
                                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                                System.out.println(id);
                                                DetailDiscussionForm showForm = null;
                                                try {
                                                    showForm = new DetailDiscussionForm(id, false, res);
                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                }
                                                showForm.show();
                                            });

                                            // Bouton "modifier"
                                            Button editButton = new Button("Modifier");
                                                                                        editButton.getStyle().setMarginRight(30);

                                            editButton.addActionListener((evt) -> {
                                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                                System.out.println(id);
                                                DetailDiscussionForm showForm = null;
                                                try {
                                                    showForm = new DetailDiscussionForm(id, true, res);
                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                }
                                                showForm.show();

                                            });

                                            // Bouton "supprimer"
                                            Button deleteButton = new Button("Supprimer");
                                            deleteButton.addActionListener((evt) -> {
                                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                                String url1 = "http://127.0.0.1:8000/deleteDiscussionJSON/" + id;
                                                System.out.println(url1);
                                                ConnectionRequest request1 = new ConnectionRequest();
                                                request1.setUrl(url1); // set the correct URL here
                                                request1.setPost(false);

                                                NetworkManager.getInstance().addToQueue(request1);
                                                                                Dialog.show("Suppression de discussion", "Discussion supprimé !", "OK", null);

                                                Display.getInstance().getCurrent().show();
                                                DiscussionsForm h = new DiscussionsForm(res);
                                                h.show();

                                                // Action du bouton "supprimer"
                                            });
                                            // Ajout des boutons au container horizontal
                                            buttonsContainer.getStyle().setMarginTop(40);
                                            discontainer.getStyle().setMarginBottom(40);
                                            buttonsContainer.add(visitButton);
                                            int id = (int) Math.round((double) discussion.get("idUser"));
                                            if (id == 1) {
                                                buttonsContainer.add(editButton);
                                                buttonsContainer.add(deleteButton);
                                            }
                                            TextArea title = new TextArea((String) discussion.get("titreDiscussion"));
                                            title.getStyle().setMargin(10, 10, 0, 0);
   title.getStyle().setFgColor(0x3fc4ed);
            title.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
                                            TextArea content = new TextArea((String) discussion.get("contenuDiscussion"));

                                            //   Label date = new Label((String) discussion.get("dateDiscussion"));
                                            String dateString = (String) discussion.get("dateDiscussion");
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            Date dateDiscussion = null;
                                            try {
                                                dateDiscussion = dateFormat.parse(dateString);
                                            } catch (ParseException ex) {
                                            }

// Formater la date
                                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
                                            String dateString2 = dateFormat2.format(dateDiscussion);
                                            Label date = new Label("créé le "+dateString2);
                                                        date.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL));


                                            discontainer.add(title);

                                            if ((String) discussion.get("imageDiscussion") != null && (String) discussion.get("imageDiscussion") != "") {

                                                String imageUrl = "http://localhost:8000/uploads/images/" + (String) discussion.get("imageDiscussion");
                                                System.out.println(imageUrl);
                                                // Création de l'image à partir de l'URL
                                                EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);
                                                int i = 0;
                                                URLImage urlImage = URLImage.createToStorage(placeHolder, (String) discussion.get("imageDiscussion"), imageUrl);
                                                i++;
                                                EncodedImage scaledImage = urlImage.scaledEncoded(500, 500);

                                                ImageViewer imageLabel1 = new ImageViewer(scaledImage);
                                                Container imageContainer = new Container(new BorderLayout());
                                                imageContainer.add(BorderLayout.CENTER, imageLabel1);
                                                discontainer.add(imageContainer);
                                            }

                                            discontainer.add(content);
                                            discontainer.add(date);
                                            discontainer.add(buttonsContainer);

                                            // ajouter l'image ici
                                            discontainer.add(new Label("")); // espace vide pour la séparation

                                            discussionsContainerR.add(discontainer);

                                        }
                                    }
                                    rechercheForm.add(discussionsContainerR);
                                    rechercheForm.show();
                                    discussionsContainerR.add(new Label("")); // espace vide pour la séparation
                                    discussionsContainerR.revalidate(); // Refresh the layout
                                }
                            }, 6000);

                        });

                        //aadi
                        for (Map<String, Object> discussion : (ArrayList<Map<String, Object>>) discussions.get("discussions")) {
                            Container buttonsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

                            Container discontainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                            // Bouton "visiter"
                            Button visitButton = new Button("Visiter");
                            visitButton.addActionListener((evt) -> {
                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                System.out.println(id);
                                DetailDiscussionForm showForm = null;
                                try {
                                    showForm = new DetailDiscussionForm(id, false, res);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                showForm.show();
                            });

                            // Bouton "modifier"
                            Button editButton = new Button("Modifier");
                            editButton.addActionListener((evt) -> {
                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                System.out.println(id);
                                DetailDiscussionForm showForm = null;
                                try {
                                    showForm = new DetailDiscussionForm(id, true, res);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                showForm.show();

                            });

                            // Bouton "supprimer"
                            Button deleteButton = new Button("Supprimer");
                            deleteButton.addActionListener((evt) -> {
                                int id = (int) Math.round((double) discussion.get("idDiscussion"));
                                String url1 = "http://127.0.0.1:8000/deleteDiscussionJSON/" + id;
                                System.out.println(url1);
                                ConnectionRequest request1 = new ConnectionRequest();
                                request1.setUrl(url1); // set the correct URL here
                                request1.setPost(false);

                                NetworkManager.getInstance().addToQueue(request1);
                                                                                                                Dialog.show("Suppression de discussion", "Discussion supprimé !", "OK", null);

                                Display.getInstance().getCurrent().show();
                                DiscussionsForm h = new DiscussionsForm(res);
                                h.show();

                                // Action du bouton "supprimer"
                            });
                            // Ajout des boutons au container horizontal
                            buttonsContainer.add(visitButton);
                            int id = (int) Math.round((double) discussion.get("idUser"));
                            if (id == idUserConnecte) {
                                buttonsContainer.add(editButton);
                                buttonsContainer.add(deleteButton);
                            }
                            TextArea title = new TextArea((String) discussion.get("titreDiscussion"));
                            title.setEditable(false);
                                            title.getStyle().setMargin(10, 0, 0, 0);

   title.getStyle().setFgColor(0x3fc4ed);
            title.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
                            TextArea content = new TextArea((String) discussion.get("contenuDiscussion"));
content.setEditable(false);
                            //   Label date = new Label((String) discussion.get("dateDiscussion"));
                            String dateString = (String) discussion.get("dateDiscussion");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date dateReclamation = dateFormat.parse(dateString);

// Formater la date
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd ");
                            String dateString2 = dateFormat2.format(dateReclamation);
                                            Label date = new Label("créé le "+dateString2);

                            discontainer.add(title);

                            if ((String) discussion.get("imageDiscussion") != null && (String) discussion.get("imageDiscussion") != "") {

                                String imageUrl = "http://localhost:8000/uploads/images/" + (String) discussion.get("imageDiscussion");
                                System.out.println(imageUrl);
                                // Création de l'image à partir de l'URL
                                EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);
                                int i = 0;
                                URLImage urlImage = URLImage.createToStorage(placeHolder, (String) discussion.get("imageDiscussion"), imageUrl);
                                i++;
                                EncodedImage scaledImage = urlImage.scaledEncoded(500, 500);

                                ImageViewer imageLabel1 = new ImageViewer(scaledImage);
                                Container imageContainer = new Container(new BorderLayout());

                                imageContainer.add(BorderLayout.CENTER, imageLabel1);
                                discontainer.add(imageContainer);
                            }

                            discontainer.add(content);
                            discontainer.add(date);
                            discontainer.add(buttonsContainer);

                            // ajouter l'image ici
                            discontainer.add(new Label("")); // espace vide pour la séparation

                            container.add(discontainer);
                        }

                        add(container);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
    
    
    
    
     @Override
    protected void showOtherForm(Resources res) {
        new Home(res).show();
    }

}
