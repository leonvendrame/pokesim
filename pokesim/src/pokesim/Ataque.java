package pokesim;

public class Ataque {
    private int     id;
    private String  nome;
    private double  ppMax;
    private double  ppAtual;
    private double  power;
    private double  accuracy;
    private Tipo    tipo;

    Ataque(int id) {
        int idReal = id - 1;
        String[][] ataque = Batalha.getTabelaAtaque();
        setId(idReal);
        setNome(ataque[idReal][1]);
        setPpMax(Double.parseDouble(ataque[idReal][3]));
        setPpAtual(Double.parseDouble(ataque[idReal][3]));
        setPower(Double.parseDouble(ataque[idReal][4]));
        setAccuracy(Double.parseDouble(ataque[idReal][5]));
        if (!(ataque[idReal][2].isEmpty())) {
            for (Tipo tipo : Tipo.values())
                if (ataque[idReal][2].compareToIgnoreCase(tipo.toString()) == 0) {
                    setTipo(tipo);
                }
        } else {
            setTipo(null);
        }
    }

    public void efeito() {
        return;
    }

    public boolean calculoCritico() {
        return false;
    }

    public boolean calculoAcerto() {
        return false;
    }

    public double calculoDano(double x) {
        return x;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPpMax(double ppMax) {
        this.ppMax = ppMax;
    }

    public void setPpAtual(double ppAtual) {
        this.ppAtual = ppAtual;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
