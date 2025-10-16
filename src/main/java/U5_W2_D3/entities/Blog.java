package U5_W2_D3.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "blogs")
@ToString
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private int tempoDiLettura;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    
    public Blog(String categoria, String titolo, String contenuto, int tempoDiLettura, Author author) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.cover = "https://picsum.photos/200/300";
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public int getTempoDiLettura() {
        return tempoDiLettura;
    }

    public void setTempoDiLettura(int tempoDiLettura) {
        this.tempoDiLettura = tempoDiLettura;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
