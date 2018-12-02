package logic;


import java.util.HashMap;
import java.util.Map;

public class MainLoop {
    static Map<String, User> Users = new HashMap<>();

    public static void runLoop(InputOutput ui){
        Request curRequest;
        String curAnswer;

        while (true){
            curRequest = ui.input();

            if (curRequest == null || curRequest.msg.equals(""))
                continue;

            if (!Users.containsKey(curRequest.idUser)){
                Users.put(curRequest.idUser, new User(curRequest.idUser, State.Starting));
            }

            var user = Users.get(curRequest.idUser);
            curAnswer = RequestHandler.HandleRequest(curRequest, user);

            ui.output(user.id, curAnswer);
        }
    }
}
