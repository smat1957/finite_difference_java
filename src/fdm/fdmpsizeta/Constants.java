package fdmpsizeta;

/**
 *
 * @author mat
 */
public class Constants {
    // privateコンストラクタでインスタンス生成を抑止

    private Constants() {
    }
    // 定数
    public static final double PI = 3.14159265;
    public static final String Title = "渦度と流れ関数による非圧縮粘性流れ：P23：流れの数値解析入門（水野明哲　著）：朝倉書店";
    public static final int Nx = 20;
    public static final int Ny = 10;
    public static final int Nx1 = 8;
    public static final int Nx2 = 12;
    public static final int Nybox = 4;
    public static final double xMax = 2.0;
    public static final double xMin = 0.0;
    public static final double yMax = 1.0;
    public static final double yMin = 0.0;
    public static final double psiMax = 0.05;
    public static final double psiMin = 0.0;
    public static final double EPSPsi = 0.001;
    public static final double EPSZeta = 0.001;
    public static final int NTMax = 100;
    public static final int NPsiMax = 10;
    //
    public static final double Courant = 0.1;   // クーラン数
    public static final double Nu = 0.0009;   // 動粘土 0.01
    //
    public static final String PATH = "./fdmpsizeta/data/";
    public static final String fName = "fname";
    //
    public static double DX, DY, DX2, DY2, DX2DY2;
    public static double DT, NuDTDX2, NuDTDY2;
    public static double V0, Re, DT4DXDY, R2DX2PDY2;
    public static double psi0, zet0;

    public static void preparation() {
        DX = (xMax - xMin) / Nx;
        DY = (yMax - yMin) / Ny;
        //DX = 0.1;
        //DY = 0.1;
        DX2 = DX * DX;
        DY2 = DY * DY;
        DX2DY2 = DX2 * DY2;
        V0 = psiMax / (DY * Ny);  //yMax = dy * Ny
        DT = Courant * DX / V0;
        NuDTDX2 = Nu * DT / DX2;
        NuDTDY2 = Nu * DT / DY2;
        Re = V0 * (DY * Nybox) / Nu;
        DT4DXDY = DT / (4.0 * DX * DY);
        R2DX2PDY2 = 1.0 / (2.0 * (DX2 + DY2));
        psi0 = psiMax;
        zet0 = V0 / (DY * Ny);   // yMax = dy * Ny
    }

}
