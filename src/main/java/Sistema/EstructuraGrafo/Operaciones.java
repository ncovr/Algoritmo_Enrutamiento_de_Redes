package Sistema.EstructuraGrafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Alberga las operaciones del grafo.
 */
public abstract class Operaciones {
    /**
     * Imprime las indicaciones de cómo llegar desde una parada hasta otra.
     * Utiliza una variación de Dijkstra que rastrea los predecesores.
     */
    void imprimirIndicacionesConLineas(Map<Integer, List<Arco>> grafo, int inicio, int destino) {
        Map<Integer, Integer> distancias = new HashMap<>();
        // ↳ Mapa para almacenar la distancia mínima desde el inicio a cada nodo
        Map<Integer, Arco> predecesores = new HashMap<>();
        // ↳ Mapa para rastrear los predecesores de cada nodo en el camino más corto
        PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distancia));
        // ↳ Cola de prioridad para procesar los nodos según la menor distancia
        Set<Integer> visitados = new HashSet<>(); // Conjunto de nodos ya procesados

        // Se inicializan las distancias de #distancias con valor "infinito"
        for (Integer nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        pq.add(new Nodo(inicio, 0));

        // Aqui se procesan los nodos usando Dijkstra
        while (!pq.isEmpty()) {
            Nodo actual = pq.poll(); // Se extrae el nodo de la cola de prioridad
            int valorActual = actual.id; // Se captura el valor del nodo que se está procesando

            // Verifica si el nodo ya ha sido procesado en alguna iteración anterior. Si es así, se salta la iteración actual
            // Recordar que el algoritmo de Dijkstra recorre cada vértice y arista una sola vez
            if (visitados.contains(valorActual)) {
                continue;
            }
            visitados.add(valorActual); // Marca al nodo actual como visitado

            if (grafo.get(valorActual) == null) {
                continue;
            }

            // Recorre las aristas que inciden del nodo actual (lista de arcos)
            for (Arco arco : grafo.get(valorActual)) { // Para cada arco conectado con inicio en #valorActual
                int llegada = arco.getF().getId(); // Se captura el nodo B de llegada (A -> B, donde A es #valorActual y B es #llegada)
                int nuevaDistancia = distancias.get(valorActual) + arco.getP(); // Se captura la suma consecutiva de los pesos hasta #valorActual + el peso del arco actual

                if (nuevaDistancia < distancias.get(llegada)) {
                    distancias.put(llegada, nuevaDistancia);
                    predecesores.put(llegada, arco); // Registrar el arco como predecesor
                    pq.add(new Nodo(llegada, nuevaDistancia));
                }
            }
        }

        // Si no se puede llegar desde inicio hasta destino
        if (!predecesores.containsKey(destino)) {
            System.out.println("No hay camino desde " + inicio + " hasta " + destino);
            return;
        }

        // A partir de aqui se imprimen las indicaciones para llegar hasta destino desde inicio
        LinkedList<Arco> camino = new LinkedList<>(); // Almacena secuencialmente los nodos del recorrido de inicio a destino
        // for: recorre la lista de predecesores va añadiendo los arcos de los predecesores obtenidos con Dijkstra
        for (Integer actual = destino; predecesores.containsKey(actual); actual = predecesores.get(actual).getI().getId())
            camino.addFirst(predecesores.get(actual));

        // Comienza a comunicar indicaciones
        System.out.println("Camino más corto desde " + inicio + " hasta " + destino + ":");
        String lineaActual = null;

        for (int i = 0; i < camino.size(); i++) {
            Arco arco = camino.get(i); // Recupera el arco de la posición i
            if (!arco.getLinea().getNombre().equals(lineaActual)) { // Si el arco recuperado es de una linea distinta
                // de la que se ha ido procesando, se cambia de línea
                System.out.println("Tomar línea " + arco.getLinea().getNombre() +
                        " desde la parada " + arco.getI().getId() +
                        " hasta la parada " + arco.getF().getId());
                lineaActual = arco.getLinea().getNombre(); // Se recupera la primera línea del arco
            } else {
                // Se continúa en la misma línea
                System.out.println("Continuar hasta la parada " + arco.getF().getId());
            }
        }
        System.out.println("Tiempo total: " + distancias.get(destino) + " minutos");
    }

    /**
     * Recibe un grafo en forma de una lista de Líneas y retorna su lista de adyacencia. Corresponde al algoritmo comúnmente
     * utilizado para generar la lista de adyacencia de un grafo, pero en esta ocasión presenta adaptaciones al modelo de
     * grafo implementado
     */
    Map<Integer, List<Arco>> getAdList(ArrayList<Linea> grafo) {
        Map<Integer, List<Arco>> adList = new HashMap<>();

        for (Linea linea : grafo) {
            for (Arco arco : linea.getArcos()) {
                int i = arco.getI().getId();
                int j = arco.getF().getId();

                adList.putIfAbsent(i, new ArrayList<>());
                adList.putIfAbsent(j, new ArrayList<>());

                adList.get(i).add(arco);
            }
        }
        return adList;
    }

    /**
     * Lee un archivo de texto para generar las rutas con sus respectivas lineas. La ruta se escribe como
     * "src/main/java/Datos/datos_###.txt"
     */
    Mapa toBuild(String nombreDelArchivo) throws IOException {
        String rutaArchivo = "src/main/java/Datos/" + nombreDelArchivo;
        Mapa build = new Mapa();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String reader, n = "";
            int i = 0, f = 0, p = 0;

            while ((reader = br.readLine()) != null) {
                reader = reader.trim();
                if (reader.isEmpty() || reader.startsWith("#")) {
                    continue;
                }

                String[] datos = reader.split(",");
                if (datos.length == 1) {
                    n = datos[0];
                    continue;
                }

                if (datos.length == 3) {
                    i = Integer.parseInt(datos[0]);
                    f = Integer.parseInt(datos[1]);
                    p = Integer.parseInt(datos[2]);
                }

                if (p > 0) build.insert(n, i, f, p);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            return null;
        }
        return build;
    }

    /**Presenta en pantalla el grafo filtrado por sus líneas, y dentro de cada línea se escriben los vértices con sus
     * respectivos pesos*/
    void toDisplay(ArrayList<Linea> grafo) {
        for (Linea l : grafo) {
            System.out.println(l);
        }
    }

    /**
     * Clase necesaria para implementar una cola de prioridad dentro del algoritmo de Dijkstra
     */
    private class Nodo {
        int id, distancia;

        Nodo(int i, int distancia) {
            this.id = i;
            this.distancia = distancia;
        }
    }
}
