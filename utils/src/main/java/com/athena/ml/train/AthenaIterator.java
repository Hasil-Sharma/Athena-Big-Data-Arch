package com.athena.ml.train;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class AthenaIterator implements DataSetIterator {
    private final WordVectors wordVectors;
    private final int batchSize;
    private final int vectorSize;
    private final int truncateLength;

    private final TokenizerFactory tokenizerFactory;
    private final String train_path;
    private BufferedReader br;
    private boolean bufferedReaderFlag = true;


    public AthenaIterator(String train_path, WordVectors wordVectors, int batchSize, int truncateReviewsToLength) throws Exception{
        this.batchSize = batchSize;
        this.vectorSize = wordVectors.getWordVector(wordVectors.vocab().wordAtIndex(0)).length;
        this.wordVectors = wordVectors;
        this.truncateLength = truncateReviewsToLength;

        this.tokenizerFactory = new DefaultTokenizerFactory();
        this.tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
        this.train_path = train_path;
        this.br = new BufferedReader(new FileReader(train_path));
        this.br.readLine();
    }

    public DataSet next(int num) {
        if (!bufferedReaderFlag) throw new NoSuchElementException();
        List<String> dataLines = new ArrayList<>();
        List<Character> labelLines = new ArrayList<>();
        for(int i = 0; i < num && bufferedReaderFlag; i++){
            String line;
            try {
                if ((line = br.readLine()) != null) {
                    dataLines.add(line.substring(0, line.length() - 2));
                    labelLines.add(line.charAt(line.length() - 1));
                } else {
                    bufferedReaderFlag = false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                bufferedReaderFlag = false;
            } catch (Exception e){
                bufferedReaderFlag = false;
            }
        }

        List<List<String>> allTokens = new ArrayList<>(dataLines.size());
        int maxLength = 0;

        for(String s : dataLines){
            List<String> tokens = tokenizerFactory.create(s).getTokens();
            List<String> tokensFiltered = new ArrayList<>();

            for(String t : tokens) {
                if (wordVectors.hasWord(t)) tokensFiltered.add(t);
            }
            allTokens.add(tokensFiltered);
            maxLength = Math.max(maxLength, tokensFiltered.size());
        }

        if (maxLength > truncateLength) maxLength = truncateLength;

        INDArray features = Nd4j.create(new int[]{dataLines.size(), vectorSize, maxLength}, 'f');
        INDArray labels = Nd4j.create(new int[]{dataLines.size(), 2, maxLength}, 'f');

        INDArray featuresMask = Nd4j.zeros(dataLines.size(), maxLength);
        INDArray labelsMask = Nd4j.zeros(dataLines.size(), maxLength);


        for(int i = 0; i < dataLines.size(); i++){
            List<String> tokens = allTokens.get(i);
            int seqLength = Math.min(tokens.size(), maxLength);
            if (seqLength == 0) continue;
            try {
                final INDArray vectors = wordVectors.getWordVectors(tokens.subList(0, seqLength)).transpose();
                features.put(
                        new INDArrayIndex[]{
                                NDArrayIndex.point(i), NDArrayIndex.all(), NDArrayIndex.interval(0, seqLength)
                        },vectors);

                featuresMask.get(new INDArrayIndex[] {NDArrayIndex.point(i), NDArrayIndex.interval(0, seqLength)}).assign(1);

                int lastIdx = Math.min(tokens.size(), maxLength);
                int idx = labelLines.get(i) - '0';
                labels.putScalar(new int[]{i, idx, lastIdx - 1}, 1.0);
                labelsMask.putScalar(new int[]{i, lastIdx - 1}, 1.0);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return new DataSet(features, labels, featuresMask, labelsMask);
    }

    @Override
    public int totalExamples() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int inputColumns() {
        return vectorSize;
    }

    @Override
    public int totalOutcomes() {
        return 2;
    }

    @Override
    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public void reset() {
        try {
            this.br = new BufferedReader(new FileReader(this.train_path));
            this.br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedReaderFlag = true;
    }

    @Override
    public int batch() {
        return batchSize;
    }

    @Override
    public int cursor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int numExamples() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPreProcessor(DataSetPreProcessor preProcessor) {

    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        return null;
    }

    @Override
    public List<String> getLabels() {
        return Arrays.asList("toxic", "not_toxic");
    }

    @Override
    public boolean hasNext() {
        return bufferedReaderFlag;
    }

    @Override
    public DataSet next() {
        return next(batchSize);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public INDArray loadFeaturesFromString(String reviewContents, int maxLength){
        List<String> tokens = tokenizerFactory.create(reviewContents).getTokens();
        List<String> tokensFiltered = new ArrayList<>();
        for(String t : tokens){
            if (wordVectors.hasWord(t)) tokensFiltered.add(t);
        }

        // TODO: This does not handle the case of OOV words
        int outputLength = Math.max(maxLength, tokensFiltered.size());

        INDArray features = Nd4j.create(1, vectorSize, outputLength);

        for(int j = 0; j < tokens.size() && j < maxLength; j++) {
            String token = tokens.get(j);
            INDArray vector = wordVectors.getWordVectorMatrix(token);
            features.put(new INDArrayIndex[]{NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(j)}, vector);
        }

        return features;
    }
}
