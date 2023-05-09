/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingbeasts.doulicha.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author ASUS
 */
public class TwilioSMS {
 public static void send(String numero, String contenu) {

        final String AUTH_TOKEN = "812f0ed0e724d33e2937fa3b984c2aeb";
        final String ACCOUNT_SID = "ACfcd8d65cbce8c17b9d28e3c3d6ffce36";
        
 System.out.println(AUTH_TOKEN);

    
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        
       Message message;
     message = Message.creator(
             new PhoneNumber(numero), // TO
             new PhoneNumber("+12707479756"), // FROM NOUMROUK IL TWILIO
             contenu
     ).create(); 

        System.out.println(message.getSid());
    }
//
}




