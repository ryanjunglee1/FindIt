import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
<<<<<<< HEAD

public class MailSender {
	private static final String sender = "craigslistnotifier1@gmail.com";
	private static final String password = "CS211project";
	private String recipient, subject, body;
=======
import java.io.File;
import java.io.FileNotFoundException;
/**
 * 
 * @author Ryan Lee
 *
 */
public class MailSender {
	private static final String sender = "craigslistnotifier1@gmail.com";
	private static final String password = Password.password;
	private String recipient, subject, body;
	/**
	 * Initializes a MailSender object which can notify users via email
	 * @param recipient
	 * @param subject
	 * @param body
	 */
>>>>>>> master
	
	public MailSender(String recipient, String subject, String body) {
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
	}
	
<<<<<<< HEAD
=======
	/**
	 * Sends the email to the user
	 * @return true if mail is sent, false if not
	 */
>>>>>>> master
	public boolean sendMail() {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			return true;
		}
		catch (MessagingException e) {
<<<<<<< HEAD
			e.printStackTrace();
=======
>>>>>>> master
			return false;
		}
	}
}
