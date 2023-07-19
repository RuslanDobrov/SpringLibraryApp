package ruslan.dobrov.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @NotNull
    @Size(min = 2, max = 100, message = "Имя дожлно быть от 2 до 100 символов длиной")
    private String fullName;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900 года")
    private int yearOfBirth;

    public Person() {}

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
