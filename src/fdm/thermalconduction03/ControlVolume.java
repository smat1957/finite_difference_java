package thermalconduction03;

import static thermalconduction03.Constants.*;
/**
 *
 * @author mat
 */
public class ControlVolume {
    double XCoord, YCoord;
    double Tp;
    double WECoeffB, WECoeff, SNCoeff;
    double Coeff1, Coeff2, Coeff3;

    public double calc(ControlVolume W, ControlVolume E, ControlVolume S, ControlVolume N) {
        Tp = WECoeff * (W.Tp + E.Tp) + SNCoeff * (S.Tp + N.Tp);
        return Tp;
    }
    public double calcB(ControlVolume W, ControlVolume S, ControlVolume N) {
        Tp = WECoeffB * W.Tp + SNCoeff * (S.Tp + N.Tp);
        return Tp;
    }
    public double calcF(ControlVolume E, ControlVolume S, ControlVolume N) {
        Tp = Coeff2 * (E.Tp + Coeff2 * Tf) + Coeff3 * (S.Tp + N.Tp);
        return Tp;
    }
    
}
