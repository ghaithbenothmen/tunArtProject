package Entites;

import Utils.ConnexionDB;
import Utils.ConnexionDB;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.Connection;

public class SMSsender {

    Connection cnx;

    public SMSsender() {
        cnx = ConnexionDB.getInstance().getCon();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "ACebb020ed73696b12b0835bf3f358138b";
    public static final String AUTH_TOKEN = "50207e23cd49f3ae90a7a4f08bbc72d2";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "ACebb020ed73696b12b0835bf3f358138b";
        String authToken = "50207e23cd49f3ae90a7a4f08bbc72d2";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+18588793064"),
                    "Votre compte est vérifié"
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}