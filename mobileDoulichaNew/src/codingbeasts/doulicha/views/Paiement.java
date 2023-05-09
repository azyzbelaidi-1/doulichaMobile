/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.views;

import com.codename1.io.Storage;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import codingbeasts.doulicha.services.ServiceDon;
 
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Source;
import com.stripe.model.Token;
import com.stripe.param.AccountCreateParams.Company.Verification.Document;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.TokenCreateParams;
import java.io.OutputStream;
 

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.text.TextAlignment;
import java.lang.String;
/**
 *
 * @author Admin
 */
public class Paiement extends BaseForm {
    private TextField cmail;
    private TextField cnom;
    private TextField cnum;
    private TextField month;
    private TextField year;
    private TextField cvc;
    private TextField prix;
    
    public Paiement() {
        
       setTitle("Un Don !");
       getStyle().setFgColor(0x0000FF);

        // création de champs de texte pour les informations de paiement
        cmail = new TextField("", "email", 20, TextField.ANY);
        cmail.getAllStyles().setFgColor(0x000000);
        cnom = new TextField("", "nom", 20, TextField.ANY);
        cnom.getAllStyles().setFgColor(0x000000);
        cnum = new TextField("", "num", 20, TextField.ANY);
        cnum.getAllStyles().setFgColor(0x000000);
        month = new TextField("", "month", 20, TextField.ANY);
        month.getAllStyles().setFgColor(0x000000);
        year = new TextField("", "year", 20, TextField.ANY);
        year.getAllStyles().setFgColor(0x000000);
        cvc = new TextField("", "cvc", 20, TextField.ANY);
        cvc.getAllStyles().setFgColor(0x000000);
        prix = new TextField("", "prix", 20, TextField.ANY);
        prix.getAllStyles().setFgColor(0x000000);
        
        
        // ajout des champs de texte et du bouton de paiement au formulaire
        ParentPaiement parentPaiement = new ParentPaiement(cmail, cnom, cnum, month, year, cvc, prix);
        addAll(cmail, cnom, cnum, month, year, cvc, prix, parentPaiement.getButton());
    }
    
    private static class ParentPaiement {
        private TextField cmail;
        private TextField cnom;
        private TextField cnum;
        private TextField month;
        private TextField year;
        private TextField cvc;
        private TextField prix;
        private Button button;

        public ParentPaiement(TextField cmail, TextField cnom, TextField cnum, TextField month, TextField year, TextField cvc, TextField prix) {
            this.cmail = cmail;
            this.cnom = cnom;
            this.cnum = cnum;
            this.month = month;
            this.year = year;
            this.cvc = cvc;
            this.prix = prix;
            button = new Button("Payer");
            button.addActionListener((e) -> {
                try {
                    payer();
                    Resources res = null;
                    new ListDonPaye(res).show();
                } catch (StripeException ex) {
                    Dialog.show("Erreur", "Une erreur est survenue lors du paiement, veuillez réessayer ultérieurement", "OK", null);
                }
            });
        }

        public Button getButton() {
            return button;
        }

        public void payer() throws StripeException {
            String mail= cmail.getText();
            String nom= cnom.getText();
            String num= cnum.getText();
            String cmois= month.getText();
            String cAnn= year.getText();
            String ccvc= cvc.getText();
            String cprix= prix.getText();

            int mois = Integer.parseInt(cmois);
            int annee = Integer.parseInt(cAnn);
            int p = Integer.parseInt(cprix) * 100;

// Initialisation de la clé d'API Stripe
 if (cnom.getText().isEmpty()) {
                Dialog.show("Erreur", "Veuillez saisir un nom", "OK", null);
                return;
            }
            if (cnum.getText().isEmpty() || cnum.getText().length() != 16) {
                Dialog.show("Erreur", "Veuillez saisir un numéro de carte valide (16 chiffres)", "OK", null);
                return;
            }
            if (month.getText().isEmpty() || month.getText().length() != 2) {
                Dialog.show("Erreur", "Veuillez saisir un mois de validité valide (format MM)", "OK", null);
                return;
            }
            if (year.getText().isEmpty() || year.getText().length() != 4) {
                Dialog.show("Erreur", "Veuillez saisir une année de validité valide (format YYYY)", "OK", null);
                return;
}
if (cvc.getText().isEmpty() || cvc.getText().length() != 3) {
Dialog.show("Erreur", "Veuillez saisir un code de sécurité valide (3 chiffres)", "OK", null);
return;
}
if (prix.getText().isEmpty() || Integer.parseInt(prix.getText()) <= 0) {
Dialog.show("Erreur", "Veuillez saisir un montant valide", "OK", null);
return;
}
Stripe.apiKey = "sk_test_51Mf1wLLusEKclnk8QPGDh0xRPSORk53EJvGWiiYcyPJY5KMHEziRmaJyjtDhiBTQVI3ddwWMZXIDVV1uuGw4w6fo00Bkad1CYS";

// Création d'un client Stripe
CustomerCreateParams.Builder customerCreateParamsBuilder = new CustomerCreateParams.Builder()
        .setEmail(mail)
        .setName(nom);

    try {
    Customer customer = Customer.create(customerCreateParamsBuilder.build());
    // Ajout d'une carte bancaire au client
    TokenCreateParams.Builder tokenCreateParamsBuilder = new TokenCreateParams.Builder()
            .setCard(new TokenCreateParams.Card.Builder()
                    .setNumber(num)
                    .setExpMonth(cmois)
                    .setExpYear(cAnn)
                    .setCvc(ccvc)
                    .build());
    Token token = Token.create(tokenCreateParamsBuilder.build());
    CustomerUpdateParams.Builder customerUpdateParamsBuilder = new CustomerUpdateParams.Builder()
            .setSource(token.getId());
    customer.update(customerUpdateParamsBuilder.build());

    // Paiement d'un montant donné en utilisant le client et la carte bancaire
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", p);
    chargeParams.put("currency", "eur");
    chargeParams.put("description", "Achat sur notre site web");
    chargeParams.put("customer", customer.getId());
    Charge charge = Charge.create(chargeParams);
    // Affichage d'un message de confirmation
Dialog.show("Confirmation", "Paiement effectué avec succès, merci pour votre achat !", "OK", null);
} catch (CardException e) {
// En cas d'erreur de carte bancaire, affichage d'un message d'erreur
Dialog.show("Erreur", "La carte bancaire a été refusée, veuillez vérifier les informations saisies", "OK", null);
} catch (StripeException e) {
// En cas d'erreur Stripe, affichage d'un message d'erreur
Dialog.show("Erreur", "Une erreur est survenue lors du paiement, veuillez réessayer ultérieurement", "OK", null);
}
    }
}
    
    
 
}