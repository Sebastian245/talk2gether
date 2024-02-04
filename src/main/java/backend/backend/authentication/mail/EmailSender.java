package backend.backend.authentication.mail;

public interface EmailSender {
    void send(String to, String email);
}
