package thermalconduction03;

import java.util.ArrayList;
import java.util.List;
import static thermalconduction03.Constants.*;
/**
 *
 * @author mat
 */
public class ThermalConduction03 {

    // 熱流束境界条件
    static ControlVolume[][] cv;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
        double Bi = h * xLen / lambda;
        double delX = 1.0 / Nx;  // 無次元化　x=0.0〜1.0  delX = xLen / Nx (無次元化しないなら)
        double delY = 1.0 / Ny;  // 無次元化　y=0.0〜1.0  delY = yLen / Ny (無次元化しないなら)
        for (int j = 0; j <= Ny; j++) {
            for (int i = 0; i <= Nx; i++) {
                cv[j][i].XCoord = delX * i;
                cv[j][i].YCoord = delY * j;
                double XArea = delY; // = WArea = EArea
                double YArea = delX; // = SArea = NArea
                double wB = 1.0 / (2.0 * (YArea * YArea + Ar * Ar * XArea * XArea));
                double wF = 1.0 / (Ar * Ar * XArea * XArea * (1.0 + Bi * YArea) + YArea * YArea);
                cv[j][i].WECoeff = (Ar * Ar)*(XArea * XArea) * wB;
                cv[j][i].SNCoeff = (YArea * YArea) * wB;
                cv[j][i].WECoeffB= (Ar * Ar)*(XArea * XArea)/(YArea * YArea + Ar * Ar * XArea * XArea);
                cv[j][i].Coeff1 = wF * Ar * Ar * XArea * XArea;
                cv[j][i].Coeff2 = Bi * YArea;
                cv[j][i].Coeff3 = wF * YArea * YArea / 2.0;
                cv[j][i].Tp = 0.5;  // 無次元化 (tMax - tMin)/2 + tMin                
            }
        }
    }

    public static void boundary() {
        for (int j = 0; j <= Ny; j++) {
            for (int i = 0; i <= Nx; i++) {
                if (j == 0) {
                    cv[j][i].Tp = 1.0;
                } else if (j == Ny) {
                    cv[j][i].Tp = 0.0;
                }
                if (i == 0) {
                    cv[j][i].Tp = (1.0 + 0.0) * 0.5;
                } else if (i == Nx) {
                    cv[j][i].Tp = (1.0 + 0.0) * 0.5;
                }
            }
        }
    }

    public static void solve() {
        for (int j = 1; j < Ny; j++) {
            for (int i = 1; i < Nx; i++) {
                ControlVolume W = cv[j][i - 1];
                ControlVolume E = cv[j][i + 1];
                ControlVolume S = cv[j - 1][i];
                ControlVolume N = cv[j + 1][i];
                double tp = cv[j][i].calc(W, E, S, N);
                if (i == Nx - 1) {
                    ControlVolume WB = cv[j][i];
                    ControlVolume SB = cv[j - 1][i+1];
                    ControlVolume NB = cv[j + 1][i+1];
                    tp = cv[j][i+1].calcB(WB, SB, NB);
                }
            }
            ControlVolume EB = cv[j][0];
            ControlVolume SB = cv[j - 1][1];
            ControlVolume NB = cv[j + 1][1];
            double tp = cv[j][0].calcF(EB, SB, NB);
        }        
        cv[0][Nx].Tp = cv[0][Nx - 2].Tp;
        cv[Ny][Nx].Tp = cv[Ny][Nx - 2].Tp;
        //
        cv[0][0].Tp = cv[0][2].Tp;
        cv[Ny][0].Tp = cv[Ny][2].Tp;        
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
