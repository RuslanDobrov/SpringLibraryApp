package ruslan.dobrov.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {

    private int id;

    @NotEmpty(message = "Book title must not be empty")
    @NotNull
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters long")
    private String title;

    @NotEmpty(message = "Author must not be empty")
    @NotNull
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    private String author;

    @Min(value = 0, message = "Year of publication must be greater than 0")
    private int year;

    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}