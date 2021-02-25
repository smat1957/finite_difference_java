package thermalconduction01;

import thermalconduction01.Constants.*;

/**
 *
 * @author mat
 */
public class ControlVolume {
    double XCoord, YCoord;
    double Tp;
    double WECoeff, SNCoeff;
    public double calc(ControlVolume W, ControlVolume E, ControlVolume S, ControlVolume N){
        Tp = WECoeff * (W.Tp + E.Tp) + SNCoeff * (S.Tp + N.Tp);
        return Tp;
    }
}
