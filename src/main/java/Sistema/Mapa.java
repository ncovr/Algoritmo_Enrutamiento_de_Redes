package Sistema;

import java.io.IOException;
import java.util.*;

public class Mapa extends Operaciones{
    ArrayList<Linea> grafo = new ArrayList<>();

    public Mapa(){}

    /**Lee un archivo para generar el grafo*/
    void build(String nombreArchivo) throws IOException {
        Mapa m = toBuild(nombreArchivo);
        if (m == null) return;
        this.grafo = m.getGrafo();
    }

    /**Cuando uno inserta un vector (inicio, final, peso) debe insertar dos paradas, el peso y especificar la linea*/
    void insert(String n, int i, int f, int p){
        Linea l = new Linea(n);
        l.addArco(i,f,p);

        if (grafo.isEmpty()){
            grafo.add(l);
            return;
        }

        // Si existe una linea con el nombre dado, se le agrega el arco
        for (Linea linea:grafo){
            if (linea.getNombre().equals(n)){
                linea.addArco(i,f,p);
                return;
            }
        }
        // Si no, se almacena la nueva linea con el arco incluido
        grafo.add(l);
    }

    /**Imprime en consola los costos desde inicio a todos los caminos disponibles */
    void costosDesde(int inicio){
        Map<Integer, Integer> mapa = dijkstra(getAdList(grafo), inicio);
        StringBuilder str = new StringBuilder();
        boolean camino = false; //

        str.append("Desde la parada "+inicio+" hasta la...\n");
        for (Integer key:mapa.keySet()){
            if (mapa.get(key) == Integer.MAX_VALUE) str.append(key+" sin camino\n");
            else {
                str.append(key + " en " + mapa.get(key) + " minutos\n");
                if (mapa.get(key) != 0) camino = true;
            }
        }

        if (camino) System.out.println(str);
        else System.out.println("Desde la parada "+inicio+" no hay camino hacia otras paradas");
    }

    /**Imprime el recorrido entre inicio y termino*/
    void indicaciones(int inicio, int termino){
        imprimirIndicacionesConLineas(getAdList(grafo), inicio, termino);
        System.out.println();
    }

    /**Imprime la lista de adyacencia de un grafo*/
    void display() {
        toDisplay(grafo);
    }

    ArrayList<Linea> getGrafo() {
        return grafo;
    }
}
