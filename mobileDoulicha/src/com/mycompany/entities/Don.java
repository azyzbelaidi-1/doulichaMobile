/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

 
/**
 *
 * @author Admin
 */
public class Don {
     
    private int idDon;
    private float valeurDon;
    private String dateDon;
    private int idProjet;
    private int idUser;
    private int etatpaiement ;

    public Don(int idDon, float valeurDon, String dateDon, int idProjet, int idUser, int etatpaiement) {
        this.idDon = idDon;
        this.valeurDon = valeurDon;
        this.dateDon = dateDon;
        this.idProjet = idProjet;
        this.idUser = idUser;
        this.etatpaiement = etatpaiement;
    }

    public Don() {
        
    }

    public int getIdDon() {
        return idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public float getValeurDon() {
        return valeurDon;
    }

    public void setValeurDon(float valeurDon) {
        this.valeurDon = valeurDon;
    }

    public String getDateDon() {
        return dateDon;
    }

    public void setDateDon(String dateDon) {
        this.dateDon = dateDon;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getEtatpaiement() {
        return etatpaiement;
    }

    public void setEtatpaiement(int etatpaiement) {
        this.etatpaiement = etatpaiement;
    }

    @Override
    public String toString() {
        return "Don{" + "idDon=" + idDon + ", valeurDon=" + valeurDon + ", dateDon=" + dateDon + ", idProjet=" + idProjet + ", idUser=" + idUser + ", etatpaiement=" + etatpaiement + '}';
    }

    
    
}
