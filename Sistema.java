import java.util.Scanner;

public class Sistema {
    // Acciones posibles del sistema
    public static final int SUREBET = 0, FIN = 100;
    // Scanner para los inputs del usuario
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Bucle de servicio
        boolean seguir = true;
        while (seguir) {
            System.out.println("############ NUEVA OPERACIÓN ############");
            // Ver cuantos casos hay e inicializar arrays
            System.out.println("Casos posibles");
            int casos = input.nextInt();
            double[] cuotas = new double[casos];
            boolean[] isFreebet = new boolean[casos];
            // preguntar por cuotas
            for(int i=0;i<casos;i++){
                System.out.println("Cuota numero " +i);
                cuotas[i] = input.nextInt();
                System.out.println("Usar freebets (S/N)?");
                isFreebet[i] = Character.toUpperCase(input.next().charAt(0)) == 'S';
            }
            // Preguntar la operacion a realizar
            System.out.println("Seleccione la operación a realizar:");
            System.out.println("0: Surebet (ganar igual en ambos lados) con cantidad fija a una cuota");
            System.out.println("100: DETENER EJECUCIÓN (fin)");
            int accion = input.nextInt();
            switch (accion) {
                case SUREBET:
                    System.out.println("Indica la cantidad fija a la primera cuota p.e 2,10");
                    realizarSurebet(cuotas,isFreebet,input.nextDouble());
                    break;
                case FIN:
                default:
                    System.out.println("Has salido/indicado acción errónea");
                    seguir = false;
                    break;
            }
        }
    }

    public static void realizarSurebet(double[] cuotas, boolean[] isFreebet, double betFijaCuota1){
        double[] apuestas = new double[cuotas.length]; //tamaño=casos 
        apuestas[0] = betFijaCuota1;
        double sumProbabilidades = 0.0;
        for(double cuota : cuotas){ sumProbabilidades += 1/cuota; }
        // Calcular el resto de apuestas
        double sumaApuestasRestantes = betFijaCuota1 * (1 - 1 / cuotas[0]) / sumProbabilidades;
        System.out.printf("Apuesta indicada a la cuota %d: %.2f\n",0,betFijaCuota1);
        double apuestaTotal = betFijaCuota1;
        for (int i = 1; i < cuotas.length; i++) {
            apuestas[i] = sumaApuestasRestantes / (1 / cuotas[i]);  
            apuestaTotal+=apuestas[i];
        }
        for (int i=0;i<apuestas.length;i++){
            double ganancia = apuestas[i]*cuotas[i] - apuestaTotal;
            System.out.printf("Apuesta a hacer a la cuota %d: %.2f, con beneficio neto %2.f\n",i,apuestas[i],ganancia);
        }
    }

}