package practice_2;

public class Rectangle {
    int width;
    int height;

    Rectangle(int width, int height){
        this.width = width;
        this.height = height;
    }

    int getWidth(){
        return this.width;
    }

    int getHeight(){
        return this.height;
    }

    void setWidth(int newWidth){
        this.width = newWidth;
    }

    int calculateArea(int width, int height){
        return width * height;
    }

}
