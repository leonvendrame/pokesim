package pokesim;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void efeito(Pokemon atacante, Pokemon defensor) {
        double dano = 0;
        setPpAtual(getPpAtual() - 1);
        Random random = new Random();
        int chanceConfusion;
        System.out.printf("%s usou %s.\n", atacante.getEspecie().getNome(), getNome());
        if (!calculoAcerto(atacante, defensor)) {
            System.out.printf(Main.ANSI_RED + "%s errou.\n" + Main.ANSI_RESET, atacante.getEspecie().getNome());
        } else {
            dano = calculoDano(atacante, defensor);
            chanceConfusion = random.nextInt(101);
            if (atacante.isConfusion() && chanceConfusion <= 50) {
                atacante.setHpAtual(atacante.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s está confuso e causou %.2f de dano em si mesmo.\n", atacante.getEspecie().getNome(), dano);
                if (atacante.getStatus() == Status.FAINTED) {
                    System.out.printf("%s foi derrotado.\n", atacante.getEspecie().getNome());
                }
            } else {
                defensor.setHpAtual(defensor.valorAtributo(Atributo.HPATUAL) - dano);
                System.out.printf("%s causou %.2f de dano em %s.\n", atacante.getEspecie().getNome(), dano, defensor.getEspecie().getNome());
                if (defensor.getStatus() == Status.FAINTED) {
                    System.out.printf("%s derrotou %s do oponente.\n", atacante.getEspecie().getNome(), defensor.getEspecie().getNome());
                }
            }
        }
        return;
    }

    public boolean calculoCritico(Pokemon atacante) {
        double chance = atacante.valorAtributo(Atributo.SPD) / 512;
        double sorteio;

        if (chance > 1) {
            return true;
        } else {
            sorteio = Math.random();
            if (sorteio <= chance) {
                return true;
            }
        }

        return false;
    }

    public boolean calculoAcerto(Pokemon atacante, Pokemon defensor) {
        double prob;
        int indiceAtk = atacante.getModifierAccuracy() + 6;
        int indiceDef = defensor.getModifierEvasion() + 6;

        prob = getAccuracy() * (Batalha.getTabelaAE()[indiceAtk][1] / Batalha.getTabelaAE()[indiceDef][1]);

        if (atacante.getStatus() == Status.FROZEN) {
            prob -= 100;
        } else if (atacante.getStatus() == Status.PARALYSIS) {
            prob -= 25;
        } else if (atacante.getStatus() == Status.SLEEP) {
            prob -= 100;
        }

        if (atacante.isFlinch()) {
            prob -= 100;
        }

        if (prob >= 100) {
            return true;
        } else {
            Random random = new Random();
            int sorteio = random.nextInt(101);
            if (sorteio <= prob) {
                return true;
            }
        }

        return false;
    }

    public double calculoDano(Pokemon atacante, Pokemon defensor) {
        int lvl;
        double power, atk = 0, def = 0, dano;

        lvl = atacante.getLevel();
        power = getPower();

        Set<Tipo> tipos_atk = tipos_atk();
        Set<Tipo> tipos_spe = tipos_spe();

        if (tipos_atk.contains(getTipo())) {
            atk = atacante.valorAtributo(Atributo.ATK);
            def = defensor.valorAtributo(Atributo.DEF);
        } else if (tipos_spe.contains(getTipo())) {
            atk = atacante.valorAtributo(Atributo.SPE);
            def = defensor.valorAtributo(Atributo.SPE);
        }

        if (calculoCritico(atacante)) {
            lvl *= 2;
        }

        if (atacante.getStatus() == Status.BURN) {
            atk /= 2;
        }

        if (atk < 0 || atk > 255) {
            if (atk < 0) {
                atk = 0;
            } else if (atk > 255) {
                atk = 255;
            }
        }

        if (def < 0 || def > 255) {
            if (def < 0) {
                def = 0;
            } else if (def > 255) {
                def = 255;
            }
        }

        dano = (lvl * atk * power / def / 50) + 2;

        if (getTipo() == atacante.getEspecie().getTipo1() || getTipo() == atacante.getEspecie().getTipo2()) {
            dano *= 1.5;
        }

        int i = 0;
        while (getTipo().toString().compareToIgnoreCase(Batalha.getTabelaDano()[i][0]) != 0) {
            i++;
        }

        if (defensor.getEspecie().getTipo1() != null) {
            int j = 0;
            double multiplicador;
            while (defensor.getEspecie().getTipo1().toString().compareToIgnoreCase(Batalha.getTabelaDano()[0][j]) != 0) {
                j++;
            }
            multiplicador = Double.parseDouble(Batalha.getTabelaDano()[i][j]);
            dano *= multiplicador;
        }

        if (defensor.getEspecie().getTipo2() != null) {
            int k = 0;
            double multiplicador;
            while (defensor.getEspecie().getTipo2().toString().compareToIgnoreCase(Batalha.getTabelaDano()[0][k]) != 0) {
                k++;
            }
            multiplicador = Double.parseDouble(Batalha.getTabelaDano()[i][k]);
            dano *= multiplicador;
        }

        Random random = new Random();
        int r = random.nextInt(39) + 217;

        dano = (dano * r) / 255;

        return dano;
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

    public double getPpAtual() {
        return ppAtual;
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

    public double getPower() {
        return this.power;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public Set<Tipo> tipos_atk() {
        Set<Tipo> tipos = new HashSet<Tipo>();
        tipos.add(Tipo.NORMAL);
        tipos.add(Tipo.FIGHTING);
        tipos.add(Tipo.FLYING);
        tipos.add(Tipo.POISON);
        tipos.add(Tipo.GROUND);
        tipos.add(Tipo.ROCK);
        tipos.add(Tipo.BUG);
        tipos.add(Tipo.GHOST);
        return tipos;
    }

    public Set<Tipo> tipos_spe() {
        Set<Tipo> tipos = new HashSet<Tipo>();
        tipos.add(Tipo.FIRE);
        tipos.add(Tipo.WATER);
        tipos.add(Tipo.ELETRIC);
        tipos.add(Tipo.GRASS);
        tipos.add(Tipo.ICE);
        tipos.add(Tipo.PSYCHIC);
        tipos.add(Tipo.DRAGON);
        return tipos;
    }
}
