import java.sql.*;
import java.util.ArrayList;

public class User {

    protected String username;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected Country country;
    protected int id;
    protected String status;
    protected Date birthday;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(){};

    public User(String username, String firstName, String lastName, String status, String password, String email, int id, Date birthday){
        this.username = username;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void upgradeToPremium(Connection myConn, ArrayList<PremiumUser> premiumUsers, ArrayList<StandardUser> standardUsers) throws SQLException {
    }
}
