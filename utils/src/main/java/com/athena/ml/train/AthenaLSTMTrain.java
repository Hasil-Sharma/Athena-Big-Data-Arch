package com.athena.ml.train;

import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.layers.GravesBidirectionalLSTM;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.CollectScoresIterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;

public class AthenaLSTMTrain {

    public static void main(String[] args)  throws Exception {
        final String TRAIN_INPUT= args[0];
        final String WORD_VECTORS_PATH = args[1];
        final String MODEL_OUTPUT = args[2];
        int vectorSize = Integer.parseInt(args[3]);
        int nEpochs = Integer.parseInt(args[4]);
        int batchSize = 64;
        int truncateReviewsToLength = 256;

        WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(WORD_VECTORS_PATH));
        AthenaIterator trainingData = new AthenaIterator(TRAIN_INPUT, wordVectors, batchSize, truncateReviewsToLength);
        Nd4j.getMemoryManager().setAutoGcWindow(10000);
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .updater(new Adam(2e-2))
                .weightInit(WeightInit.XAVIER)
                .l2(1e-5)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
                .trainingWorkspaceMode(WorkspaceMode.SEPARATE).inferenceWorkspaceMode(WorkspaceMode.SEPARATE)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(256)
                        .activation(Activation.TANH)
                        .build())
                .layer(1, new RnnOutputLayer.Builder().activation(Activation.SIGMOID)
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(256).nOut(2).build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        ScoreIterationListener scoreIterationListener = new ScoreIterationListener(1);
        net.setListeners(scoreIterationListener);
        System.out.println("Starting Training");

        for (int i = 0; i < nEpochs; i++) {
            net.fit(trainingData);
            System.out.println("Epoch " + i + " complete");
        }

        File locationToSave = new File(MODEL_OUTPUT);
        boolean saveUpdater = true;
        ModelSerializer.writeModel(net, locationToSave, saveUpdater);
    }


}
