/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.entities;

/**
 *
 * @author Admin
 */
public class Projet {
    private int idProjet;
    private String nomProjet,descriptionProjet,imageProjet;
    private float objectifProjet;
    private int etatProjet;

    public Projet(int idProjet, String nomProjet, String descriptionProjet, float objectifProjet, int etatProjet,String imageProjet) {
        this.idProjet = idProjet;
        this.nomProjet = nomProjet;
        this.descriptionProjet = descriptionProjet;
        this.imageProjet = imageProjet;
        this.objectifProjet = objectifProjet;
        this.etatProjet = etatProjet;
    }

    public Projet() {
         
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getDescriptionProjet() {
        return descriptionProjet;
    }

    public void setDescriptionProjet(String descriptionProjet) {
        this.descriptionProjet = descriptionProjet;
    }

    public String getImageProjet() {
        return imageProjet;
    }

    public void setImageProjet(String imageProjet) {
        this.imageProjet = imageProjet;
    }

    public float getObjectifProjet() {
        return objectifProjet;
    }

    public void setObjectifProjet(float objectifProjet) {
        this.objectifProjet = objectifProjet;
    }

    public int getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(int etatProjet) {
        this.etatProjet = etatProjet;
    }

    @Override
    public String toString() {
        return "Projet{" + "idProjet=" + idProjet + ", nomProjet=" + nomProjet + ", descriptionProjet=" + descriptionProjet + ", imageProjet=" + imageProjet + ", objectifProjet=" + objectifProjet + ", etatProjet=" + etatProjet + '}';
    }

    public Projet(String nomProjet, String descriptionProjet, String imageProjet, float objectifProjet, int etatProjet) {
        this.nomProjet = nomProjet;
        this.descriptionProjet = descriptionProjet;
        this.imageProjet = imageProjet;
        this.objectifProjet = objectifProjet;
        this.etatProjet = etatProjet;
    }
    
}
