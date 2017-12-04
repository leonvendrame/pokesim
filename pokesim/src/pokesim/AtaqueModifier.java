package pokesim;

import java.util.Random;

public class AtaqueModifier extends Ataque {
    private Atributo mod;
    private int n;
    private int chance;

    AtaqueModifier(int id, Pokemon pokemon, String[] parametros) {
        super(id, pokemon);
        for (Atributo atributo : Atributo.values()) {
            if (parametros[0].compareToIgnoreCase(atributo.toString()) == 0){
                setMod(atributo);
            }
        }
        setN(Integer.parseInt(parametros[1]));
        setChance(Integer.parseInt(parametros[2]));
    }

    @Override
    public void efeito() {
        super.efeito();

        if (ocorre()) {
            if (getN() >= 0) {
                //efeito no pokemon
            } else {
                //efeito no outro pokemon
            }
        }
    }

    public void setMod(Atributo mod) {
        this.mod = mod;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public boolean ocorre() {
        Random random = new Random();
        int sorteio = random.nextInt(101);
        if (sorteio <= getChance()) {
            return true;
        }
        return false;
    }
}
