import java.util.List;
import java.util.Objects;

public class Visitor {
    private String name;
    private String surname;
    private String phone;
    private List<Book> favoriteBooks;
    private boolean subscribed;

    public Visitor() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<Book> getFavoriteBooks() { return favoriteBooks; }
    public void setFavoriteBooks(List<Book> favoriteBooks) { this.favoriteBooks = favoriteBooks; }
    public boolean isSubscribed() { return subscribed; }
    public void setSubscribed(boolean subscribed) { this.subscribed = subscribed; }

    @Override
    public String toString() {
        return "Visitor{name='" + name + "', surname='" + surname + "', books=" + (favoriteBooks != null ? favoriteBooks.size() : 0) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return subscribed == visitor.subscribed &&
                Objects.equals(name, visitor.name) &&
                Objects.equals(surname, visitor.surname) &&
                Objects.equals(phone, visitor.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, phone, subscribed);
    }
}