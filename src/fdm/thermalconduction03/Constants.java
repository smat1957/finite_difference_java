package thermalconduction03;

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
    public static final String Title="熱流束境界条件：P.30：Fortran77による伝熱解析プログラム（中村博 著）：サイエンス社";
    public static final int Nx = 5;
    public static final int Ny = 10;
    public static final double xLen = 0.6;      // [m]
    public static final double yLen = 0.8;      // [m]
    public static final double Th = 100.0;      // [degree]
    public static final double Tc = 50.0;       // [degree]
    public static final double Tf = 0.0;        // [degree]
    public static final double h = 1000.0;      // [W/m^2 deg]
    public static final double lambda = 300.0;  // [W/m deg]
    public static final int NIter = 15;
    //
    public static final double xMax = xLen;
    public static final double yMax = yLen;
    public static final double xMin = 0.0;
    public static final double yMin = 0.0;    
    //
    public static final String PATH = "./thermalconduction03/data/";
    public static final String fName = "fname";
    //
}
