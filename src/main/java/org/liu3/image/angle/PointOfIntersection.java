package org.liu3.image.angle;

/**
 * 计算线段外一点与线段垂线的交点
 * @Author: liutianshuo
 * @Date: 2020/12/11
 */
public class PointOfIntersection {

    public static void main(String[] args) {

        Point a = new Point(0,0);
        Point b = new Point(3,0);
        Point c = new Point(3,4);

        Line l = new Line(a.x-b.x, a.y-b.y, a);
        Line l1 = new Line(b.y-a.y,a.x-b.x, c);

        //ansx=(b.y-a.y)*t+c.x;
        //ansy=(a.x-b.x)*t+c.y;
        //(ansx-a.x)/(a.x-b.x)=(ansy-a.y)/(a.y-b.y)

        double t=((c.y-a.y)*(a.x-b.x)-(c.x-a.x)*(a.y-b.y))/((a.y-b.y)*(b.y-a.y)-(a.x-b.x)*(a.x-b.x));

        System.out.println("t="+t);
        if(t>1||t<0) {
            System.out.println("不存在");
        }
        double ansx=(b.y-a.y)*t+c.x;
        double ansy=(a.x-b.x)*t+c.y;
        System.out.println(ansx+", "+ansy);


    }



    static class Point{
        double x;
        double y;

        public Point(double x, double y){
            this.x=x;
            this.y=y;
        }
    }

    static class Line{
        double x;
        double y;

        Point a;

        public Line(double x, double y, Point a) {
            this.x = x;
            this.y = y;
            this.a = a;
        }
    }
}
