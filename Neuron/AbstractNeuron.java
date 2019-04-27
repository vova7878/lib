package My.Neuron;

abstract public class AbstractNeuron {

    double out;

    public double getout() {
        return out;
    }

    public double getprout() {
        return -(out*out-1) / 4;
    }

    public static double getActiv(double in1) {
        double out = 0;
        if (in1 < -3) {
            out = -1;
        } else if (in1 > 3) {
            out = 1;
        } else {
            out = in1 / 3;
        }
        return out;
    }
}
