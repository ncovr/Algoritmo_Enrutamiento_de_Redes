package Sistema.EstructuraGrafo;
import java.io.IOException;
import java.util.*;

public class Mapa extends Operaciones {
   private ArrayList<Linea> grafo = new ArrayList<>();

    public Mapa(){}

    /**Lee un archivo para generar el grafo*/
    public void build(String nombreArchivo) throws IOException {
        Mapa m = toBuild(nombreArchivo);
        if (m == null) return;
        this.grafo = m.getGrafo();
    }

    /**Cuando uno inserta un vector (inicio, final, peso) debe insertar dos paradas, el peso y especificar la linea*/
    public void insert(String n, int i, int f, int p){
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

    /**Imprime el recorrido entre inicio y termino*/
    public void indicaciones(int inicio, int termino){
        imprimirIndicacionesConLineas(getAdList(grafo), inicio, termino);
        System.out.println();
    }

    /**Imprime la lista de adyacencia de un grafo*/
    public void display() {
        toDisplay(grafo);
    }

    public boolean isEmpty(){
        return grafo.isEmpty();
    }

    ArrayList<Linea> getGrafo() {
        return grafo;
    }
}
