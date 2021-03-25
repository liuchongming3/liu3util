package org.liu3.image.angle;

/**
 * 计算两条直线的夹角
 *
 * @Author: liutianshuo
 * @Date: 2020/12/4
 */
public class CalcAngle {


    public static void main(String[] args) {

        Point p1 = new Point(5, 0);
        Point p2 = new Point(8, 4);
        Point p3 = new Point(8, 0);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        System.out.println(getInAngle2(p1, p2, p3));
        System.out.println(getInAngle2(p2, p1, p3));
        System.out.println(getInAngle2(p3, p1, p2));
    }

    public static double getInAngle2(Point p1, Point p2, Point p3) {


//        int t = (p2.x - p1.x) * (p3.x - p1.x) + (p2.y - p1.y) * (p3.y - p1.y);
//
//
////        int i1 = Math.abs((p2.x - p1.x) * (p2.x - p1.x)) + Math.abs((p2.y - p1.y) * (p2.y - p1.y));
////        int i2 = Math.abs((p3.x - p1.x) * (p3.x - p1.x)) + Math.abs((p3.y - p1.y) * (p3.y - p1.y));
//
//        Point i1 = new Point(p1.x-p3.x,p1.y-p3.y);
//        Point i2 = new Point(p2.x-p3.x,p2.y-p3.y);
//
//        int dot = i1.x * i2.x + i1.y * i2.y;
//        int det = i1.x * i2.y - i1.y * i2.x;
//
//        double angle = Math.atan2(dot, det) / Math.PI * 180;
//
//
//        return (angle+360)%360;
        return 1;
    }

    /**
     *
     * @param p1 两条直线夹角的点
     * @param p2
     * @param p3
     * @return
     */
    public static double getInAngle(Point p1, Point p2, Point p3) {


        int t = (p2.x - p1.x) * (p3.x - p1.x) + (p2.y - p1.y) * (p3.y - p1.y);

        //为了精确直接使用而不使用中间变量
        /**
         * 步骤：
         * A=向量的点乘/向量的模相乘
         * B=arccos(A)，用反余弦求出弧度
         * result=180*B/π 弧度转角度制
         */

        int i1 = Math.abs((p2.x - p1.x) * (p2.x - p1.x)) + Math.abs((p2.y - p1.y) * (p2.y - p1.y));
        int i2 = Math.abs((p3.x - p1.x) * (p3.x - p1.x)) + Math.abs((p3.y - p1.y) * (p3.y - p1.y));

        //角度=180°×弧度/π
        //弧度=角度×π/180°

        double result = 180 * Math.acos(t / Math.sqrt(i1 * i2)) / Math.PI;

        //      pi   = 180
        //      x    =  ？
        //====> ?=180*x/pi

        return result;
    }

    static class Point {
        int x;
        int y;

        public Point() {

        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
