package no.woact.pg4100.assignment2.ronesp13.server.model;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class Book {

    private String author;
    private String title;
    private String ISBN;
    private Integer pages;
    private Integer releaseYear;

    public Book(String author, String title, String ISBN, Integer pages, Integer releaseYear) {
        this.author = author;
        this.title = title;
        this.ISBN = ISBN;
        this.pages = pages;
        this.releaseYear = releaseYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
