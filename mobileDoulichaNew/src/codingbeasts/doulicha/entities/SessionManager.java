/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.entities;

/**
 *
 * @author Asus
 */
public class SessionManager {
    private static Utilisateur utilisateurConnecte;

    public static Utilisateur getLoggedInUser() {
        return utilisateurConnecte;
    }

    public static void setLoggedInUser(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }
    
}
