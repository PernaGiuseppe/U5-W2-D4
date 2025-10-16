package U5_W2_D3.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class NewBlogPayload {

    @NotBlank(message = "Il titolo è obbligatorio")
    private String title;

    @NotBlank(message = "Il contenuto è obbligatorio")
    private String content;

    @Min(value = 1, message = "Il tempo di lettura deve essere almeno 1 minuto")
    private int readingTime;

    @NotNull(message = "L'ID dell'autore è obbligatorio")
    private String authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
