package thermalconduction01;

/**
 *
 * @author mat
 */

import java.util.ArrayList;
import java.util.List;
import static thermalconduction01.Constants.*;

public class ThermalConduction01 {

    /**
     * @param args the command line arguments
     */
    // 温度境界条件
    static ControlVolume[][] cv;

    public static void main(String[] args) {
        init();
        boundary();
        int iter = 0;
        do {
            iter++;
            solve();
        } while (iter < NIter);
        pout();
    }

    public static void init() {
        cv = new ControlVolume[Ny + 1][];
        for (int j = 0; j < Ny + 1; j++) {
            ControlVolume x[] = new ControlVolume[Nx + 1];
            for (int i = 0; i < Nx + 1; i++) {
                ControlVolume v = new ControlVolume();
                x[i] = v;
            }
            cv[j] = x;
        }
        double Ar = yLen / xLen;
        double delX = 1.0 / Nx;  // 無次元化　x=0.0〜1.0  delX = xLen / Nx (無次元化しないなら)
        double delY = 1.0 / Ny;  // 無次元化　y=0.0〜1.0  delY = yLen / Ny (無次元化しないなら)
        for (int j = 0; j <= Ny; j++) {
            for (int i = 0; i <= Nx; i++) {
                cv[j][i].XCoord = delX * i;
                cv[j][i].YCoord = delY * j;
                double XArea = delY; // = WArea = EArea
                double YArea = delX; // = SArea = NArea
                double w = 1.0 / (2.0 * (YArea * YArea + (Ar * Ar) * XArea * XArea));
                cv[j][i].WECoeff = (Ar * Ar)*(XArea * XArea) * w;
                cv[j][i].SNCoeff = (YArea * YArea) * w;
                cv[j][i].Tp = 0.5;  // 無次元化 (tMax - tMin)/2 + tMin
            }
        }
    }

    public static void boundary() {
        //Tp = (Tp - Tc)/(Th - Tc); //無次元化 Tp=0.0〜1.0
        for (int j = 0; j <= Ny; j++) {
            for (int i = 0; i <= Nx; i++) {
                if (j == 0) {
                    cv[j][i].Tp = 1.0;  // Th
                } else if (j == Ny) {
                    cv[j][i].Tp = 0.0;  // Tc
                }
                if (i == 0) {
                    cv[j][i].Tp = 0.0;  // Tc
                } else if (i == Nx) {
                    cv[j][i].Tp = 0.0;  // Tc
                }
            }
        }
    }

    public static void solve() {
        for (int j = 1; j < Ny; j++) {
            for (int i = 1; i < Nx; i++) {
                ControlVolume W = cv[j][i-1];
                ControlVolume E = cv[j][i+1];
                ControlVolume S = cv[j-1][i];
                ControlVolume N = cv[j+1][i];
                double tp = cv[j][i].calc(W, E, S, N);
            }
        }
    }

    public static void pout() {
        List<String> arList = new ArrayList<String>();
        //　１行目
        //System.out.println(Title);
        arList.add(Title);
        //　２行目
        //System.out.println(Nx + "\t" + Ny);
        arList.add( String.valueOf(Nx) + "\t" + String.valueOf(Ny) );
        //　３行目
        //System.out.println(xMin + "\t" + xMax + "\t"+ yMin +"\t" + yMax);
        arList.add( String.valueOf(xMin) + "\t" + String.valueOf(xMax) + "\t" + String.valueOf(yMin) + "\t" + String.valueOf(yMax) );
        //　４行目　除外するセル
        //System.out.println(Nx1 + "\t" + Nx2 + "\t" + 0 + "\t" + Nybox);
        arList.add( "-1\t0\t0\t0" );
        //　５行目　時間カウント
        //System.out.println(DT + "\t" + Variables.NtCount);
        arList.add( "0.0\t0" );
        //　６行目以降 無次元化された値を元に戻す
        double dummy = 0.0;
        //double delx = (xMax - xMin) / Nx;
        //double dely = (yMax - yMin) / Ny;
        double x = xMax - xMin;
        double y = yMax - yMin;
        double t = Th - Tc;
        for (int j = 0; j <= Ny; j++) {
            //double yc = dely * j; 
            for (int i = 0; i <= Nx; i++) {
                //double xc = delx * i;
                //System.out.println((x*cv[j][i].XCoord+xMin) +"\t" + (y*cv[j][i].YCoord+yMin) +"\t" + (t*cv[j][i].Tp+Tc) +"\t" + dummy);
                String w = String.valueOf(x*cv[j][i].XCoord+xMin) + "\t"
                         + String.valueOf(y*cv[j][i].YCoord+yMin) + "\t"
                         + String.valueOf(t*cv[j][i].Tp+Tc) + "\t" + String.valueOf(dummy);
                arList.add(w);
            }
        }        
        FileIO.writeLines(arList, fName);
        /*
        //　１行目
        System.out.println(Title);
        //　２行目
        System.out.println(Nx+"\t"+Ny);
        //　３行目
        System.out.println("0.0\t"+xLen+"\t0.0\t"+yLen);
        //　４行目　除外するセル
        int Nx1=-1,Nx2=-1,Nybox=-1;
        System.out.println(Nx1+"\t"+Nx2+"\t"+0+"\t"+Nybox);
        //　５行目
        System.out.println("0.0\t0");
        //　６行目以降 無次元化された値を元に戻す
        double dummy = 0.0;
        double xMax = xLen;
        double xMin = 0.0;
        double x = xMax - xMin;
        double yMax = yLen;
        double yMin = 0.0;
        double y = yMax - yMin;
        double t = Th - Tc;
        for (int j = 0; j <= Ny; j++) {
            for (int i = 0; i <= Nx; i++) {
                //System.out.println(cv[j][i].XCoord + "\t" + cv[j][i].YCoord + "\t" + cv[j][i].Tp);
                System.out.println((x*cv[j][i].XCoord+xMin) +
                        "\t" + (y*cv[j][i].YCoord+yMin) +
                        "\t" + (t*cv[j][i].Tp+Tc) +
                        "\t" + dummy);
            }
        }
        */
    }
}
