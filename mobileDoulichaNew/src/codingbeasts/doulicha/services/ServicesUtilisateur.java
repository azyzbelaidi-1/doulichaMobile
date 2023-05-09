/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;

import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.views.LoginForm;
import codingbeasts.doulicha.utils.Statics;
import java.io.IOException;
import java.util.Map;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author Asus
 */
public class ServicesUtilisateur {

    //singleton 
    public static ServicesUtilisateur instance = null;

    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;

    private Utilisateur utilisateurConnecte;

    public static ServicesUtilisateur getInstance() {
        if (instance == null) {
            instance = new ServicesUtilisateur();
        }
        return instance;
    }

    public ServicesUtilisateur() {
        req = new ConnectionRequest();

    }
//    //ajout 
//    public boolean inscription(Utilisateur utilisateur) {
//   ConnectionRequest r = new ConnectionRequest();
//        String nom = utilisateur.getNom_user(); 
//     String prenom = utilisateur.getPrenom_user(); 
//     String email = utilisateur.getEmail_user(); 
//     String motDePass = utilisateur.getMdp_user();
//     
//      String url = Statics.BASE_URL + "/inscriptionJson?emailUser=" +email+ "&nomUser=" +nom+ "&prenomUser=" +prenom+ "&mdpUser=" +motDePass;
////       String url =  "http://127.0.0.1:8000/inscriptionJson?emailUser=taheraaa@esprit.tn&nomUser=taherqq&prenomUser=dhiflaoui&mdpUser=taher123";
//       req.setUrl(url);
//        req.setPost(false);
////        Connection.setRequestProperty( "Accept-Encoding", "" );
////        req.setReadResponseForErrors(true);
//        
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
////                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOk;
//         
//    

    public boolean inscription(Utilisateur u) {
        ConnectionRequest r = new ConnectionRequest();

        String nom = u.getNom_user();
        String prenom = u.getPrenom_user();
        String email = u.getEmail_user();
        String motDePass = u.getMdp_user();

        String url = Statics.BASE_URL + "/inscriptionJson?emailUser=" + email + "&nomUser=" + nom + "&prenomUser=" + prenom + "&mdpUser=" + motDePass;

        //Control saisi
        if (nom.isEmpty() || prenom.isEmpty() || motDePass.isEmpty() || email.isEmpty()) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        }
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return true;

    }
    //Signup
