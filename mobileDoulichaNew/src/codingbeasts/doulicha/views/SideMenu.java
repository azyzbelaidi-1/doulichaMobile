/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.entities.Utilisateur;
import codingbeasts.doulicha.services.ServicesUtilisateur;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;



public abstract class SideMenu extends Form {
    public SideMenu(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenu(String title) {
        super(title);
    }

    public SideMenu() {
    }

    public SideMenu(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }
    
     public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public void setupSideMenu(Resources res) {
//        Container cnt1 = new Container(BoxLayout.y());
//        SpanLabel test = new SpanLabel("TEST");
//        cnt1.add(test);
//        Container sidemenuTop = BorderLayout.center(cnt1);
//        sidemenuTop.setUIID("SidemenuTop");

//        getToolbar().addComponentToSideMenu(sidemenuTop);
Utilisateur utilisateur = ServicesUtilisateur.getInstance().getUtilisateurConnecte();

            getToolbar().addCommandToSideMenu("Evenements", null, e -> new ListEvents(res).show());
            getToolbar().addCommandToSideMenu("Logements", null, e -> new afficherLogementsForm(res).show());
            getToolbar().addCommandToSideMenu("Avis", null, e -> new ListCategorieForm(res).show());
             getToolbar().addCommandToSideMenu("Boutique", null, e -> new Listprods(res).show());
              getToolbar().addCommandToSideMenu("Forum de discussions", null, e -> new DiscussionsForm(res).show());
              getToolbar().addCommandToSideMenu("Projets à financer", null, e -> new ListProjetForm(res).show());
           getToolbar().addCommandToSideMenu("----------------",null,e-> new Home(res).show());
             
            getToolbar().addCommandToSideMenu("Mes Participations", null, e -> new ListParticipations(res).show());
            getToolbar().addCommandToSideMenu("Mes Réservations", null, e -> new MesReservationsForm(res).show());
             getToolbar().addCommandToSideMenu("Mes réclamations", null, e -> new ListReclamationForm(res).show());
              getToolbar().addCommandToSideMenu("Mes dons", null, e -> new ListDonForm(res).show());
             getToolbar().addCommandToSideMenu("Les dons effectués", null, e -> new ListDonPaye(res).show());
            getToolbar().addCommandToSideMenu("Mon panier", null, e -> new panier(res).show());
            
            getToolbar().addCommandToSideMenu("----------------",null,e-> new Home(res).show());
              getToolbar().addCommandToSideMenu("Changer mon Mot de passe", null, e -> new ModifierPasswordForm(res).show());
              
              
              Command profileCommand = new Command("Mon profil") {
    @Override
    public void actionPerformed(ActionEvent evt) {
        Utilisateur utilisateur = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
        if (utilisateur != null) {
            profileForm profileFormInstance = new profileForm(utilisateur, res);
            profileFormInstance.show();
        } else {
            Dialog.show("Erreur", "Aucun utilisateur connecté", "OK", null);
        }
    }
};

getToolbar().addCommandToSideMenu(profileCommand);
//               getToolbar().addCommandToSideMenu("Mon profil",null, e-> new profileForm(utilisateur, res));
/*getToolbar().addCommandToSideMenu("Mon profil", null, e -> {
    // Retrieve the currently logged in user's information
    Utilisateur currentUser = getCurrentUser(); // Replace this with your own implementation

    // Create an instance of the profileForm and pass the user's information
    Form profileForm = new profileForm(currentUser, getResources());
    profileForm.show();
}); */
/*
Command profileCommand = new Command("Mon profil") {
    public void actionPerformed(ActionEvent evt) {
        Utilisateur utilisateur = ServicesUtilisateur.getInstance().getUtilisateurConnecte();
        if (utilisateur != null) {
            profileForm profileFormInstance = new profileForm(utilisateur);
            profileFormInstance.show();
        } else {
            Dialog.show("Erreur", "Aucun utilisateur connecté", "OK", null);
        }
    }
};

getToolbar().addCommandToSideMenu(profileCommand);*/
              getToolbar().addCommandToSideMenu("Se déconnecter", null, e -> ServicesUtilisateur.getInstance().logout() );
            
            
           // getToolbar().addCommandToSideMenu("Article", null, e -> new Art(res).show());

        }
        

    protected abstract void showOtherForm(Resources res);
}
