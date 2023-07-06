package ruslan.dobrov.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {

    private int id;

    @NotEmpty(message = "Название книги не может быть пустым")
    @NotNull
    @Size(min = 1, max = 50, message = "Название дожлно быть от 1 до 50 символов")
    private String title;

    @NotEmpty(message = "Автор не может быть пустым")
    @NotNull
    @Size(min = 1, max = 100, message = "Имя дожлно быть от 1 до 100 символов")
    private String author;

    @Min(value = 0, message = "Год издания не может быть отрицательным")
    private int year;

    private int person_id;

    public Book() {}

    public Book(int id, String title, String author, int year) {
        this.id = id;
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
