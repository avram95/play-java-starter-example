package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smartphone")
@NamedQuery(name = "Smartphone.getAll", query = "SELECT s FROM Smartphone s")

public class Smartphone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "brand", length = 255)
    private String brand;

    @Column(name = "model", length = 255)
    private String model;

    @Column(name = "article", length = 5000)
    private String article;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;


    public Smartphone(String brand, String model, String article, Date releaseDate) {
        this.brand = brand;
        this.model = model;
        this.article = article;
        this.releaseDate = releaseDate;
    }

    public Smartphone() {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String name) {
        this.brand = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                "id=" + id +
                ", name='" + brand + '\'' +
                ", name='" + model + '\'' +
                ", name='" + article + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }

}
