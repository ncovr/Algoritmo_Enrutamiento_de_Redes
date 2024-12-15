// Desarrollado por:
// Nicolás Verdugo Barrera
// Rocío Gaete Orellana

package Sistema.Test;
import Sistema.EstructuraGrafo.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        Mapa main = new Mapa();
        File carpeta = new File("src/main/java/Datos"); // Directorio de archivos generadores
        File[] archivos = carpeta.listFiles();

        System.out.println("Bienvenido al SISTEMA DE ENRUTAMIENTO DE REDES");
        carpeta.mkdir(); // Si no existe el directorio, se crea
        suspensivos("LEYENDO ARCHIVOS");
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("Cree un archivo dentro de la carpeta Datos, ingrese datos y ejecute nuevamente el sistema");
        }
        if (archivos.length == 0) {
            System.out.println("ERROR: NO HAY ARCHIVOS .txt PRESENTES EN LA CARPETA");
            System.out.println("CONSEJO: CREE UN ARCHIVO Y RELLÉNELO CON DATOS RELEVANTES");
            System.exit(0);
        }
        System.out.println("Seleccione el archivo generador");
        for (File archivo : archivos) {
            if (archivo.getName().endsWith(".txt")) {
                mensaje2("-> " + archivo.getName(), 400);
            }

        }
        Thread.sleep(600);
        System.out.print("> ");
        String entrada = in.nextLine();
        System.out.println();
        System.out.println("TAREA: CREAR SISTEMA");

        for (int i = 0; i < archivos.length; i++) {
            if (archivos[i].getName().equals(entrada)) {
                carpeta = archivos[i];
                break;
            }
        }

        if (carpeta == null || carpeta.isDirectory()) {
            System.out.println("ESTADO: ERROR");
            suspensivos("DIAGNOSTICANDO PROBLEMA");
            System.out.println("CAUSA: ARCHIVO NO ENCONTRADO");
            System.out.println("CONSEJO: INGRESE EL NOMBRE NUEVAMENTE");
            return;
        }

        System.out.print("ESTADO: "); suspensivos("PROCESANDO");
        main.build(entrada);
        if (main.isEmpty()) {
            System.out.println("ESTADO: ERROR");
            suspensivos("DIAGNOSTICANDO PROBLEMA");
            System.out.println("CAUSA: ARCHIVO SIN DATOS RELEVANTES");
            System.out.println("CONSEJO: RELLENE EL ARCHIVO CON DATOS RELEVANTES");
            return;
        }

        System.out.println("ESTADO: OK\n");

        // Interacción del usuario con el sistema
        while (!entrada.equals("0")){
            System.out.println("MENÚ PRINCIPAL");
            System.out.println("Ingrese:");
            System.out.println("1. Para solicitar indicaciones");
            System.out.println("2. Para ver el sistema");
            System.out.println("0. Salir");
            System.out.print("> ");
            entrada = in.nextLine();
            switch (entrada) {
                case "1": // Solicitar indicaciones desde a hasta b
                    boolean continuar = true;
                    do{
                        System.out.println("Ingrese el inicio y el fin en formato i f (por ejemplo, 1 8) ");
                        System.out.println("Para salir ingrese alguna letra");
                        System.out.print("> ");
                        entrada = in.nextLine().trim();
                        if (entrada.matches("\\d+\\s+\\d+")) {
                            String[] n = entrada.split("\\s+");
                            System.out.println();
                            main.indicaciones(Integer.parseInt(n[0]), Integer.parseInt(n[1]));
                        } else {
                            continuar = false;
                        }
                    } while (continuar);
                    break;
                case "2": // Visualizar el sistema
                    System.out.println();
                    main.display();
                    break;
                case "0":
                    suspensivos("SALIENDO DEL SISTEMA");
            }
        }
    }

    /**Funciones privadas para controlar mensajes en consola*/
    private static void suspensivos(String st) throws InterruptedException {
        System.out.print(st);
        int ms = 800;
        Thread.sleep(ms);
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(ms);
        }
        System.out.println();
    }

    private static void mensaje2(String mensaje, int ms) throws InterruptedException {
        Thread.sleep(ms);
        System.out.println(mensaje);
    }
}