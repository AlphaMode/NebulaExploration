package alphamode.core.nebula.transfer;

import java.util.Set;

public interface IPower {
    Set<PowerPath> getPowerPaths();

    //Set getMax[type]PowerLevel to return 0 for it to not store that kind of power.

}
