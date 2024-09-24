package utilities;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SendEmailReportUsingSMTP2 {

    String emailId = "deepusingh201301@gmail.com";  // Set your email here
    String password = "rkee ieut beds qngf";  // App-specific password
    String receiverEmailId = "gsc9136@gmail.com";  // Set receiver's email here
    static String reName = System.getProperty("user.dir") + "\\reports\\" + "TestReport_2024.09.16.13.16.13.html";

    public void sendReportEmail(String repName) {
        try {
            // Resolve file URL safely
            File reportFile = new File(repName);
            URL url = reportFile.toURI().toURL();

            // Create the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);  // Use port 587 for TLS
            email.setAuthenticator(new DefaultAuthenticator(emailId, password));  // Use app password
            email.setStartTLSEnabled(true);  // Enable TLS
            
            email.setFrom(emailId);   // Sender
            email.setSubject("Test Result");
            email.setMsg("Please find the attached report.");
            email.addTo(receiverEmailId);     // Receiver
            email.attach(url, "Extent Report", "Please check the attached report.");
            
            // Send the email
            email.send();
            
            System.out.println("Email sent successfully!");

        } catch (MalformedURLException e) {
            System.err.println("Failed to create a valid URL for the report.");
            e.printStackTrace();
        } catch (EmailException e) {
            System.err.println("Failed to send email.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SendEmailReportUsingSMTP2 smtp = new SendEmailReportUsingSMTP2();
        smtp.sendReportEmail(reName);
    }
}
