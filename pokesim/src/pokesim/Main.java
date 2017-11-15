package pokesim;

public class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.printf("Não possui argumentos.");
        } else {
            System.out.printf("%s - %s", args[0], args[1]);
        }
    }
}
