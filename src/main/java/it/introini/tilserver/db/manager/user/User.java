package it.introini.tilserver.db.manager.user;

/**
 * Created by thomas on 16/02/16.
 */
public class User {
    private final long id;
    private final String login;
    private final String password;

    public static User of(long id, String login, String password){
        return new User(id, login, password);

    }

    private User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
