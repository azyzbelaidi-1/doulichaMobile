
package codingbeasts.doulicha.entities;







public class Discussion {
    private int ID_discussion;
    private int ID_user;
    private String titre_discussion;
    private String contenu_discussion;
    private String date_discussion;
    private String image_discussion;
    public Discussion() {
    }

    public Discussion(int ID_user, String titre_discussion, String contenu_discussion, String date_discussion, String image_discussion) {
        this.ID_user = ID_user;
        this.titre_discussion = titre_discussion;
        this.contenu_discussion = contenu_discussion;
        this.date_discussion = date_discussion;
        this.image_discussion = image_discussion;
    }

    public Discussion(int ID_discussion, int ID_user, String titre_discussion, String contenu_discussion, String date_discussion, String image_discussion) {
        this.ID_discussion = ID_discussion;
        this.ID_user = ID_user;
        this.titre_discussion = titre_discussion;
        this.contenu_discussion = contenu_discussion;
        this.date_discussion = date_discussion;
        this.image_discussion = image_discussion;
    }

    public String getImage_discussion() {
        return image_discussion;
    }

    public void setImage_discussion(String image_discussion) {
        this.image_discussion = image_discussion;
    }


    public int getID_discussion() {
        return ID_discussion;
    }

    public void setID_discussion(int ID_discussion) {
        this.ID_discussion = ID_discussion;
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public String getTitre_discussion() {
        return titre_discussion;
    }

    public void setTitre_discussion(String titre_discussion) {
        this.titre_discussion = titre_discussion;
    }

    public String getContenu_discussion() {
        return contenu_discussion;
    }

    public void setContenu_discussion(String contenu_discussion) {
        this.contenu_discussion = contenu_discussion;
    }

    public String getDate_discussion() {
        return date_discussion;
    }

    public void setDate_discussion(String date_discussion) {
        this.date_discussion = date_discussion;
    }

    @Override
    public String toString() {
        return "Discussion{" + "ID_discussion=" + ID_discussion + ", ID_user=" + ID_user + ", titre_discussion=" + titre_discussion + ", contenu_discussion=" + contenu_discussion + ", date_discussion=" + date_discussion + '}';
    }
    
}