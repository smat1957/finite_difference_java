package contour;

import java.awt.Color;
import java.awt.Graphics;
import static contour.Variables.*;
import static contour.Constants.*;
import java.awt.Point;

/**
 *
 * @author mat
 */
public class Rectangle {

    double cx1, cy1, z1, w1;
    double cx2, cy2, z2, w2;
    double cx3, cy3, z3, w3;
    double cx4, cy4, z4, w4;
    double v, u;
    Graphics g;
    Device dev;

    public void setDevice(Graphics gr) {
        this.g = gr;
        dev = new Device(0, WIDTH, 0, HEIGHT);
        dev.xyrange(xMin, xMax, yMin, yMax);
        prect();
    }

    public void prect() {
        pline(cx1, cy1, cx2, cy2, Color.LIGHT_GRAY);
        pline(cx2, cy2, cx3, cy3, Color.LIGHT_GRAY);
        pline(cx3, cy3, cx4, cy4, Color.LIGHT_GRAY);
        pline(cx4, cy4, cx1, cy1, Color.LIGHT_GRAY);
    }

    public void pline(double x1, double y1, double x2, double y2, Color c) {
        dev.pline(x1, y1, x2, y2);
        g.setColor(c);
        g.drawLine(dev.getX1(), dev.getY1(), dev.getX2(), dev.getY2());
    }

    public boolean XOR(boolean bL, boolean bR) {
        // 排他的論理和を直接計算する演算子はないため
        // bL XOR bR = ( bL OR bR ) AND ( NOT bL OR NOT bR )
        // を使用します。
        return (bL || bR) && (!bL || !bR);
    }

    public void arrow0(double du, double dv, Color c) {
        double pl1 = cx1 + (cx2 - cx1) / 2.0;
        double pr1 = cx4 + (cx3 - cx4) / 2.0;
        double p1 = (pl1 + pr1) / 2.0;
        double ql1 = cy1 + (cy4 - cy1) / 2.0;
        double qr1 = cy2 + (cy3 - cy2) / 2.0;
        double q1 = (ql1 + qr1) / 2.0;
        double p2 = p1 + du;
        double q2 = q1 + dv;
        Arrow(p1, q1, p2, q2, c);
    }

    public void Arrow(double p1, double q1, double p2, double q2, Color c) {
        double ll = Math.max(Math.abs(p2-p1), Math.abs(q2-q1)) / 4.0;
        double cost = 0.9659258263;
        double sint = 0.2588190451;
        double dp, dq, rd, bn;
        double dp1, dp2, dq1, dq2;
        dp = p2 - p1;
        dq = q2 - q1;
        rd = Math.sqrt(dp * dp + dq * dq);
        bn = ll / rd;
        dp1 = (cost * dp - sint * dq) * bn;
        dq1 = (sint * dp + cost * dq) * bn;
        dp2 = (cost * dp + sint * dq) * bn;
        dq2 = (cost * dq - sint * dp) * bn;
        pline(p1, q1, p2, q2, c);
        pline(p2, q2, p2 - dp1, q2 - dq1, c);
        pline(p2, q2, p2 - dp2, q2 - dq2, c);
    }

    public void Contour0(double psi, Color c) {
        Contour1(psi, c);
        Contour2(psi, c);
    }

    public void Contour1(double psi, Color c) {
        double a1=w1, a2=w2, a3=w3, a4=w4;
        if (onetwo==0) {
            a1 = w1;
            a2 = w2;
            a3 = w3;
            a4 = w4;
        } else if(onetwo==1) {
            a1 = z1;
            a2 = z2;
            a3 = z3;
            a4 = z4;
        }
        boolean m1 = XOR(a1 > psi, a2 > psi); // boolean m1 = (v1 > psi)^(v2 > psi);
        boolean m2 = XOR(a2 > psi, a3 > psi); // boolean m2 = (v2 > psi)^(v3 > psi);
        boolean m3 = XOR(a3 > psi, a1 > psi); // boolean m3 = (v3 > psi)^(v1 > psi);
        if (m1 || m2 || m3) {
            double p1, p2, px0, py0, dx, dy;
            double px1 = 1, py1 = 1, px2 = 1, py2 = 1, px3 = 1, py3 = 1;
            if (m1) {
                p1 = a1;
                p2 = a2;
                px0 = cx1;
                py0 = cy1;
                dx = cx2 - cx1;
                dy = cy2 - cy1;
                px1 = px0 + dx * (psi - p1) / (p2 - p1);
                py1 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m2) {
                p1 = a2;
                p2 = a3;
                px0 = cx2;
                py0 = cy2;
                dx = cx3 - cx2;
                dy = cy3 - cy2;
                px2 = px0 + dx * (psi - p1) / (p2 - p1);
                py2 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m3) {
                p1 = a3;
                p2 = a1;
                px0 = cx3;
                py0 = cy3;
                dx = cx1 - cx3;
                dy = cy1 - cy3;
                px3 = px0 + dx * (psi - p1) / (p2 - p1);
                py3 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m1 && m2) {
                pline(px1, py1, px2, py2, c);
            }
            if (m1 && m3) {
                pline(px1, py1, px3, py3, c);
            }
            if (m2 && m3) {
                pline(px2, py2, px3, py3, c);
            }
        }
    }