//    public void  inscription (Utilisateur u) {
//        ConnectionRequest r = new ConnectionRequest();
//
//     String nom = u.getNom_user(); 
//     String prenom = u.getPrenom_user(); 
//     String email = u.getEmail_user(); 
//     String motDePass = u.getMdp_user(); 
//    
//        
//      String url = Statics.BASE_URL+"/inscriptionJson?emailUser=" +email+ "&nomUser=" +nom+ "&prenomUser=" +prenom+ "&mdpUser=" +motDePass;
//     
//      
//        //Control saisi
//        if(nom.isEmpty() || prenom.isEmpty() || motDePass.isEmpty() || email.isEmpty()) {
//            
//            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
//            
//        }
//        req.setUrl(url);
//        req.setPost(false);
//        
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                JSONParser j = new JSONParser();
//            
//            String json = new String(req.getResponseData()) + "";
//                  try {
//                if (json.equals("Email already exists.")) {
//                  
//                 Dialog.show("Echec d'inscription", "cet email existe déja.", "OK",null); 
//            
//                }else{
//                Dialog.show("Success", "Utilisateur ajouté", "OK",null); //Code HTTP 200 OK
////                new CodeForm(res).show();
//                  req.removeResponseListener(this);
//                 
//                }
//            }catch(Exception ex) {
//                ex.printStackTrace();
//            }
//                
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//      
//        
//    }

    public boolean oubliepassword(String email) {
        String url = Statics.BASE_URL + "/forgot-passwordJSON?emailUser=" + email;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

//    public boolean inscription(Utilisateur utilisateur) {
//        
//    String nom = utilisateur.getNom_user();
//    String prenom = utilisateur.getPrenom_user();
//    String email = utilisateur.getEmail_user();
//    String motDePass = utilisateur.getMdp_user();
//
//    String url = Statics.BASE_URL + "/inscriptionJson?emailUser=" + email + "&nomUser=" + nom + "&prenomUser=" + prenom + "&mdpUser=" + motDePass;
//    ConnectionRequest req = new ConnectionRequest();
//    req.setUrl(url);
//    req.setPost(false);
//
//    req.addResponseListener(new ActionListener<NetworkEvent>() {
//        @Override
//        public void actionPerformed(NetworkEvent evt) {
//            resultOk = req.getResponseCode() == 200; // Code HTTP 200 OK
//            req.removeResponseListener(this);
//        }
//    });
//
//    NetworkManager.getInstance().addToQueueAndWait(req);
//    return resultOk;
//}
//    
    public boolean connexion(String email, String motDePasse) {

        String url = Statics.BASE_URL + "/loginJson?emailUser=" + email + "&mdpUser=" + motDePasse;
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = req.getResponseData();
                if (data != null) {
                    String response = new String(data);

                    try {
                        // Convertir la réponse JSON en objet Java
                        JSONObject json = new JSONObject(response);

                        // Vérifier les valeurs dans l'objet JSON renvoyé
                        int id = json.getInt("id");
                        String nom = json.getString("nom");
                        String prenom = json.getString("prenom");
                        String email = json.getString("email");
                        String role = json.getString("role");

                        // Vérifier les conditions appropriées pour déterminer la connexion réussie
                        if (id != 0 && !nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !role.isEmpty()) {
                            resultOk = true;
                            System.out.println("ID utilisateur connecté : " + id);
                            utilisateurConnecte = new Utilisateur();
                            utilisateurConnecte.setID_user(id);
                            utilisateurConnecte.setNom_user(nom);
                            utilisateurConnecte.setPrenom_user(prenom);
                            utilisateurConnecte.setEmail_user(email);

                            utilisateurConnecte.setRole_user(role);
                        } else {
                            resultOk = false;
                        }
                    } catch (JSONException e) {
                        resultOk = false;
                    }
                } else {
                    resultOk = false;
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    public void logout() {
    // Réinitialiser les informations de l'utilisateur connecté
    utilisateurConnecte = null;
    resultOk = false;

    // Effectuer d'autres opérations de déconnexion si nécessaire

    // Rediriger vers l'écran de connexion
    LoginForm loginForm = new LoginForm();
    loginForm.show();
}
    

    public void getProfileData(int userId, ProfileCallback callback) {
        String url = Statics.BASE_URL + "/profileJson?id=" + userId;
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(url);

        request.addResponseListener(evt -> {
            byte[] data = request.getResponseData();
            if (data != null) {
                String response = new String(data);

                try {
                    // Convertir la réponse JSON en objet Utilisateur
                    JSONParser parser = new JSONParser();
                    Map<String, Object> json = parser.parseJSON(new CharArrayReader(response.toCharArray()));

                    // Créer un objet Utilisateur avec les données récupérées
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setID_user((int) json.get("id"));
                    utilisateur.setNom_user((String) json.get("nom"));
                    utilisateur.setPrenom_user((String) json.get("prenom"));
                    utilisateur.setEmail_user((String) json.get("email"));
                    // ...

                    // Appeler le callback avec l'objet Utilisateur
                    callback.onProfileReceived(utilisateur);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onProfileError();
                }
            } else {
                callback.onProfileError();
            }
        });

        NetworkManager.getInstance().addToQueue(request);
    }

    public interface ProfileCallback {

        void onProfileReceived(Utilisateur utilisateur);

        void onProfileError();
    }

    public void updatePass(Utilisateur utilisateur, String passactu, String newpass) {
        ConnectionRequest req = new ConnectionRequest(); // Créer une nouvelle instance de ConnectionRequest

        int id = utilisateurConnecte.getID_user();

        String url = Statics.BASE_URL + "/change-password/" + id + "?mdpUser=" + passactu + "&mdpUserConfirm=" + newpass;
        req.setUrl(url);
        req.setPost(true);

        req.addResponseListener((e) -> {
            String json = new String(req.getResponseData());
            if (json.equals("Votre mot de passe actuel n'est pas correct")) {
                Dialog.show("Erreur", "Votre mot de passe actuel n'est pas correct", "OK", null);
            } else if (json.equals("Le nouveau mot de passe ne peut pas être nul")) {
                Dialog.show("Erreur", "Le nouveau mot de passe ne peut pas être nul", "OK", null);
            } else {
                Dialog.show("Succès", "Mot de passe modifié", "OK", null);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

}

//      public void connexion(String email,String password, Resources rs ) {
//        
//       
//          
//       String url = Statics.BASE_URL + "/loginJson?emailUser=" + email + "&mdpUser=" + password;
//
//    req.setUrl(url);
//
//    req.addResponseListener((e) -> {
//    JSONParser j = new JSONParser();
//    String json = new String(req.getResponseData()) + "";
//    try {
//        if (json.equals("cet utilisateur n'existe pas")) {
//            Dialog.show("Echec d'authentification", "Cet utilisateur n'existe pas", "OK", null);
//        } else if (json.equals("Mot de passe invalide")) {
//            Dialog.show("Echec d'authentification", "Mot de passe invalide", "OK", null);
//        } else {
//            // L'authentification a réussi, vous pouvez rediriger l'utilisateur vers une autre page.
//            // Par exemple: new MainPage(res).show();
//            Dialog.show("Vous etes connecté ", "", "OK", null);
////            JSONObject userJson = new JSONObject(json);
////            Utilisateur user = new Utilisateur();
////            user.setID_user(userJson.getInt("id"));
////            user.setNom_user(userJson.getString("nom"));
////            user.setPrenom_user(userJson.getString("prenom"));
////            user.setEmail_user(userJson.getString("email"));
//          
////
////            SessionManager.setLoggedInUser(user);
////                Utilisateur loggedInUser = SessionManager.getLoggedInUser();
////                int loggedInUserId = loggedInUser.getID_user();
////                System.out.println("ID utilisateur : "+loggedInUserId+"USER:"+userJson);
//        }
//    } catch (Exception ex) {
//        ex.printStackTrace();
//    }
//});
//    NetworkManager.getInstance().addToQueueAndWait(req);
//    }

