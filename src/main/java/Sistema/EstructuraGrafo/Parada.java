package Sistema.EstructuraGrafo;

/**Representa a un nodo en terminos del grafo*/
public class Parada {
    private int id;

    public Parada(int v) {
        this.id = v;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return id+"";
    }
}
