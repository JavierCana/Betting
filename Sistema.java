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
            System.out.println("Indique si va a usar freebets en alguna cuota (S/N)");
            System.out.println("seguido de las cuotas de los casos que hay p.e: N 2,0 5,5 12");
            boolean usarFree = Character.toUpperCase(input.next().charAt(0)) == 'S';
            //Obtención de las cuotas
            for(int i=0;i<casos;i++){ cuotas[i] = input.nextDouble(); }

            //! Ver en cual queremos usar freebets, en ellas restamos 1 a la cuota (perdemos el importe de la free al ganar)
            if(usarFree){
                System.out.println("Indique en cuales cuotas va a usar freebets escribiendo S/N");
                System.out.println("ejemplo: si las cuotas son 1,8 y 1,91 y no queremos usar freebets en ninguna sería 'N N'");
                for(int i=0;i<casos;i++){ isFreebet[i] = Character.toUpperCase(input.next().charAt(0)) == 'S'; }
            }

            // Preguntar la operacion a realizar
            System.out.println("----------------------");
            System.out.println("Seleccione la operación a realizar:");
            System.out.println("0: Surebet (ganar igual en ambos lados) con cantidad fija a una cuota");
            // System.out.println("1: Apuesta al probable (Ganar todo en 1er caso y en el resto recuperar inversión)");
            System.out.println("100: DETENER EJECUCIÓN (fin)");
            System.out.println("----------------------");
            int accion = input.nextInt();
            switch (accion) {
                case SUREBET:
                    System.out.println("Indica la cantidad fija de dinero a la primera cuota p.e 10,0");
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
    // *****************************************************************************************
    // *                              realizarSurebet                                          *
    // *****************************************************************************************
    // Realiza la surebet tradicional que hicimos hasta ahora, diciendo cuanto apostar al primero y ajustar
    // *****************************************************************************************
    public static void realizarSurebet(double[] cuotas,boolean[] isFreebet, double betFijaCuota1){
        double sumInversasCuotas = 1/cuotas[0];
        double apuestaTotal= isFreebet[0] ? 0.0 : betFijaCuota1;
        double apuestaTotalFreebets = isFreebet[0] ? betFijaCuota1 :  0.0;
        double[] apuestas = new double[cuotas.length];
        // Realizar e imprimir calculos
        apuestas[0] = betFijaCuota1;
        for (int i = 1; i < cuotas.length; i++) {
            if(!isFreebet[i]){
                apuestas[i] = (betFijaCuota1 * cuotas[0]) / cuotas[i];
                apuestaTotal+= apuestas[i];
            }
            else{
                apuestas[i] = (betFijaCuota1 * cuotas[0]) / (cuotas[i]-1);
                apuestaTotalFreebets += apuestas[i];
            }
            sumInversasCuotas+= 1/cuotas[i];
        }
         // Calcular las apuestas necesarias para las otras cuotas
        imprimirMensaje(apuestaTotal,apuestaTotalFreebets,isFreebet,apuestas,cuotas);
        if(sumInversasCuotas < 1){ System.out.println("!!!! ES UNA SUREBET, SI NO HAY LIMITES NO USES FREEBETS Y APUESTA FUERTE (A NO SER QUE QUIERAS BLANQUEAR) !!!!");}
        double porcentajeGanancia = (1 / sumInversasCuotas - 1) * 100;
        System.out.printf("Porcentaje de ganancia: %.2f%%\n", porcentajeGanancia);
    }

    // *****************************************************************************************
    // *                              imprimirMensaje                                          *
    // *****************************************************************************************
    // Imprime las apuestas a realizar indicando el monto total gastado, beneficios, freebets usadas...
    // *****************************************************************************************
    private static void imprimirMensaje(double apuestaDinero, double apuestaFree, boolean[]isFreebet, double[]apuestas,double[]cuotas){
        System.out.println("----------------------");
        System.out.printf("Apuesta Total de dinero: %.2f y de FREEBETS %.2f\n",apuestaDinero,apuestaFree);
        for (int i = 0; i < cuotas.length; i++){
            if(isFreebet[i]){
                System.out.printf("Apostar a cuota %.2f cantidad (de FREEBETS) %.2f con profit %.3f\n",cuotas[i], apuestas[i], apuestas[i] * (cuotas[i]-1) - apuestaDinero);
            }
            else{
                System.out.printf("Apostar a cuota %.2f cantidad %.2f con profit %.3f\n",cuotas[i], apuestas[i], apuestas[i] * cuotas[i] - apuestaDinero);
            }
        }
        System.out.println("----------------------");
    }
}