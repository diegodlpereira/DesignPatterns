class Rectangle {

    private int height, width;

    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Rectangle() {
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getArea(){
        return (height * width);
    }
}

class Square extends Rectangle {

    public Square() {
    }

    public Square(int side) {
        super(side, side);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }
}

class ShapeFactory {

    public Rectangle newRectangle(int width, int height){
        return new Rectangle(width,height);
    }

    public Rectangle newSquare(int side){
        return new Rectangle(side,side);
    }
}

public class LSPDemo {

    static void useIt(Rectangle rc){
        int width = rc.getWidth();
        rc.setHeight(10);

        System.out.println("Area should be: " + (width * 10) + ", got:" + rc.getArea());
    }

    public static void main(String[] args) {
        Rectangle rc = new Rectangle(2,3);
        useIt(rc);

        Rectangle sq = new Square();
        sq.setHeight(5);
        sq.setWidth(6);
        useIt(sq); // when useIt method is called square object behaves incorrectly

        // Potential solution for that is create a ShapeFactory (example above)
    }

}
