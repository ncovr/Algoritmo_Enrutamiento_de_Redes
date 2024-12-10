package Sistema;

import java.util.ArrayList;

/**Representa a un nodo en terminos del grafo*/
public class Parada {
    private int id;

    public Parada(int v) {
        this.id = v;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return id+"";
    }
}
