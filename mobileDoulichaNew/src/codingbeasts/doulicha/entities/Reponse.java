/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.entities;





public class Reponse {
    private int ID_reponse;
    private int ID_user;
    private int ID_discussion;
    private String contenu_reponse;
    private String date_reponse;
        private String image_reponse;


    public Reponse() {
    }

    public Reponse(int ID_reponse, int ID_user, int ID_discussion, String contenu_reponse, String date_reponse, String image_reponse) {
        this.ID_reponse = ID_reponse;
        this.ID_user = ID_user;
        this.ID_discussion = ID_discussion;
        this.contenu_reponse = contenu_reponse;
        this.date_reponse = date_reponse;
        this.image_reponse = image_reponse;
    }

    public Reponse(int ID_user, int ID_discussion, String contenu_reponse, String date_reponse, String image_reponse) {
        this.ID_user = ID_user;
        this.ID_discussion = ID_discussion;
        this.contenu_reponse = contenu_reponse;
        this.date_reponse = date_reponse;
        this.image_reponse = image_reponse;
    }

    public String getImage_reponse() {
        return image_reponse;
    }

    public void setImage_reponse(String image_reponse) {
        this.image_reponse = image_reponse;
    }


    public int getID_reponse() {
        return ID_reponse;
    }

    public void setID_reponse(int ID_reponse) {
        this.ID_reponse = ID_reponse;
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public int getID_discussion() {
        return ID_discussion;
    }

    public void setID_discussion(int ID_discussion) {
        this.ID_discussion = ID_discussion;
    }

    public String getContenu_reponse() {
        return contenu_reponse;
    }

    public void setContenu_reponse(String contenu_reponse) {
        this.contenu_reponse = contenu_reponse;
    }

    public String getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(String date_reponse) {
        this.date_reponse = date_reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" + "ID_reponse=" + ID_reponse + ", ID_user=" + ID_user + ", ID_discussion=" + ID_discussion + ", contenu_reponse=" + contenu_reponse + ", date_reponse=" + date_reponse + ", image_reponse=" + image_reponse + '}';
    }

 
}