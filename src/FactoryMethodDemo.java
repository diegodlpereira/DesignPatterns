
class Clazz {
    private int x,y;

    private Clazz(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Clazz newA(int x, int y) {
        return new Clazz(x,y);
    }

    public static Clazz newB(int x, int y) {
        return new Clazz((x*2), (y*2));
    }
}


public class FactoryMethodDemo {

    public static void main(String[] args) {
        Clazz A = Clazz.newA(2, 1);
        Clazz B = Clazz.newB(2, 2);
    }

}
