package thermalconduction01;

/**
 *
 * @author mat
 */
public class Constants {
	// privateコンストラクタでインスタンス生成を抑止
	private Constants(){} 	
	// 定数
	public static final double PI = 3.14159265;
        public static final String Title="温度境界条件：P.14：Fortran77による伝熱解析プログラム（中村博 著）：サイエンス社";
        public static final int Nx = 10;
        public static final int Ny = 10;
        public static final double xLen = 1.2;
        public static final double yLen = 0.8;
        public static final double Th = 100.0;
        public static final double Tc = 50.0;
        public static final int NIter = 15;
    //
    public static final double xMax = xLen;
    public static final double yMax = yLen;
    public static final double xMin = 0.0;
    public static final double yMin = 0.0;    
    //
    public static final String PATH = "./thermalconduction01/data/";
    public static final String fName = "fname";
    //
}
