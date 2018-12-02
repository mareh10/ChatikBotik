package logic;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

public class VkBot implements InputOutput {
    private static final Group GROUP = new Group(174771721,
            "a882792412bfa72a9e1f48a33306de3bb06e637f2121eccca34e1eb2cedadaa5b5927080a23696136a1bf");
    private ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<>();

    public Request input() {
        GROUP.onEveryMessage(message ->
                requestQueue.add(new Request(message.getText(), message.authorId().toString())));

        return requestQueue.poll();
    }

    public void output(String userId, String output) {
        new Message()
                .from(GROUP)
                .to(Integer.parseInt(userId))
                .text(output)
                .send();
    }
}
