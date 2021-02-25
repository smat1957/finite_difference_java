package fdmpsizeta;

import static fdmpsizeta.Constants.*;

/**
 *
 * @author mat
 */
public class Zeta {

    //public static double ez_max;
    public static void vor_tran(int j1, int j2, int i) {
        /*
        for (int j = 0; j <= Ny; j++) {
            Variables.zt1[j] = Variables.zt0[j];
            Variables.zt0[j] = Variables.zet[j][i];
        }
         */
        for (int j = j1; j <= j2; j++) {
            if (((Nx1 <= i) && (i <= Nx2)) && ((0 <= j) && (j <= Nybox))) {
            } else {
                double dzet = -DT4DXDY * ((Variables.psi[j+1][i] - Variables.psi[j-1][i])
                                        * (Variables.zet[j][i+1] - Variables.zt1[j])
                                        - (Variables.psi[j][i+1] - Variables.psi[j][i-1]) 
                                        * (Variables.zet[j+1][i] - Variables.zt0[j-1]))
                              + NuDTDX2 * (Variables.zt1[j]   - 2.0 * Variables.zet[j][i] + Variables.zet[j][i+1])
                              + NuDTDY2 * (Variables.zt0[j-1] - 2.0 * Variables.zet[j][i] + Variables.zet[j+1][i]);
                Variables.zet[j][i] += dzet;
                if (Math.abs(dzet) > Variables.ez_max) {
                    Variables.ez_max = Math.abs(dzet);
                }
            }
        }
    }

    public static double zeta_np() {

        for (int j = 0; j <= Ny; j++) {
            Variables.zt0[j] = Variables.zet[j][0];
        }
        for (int i = 1; i < Nx; i++) {
            for (int j = 0; j <= Ny; j++) {
                if (((Nx1 <= i) && (i <= Nx2)) && ((0 <= j) && (j <= Nybox))) {
                } else {
                    Variables.zt1[j] = Variables.zt0[j];
                    Variables.zt0[j] = Variables.zet[j][i];
                }
            }
            vor_tran(1, Ny - 1, i);
        }
        /*
        for (int i = 1; i < Nx1; i++) {
            vor_tran(1, Ny - 1, i);
        }
        for (int i = Nx1; i <= Nx2; i++) {
            vor_tran(Nybox + 1, Ny - 1, i);
        }
        for (int i = Nx2 + 1; i < Nx; i++) {
            vor_tran(1, Ny - 1, i);
        }
         */
        return Variables.ez_max;
    }

}
