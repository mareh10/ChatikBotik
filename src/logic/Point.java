package logic;

public class Point{

    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode(){
        return x * 10 + y;
    }

    public Point add(Point point){
        return new Point(this.x + point.x, this.y + point.y);
    }

    public Point add(int x, int y){
        return new Point(this.x + x, this.y + y);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || obj.getClass() != getClass())
            return false;
        if (obj == this)
            return true;
        Point other = (Point)obj;

        return other.x == this.x && other.y == this.y;
    }

    public static Point parse(String s){
        s = s.toLowerCase();
        if (!s.matches("[a-z]([1-9]|10)"))
            throw new IllegalArgumentException("Wrong point format");
        return new Point(Character.getNumericValue(s.charAt(0) - 49),
                Integer.parseInt(s.substring(1)) - 1);
    }
}
