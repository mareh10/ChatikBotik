package logic;

public class User {
    public State state;
    public String id;
    public Game game;

    public User(String id, State state) {
        this.state = state;
        this.id = id;
    }
}
