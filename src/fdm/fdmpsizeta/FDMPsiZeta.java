package fdmpsizeta;

import static fdmpsizeta.Constants.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mat
 */
public class FDMPsiZeta {

    /**
     * @param args the command line arguments
     */
    /*
        static Variables v=new Variables();
        static Boundary  b=new Boundary();
        static FileIO    f=new FileIO();
        static Psi       p=new Psi();
        static Zeta      z=new Zeta();
    */
    public static void main(String[] args) {
        //double ez_max;
        preparation();
        init_value();
        Boundary.bound_fixed();
        Variables.NtCount = 1;
        do {
            Boundary.bound_variables();                        
            Variables.ez_max = 0.0;
            Zeta.zeta_np();
            Variables.ez_max = Variables.ez_max / zet0;
            Psi.psi_np();
            System.out.println("NtCount=" + Variables.NtCount + "\tez_max=" + Variables.ez_max);
            Variables.NtCount++;
        } while ((Variables.ez_max > EPSZeta) && (Variables.NtCount < NTMax));
        System.out.println(" Re = " + Re);
        System.out.println(" dt = " + DT);
        out_result();
    }

    public static void init_value() {

        boolean flag = true;
        if (!flag) {
            //if read from the file
            System.out.println("Read lines for initial value from the file....");
            List<String> lines = FileIO.readLines(fName);
            String[] arr = null;
            int k = 0;  lines.get(k); // Title
            k++;      lines.get(k);   // Nx, Ny
            k++;      lines.get(k);   // xMin, xMax, yMin, yMax
            k++;      lines.get(k);   // Nx1, Nx2, 0, Nybox
            k++;      lines.get(k);   // DT, NCount
            k++;
            for (int j = 0; j <= Ny; j++) {
                for (int i = 0; i <= Nx; i++) {
                    arr = lines.get(k).split("\t");
                    //System.out.println("k=" + k + ":" + arr[0] + "\t" + arr[1]);
                    Variables.psi[j][i] = Double.parseDouble(arr[2]);
                    Variables.zet[j][i] = Double.parseDouble(arr[3]);
                    k++;
                }
            }
        } else {
            // otherwise
            for (int j = 0; j <= Ny; j++) {
                for (int i = 0; i <= Nx; i++) {
                    Variables.psi[j][i] = psiMax / Ny * j;
                    Variables.zet[j][i] = 0.0;
                }
            }
        }
    }

    public static void out_result() {
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
        arList.add( String.valueOf(DT) + "\t" + String.valueOf(Variables.NtCount) );
        //　６行目以降
        for (int j = 0; j <= Ny; j++) {
            double yc = DY * j; 
            for (int i = 0; i <= Nx; i++) {
                double xc = DX * i;
                double u=0.0,v=0.0;
                if((0<i)&&(i<Nx)){
                    v = -(Variables.psi[j][i+1] - Variables.psi[j][i-1])/(2.0*DX);
                }
                if((0<j)&&(j<Ny)){
                    u = (Variables.psi[j+1][i] - Variables.psi[j-1][i])/(2.0*DY);
                }
                String w = String.valueOf(xc) + "\t"
                         + String.valueOf(yc) + "\t"
                         + String.valueOf(Variables.psi[j][i]) + "\t"
                         + String.valueOf(Variables.zet[j][i]) + "\t"
                         + String.valueOf(u) + "\t"
                         + String.valueOf(v);
                arList.add(w);
            }
        }
        FileIO.writeLines(arList, fName);
    }

}
