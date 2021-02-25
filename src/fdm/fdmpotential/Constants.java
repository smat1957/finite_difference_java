package fdmpotential;

/**
 *
 * @author mat
 */
public class Constants {
    // privateコンストラクタでインスタンス生成を抑止

    private Constants() {
    }
    // 定数
    public static final String PATH = "./fdmpotential/data/";
    public static final String fName = "fname";
    //
    public final static int Nx = 20; // 20+1
    public final static int Ny = 10; // 10+1
    public final static int Nx1 = 8;
    public final static int Nx2 = 12; // 12+1
    public final static int Nybox = 4;
    public final static double psi_max = 1.0;
    public final static double EPS = 0.001;
    public final static int Nmax = 400;
    //
    public final static String Title = "ステップを超えるポテンシャル流れ：第2章：流れの数値解析入門（水野明哲 著）：朝倉書店";
    public final static double xLen = 2.0;
    public final static double yLen = 1.0;
    public final static double xMax = xLen;
    public final static double yMax = yLen;
    public final static double xMin = 0.0;
    public final static double yMin = 0.0;
    public final static double psi_min = 0.0;
}
