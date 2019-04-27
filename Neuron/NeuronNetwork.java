package My.Neuron;

import static My.Output.*;

public class NeuronNetwork {

    public InputNeuron inputn[];
    public SNeuronLayer neurons[];
    double needout[], out[];

    public NeuronNetwork(int in1[], double in2[], double in3[]) {
        inputn = new InputNeuron[in1[0]];
        for (int i = 0; i < in1[0]; i++) {
            inputn[i] = new InputNeuron(in2[i]);
        }
        neurons = new SNeuronLayer[in1.length - 1];
        neurons[0] = new SNeuronLayer(inputn, in1[1]);
        for (int i = 1; i < in1.length - 1; i++) {
            neurons[i] = new SNeuronLayer(neurons[i - 1].neuron, in1[i + 1]);
        }
        needout = in3.clone();
        out = new double[needout.length];
    }

    public void update() {
        for (int i = 0; i < neurons.length; i++) {
            for (int t = 0; t < neurons[i].neuron.length; t++) {
                neurons[i].neuron[t].update();
                if (i == neurons.length - 1) {
                    out[t] = neurons[i].neuron[t].getout();
                }
            }
        }
    }

    public void print() {
        for (int t = 0; t < inputn.length; t++) {
            kprintln(inputn[t].getout());
        }
        for (int i = 0; i < neurons.length; i++) {
            for (int t = 0; t < neurons[i].neuron.length; t++) {
                kprintln(neurons[i].neuron[t].getout());
            }
        }
    }

    public void printerr() {
        for (int i = 0; i < neurons.length; i++) {
            for (int t = 0; t < neurons[i].neuron.length; t++) {
                kprintln(neurons[i].neuron[t].error);
            }
        }
    }

    public void setInput(double in1[]) {
        for (int t = 0; t < inputn.length; t++) {
            inputn[t].setOut(in1[t]);
        }
    }

    public void setneedOut(double in1[]) {
        needout = in1.clone();
    }

    public void updateError() {
        for (int t = 0; t < neurons[neurons.length - 1].neuron.length; t++) {
            neurons[neurons.length - 1].neuron[t].error = needout[t] - out[t];
        }
        for (int i = neurons.length - 2; i >= 0; i--) {
            for (int t = 0; t < neurons[i].neuron.length; t++) {
                double error = 0;
                for (int u = 0; u < neurons[i + 1].neuron.length; u++) {
                    error += neurons[i + 1].neuron[u].error * neurons[i + 1].neuron[u].weight[neurons[i + 1].neuron[u].findParent(neurons[i].neuron[t])];
                }
                neurons[i].neuron[t].error = error;
            }
        }
    }

    public void updateWeight(double n) {
        for (int i = 0; i < neurons.length; i++) {
            for (int t = 0; t < neurons[i].neuron.length; t++) {
                double preweight = n * neurons[i].neuron[t].error* neurons[i].neuron[t].getprout();
                for (int u = 0; u < neurons[i].neuron[t].parent.length; u++) {
                    double weight = preweight * neurons[i].neuron[t].parent[u].getout();
                    neurons[i].neuron[t].weight[u] += weight;
                }
            }
        }
    }
}
