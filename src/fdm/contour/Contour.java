package contour;

import static contour.Constants.*;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author mat
 */
public class Contour extends Frame implements WindowListener, ActionListener {

    private Panel panel;
    private Button drawButton;
    private CheckboxGroup checkBoxGroup;
    private Checkbox[] checkbox = new Checkbox[3];
    private Choice[] choice = new Choice[2];
    private TextField text = null;

    /* コンストラクタ */
    public Contour() {
        panel = new Panel();
        panel.setBackground(Color.LIGHT_GRAY);
        add(panel, "North");
        if (choiceb) {
            checkBoxGroup = new CheckboxGroup();
            checkbox[0] = new Checkbox("流線", checkBoxGroup, true);
            checkbox[1] = new Checkbox("渦度", checkBoxGroup, false);
            checkbox[2] = new Checkbox("速度", checkBoxGroup, false);
            for (int i = 0; i < checkbox.length; i++) {
                panel.add(checkbox[i]);
            }
        }
        String[] labelTexts = {"基本色", "目的色", "分割数"};
        String[] baseColorNames = {"青", "水", "緑", "黄", "赤", "桃", "灰"};
        String[] targetColorNames = {"赤", "緑", "青", "紫", "黄", "黒", "白"};
        panel.add(new Label(labelTexts[0]));
        choice[0] = new Choice();
        panel.add(choice[0]);
        for (int j = 0; j < baseColorNames.length; j++) {
            choice[0].addItem(baseColorNames[j]);
        }
        panel.add(new Label(labelTexts[1]));
        choice[1] = new Choice();
        panel.add(choice[1]);
        for (int j = 0; j < targetColorNames.length; j++) {
            choice[1].addItem(targetColorNames[j]);
        }
        panel.add(new Label(labelTexts[2]));
        text = new TextField("100");
        panel.add(text);
        drawButton = new Button("作図");
        drawButton.addActionListener(this);
        panel.add(drawButton);
        addWindowListener(this);
    }

