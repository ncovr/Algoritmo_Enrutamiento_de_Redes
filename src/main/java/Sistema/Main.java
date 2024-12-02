package Sistema;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        GrafoDirigidoPonderado gp = new GrafoDirigidoPonderado();
        System.out.print("Bienvenido al Sistema. Ingrese un numero (1 al 3) para testear el sistema: ");
        switch (in.nextInt()) {
            case 1:
                // Caso sencillo. solo para probar el recorrido Dijkstra
                gp.cargarDesdeArchivo("src/main/java/Datos/datos_001.txt");
                gp.imprimirConexiones();
                gp.imprimirLineas();
                gp.printCaminoMasCorto(1,7);
                break;
            case 2:
                // Diferentes casos: bucles, nodos inexistentes, otros
                gp.cargarDesdeArchivo("src/main/java/Datos/datos_002.txt");
                gp.imprimirConexiones();
                gp.imprimirLineas();
                gp.printCaminoMasCorto(1, 5);
                gp.printCaminoMasCorto(7,1);
                gp.printCaminoMasCorto(1,4);
                gp.printCaminoMasCorto(7,8);
                gp.printCaminoMasCorto(2,3);
                gp.printCaminoMasCorto(4,7);
                gp.printCaminoMasCorto(1,7);
                gp.printCaminoMasCorto(1,1);
                gp.printCaminoMasCorto(1,10);
                gp.printCaminoMasCorto(3,5);
                gp.printCaminoMasCorto(8,4);
                gp.printCaminoMasCorto(1,6);
                break;
            case 3:
                // Cuando el mismo camino mas corto tiene dos lineas distintas
                gp.cargarDesdeArchivo("src/main/java/Datos/datos_003.txt");
                gp.imprimirConexiones();
                gp.imprimirLineas();
                gp.printCaminoMasCorto(1,4);
                break;
            default:
                System.out.println("Incorrecto. Arranque el sistema nuevamente...");
        }

    }
}