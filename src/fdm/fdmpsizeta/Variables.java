package fdmpsizeta;
import static fdmpsizeta.Constants.*;

/**
 *
 * @author mat
 */
public class Variables {
    public static double[][] psi = new double[Ny+1][Nx+1];
    public static double[][] zet = new double[Ny+1][Nx+1];
    public static double[] zt1 = new double[Ny+1];
    public static double[] zt0 = new double[Ny+1];
    
    public static void init(){
        //zt1 = new double[Ny+1];
        //zt0 = new double[Ny+1];
        //
        //psi = new double[Ny+1][];
        //zet = new double[Ny+1][];
        for(int j=0; j<=Ny; j++){
            double[] wp = new double[Nx+1];
            double[] wz = new double[Nx+1];
            for(int i=0; i<=Nx; i++){
                wp[i] = 0.0;
                wz[i] = 0.0;
            }
            psi[j] = wp;
            zet[j] = wz;
        }        
    }
    
    public static double ep_max=0.0, ez_max=0.0;
    public static int NtCount;
}
