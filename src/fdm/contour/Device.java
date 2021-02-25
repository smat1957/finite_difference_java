package contour;

import java.awt.Point;
import static contour.Constants.HEIGHT;

/**
 *
 * @author mat
 */
public class Device {
    
    int pmin, pmax, qmin, qmax;
    double x_scale, y_scale;
    double xmin, xmax, ymin, ymax;
    int p1, q1, p2, q2;
    Point pt1, pt2;

    public Device(){
    }
    public Device(int pmin, int pmax, int qmin, int qmax) {
        this.pmin = pmin;
        this.pmax = pmax;
        this.qmin = qmin;
        this.qmax = qmax;
    }

    public void xyrange(double xmin, double xmax, double ymin, double ymax) {
        double sig_x, sig_y;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        x_scale = (double)(pmax - pmin) / (xmax - xmin);
        if (x_scale < 0) {
            sig_x = -1.0;
        } else {
            sig_x = 1.0;
        }
        y_scale = (double)(qmax - qmin) / (ymax - ymin);
        if (y_scale < 0) {
            sig_y = -1.0;
        } else {
            sig_y = 1.0;
        }
        if (Math.abs(x_scale) > Math.abs(y_scale)) {
            x_scale = sig_x * Math.abs(y_scale);
        } else {
            y_scale = sig_y * Math.abs(x_scale);
        }
    }
    
    public void pline(double x1, double y1, double x2, double y2) {
        p1 = (int) (Math.round((x1 - xmin) * x_scale)) + pmin;
        q1 = HEIGHT - (int) (Math.round((y1 - ymin) * y_scale)) + qmin;
	pt1 = new Point(p1, q1);
        p2 = (int) (Math.round((x2 - xmin) * x_scale)) + pmin;
        q2 = HEIGHT - (int) (Math.round((y2 - ymin) * y_scale)) + qmin;
	pt2 = new Point(p2, q2);
    }
    
    public Point getPoint1(){
        return pt1;
    }
    public Point getPoint2(){
        return pt2;
    }
    public int getX1(){
        return p1;
    }
    public int getY1(){
        return q1;
    }
    public int getX2(){
        return p2;
    }
    public int getY2(){
        return q2;
    }
    public int getWidth(){
        return p2 - p1;
    }
    public int getHeight(){
        return q2 - q1;
    }
}
