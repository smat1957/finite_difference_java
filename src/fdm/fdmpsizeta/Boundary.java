package fdmpsizeta;

import static fdmpsizeta.Constants.*;

/**
 *
 * @author mat
 */
public class Boundary {
    public static void bound_fixed() {
        for (int i = 0; i <= Nx; i++) {
            Variables.psi[0][i] = 0.0;
            Variables.psi[Ny][i] = psiMax;
            Variables.zet[Ny][i] = 0.0;
        }
        for (int j = 0; j <= Nybox; j++) {
            Variables.psi[j][Nx1] = 0.0;
            Variables.psi[j][Nx2] = 0.0;
        }
        for (int i = Nx1; i <= Nx2; i++) {
            Variables.psi[Nybox][i] = 0.0;
        }
    }

    public static void bound_variables() {
        
        for (int i = 1; i < Nx1; i++) {
            Variables.zet[0][i] = 2.0 / DY2 * (Variables.psi[0][i] - Variables.psi[1][i]);
        }
        for (int j = 1; j <= Nybox; j++) {
            Variables.zet[j][Nx1] = 2.0 / DX2 * (Variables.psi[j][Nx1] - Variables.psi[j][Nx1-1]);
            Variables.zet[j][Nx2] = 2.0 / DX2 * (Variables.psi[j][Nx2] - Variables.psi[j][Nx2+1]);
        }
        for (int i = Nx1; i <= Nx2; i++) {
            Variables.zet[Nybox][i] = 2.0 / DY2 * (Variables.psi[Nybox][i] - Variables.psi[Nybox+1][i]);
        }
        for (int i = Nx2+1; i < Nx; i++) {
            Variables.zet[0][i] = 2.0 / DY2 * (Variables.psi[0][i] - Variables.psi[1][i]);
        }
        for (int j = 0; j <= Ny; j++) {
            Variables.psi[j][0] = Variables.psi[j][1];
            Variables.zet[j][0] = Variables.zet[j][1];
            Variables.psi[j][Nx] = Variables.psi[j][Nx-1];
            Variables.zet[j][Nx] = Variables.zet[j][Nx-1];
        }
    }
    
}
