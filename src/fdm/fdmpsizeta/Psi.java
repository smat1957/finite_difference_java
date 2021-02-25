package fdmpsizeta;

import static fdmpsizeta.Constants.*;

/**
 *
 * @author mat
 */
public class Psi {

    public static double e_psi_max;

    public static void poisson(int j1, int j2, int i) {
        for (int j = j1; j <= j2; j++) {
            if (((Nx1 <= i) && (i <= Nx2)) && ((0 <= j) && (j <= Nybox))) {
            } else {
                double dpsi = R2DX2PDY2 
                        * ((Variables.psi[j-1][i] + Variables.psi[j+1][i]) * DX2
                         + (Variables.psi[j][i-1] + Variables.psi[j][i+1]) * DY2
                         + Variables.zet[j][i] * DX2DY2 );
                         //- Variables.psi[j][i];
                if (Math.abs( Variables.psi[j][i]-dpsi ) > e_psi_max) {
                    e_psi_max = Math.abs(dpsi);
                }
                Variables.psi[j][i] = dpsi;
            }
        }
    }

    public static double psi_np() {

        int GzCount = 1;
        do {
            for (int j = 1; j < Ny; j++) {
                Variables.psi[j][0] = Variables.psi[j][1];
                Variables.psi[j][Nx] = Variables.psi[j][Nx - 1];
            }
            e_psi_max = 0.0;
            for(int i=1; i<Nx; i++){
                poisson(1, Ny - 1, i);
            }
            /*
            for (int i = 1; i < Nx; i++) {
                poisson(1, Ny - 1, i);
            }
            for (int i = Nx1; i <= Nx2; i++) {
                poisson(Nybox + 1, Ny - 1, i);
            }
            for (int i = Nx2 + 1; i < Nx; i++) {
                poisson(1, Ny - 1, i);
            }
            */
            e_psi_max = e_psi_max / psi0;
            System.out.println("GzCount=" + GzCount + "\tpsi_max=" + e_psi_max);
            if (GzCount == 1) {
                Variables.ep_max = e_psi_max;
            }
            GzCount++;
        } while ((e_psi_max > EPSPsi) && (GzCount < NPsiMax));

        return e_psi_max;
    }

}
