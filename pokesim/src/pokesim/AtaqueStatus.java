package pokesim;

public class AtaqueStatus extends Ataque {
    private Status status;
    private int chance;

    AtaqueStatus(int id, Pokemon pokemon, String[] parametros) {
        super(id, pokemon);
        if (!parametros[0].isEmpty()) {
            for (Status status : Status.values()) {
                if (parametros[0].compareToIgnoreCase(status.toString()) == 0) {
                    setStatus(status);
                }
            }
        } else {
            setStatus(null);
        }
        if (!parametros[1].isEmpty()){
            setChance(Integer.parseInt(parametros[1]));
        }
    }

    @Override
    public void efeito() {
        super.efeito();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
