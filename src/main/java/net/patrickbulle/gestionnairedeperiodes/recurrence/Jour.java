package net.patrickbulle.gestionnairedeperiodes.recurrence;

public enum Jour {
    LUNDI(1),
    MARDI(2),
    MERCREDI(4),
    JEUDI(8),
    VENDREDI(16),
    SAMEDI(32),
    DIMANCHE(64);

    public final int indice;

    private Jour(int indice) {
        this.indice = indice;
    }
}