    /* WindowListener */
    public void windowClosing(WindowEvent arg0) {
        dispose(); // ウィンドウを閉じる
    }

    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("引数を1つ指定して下さい");
            System.exit(1);
        }
        Constants.fileName = args[0];
        Contour frame = new Contour();
        frame.setTitle("Contour 作図");
        frame.setLocation(300, 200);
        frame.setSize(640, 480); // ウィンドウサイズの指定
        frame.setVisible(true); // ウィンドウを表示
    }

    static Rectangle[][] rect;
    static double xcoord[][], ycoord[][], value[][], zeta[][], u[][], v[][];

    public static List<String> readLines(String fname) {
        try {
            String PATH = fname + ".txt";
            Path path = Paths.get(PATH);
            File file = path.toFile();
            List<String> lines = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.toList());
            return lines;
        } catch (IOException ex) {
            Logger.getLogger(Contour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void dataRead() {
        List<String> lines = readLines(fileName);
        String[] arr = null;
        int k = 0;
        // 1行目
        Variables.Title = lines.get(k);
        this.setTitle(Variables.Title);
        System.out.println(Variables.Title);
        k++;
        // 2行目
        arr = lines.get(k).split("\t");
        Variables.Nx = Integer.parseInt(arr[0]);
        Variables.Ny = Integer.parseInt(arr[1]);
        System.out.println("Nx=" + Variables.Nx + "\tNy=" + Variables.Ny);
        k++;
        // 3行目
        arr = lines.get(k).split("\t");
        double xxMin = Double.parseDouble(arr[0]);
        double xxMax = Double.parseDouble(arr[1]);
        Variables.xMin = xxMin - (xxMax - xxMin) * 0.1;
        Variables.xMax = xxMax + (xxMax - xxMin) * 0.1;
        double yyMin = Double.parseDouble(arr[2]);
        double yyMax = Double.parseDouble(arr[3]);
        Variables.yMin = yyMin - (yyMax - yyMin) * 0.1;
        Variables.yMax = yyMax + (yyMax - yyMin) * 0.1;
        System.out.println("xMin(" + xxMin + ")=" + Variables.xMin + "\txMax(" + xxMax + ")=" + Variables.xMax + "\tyMin(" + yyMin + ")=" + Variables.yMin + "\tyMax(" + yyMax + ")=" + Variables.yMax);
        k++;
        // 4行目
        arr = lines.get(k).split("\t");
        Variables.Nx1 = Integer.parseInt(arr[0]);
        Variables.Nx2 = Integer.parseInt(arr[1]);
        Variables.Ny1 = Integer.parseInt(arr[2]);
        Variables.Ny2 = Integer.parseInt(arr[3]);
        System.out.println("Nx1=" + Variables.Nx1 + "\tNx2=" + Variables.Nx2 + "\tNy1=" + Variables.Ny1 + "\tNy2=" + Variables.Ny2);
        k++;
        // 5行目
        arr = lines.get(k).split("\t");
        double DT = Double.parseDouble(arr[0]);
        int NTCount = Integer.parseInt(arr[1]);
        k++;
        // 6行目以降
        xcoord = new double[Variables.Ny + 1][];
        ycoord = new double[Variables.Ny + 1][];
        value = new double[Variables.Ny + 1][];
        zeta = new double[Variables.Ny + 1][];
        u = new double[Variables.Ny + 1][];
        v = new double[Variables.Ny + 1][];
        for (int j = 0; j <= Variables.Ny; j++) {
            double x[] = new double[Variables.Nx + 1];
            double y[] = new double[Variables.Nx + 1];
            double w[] = new double[Variables.Nx + 1];
            double z[] = new double[Variables.Nx + 1];
            double w1[] = new double[Variables.Nx + 1];
            double w2[] = new double[Variables.Nx + 1];
            for (int i = 0; i <= Variables.Nx; i++) {
                arr = lines.get(k).split("\t");
                System.out.println("k=" + k + ":" + arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3]);
                x[i] = Double.parseDouble(arr[0]);
                y[i] = Double.parseDouble(arr[1]);
                w[i] = Double.parseDouble(arr[2]);
                z[i] = Double.parseDouble(arr[3]);
                Variables.psiMax = Math.max(Variables.psiMax, w[i]);
                Variables.psiMin = Math.min(Variables.psiMin, w[i]);
                Variables.zetaMax = Math.max(Variables.zetaMax, z[i]);
                Variables.zetaMin = Math.min(Variables.zetaMin, z[i]);
                if(choiceb){
                    w1[i] = Double.parseDouble(arr[4]);
                    w2[i] = Double.parseDouble(arr[5]);
                }else{
                    w1[i]=0.0;
                    w2[i]=0.0;
                }
                double uv = Math.sqrt(w1[i] * w1[i] + w2[i] * w2[i]);
                Variables.uMax = Math.max(Variables.uMax, w1[i]);
                Variables.vMax = Math.max(Variables.vMax, w2[i]);
                Variables.uMin = Math.min(Variables.uMin, w1[i]);
                Variables.vMin = Math.min(Variables.vMin, w2[i]);
                Variables.uvMax = Math.max(Variables.uvMax, uv);
                Variables.uvMin = Math.min(Variables.uvMin, uv);
                k++;
            }
            System.out.println();
            xcoord[j] = x;
            ycoord[j] = y;
            value[j] = w;
            zeta[j] = z;
            u[j] = w1;
            v[j] = w2;
        }

        rect = new Rectangle[Variables.Ny + 1][];
        for (int j = 0; j < Variables.Ny; j++) {
            Rectangle r[] = new Rectangle[Variables.Nx + 1];
            for (int i = 0; i < Variables.Nx; i++) {
                Rectangle ww = new Rectangle();

                ww.w1 = value[j][i];
                ww.z1 = zeta[j][i];
                ww.cx1 = xcoord[j][i];
                ww.cy1 = ycoord[j][i];

                ww.w2 = value[j][i + 1];
                ww.z2 = zeta[j][i + 1];
                ww.cx2 = xcoord[j][i + 1];
                ww.cy2 = ycoord[j][i + 1];

                ww.w3 = value[j + 1][i + 1];
                ww.z3 = zeta[j + 1][i + 1];
                ww.cx3 = xcoord[j + 1][i + 1];
                ww.cy3 = ycoord[j + 1][i + 1];

                ww.w4 = value[j + 1][i];
                ww.z4 = zeta[j + 1][i];
                ww.cx4 = xcoord[j + 1][i];
                ww.cy4 = ycoord[j + 1][i];

                ww.u = u[j][i];
                ww.v = v[j][i];

                ww.setDevice(getGraphics());
                r[i] = ww;
            }
            rect[j] = r;
        }
    }

    Color baseClr = null;
    Color targetClr = null;
    String numstr = null;

    public void gradation(int num) {
        /*
            http://www.sofgate.com/design/ct_gradation.html
            基本色  青：（R=0x55）（G=0x55）（B=0xAA）
                   水：（R=0x55）（G=0xAA）（B=0xAA） 
                   緑：（R=0x55）（G=0xAA）（B=0x55）
                   黄：（R=0xAA）（G=0xAA）（B=0x55）
                   赤：（R=0xAA）（G=0x55）（B=0x55）
                   桃：（R=0xAA）（G=0x55）（B=0xAA）
                   灰：（R=0xAA）（G=0xAA）（B=0xAA）
            ＝＝＝＝＝＝＝＝＝＝
            ［等比計算］
            基本色を「R=Ra, G=Ga, B=Ba」、目的色を「R=Rb, G=Gb, B=Bb」とする。
            この際の「X%」にあたる中間色「R=R(X), G=G(X), B=B(X)」は以下の計算式で求める。
            ---
            ・R(X) = (Rb - Ra) * X / 100 + Ra
            ・G(X) = (Gb - Ga) * X / 100 + Ga
            ・B(X) = (Bb - Ba) * X / 100 + Ba
            ---
            ＊X=0～100で小数利用も可能だが、演算結果は整数化が必要。
            例えば「目的色」を白（R=0xFF, G=0xFF, B=0xFF）とする。
            ＝＝＝＝＝＝＝＝＝＝
            ［等差計算］
            基本色を「R=Ra, G=Ga, B=Ba」、その最小値を「RGBmin」、最大値を「RGBmax」とする。
            加えて、配色用の下限値を「0」、上限値を「Cmax」とする。
            この際の「X%」にあたる中間色「R=R(X), G=G(X), B=B(X)」は以下の計算式で求める。
            ---
            ・R(X) = (Cmax - RGBmax + RGBmin) * X / 100 + Ra
            ・G(X) = (Cmax - RGBmax + RGBmin) * X / 100 + Ga
            ・B(X) = (Cmax - RGBmax + RGBmin) * X / 100 + Ba
            ---
            ＊X=0～100で小数利用も可能だが、演算結果は整数化が必要。
            ＊ＨＴＭＬの場合は、Cmax=0xFF(255)。
         */

        double wMin=Variables.psiMin, wMax=Variables.psiMax, val = 0.0;
        if (Variables.onetwo==0) {
            wMin = Variables.psiMin;
            wMax = Variables.psiMax;
        } else if (Variables.onetwo==1){
            wMin = Variables.zetaMin;
            wMax = Variables.zetaMax;
        }
        boolean f = true;
        double delp = (wMax - wMin) / num;
        do {
            double X = 100.0 * val / (wMax - wMin);
            //
            int r = (int) ((targetClr.getRed() - baseClr.getRed()) * X / 100. + baseClr.getRed());
            int g = (int) ((targetClr.getGreen() - baseClr.getGreen()) * X / 100. + baseClr.getGreen());
            int b = (int) ((targetClr.getBlue() - baseClr.getBlue()) * X / 100. + baseClr.getBlue());
            /*
            int r = (int) ((0xFF - 0x00) * X / 100. + 0x00);
            int g = (int) ((0x00 - 0x00) * X / 100. + 0x00);
            int b = (int) ((0x00 - 0xFF) * X / 100. + 0xFF);
             */
            Color c = new Color(r, g, b);
            for (int j = 0; j < Variables.Ny; j++) {
                for (int i = 0; i < Variables.Nx; i++) {
                    if ((0 <= Variables.Nx1) && ((Variables.Nx1 <= i) && (i < Variables.Nx2)) && ((Variables.Ny1 <= j) && (j < Variables.Ny2))) {
                    } else {
                        if (f) {
                            rect[j][i].prect();
                        }
                        rect[j][i].Contour0(val + wMin, c);
                    }
                }
            }
            val += delp;
            f = false;
        } while (val < (wMax - wMin));
    }

    public void vect(int num) {
        double MaxLen = Math.max(Math.abs(Variables.xMax - Variables.xMin),
                                 Math.abs(Variables.yMax - Variables.yMin)) / 20.0;
        double uLenMax = MaxLen;
        double vLenMax = MaxLen;
        Color c = targetC[choice[1].getSelectedIndex()];
        for (int j = 0; j < Variables.Ny; j++) {
            for (int i = 0; i < Variables.Nx; i++) {
                if ((0 <= Variables.Nx1) && ((Variables.Nx1 <= i) && (i < Variables.Nx2)) && ((Variables.Ny1 <= j) && (j < Variables.Ny2))) {
                } else {
                    rect[j][i].prect();
                    if (i != 0) {
                        if (j != 0) {
                            double upcent = rect[j][i].u / (Variables.uMax - Variables.uMin);
                            double ulen = uLenMax * upcent;
                            double vpcent = rect[j][i].v / (Variables.vMax - Variables.vMin);
                            double vlen = vLenMax * vpcent;
                            rect[j][i].arrow0(ulen, vlen, c);
                        }
                    }
                }
            }
        }
    }
    public static boolean hajime = true;

    /* ActionListener */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == drawButton) {
            baseClr = baseC[choice[0].getSelectedIndex()];
            targetClr = targetC[choice[1].getSelectedIndex()];
            numstr = text.getText();
            if (hajime) {
                dataRead();
                hajime = false;
            }
            getGraphics().clearRect(0, 0, WIDTH, HEIGHT);
            update(getGraphics());
            if (choiceb) {
                if (checkbox[0].getState()) {
                    Variables.onetwo = 0;
                    gradation(Integer.parseInt(numstr));
                } else if (checkbox[1].getState()) {
                    Variables.onetwo = 1;
                    gradation(Integer.parseInt(numstr));
                } else if (checkbox[2].getState()) {
                    Variables.onetwo = 2;
                    vect(Integer.parseInt(numstr));
                }
            }else{
                Variables.onetwo = 0;
                gradation(Integer.parseInt(numstr));
            }
        }
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }
}
