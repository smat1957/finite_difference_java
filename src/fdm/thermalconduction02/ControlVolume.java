package thermalconduction02;

/**
 *
 * @author mat
 */
public class ControlVolume {
    double XCoord, YCoord;
    double Tp;
    double WECoeffB, WECoeff, SNCoeff;

    public double calc(ControlVolume W, ControlVolume E, ControlVolume S, ControlVolume N) {
        Tp = WECoeff * (W.Tp + E.Tp) + SNCoeff * (S.Tp + N.Tp);
        return Tp;
    }
    public double calcB(ControlVolume W, ControlVolume S, ControlVolume N) {
        Tp = WECoeffB * W.Tp + SNCoeff * (S.Tp + N.Tp);
        return Tp;
    }

}
