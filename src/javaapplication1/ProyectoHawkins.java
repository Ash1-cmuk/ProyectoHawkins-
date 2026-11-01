
package javaapplication1;

import java.util.Scanner;

public class ProyectoHawkins {

    public static void main(String[] args) {

        final double MAX_ENERGIA = 200.0;
        final double LIMITE_RIESGO = 150.0;

        String operadorActual = "";
        double sensorMedido = 0;
        int codigoRiesgo = 0;
        int porcentajeEstado = 0;
        boolean portalAbierto = false;
        int opcionMenu = 0;

        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce tu nombre: ");
        operadorActual = teclado.nextLine();

        mostrarEncabezado(operadorActual);
    
do {
    System.out.println("\n--- SISTEMA HAWKINS ---");
    System.out.println("Hola, " + operadorActual);
    System.out.println("1. Inspeccionar Sensor Individual");
    System.out.println("2. Iniciar Muestreo Continuo");
    System.out.println("0. Salir de la Consola");

    System.out.print("Elige una opcion: ");
    opcionMenu = teclado.nextInt();

    switch (opcionMenu) {
        

        case 1:
           sensorMedido = leerLecturaSegura(teclado, "Introduce lectura µT: ");
           codigoRiesgo = evaluarNivelRiesgo(sensorMedido);
           porcentajeEstado = calcularPorcentajeTruncado(sensorMedido, MAX_ENERGIA);
           portalAbierto = ejecutarProtocoloAccion(codigoRiesgo);

           System.out.println("Porcentaje de energia: " + porcentajeEstado + "%");
           System.out.println("Portal abierto: " + portalAbierto);
            break;



        
        case 2:
            simularMuestreoContinuo(teclado, LIMITE_RIESGO);
            break;


        case 0:
            System.out.println("Cerrando sesion...");
            break;

        default:
            System.out.println("Opcion no valida");
    }

} while (opcionMenu != 0);
}

    public static void mostrarEncabezado(String operador) {
        System.out.printf("\n\tSISTEMA HAWKINS\n");
        System.out.printf("Operador:\t%s\n", operador);
    }

public static double leerLecturaSegura(Scanner teclado, String mensaje) {

    double lectura = 0;
    boolean correcto = false;

    while (!correcto) {
        try {
            System.out.print(mensaje);
            lectura = teclado.nextDouble();
            correcto = true;
        } catch (Exception e) {
            System.out.println("Error, introduce un numero.");
            teclado.nextLine();
        }
    }

    return lectura;
}

public static int evaluarNivelRiesgo(double microTeslas) {

    int riesgo;

    if (microTeslas < 50.0) {
        riesgo = 1;
    } else if (microTeslas <= 150.0) {
        riesgo = 2;
    } else {
        riesgo = 3;
    }

    String nivel = riesgo == 1 ? "Seguro" : riesgo == 2 ? "Alerta" : "Critico";

    System.out.println("Nivel de riesgo: " + nivel);

    return riesgo;
}

public static int calcularPorcentajeTruncado(double lectura, double maxEnergia) {

    double porcentaje = (lectura / maxEnergia) * 100;

    return (int) porcentaje;
}

public static boolean ejecutarProtocoloAccion(int codigoRiesgo) {

    switch (codigoRiesgo) {
        case 1:
            System.out.println("Modo Estable");
            return false;

        case 2:
            System.out.println("Anomalía Detectada");
            return false;

        case 3:
            System.out.println("¡ALERTA MÁXIMA!");
            return true;

        default:
            System.out.println("Código no válido");
            return false;
    }
}

public static void simularMuestreoContinuo(Scanner teclado, double limite) {

    double lectura;
    double suma = 0;
    int contador = 0;

    while (true) {

        lectura = leerLecturaSegura(teclado, "Introduce lectura (-1 para salir): ");

        if (lectura < 0) {
            break;
        }

        if (lectura == 0) {
            continue;
        }

        suma = suma + lectura;
        contador++;

        if (lectura > limite) {
            System.out.println("Cuidado! Limite superado");
        }
    }

    if (contador > 0) {
        System.out.printf("Media: %.2f\n", suma / contador);
    } else {
        System.out.printf("Media: %.2f\n", 0.0);
    }
}
}

