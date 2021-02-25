package fdmpotential;
import static fdmpotential.Constants.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mat
 */
public class FDMPotential {


    private static double[][] psi;
    private static double e_max;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        init_value();
        bound_value();
        int n = 1;
        do {
            e_max = 0.0;
            /*
            //for(int i=1; i<i1-1; i++) // except for the boundary
            for (int i = 1; i < Nx1; i++) {
                relax(1, Ny - 1, i);
            }
            for (int i = Nx1; i <= Nx2; i++) {
                relax(Nybox + 1, Ny - 1, i);
            }
            //for(int i=i2+1; i<imax-1; i++)
            for (int i = Nx2 + 1; i < Nx; i++) {
                relax(1, Ny - 1, i);
            }
            */
            
            for(int i=1; i<Nx; i++){
                relax(1, Ny - 1, i);
            }
            
            System.out.println(n + "\t" + e_max);
            n++;
        } while ((e_max > EPS) && (n < Nmax));
        //out_result();
        pout();
    }

    private static void init_value() {
        psi = new double[Ny + 1][];
        for (int j = 0; j < Ny + 1; j++) {
            double p[] = new double[Nx + 1];
            for (int i = 0; i < Nx + 1; i++) {
                p[i] = 0.0;
            }
            psi[j] = p;
        }
    }

    private static void bound_value() {
        for (int i = 0; i <= Nx; i++) {
            psi[0][i] = 0.0;
            psi[Ny][i] = psi_max;
         }
        for (int j = 0; j <= Nybox; j++) {
            psi[j][Nx1] = 0.0;
            psi[j][Nx2] = 0.0;
        }
        for (int i = Nx1; i <= Nx2; i++) {
            psi[Nybox][i] = 0.0;
        }
        double ds = psi_max / Ny;
        for (int j = 0; j <= Ny; j++) {
            double s = j * ds;
            psi[j][0] = s;
            psi[j][Nx] = s;
        }
    }

    private static void relax(int j1, int j2, int i) {
        for (int j = j1; j <= j2; j++) {
            if (((Nx1 <= i) && (i <= Nx2)) && ((0 <= j) && (j <= Nybox))) {
            } else {
                double dpsi = (psi[j][i+1] + psi[j][i-1]
                             + psi[j+1][i] + psi[j-1][i]) / 4.0;
                             // - pdi[j][i];
                //if (Math.abs(psi[j][i]-dpsi) > e_max) {
                if (Math.abs(psi[j][i]-dpsi) > e_max) {
                    e_max = Math.abs(dpsi);
                }
                //psi[j][i] += dpsi;
                psi[j][i] = dpsi;
            }
        }
    }

    private static void wrt_j(int j1, int j2, int ii) {
        for (int j = j1; j <= j2; j++) {
            System.out.println(ii + "\t" + j + "\t" + psi[j][ii]);
        }
    }

    private static void out_result() {
        for (int i = 0; i <= Nx1; i++) {
            wrt_j(0, Ny, i);
        }
        //for(int i=i1+1; i<i2-1; i++)
        for (int i = Nx1 + 1; i < Nx2; i++) {
            wrt_j(Nybox, Ny, i);
        }
        //for(int i=i2; i<imax; i++)
        for (int i = Nx2; i <= Nx; i++) {
            wrt_j(0, Ny, i);
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
        arList.add( String.valueOf(Nx1) + "\t" + String.valueOf(Nx2) + "\t0\t" + String.valueOf(Nybox) );
        //　５行目　時間カウント
        //System.out.println(DT + "\t" + Variables.NtCount);
        arList.add( "0.0\t0" );
        //　６行目以降 無次元化された値を元に戻す
        double dummy = 0.0;
        double delx = (xMax - xMin) / Nx;
        double dely = (yMax - yMin) / Ny;
        for (int j = 0; j <= Ny; j++) {
            double yc = dely * j; 
            for (int i = 0; i <= Nx; i++) {
                double xc = delx * i;
                String w = String.valueOf(xc) + "\t" + String.valueOf(yc) + "\t" + psi[j][i] + "\t" + String.valueOf(dummy);
                arList.add(w);
            }
        }
        FileIO.writeLines(arList, fName);
    }

}
