package model;

class Account {

    private double value;
    private final String requisites;

    public Account(String requisites, double value) {
        this.value = value;
        this.requisites = requisites;
    }

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    void setValue(double value) {
        this.value = value;
    }
}
