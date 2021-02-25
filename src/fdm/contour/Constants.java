package contour;

import java.awt.Color;

/**
 *
 * @author mat
 */
public class Constants {
    // privateコンストラクタでインスタンス生成を抑止

    private Constants() {
    }
    // 定数
    //
    // -> 13H 実習（電場）
    public static String fileName = "./contour/data/testFile0";
    public static final boolean choiceb = false;
    //
    //ThermalConductance01 -> 温度境界条件：P.14：Fortran77による伝熱解析プログラム（中村博 著）：サイエンス社
    //public static final String fileName = "testFile6";
    //public static final boolean choiceb = false;
    //
    //ThermalConductance02 -> 断熱境界条件：P.20：Fortran77による伝熱解析プログラム（中村博 著）：サイエンス社
    //public static final String fileName = "testFile7";
    //public static final boolean choiceb = false;
    //
    //ThermalConductance03 -> 熱流束境界条件：P.30：Fortran77による伝熱解析プログラム（中村博 著）：サイエンス社
    //public static final String fileName = "testFile8";
    //public static final boolean choiceb = false;
    //
    //FDMPotential01 -> ステップを超えるポテンシャル流れ：第2章：流れの数値解析入門（水野明哲 著）：朝倉書店
    //public static final String fileName = "testFile9";
    //public static final boolean choiceb = false;
    //
    //FDMPsiZeta -> 渦度と流れ関数による非圧縮粘性流れ：P23：流れの数値解析入門（水野明哲　著）：朝倉書店
    //public static final String fileName = "testFile10";
    //public static final boolean choiceb = true;
    //
    public static final int WIDTH = 640, HEIGHT = 480;
    public static final Color baseC[] = {new Color(0x55, 0x55, 0xAA),
        new Color(0x55, 0xAA, 0xAA),
        new Color(0x55, 0xAA, 0x55),
        new Color(0xAA, 0xAA, 0x55),
        new Color(0xAA, 0x55, 0x55),
        new Color(0xAA, 0x55, 0xAA),
        new Color(0xAA, 0xAA, 0xAA)};
    public static final Color targetC[] = {Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.CYAN,
        Color.YELLOW,
        Color.BLACK,
        Color.WHITE};
}
