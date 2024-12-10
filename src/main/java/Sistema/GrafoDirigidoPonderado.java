package Sistema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GrafoDirigidoPonderado {
    private final Map<Integer, List<Arco>> listaAdyacencia;
    private final Map<String, List<Integer>> lineas;

    public GrafoDirigidoPonderado() {
        this.listaAdyacencia = new HashMap<>();
        this.lineas = new HashMap<>();
    }

    /**Imprime en consola las instrucciones de como llegar desde inicio hasta fin*/
    void printCaminoMasCorto(int inicio, int fin) {
        System.out.println();
        if (inicio == fin) {
            System.out.println("Recorrido de "+inicio+" hasta "+fin+": ERROR");
            System.out.println("los nodos ingresados son iguales");
            return;
        }

        List<Integer> camino = dijkstra(inicio, fin);

        if (camino == null || camino.isEmpty()) {
            System.out.println("Recorrido de "+inicio+" hasta "+fin+": ERROR");
            System.out.println("No existe un camino que conecte las paradas");
            return;
        }

        int costo = calcularCostoTotal(camino);
        System.out.println("Recorrido desde la parada " + inicio + " hasta la parada " + fin + ", en "+costo+" minutos");

        String lineaActual = null; // Línea en uso actualmente
        for (int i = 0; i < camino.size() - 1; i++) {
            int paradaActual = camino.get(i);
            int paradaSiguiente = camino.get(i + 1);

            // Determinar la línea común entre las dos paradas
            String lineaSiguiente = obtenerLineaComun(paradaActual, paradaSiguiente);

            if (lineaSiguiente == null) {
                System.out.println("Error: no hay línea directa entre las paradas " + paradaActual + " y " + paradaSiguiente);
                return;
            }

            if (!lineaSiguiente.equals(lineaActual)) {
                // Cambio de línea detectado
                if (lineaActual != null) {
                    System.out.println("Cambiar a la línea " + lineaSiguiente + " en la parada " + paradaActual);
                } else {
                    System.out.println("Tomar la línea " + lineaSiguiente + " desde la parada " + paradaActual);
                }
                lineaActual = lineaSiguiente;
            }
        }

        // Imprimir llegada a la última parada
        System.out.println("Llegar a la parada " + fin + " por la línea " + lineaActual);

        System.out.print("Orden del recorrido: ");
        for (int i = 0; i < camino.size(); i++) {
            if (i == camino.size() - 1) {
                System.out.println(camino.get(i));
            } else {
                System.out.print(camino.get(i) + " -> ");
            }
        }
    }

    private int calcularCostoTotal(List<Integer> ruta) {
        int costoTotal = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            int nodoActual = ruta.get(i);
            int nodoSiguiente = ruta.get(i + 1);

            // Buscar el costo del arco entre nodoActual y nodoSiguiente
            for (Arco arco : obtenerAdyacentes(nodoActual)) {
                if (arco.destino == nodoSiguiente) {
                    costoTotal += arco.peso;
                    break;
                }
            }
        }
        return costoTotal;
    }

    private String obtenerLineaComun(int paradaActual, int paradaSiguiente) {
        // Buscar líneas que incluyan ambas paradas
        for (Map.Entry<String, List<Integer>> entrada : lineas.entrySet()) {
            List<Integer> paradas = entrada.getValue();
            if (paradas.contains(paradaActual) && paradas.contains(paradaSiguiente)) {
                return entrada.getKey();
            }
        }
        return null;
    }

    private List<Integer> dijkstra(int inicio, int fin) {
        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> anteriores = new HashMap<>();
        Set<Integer> procesados = new HashSet<>();  // Para llevar un registro de los nodos procesados
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        // Inicializamos las distancias y anteriores para cada nodo
        for (Integer vertice : listaAdyacencia.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);  // Inicializamos con infinito
            anteriores.put(vertice, null);
        }

        distancias.put(inicio, 0);
        pq.add(inicio);

        while (!pq.isEmpty()) {
            int actual = pq.poll();

            // Si el nodo ya ha sido procesado, lo saltamos
            if (procesados.contains(actual)) continue;

            // Marcamos el nodo como procesado
            procesados.add(actual);

            // Si llegamos al nodo destino, reconstruimos el camino
            if (actual == fin) {
                return reconstruirRuta(inicio, fin, anteriores);
            }

            // Procesamos los nodos adyacentes
            for (Arco arco : obtenerAdyacentes(actual)) {
                int vecino = arco.destino;
                int nuevaDistancia = distancias.get(actual) + arco.peso;

                // Si encontramos un camino más corto
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    anteriores.put(vecino, actual);
                    pq.add(vecino);  // Se añade el vecino a la cola para procesarlo
                }
            }
        }

        return null;  // Si no se encuentra un camino
    }

    private List<Integer> reconstruirRuta(int inicio, int fin, Map<Integer, Integer> anteriores) {
        List<Integer> ruta = new ArrayList<>();
        Integer actual = fin;

        while (actual != null && actual != inicio) {
            ruta.add(actual);
            actual = anteriores.get(actual);
        }

        if (actual != null) {
            ruta.add(inicio);  // Agregar el nodo de inicio si se ha encontrado el camino
        }

        Collections.reverse(ruta);  // Invertir la lista para tener la ruta en el orden correcto
        return ruta;
    }

    private void agregarVertice(int vertice) {
        listaAdyacencia.putIfAbsent(vertice, new ArrayList<>());
    }

    private void agregarArco(int origen, int destino, int peso) {
        agregarVertice(origen);
        agregarVertice(destino);
        listaAdyacencia.get(origen).add(new Arco(destino, peso));
    }

    private List<Arco> obtenerAdyacentes(int vertice) {
        return listaAdyacencia.getOrDefault(vertice, new ArrayList<>());
    }


    // Clase interna para las aristas
    static class Arco {
        int destino;
        int peso;

        public Arco(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return "(" + destino + ", " + peso + ")";
        }
    }

}