    public void Contour2(double psi, Color c) {
        double a1=w1, a2=w2, a3=w3, a4=w4;
        if (onetwo==0) {
            a1 = w1;
            a2 = w2;
            a3 = w3;
            a4 = w4;
        } else if (onetwo==1){
            a1 = z1;
            a2 = z2;
            a3 = z3;
            a4 = z4;
        }
        boolean m1 = XOR(a1 > psi, a3 > psi);
        boolean m2 = XOR(a3 > psi, a4 > psi);
        boolean m3 = XOR(a4 > psi, a1 > psi);
        if (m1 || m2 || m3) {
            double p1, p2, px0, py0, dx, dy;
            double px1 = 1, py1 = 1, px2 = 1, py2 = 1, px3 = 1, py3 = 1;
            if (m1) {
                p1 = a1;
                p2 = a3;
                px0 = cx1;
                py0 = cy1;
                dx = cx3 - cx1;
                dy = cy3 - cy1;
                px1 = px0 + dx * (psi - p1) / (p2 - p1);
                py1 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m2) {
                p1 = a3;
                p2 = a4;
                px0 = cx3;
                py0 = cy3;
                dx = cx4 - cx3;
                dy = cy4 - cy3;
                px2 = px0 + dx * (psi - p1) / (p2 - p1);
                py2 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m3) {
                p1 = a4;
                p2 = a1;
                px0 = cx4;
                py0 = cy4;
                dx = cx1 - cx4;
                dy = cy1 - cy4;
                px3 = px0 + dx * (psi - p1) / (p2 - p1);
                py3 = py0 + dy * (psi - p1) / (p2 - p1);
            }
            if (m1 && m2) {
                pline(px1, py1, px2, py2, c);
            }
            if (m1 && m3) {
                pline(px1, py1, px3, py3, c);
            }
            if (m2 && m3) {
                pline(px2, py2, px3, py3, c);
            }
        }
    }
    /*
    public void Contr1(double psi0, Color c) {
        double a1, a2, a3, a4;
        if(onetwo){
            a1 = w1;
            a2 = w2;
            a3 = w3;
            a4 = w4;
        }else{            
            a1 = z1;
            a2 = z2;
            a3 = z3;
            a4 = z4;
        }
        boolean m1 = XOR(a1 > psi0, a2 > psi0); // boolean m1 = (v1 > psi0)^(v2 > psi0);
        boolean m2 = XOR(a2 > psi0, a3 > psi0); // boolean m2 = (v2 > psi0)^(v3 > psi0);
        boolean m3 = XOR(a3 > psi0, a1 > psi0); // boolean m3 = (v3 > psi0)^(v1 > psi0);
        if (m1 || m2 || m3) {
            double dx, dy;
            double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;            
            if (m1) {
                dx = cx2 - cx1;
                x1 = ((psi0 - a1) / (a2 - a1)) * dx + cx1;
                y1 = cy1;
            }
            if (m2) {
                x2 = cx2;
                dy = cy3 - cy2;
                y2 = ((psi0 - a2) / (a3 - a2)) * dy + cy2; //cy1
            }
            if (m3) {
                dx = cx1 - cx3;
                x3 = ((psi0 - a3) / (a1 - a3)) * dx + cx3;
                dy = cy1 - cy3;
                y3 = ((psi0 - a3) / (a1 - a3)) * dy + cy3;
            }
            if (m1 && m2) {
                pline(x1, y1, x2, y2, c);
            }
            if (m1 && m3) {
                pline(x1, y1, x3, y3, c);
            }
            if (m2 && m3) {
                pline(x2, y2, x3, y3, c);
            }
        }
    }
    public void Contr2(double psi0, Color c) {
        double a1, a2, a3, a4;
        if(onetwo){
            a1 = w1;
            a2 = w2;
            a3 = w3;
            a4 = w4;
        }else{            
            a1 = z1;
            a2 = z2;
            a3 = z3;
            a4 = z4;
        }
        boolean m1 = XOR(a1 > psi0, a3 > psi0); // boolean m1 = (v1 > psi0)^(v3 > psi0);
        boolean m2 = XOR(a3 > psi0, a4 > psi0); // boolean m2 = (v3 > psi0)^(v4 > psi0);
        boolean m3 = XOR(a4 > psi0, a1 > psi0); // boolean m3 = (v4 > psi0)^(v1 > psi0);
        if (m1 || m2 || m3) {
            double dx, dy;
            double x1 = 0, x3 = 0, x4 = 0, y1 = 0, y3 = 0, y4 = 0;            
            if (m1) {
                dx = cx3 - cx1;
                x1 = ((psi0 - a1) / (a3 - a1)) * dx + cx1;
                dy = cy3 - cy1;
                y1 = ((psi0 - a1) / (a3 - a1)) * dy + cy1;
            }
            if (m2) {
                dx = cx4 - cx3;
                x3 = ((psi0 - a3) / (a4 - a3)) * dx + cx3;
                y3 = cy3;
            }
            if (m3) {
                x4 = cx4;
                dy = cy1 - cy4;
                y4 = ((psi0 - a4) / (a1 - a4)) * dy + cy4;
            }
            if (m1 && m2) {
                pline(x1, y1, x3, y3, c);
            }
            if (m1 && m3) {
                pline(x1, y1, x4, y4, c);
            }
            if (m2 && m3) {
                pline(x3, y3, x4, y4, c);
            }
        }
    }
    
    public void Contr0(double psi0, Color c) {
        Contr1(psi0, c);
        Contr2(psi0, c);
    }
    
    public void Contr(double psi0, Color c) {
        double a1, a2, a3, a4;
        if(onetwo){
            a1 = w1;
            a2 = w2;
            a3 = w3;
            a4 = w4;
        }else{            
            a1 = z1;
            a2 = z2;
            a3 = z3;
            a4 = z4;
        }
        boolean m1 = XOR(a1 > psi0, a2 > psi0); // boolean m1 = (v1 > psi0)^(v2 > psi0);
        boolean m2 = XOR(a2 > psi0, a3 > psi0); // boolean m2 = (v2 > psi0)^(v3 > psi0);
        boolean m3 = XOR(a3 > psi0, a4 > psi0); // boolean m3 = (v3 > psi0)^(v4 > psi0);
        boolean m4 = XOR(a4 > psi0, a1 > psi0); // boolean m4 = (v4 > psi0)^(v1 > psi0);
        if (m1 || m2 || m3 || m4) {
            double dx, dy;
            double x1 = 0, x2 = 0, x3 = 0, x4 = 0, y1 = 0, y2 = 0, y3 = 0, y4 = 0;
            if (m1) {
                dx = cx2 - cx1;
                x1 = ((psi0 - a1) / (a2 - a1)) * dx + cx1;
                y1 = cy1;
            }
            if (m2) {
                x2 = cx2;
                dy = cy3 - cy2;
                y2 = ((psi0 - a2) / (a3 - a2)) * dy + cy2;
            }
            if (m3) {
                dx = cx4 - cx3;
                x3 = ((psi0 - a3) / (a4 - a3)) * dx + cx3;
                y3 = cy3;
            }
            if (m4) {
                x4 = cx4;
                dy = cy1 - cy4;
                y4 = ((psi0 - a4) / (a1 - a4)) * dy + cy4;
            }
            
            if (m1 && m2) {
                pline(x1, y1, x2, y2, c);
            }
            if (m1 && m3) {
                pline(x1, y1, x3, y3, c);
            }
            if (m1 && m4) {
                pline(x1, y1, x4, y4, c);
            }
            if (m2 && m3) {
                pline(x2, y2, x3, y3, c);
            }
            if (m2 && m4) {
                pline(x2, y2, x4, y4, c);
            }
            if (m3 && m4) {
                pline(x3, y3, x4, y4, c);
            }
        }
    }
     */
}
