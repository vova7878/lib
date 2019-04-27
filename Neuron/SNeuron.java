package My.Neuron;

public class SNeuron extends AbstractNeuron {

    AbstractNeuron parent[];
    public double weight[], error;

    public SNeuron(AbstractNeuron parent[]) {
        this.parent = parent;
        weight = new double[this.parent.length];
        for (int i = 0; i < weight.length; i++) {
            weight[i] =0.5;//1- Math.random()*2;//0.5;
        }
        weight[0] =0.33;
    }

    public void update() {
        out = 0;
        for (int i = 0; i < parent.length; i++) {
            out += parent[i].getout() * weight[i];
        }
        out = getActiv(out);
    }

    public int findParent(AbstractNeuron in1) {
        int out = 0;
        for (int i = 0; i < parent.length; i++) {
            if (in1.equals(parent[i])) {
                out = i;
            }
        }
        return out;
    }
}
