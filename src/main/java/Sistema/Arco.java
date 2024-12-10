package Sistema;

import java.util.ArrayList;

/**Como su nombre lo indica, representa un arco en el grafo. Conecta dos nodos y le asigna un peso*/
public class Arco {
    private Parada i;
    private Parada f;
    private int p;
    ArrayList<Linea> lineas = new ArrayList<>(); // Un arcox puede formar parte de varias lineas

    public Arco(int i, int f, int peso, Linea linea) {
        this.i = new Parada(i);
        this.f = new Parada(f);
        this.p = peso;
        this.lineas.add(linea);
    }

    // ------ Getter & Setter ------
    public Parada getI() {
        return i;
    }

    public Parada getF() {
        return f;
    }

    public int getP() {
        return p;
    }

    Linea getLinea(){
        return this.lineas.get(0);
    }

    @Override
    public String toString(){
        return i+" -> "+f+" con peso "+p+" de linea "+lineas.get(0).getNombre();
    }
}
