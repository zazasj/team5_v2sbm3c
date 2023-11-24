package dev.mvc.tool;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailTool {
    /**
     * 메일 전송
     * @param receiver 메일 받을 이메일 주소
     * @param from 보내는 사람 이메일 주소
     * @param title 제목
     * @param content 전송 내용
     */
    public void send(String receiver, String from, String title, String content) {
      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
      
      // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
              String user="zazang5@gmail.com";
              String password="xvtb dbim rlba dzll";
              return new PasswordAuthentication(user, password);
          }
      });
    
      Message message = new MimeMessage(session);
      try {
          message.setFrom(new InternetAddress(from));
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
          message.setSubject(title);
          message.setContent(content, "text/html; charset=utf-8");

          Transport.send(message);
      } catch (Exception e) {
          e.printStackTrace();
      }    
  }
 
  
  
}