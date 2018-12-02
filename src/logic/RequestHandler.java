package logic;

public class RequestHandler {
    private static final String[] GAMES = new String[] {"1. Викторина"};

    public static String HandleRequest(Request request, User user){
        String curAnswer = "";

        if (user.state == State.Starting){
            user.state = State.Choosing;
            return String.join("\n", GAMES);
        }

        if (user.state == State.Choosing){
            switch (request.msg){
                case "1":
                    user.state = State.Playing;
                    user.game = new Quiz();
                    break;
                default:
                    return "Попробуй ещё раз:3";
            }
        }

        if (user.state == State.Playing){
            return user.game.handleGameRequest(request.msg, user);
        }

        return null;
    }
}
