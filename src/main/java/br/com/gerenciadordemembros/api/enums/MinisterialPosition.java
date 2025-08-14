package br.com.gerenciadordemembros.api.enums;

public enum MinisterialPosition {
    PRESIDENT("Pastor Presidente"),
    VICE_PRESIDENT("Vice-presidente"),
    AUXILIARY_PASTOR("Pastor Auxiliar"),
    SHEPHERD("Pastor"),
    DEACON("Diácono"),
    DEACONESS("Diaconisa"),
    EVANGELIST("Evangelista"),
    MISSIONARY("Missionária"),
    COOPERATOR("Cooperador");

    private final String labelPtBr;

    MinisterialPosition(String labelPtBr) {
        this.labelPtBr = labelPtBr;
    }

    public String getLabelPtBr() {
        return labelPtBr;
    }
}
