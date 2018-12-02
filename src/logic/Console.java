package logic;

import java.util.Scanner;

public class Console implements InputOutput{
    private static final Scanner IN = new Scanner(System.in);
    private static final String CONSOLE_ID = "CONSOLE_ID";

    @Override
    public Request input() {
        return new Request(IN.nextLine(), CONSOLE_ID);
    }

    @Override
    public void output(String userId, String answer) {
        System.out.println(answer);
    }
}
