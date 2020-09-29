package eu.mrndesign.matned.portfolioapp.message;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_TASK;

public class Message {


    private Long id;

    @NotNull(message = "The email cannot be empty")
    @NotEmpty(message = "The email cannot be empty")
    @Size(min = 5, message = "The email must be at least {min} signs long")
    @Pattern(
            regexp = ".{1,}@.{1,}[.].{2,3}",
            message = "It should be a valid email address"
    )
    private String email;

    @NotNull(message = "The subject cannot be empty")
    @NotEmpty(message = "The subject cannot be empty")
    @Size(min = 5, message = "The subject must be at least {min} signs long")
    private String subject;

    @NotNull(message = "The message cannot be empty")
    @NotEmpty(message = "The message cannot be empty")
    @Size(min = 10, max = 1000, message = "The message must be min {min} and max {max} signs long")
    private String message;
    private String date;

    public Message() {
        id = 0L;
        date = LocalDateTime.now().format(DATE_TIME_FORMATTER_TASK);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
