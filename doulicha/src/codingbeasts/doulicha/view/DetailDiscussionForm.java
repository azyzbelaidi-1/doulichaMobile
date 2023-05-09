package codingbeasts.doulicha.view;

import codingbeasts.doulicha.services.PerspectiveService;
import codingbeasts.doulicha.services.TranslationService;
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
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DetailDiscussionForm extends Form {

    public DetailDiscussionForm(int idDiscussion, Boolean editMode) throws IOException {
        setTitle("Discussion");
        this.setScrollableY(true);
        Button retourButton = new Button("Retour");
        add(retourButton);
        retourButton.addActionListener(ret
                -> {
            new DiscussionsForm().show();

        });
        // Récupération des données JSON depuis l'URL
        String url = "http://127.0.0.1:8000/discussionJSON/" + idDiscussion;
        MultipartRequest request = new MultipartRequest();
        request.setUrl(url);
        request.setPost(false);
        Button modifier = new Button("modifier");

        request.addResponseListener((e) -> {
            try {
                byte[] data = request.getResponseData();
                if (data != null) {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> discussion = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                    Container discontainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                    System.out.println(discussion);
                    if (discussion != null) {
                        // Création des éléments de l'interface graphique
                        discussion = (Map<String, Object>) discussion.get("discussion");
                        Label title = new Label((String) discussion.get("titreDiscussion"));

                        title.getStyle().setFgColor(0x3fc4ed);
                        title.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
                        TextArea content = new TextArea((String) discussion.get("contenuDiscussion"));
                        content.setEditable(false);
                        content.setEditable(false);
                        if (editMode == true) {
                            content.setEditable(true);

                        }

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
                        Label date = new Label("créé le " + dateString2);

                        Button translateButton = new Button("Traduire");
                        translateButton.getStyle().setMarginTop(40);

                        discontainer.add(title);
                        if ((String) discussion.get("imageDiscussion") != null && (String) discussion.get("imageDiscussion") != "") {

                            String imageUrl = "http://localhost:8000/uploads/images/" + (String) discussion.get("imageDiscussion");
                            System.out.println(imageUrl);
                            // Création de l'image à partir de l'URL
                            EncodedImage placeHolder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffcccccc), true);
                            URLImage urlImage = URLImage.createToStorage(placeHolder, (String) discussion.get("imageDiscussion"), imageUrl);
                            EncodedImage scaledImage = urlImage.scaledEncoded(1000, 1000);

                            ImageViewer imageLabel = new ImageViewer(scaledImage);
                            Container imageContainer = new Container(new BorderLayout());
                            imageContainer.add(BorderLayout.CENTER, imageLabel);
                            discontainer.add(imageContainer);

                            if (editMode == true) {
                                Label imageLabel1 = new Label("Modifier l'image");
                                Button selectImageButton = new Button("Choisir une image");

                                final EncodedImage[] selectedImage = {null};

                                selectImageButton.addActionListener(e3 -> {
                                    ActionListener callback = e2 -> {
                                        if (e2 != null && e2.getSource() != null) {
                                            try {
                                                Image image = Image.createImage((String) e2.getSource());
                                                selectedImage[0] = EncodedImage.createFromImage(image, false);
                                                // Set the selected image in the selectedImage variable
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    };
                                    FileChooser.showOpenDialog(".png,.jpg,.jpeg,.gif", callback);
                                });

                                discontainer.add(imageLabel1);
                                discontainer.add(selectImageButton);

                                modifier.addActionListener(e4 -> {
                                    // Get the values of the form fields
                                    String contenu = content.getText();
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
                                    MultipartRequest request2 = new MultipartRequest();
                                    PerspectiveService ser = new PerspectiveService();
                                    float i = 0;
                                    float j = 0;
                                    try {
                                        j = ser.getToxicity(contenu);
                                    } catch (IOException ex) {
                                    } catch (GeneralSecurityException ex) {
                                    }

                                    if (j >= 0.7) {
                                        Dialog.show("Error", "Contenu très toxique !", "OK", null);
                                    } else if (contenu.isEmpty()) {
                                        Dialog.show("Error", "Veuillez remplir tous les champs !", "OK", null);
                                    } else {
                                        request2.addArgument("titreDiscussion", title.getText());
                                        request2.addArgument("contenuDiscussion", contenu);

                                        System.out.println(base64EncodedImage);

                                        if (!(base64EncodedImage != "")) {
                                            // Add the base64-encoded image to the request
                                            request2.addArgumentNoEncoding("imageDiscussion", base64EncodedImage);
                                        }

                                        request2.setUrl("http://127.0.0.1:8000/updateDiscussionJSON/" + idDiscussion);
                                        request2.setPost(true);
                                        request2.setFailSilently(true); // Optional: Suppress errors
                                        NetworkManager.getInstance().addToQueue(request2);

                                        try {
                                            DetailDiscussionForm sh1 = new DetailDiscussionForm(idDiscussion, false);
                                            sh1.show();
                                        } catch (IOException ex) {
                                        }

                                    }
                                });

                            }
                        }
                        discontainer.add(content);
                        discontainer.add(date);
                        TranslationService ser1 = new TranslationService();
                        String translatedText = ser1.translate(content.getText(), "en", "fr");
                        discontainer.add(translateButton);
                        TextArea translation = new TextArea(translatedText);

                        translation.setEditable(false);
                        translation.setHidden(true);
                        discontainer.add(translation);

                        if (editMode == true) {
                            discontainer.add(modifier);
                        }

                        translateButton.addActionListener(e1 -> {
                            if (translateButton.getText() == "Traduire") {
                                translateButton.setText("Voir moins");
                                translation.setHidden(false);

                                discontainer.revalidate();
                            } else {
                                translateButton.setText("Traduire");
                                translation.setHidden(true);

                                discontainer.revalidate();
                            }

                        });

                        discontainer.add(new Label("Répondre à cette discussion"));
                        Label contenuLabel = new Label("Contenu");
                        TextArea contenuArea = new TextArea();

                        Button ajouterButton = new Button("Ajouter");

                        discontainer.add(contenuLabel);
                        discontainer.add(contenuArea);
                        discontainer.add(ajouterButton);
                        add(discontainer);
                        ajouterButton.addActionListener(e1 -> {
                            String contenu = contenuArea.getText();
                            PerspectiveService ser = new PerspectiveService();
                            float tox = 0;
                            try {
                                tox = ser.getToxicity(contenu);
                            } catch (IOException ex) {
                            } catch (GeneralSecurityException ex) {
                            }
                            if (tox >= 0.7) {
                                Dialog.show("Error", "Contenu très toxique !", "OK", null);

                            } else {
                                ConnectionRequest request1 = new ConnectionRequest();
                                request1.setUrl("http://127.0.0.1:8000/reponsesJSON/new");
                                request1.addArgument("contenuReponse", contenu);
                                String id = String.valueOf(idDiscussion);
                                request1.addArgument("idDiscussion", id);

                                request1.setPost(true);
                                request1.setFailSilently(true); // Optional: Suppress errors
                                NetworkManager.getInstance().addToQueue(request1);

                                try {
                                    DetailDiscussionForm sh = new DetailDiscussionForm(idDiscussion, false);
                                    sh.show();
                                } catch (IOException ex) {
                                }
                            }
                        });
                        Container reponsescontainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                        Map<String, Object> reponses = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                        if (reponses.get("reponses") != null) {
                            for (Map<String, Object> reponse : (ArrayList<Map<String, Object>>) reponses.get("reponses")) {
                                Container buttonsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

                                Container repcontainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                                // Bouton "modifier"
                                Button editButton = new Button("Modifier");

                                // Bouton "supprimer"
                                Button deleteButton = new Button("Supprimer");
                                int id = (int) Math.round((double) reponse.get("idReponse"));

                                deleteButton.addActionListener((evt) -> {
                                    String url1 = "http://127.0.0.1:8000/deleteReponseJSON/" + id;
                                    System.out.println(url1);
                                    ConnectionRequest request1 = new ConnectionRequest();
                                    request1.setUrl(url1); // set the correct URL here
                                    request1.setPost(false);

                                    NetworkManager.getInstance().addToQueue(request1);
                                    Display.getInstance().getCurrent().show();

                                    try {
                                        DetailDiscussionForm sh = new DetailDiscussionForm(idDiscussion, false);
                                        sh.show();
                                    } catch (IOException ex) {
                                    }
                                });

                                TextArea repcontent = new TextArea((String) reponse.get("contenuReponse"));
                                repcontent.setEditable(false);

                                String dateStringg = (String) reponse.get("dateReponse");
                                SimpleDateFormat dateFormatt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date dateReponse = null;
                                try {
                                    dateReponse = dateFormatt.parse(dateStringg);
                                } catch (ParseException ex) {
                                }

// Formater la date
                                SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd ");
                                String dateString3 = dateFormat3.format(dateReponse);
                                Label repdate = new Label("créé le " + dateString3);
                                repcontainer.add(repcontent);
                                repcontainer.add(repdate);
                                Button enregistrerButton = new Button("Enregistrer");
                                repcontainer.add(enregistrerButton);
                                enregistrerButton.setHidden(true);
                                editButton.addActionListener((evt) -> {
                                    enregistrerButton.setHidden(false);

                                    repcontainer.revalidate();
                                    repcontent.setEditable(true);
                                    enregistrerButton.addActionListener((evt1) -> {
                                        ConnectionRequest request1 = new ConnectionRequest();
                                        request1.addArgument("contenuReponse", repcontent.getText());
                                        String url1 = "http://127.0.0.1:8000/updateReponseJSON/" + id;
                                        System.out.println(url1);
                                        request1.setUrl(url1); // set the correct URL here
                                        request1.setPost(true);

                                        NetworkManager.getInstance().addToQueue(request1);
                                        Display.getInstance().getCurrent().show();

                                        try {
                                            DetailDiscussionForm sh = new DetailDiscussionForm(idDiscussion, false);
                                            sh.show();
                                        } catch (IOException ex) {
                                        }

                                    });

                                });
                                int id1 = (int) Math.round((double) reponse.get("idUser"));
                                if (id1 == 1) {
                                    buttonsContainer.add(editButton);
                                    buttonsContainer.add(deleteButton);
                                    repcontainer.add(buttonsContainer);

                                }

                                // ajouter l'image ici
                                repcontainer.add(new Label("")); // espace vide pour la séparation

                                reponsescontainer.add(repcontainer);
                            }
                            discontainer.add(reponsescontainer);
                        }

                        // Ajout du conteneur dans la forme
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        request.setFailSilently(true);
        request.setDuplicateSupported(true);
        request.setTimeout(5000);
        NetworkManager.getInstance().addToQueue(request);
    }
}
