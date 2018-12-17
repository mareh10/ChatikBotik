package logic;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Battleship implements Game {
    // еще килы, закрытую мапу и постепенное открытие

    public Map<Point, String> playerMap;
    public Map<Point, String> enemyMap;
    public Map<Point, String> hiddenEnemyMap;
    private static final String WATER_CHAR = "~";
    private static final String SHIP_CHAR = "#";
    private static final String FOG_CHAR = "*";
    private static final String HIT_CHAR = "X";
    private static final String MISS_CHAR = "O";

    private boolean isWaitingUserAnswer = false;

    private boolean playerTurnFlag = true;

    public Battleship(){
        playerMap = generateMap();
        enemyMap = generateMap();
        hiddenEnemyMap = hiddenEnemyMap();
    }

    @Override
    public String handleGameRequest(String msg, User user) {
        if (!isWaitingUserAnswer) {
            isWaitingUserAnswer = true;
            return "Ваша карта: \n" + formatMap(playerMap) + "\n" +
                    "Карта оппонента \n" + formatMap(hiddenEnemyMap);
        }

        var random = new Random();

        Point point;
        StringBuilder message = new StringBuilder();

        if (playerTurnFlag) {
            try {
                point = Point.parse(msg);
            } catch (IllegalArgumentException e) {
                return "Некорректная точка, попробуй еще";
            }
            if (enemyMap.get(point).equals(WATER_CHAR)) {
                hiddenEnemyMap.replace(point, MISS_CHAR);
                enemyMap.replace(point, MISS_CHAR);
                playerTurnFlag = false;
                message.append("Мимо \n" + "Карта: \n" + formatMap(playerMap) + "\n\n" +
                        "Карта оппонента \n" + formatMap(hiddenEnemyMap));

                boolean botMissed = false;
                while (!botMissed) {
                    point = new Point(random.nextInt(10), random.nextInt(10));
                    if (playerMap.get(point).equals(WATER_CHAR)) {
                        playerMap.replace(point, MISS_CHAR);
                        playerTurnFlag = true;
                        botMissed = true;
                        message.append("Оппонент не попал! \n" + "Карта: \n" + formatMap(playerMap) + "\n" +
                                "Карта оппонента\n" + formatMap(hiddenEnemyMap));
                    }
                    else if (playerMap.get(point).equals(SHIP_CHAR)) {
                        playerMap.replace(point, HIT_CHAR);
                        message.append("Оппонент попал! \n" + "Карта: \n" + formatMap(playerMap) + "\n" +
                                "Карта оппонента\n" + formatMap(hiddenEnemyMap));
                    }
                }
            }
            else if (enemyMap.get(point).equals(SHIP_CHAR)) {
                hiddenEnemyMap.replace(point, HIT_CHAR);
                enemyMap.replace(point, HIT_CHAR);
                message.append("Попал! \n\n" + "Карта: \n" + formatMap(playerMap) + "\n" +
                        "Карта оппонента\n" + formatMap(hiddenEnemyMap));
            }
            else
                return "Сюда уже стрелял, попробуй еще";
        }

        if (!enemyMap.values().contains(SHIP_CHAR)) {
            user.state = State.Choosing;
            message.append("Ты победил! Грац! \n Что дальше? \n \t 1. Викторина \n\t 2. Морской бой");
        }

        if (!playerMap.values().contains(SHIP_CHAR)) {
            user.state = State.Choosing;
            message.append("Ты проиграл! Очень жаль :( \n Что дальше? \n \t 1. Викторина \n\t 2. Морской бой");
        }

        return message.toString();
    }

    private static Point getDirection(){
        var random = new Random();
        int dx = random.nextInt(2);
        int dy = random.nextInt(2);
        Point direction = new Point(dx, dy);
        while (dx == dy){
            dx = random.nextInt(2);
            dy = random.nextInt(2);
            direction = new Point(dx, dy);
        }
        return direction;
    }

    private boolean isPossibleToPlaceOnField(Map<Point, String> map, Point point){
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                try {
                    if (point.x >= 10 || point.y >= 10)
                        return false;
                    if (map.get(point.add(x, y)).equals(SHIP_CHAR))
                        return false;
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }
        return true;
    }

    private boolean allLengthIsClear(Map<Point, String> map, Point point, Point direction, int length) {
        for (int i = 0; i < length; i++){
            if (!isPossibleToPlaceOnField(map, point))
                return false;
            point = point.add(direction);
        }
        return true;
    }

    private HashMap<Point, String> generateMap(){
        var map = new HashMap<Point, String>();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                map.put(new Point(x, y), WATER_CHAR);
            }
        }

        var random = new Random();
        Point point = new Point(random.nextInt(10), random.nextInt(10));
        var direction = getDirection();

        while (true) {
            if (allLengthIsClear(map, point, direction, 4)) {
                for (int i = 0; i < 4; i++) {
                    map.replace(point, SHIP_CHAR);
                    point = point.add(direction);
                }
                break;
            }
            point = new Point(random.nextInt(10) + 1, random.nextInt(10) + 1);
        }

        for (int k = 0; k < 2; k++) {
            while (true) {
                point = new Point(random.nextInt(10) + 1, random.nextInt(10) + 1);
                direction = getDirection();
                if (allLengthIsClear(map, point, direction, 3)) {
                    for (int i = 0; i < 3; i++) {
                        map.replace(point, SHIP_CHAR);
                        point = point.add(direction);
                    }
                    break;
                }
            }
        }

        for (int k = 0; k < 3; k++) {
            while (true) {
                point = new Point(random.nextInt(10) + 1, random.nextInt(10) + 1);
                direction = getDirection();
                if (allLengthIsClear(map, point, direction, 3)) {
                    for (int i = 0; i < 2; i++) {
                        map.replace(point, SHIP_CHAR);
                        point = point.add(direction);
                    }
                    break;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            while (true) {
                point = new Point(random.nextInt(10), random.nextInt(10));
                if (isPossibleToPlaceOnField(map, point)) {
                    map.replace(point, SHIP_CHAR);
                    break;
                }
            }
        }
        return map;
    }

    public static String formatMap(Map<Point, String> map){
        var sb = new StringBuilder();
        sb.append("__|a |b |c |d|e |f |g |h| i |j |\n");
        for (int y = 0; y < 10; y++) {
            if (y != 9)
                sb.append(y + 1 + "_|");
            else
                sb.append(y + 1 + "|");
            for (int x = 0; x < 10; x++) {
                sb.append(map.get(new Point(x, y)));
                sb.append("|");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public HashMap<Point, String> hiddenEnemyMap(){
        var map = new HashMap<Point, String>();
        for (int x = 0; x < 10; x++){
            for (int y = 0; y < 10; y++){
                map.put(new Point(x, y), FOG_CHAR);
            }
        }
        return map;
    }
}

