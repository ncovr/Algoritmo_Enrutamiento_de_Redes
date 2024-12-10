package Sistema.Test;

import Sistema.EstructuraGrafo.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Mapa main = new Mapa(); // Se instancia un objeto de la clase Mapa para realizar operaciones en él

        // Grafo 1
        System.out.println("\tGrafo 1");
        main.build("datos_001.txt");
        main.display();

        // Test Grafo 1
        main.indicaciones(1,8); // Camino existente

        // Grafo 2
        System.out.println("\tGrafo 2");
        main.build("datos_002.txt");
        main.display();
        main.indicaciones(1,10); // Camino más largo entre dos nodos dentro del grafo
        main.indicaciones(1,2); // 1 y 2 no se conectan bajo ningún motivo
    }
}