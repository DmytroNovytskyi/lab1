package com.nulp.neuron;

import java.util.Random;

public class Network {
    private final int EPOCHS = 1000;
    private final Neuron[] NEURONS = {
            new Neuron(), new Neuron(), new Neuron(),
            new Neuron()};

    public Double predict(double input1, double input2, double input3){
        double neuron0 = NEURONS[0].compute(input1, input2, input3);
        double neuron1 = NEURONS[1].compute(input1, input2, input3);
        double neuron2 = NEURONS[2].compute(input1, input2, input3);
        return NEURONS[3].compute(neuron0, neuron1, neuron2);
    }

    public void train(double[][] data, double[] answers) {
        Double bestEpochLoss = null;
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            Neuron epochNeuron = NEURONS[epoch % NEURONS.length];
            epochNeuron.mutate();

            double[] predictions = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                predictions[i] = predict(data[i][0], data[i][1], data[i][2]);
            }
            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            if (bestEpochLoss == null) {
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            } else {
                if (thisEpochLoss < bestEpochLoss) {
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                } else {
                    epochNeuron.forget();
                }
            }
            if (epoch % 10 == 0) {
                System.out.printf("Epoch: %3s | bestEpochLoss: %.15f | thisEpochLoss: %.15f%n",
                        epoch, bestEpochLoss, thisEpochLoss);
            }
        }
    }
}
