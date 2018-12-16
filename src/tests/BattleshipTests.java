package tests;

import junit.framework.TestCase;
import logic.Battleship;
import logic.Point;
import logic.State;
import logic.User;

import java.util.HashMap;
import java.util.Map;

public class BattleshipTests extends TestCase {
    private static final String WATER_CHAR = "~";
    private static final String SHIP_CHAR = "#";
    private static final String FOG_CHAR = "*";
    private static final String HIT_CHAR = "X";
    private static final String MISS_CHAR = "O";

    HashMap<Point, String> map = new HashMap<>();
    Battleship battleship = new Battleship();
    User user;

    public void setUp(){
        user = new User("1", State.Playing);

        Point point = new Point(0, 0);
        for (int x = 0; x < 4; x++){
            map.put(point, SHIP_CHAR);
            point = point.add(1, 0);
        }
        for (int i = 0; i < 3; i++) {
            map.put(point, WATER_CHAR);
            point = point.add(1, 0);
            map.put(point, SHIP_CHAR);
            point = point.add(1, 0);
        }

        point = new Point(0,2);
        for (int i = 0; i < 2; i++) {
            for (int x = 0; x < 3; x++){
                map.put(point, SHIP_CHAR);
                point = point.add(1, 0);
            }
            map.put(point, WATER_CHAR);
            point = point.add(1, 0);
        }
        map.put(point, SHIP_CHAR);
        point = point.add(1, 0);
        map.put(point, SHIP_CHAR);
        point = point.add(1, 0);

        point = new Point(0, 4);
        for (int i = 0; i < 2; i++) {
            for (int x = 0; x < 2; x++){
                map.put(point, SHIP_CHAR);
                point = point.add(1, 0);
            }
            map.put(point, WATER_CHAR);
            point = point.add(1, 0);
        }
        map.put(point, SHIP_CHAR);
        point = point.add(1, 0);

        for (int x = 0; x < 10; x++){
            for (int y = 0; y < 10; y++){
                try {
                    if (map.get(new Point(x, y)).equals(SHIP_CHAR)) continue;
                }
                catch (NullPointerException e) {
                    map.put(new Point(x, y), WATER_CHAR);
                }
            }
        }
        battleship.enemyMap = map;
        battleship.handleGameRequest("blablabla", user);
    }

    public void testHitShoot(){
        battleship.handleGameRequest("a1", user);
        assertEquals(HIT_CHAR, battleship.enemyMap.get(new Point(0,0)));
    }

    public void testMissShoot(){
        battleship.handleGameRequest("a2", user);
        assertEquals(MISS_CHAR, battleship.enemyMap.get(new Point(0,1)));
    }

    public void testWrongPoint(){
        assertEquals("Некорректная точка, попробуй еще", battleship.handleGameRequest("a20", user));
    }
}
