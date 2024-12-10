package Sistema;

import java.util.ArrayList;

public class Linea {
    private String nombre;
    private ArrayList<Arco> arcos = new ArrayList();

    public Linea(String nombre) {
        this.nombre = nombre;
    }

    void addArco(int i, int f, int p) {
        Arco arco = new Arco(i,f,p,this);

        if (arcos.size() == 0) {
            arcos.add(arco);
            return;
        }

        for (Arco a : arcos){
            if (a.toString().equals(arco.toString())) {
                return;
            }
        }
        arcos.add(new Arco(i, f, p, this));
    }

    //------ Getter & Setter ------
    public String getNombre() {
        return nombre;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Linea " + nombre + "\n");
        for (Arco arco : arcos) {
            s.append(arco + "\n");
        }
        return s.toString();
    }

    ArrayList<Arco> getArcos() {
        return arcos;
    }
}
