package My.Neuron;

public class SNeuronLayer {

    SNeuron neuron[];

    public SNeuronLayer(AbstractNeuron parent[], int length) {
        neuron = new SNeuron[length];
        for (int i = 0; i < length; i++) {
            neuron[i] = new SNeuron(parent);
        }
    }

    public SNeuron getNeuron(int in1) {
        return neuron[in1];
    }
}
