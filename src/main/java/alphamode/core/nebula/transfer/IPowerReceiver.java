package alphamode.core.nebula.transfer;

public interface IPowerReceiver extends IPower {

    void markNetworkDirty();
    boolean isNetworkDirty();
    void findPaths();
    boolean wantsPower();
}
