package MK;

import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  LSTM-Network class for music sequence generation.
 *
 * @author: Marina Knabbe (based on DL4J examples)
 */
public class LSTMNetwork {

    /**
     * Creates MultiLayerNetwork with given parameters.
     *
     * @param lstmLayerSize Size of hidden layers.
     * @param tbpttLength   Length for truncated backpropagation through time.
     * @param nIn           Size of input layer.
     * @param nOut          Size of output layer.
     * @return              created MultiLayerNetwork
     */
    public static MultiLayerNetwork createNetwork(int lstmLayerSize, int tbpttLength, int nIn, int nOut) {
        //Set up network configuration:
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(1)
                .learningRate(0.1)
                .rmsDecay(0.95)
                .seed(12345)
                .regularization(true)
                .l2(0.001)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.RMSPROP)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(nIn).nOut(lstmLayerSize)
                        .activation("tanh").build())
                .layer(1, new GravesLSTM.Builder().nIn(lstmLayerSize).nOut(lstmLayerSize)
                        .activation("tanh").build())
                .layer(2, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).activation("softmax") //MCXENT + softmax for classification
                        .nIn(lstmLayerSize).nOut(nOut).build())
                .backpropType(BackpropType.TruncatedBPTT).tBPTTForwardLength(tbpttLength).tBPTTBackwardLength(tbpttLength)
                .pretrain(false).backprop(true)
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));
        return net;
    }

    /**
     * Trains given MultiLayerNetwork and generates sample output file(s).
     *
     * @param numEpochs     Number of training epochs.
     * @param trainingsData The training data.
     * @param net           The Network, which should be trained.
     * @param sampleTrack   The sample track, which was used for the training data.
     * @param sampleSize    The number of events of generated sample.
     */
    public static void trainNetwork(int numEpochs, DataSet trainingsData, MultiLayerNetwork net,
                                    ArrayList<Integer> sampleTrack, int sampleSize, List<Integer> VALIED_INTEGER_LIST) {
        for (int i = 0; i < numEpochs; i++) {
            net.fit(trainingsData);

            // net output to midi file
            ArrayList<Integer> track = getSampleNetOutput(net, sampleTrack, sampleSize, VALIED_INTEGER_LIST);
            MidiWriter midiWriter = new MidiWriter("midifile track");
            midiWriter.createMidiFile(track);
        }
    }

    public static void trainNetwork(int numEpochs, DataSet trainingsData, MultiLayerNetwork net,
                                    ArrayList<Integer> sampleTrack, int sampleSize) {
        for (int i = 0; i < numEpochs; i++) {
            net.fit(trainingsData);

            // net output to midi file
            ArrayList<Integer> track = getSampleNetOutput(net, sampleTrack, sampleSize);
            MidiWriter midiWriter = new MidiWriter("midifile track");
            midiWriter.createMidiFile(track);
        }
    }
    /**
     * Gets sample net output based random event
     * (Used by trainNetwork)
     *
     * @param net           MultiLayerNetwork to get sample from.
     * @param track         Track to get notes according to output.
     * @param sampleSize    Number of events the net generates for sample.
     * @return              List of generated events (for midi file).
     */
    private static ArrayList<Integer> getSampleNetOutput(MultiLayerNetwork net, ArrayList<Integer> track,
                                                         int sampleSize, List<Integer> VALIED_INTEGER_LIST) {
        ArrayList<Integer> outputTrack = new ArrayList<>();
        // clear current state from the last example
        net.rnnClearPreviousState();

        // put a random event into net as an initialisation
        INDArray testInit = Nd4j.zeros(VALIED_INTEGER_LIST.size());
        /*Random random = new Random();
        int randomIndex = random.nextInt(track.size());
        System.out.println(randomIndex);*/
        testInit.putScalar(VALIED_INTEGER_LIST.indexOf(track.get(5/*randomIndex*/)), 1);

        // run one step, the output shows what the net thinks what should come next
        INDArray output = net.rnnTimeStep(testInit);

        // now the net estimates following notes
        for (int j = 0; j < sampleSize; j++) {

            // find the most likeliest output
            double[] outputProbDistribution = new double[VALIED_INTEGER_LIST.size()];
            for (int k = 0; k < outputProbDistribution.length; k++) {
                outputProbDistribution[k] = output.getDouble(k);
            }
            int sampledEventIdx = findIndexOfHighestValue(outputProbDistribution);

            // add event to output track
            outputTrack.add(VALIED_INTEGER_LIST.get(sampledEventIdx));

            // use the last output as input
            INDArray nextInput = Nd4j.zeros(VALIED_INTEGER_LIST.size());
            nextInput.putScalar(sampledEventIdx, 1);
            output = net.rnnTimeStep(nextInput);
        }
        System.out.println(outputTrack.toString());
        return outputTrack;
    }

    private static ArrayList<Integer> getSampleNetOutput(MultiLayerNetwork net, ArrayList<Integer> track, int sampleSize) {
        ArrayList<Integer> outputTrack = new ArrayList<>();
        // clear current state from the last example
        net.rnnClearPreviousState();

        // put a random event into net as an initialisation
        INDArray testInit = Nd4j.zeros(track.size());
        Random random = new Random();
       /* int randomIndex = random.nextInt(track.size());
        System.out.println(randomIndex);*/
        testInit.putScalar(track.indexOf(track.get(1/*randomIndex*/))
                /*VALIED_INTEGER_LIST.get(randomIndex)*/, 1);

        // run one step, the output shows what the net thinks what should come next
        INDArray output = net.rnnTimeStep(testInit);

        // now the net estimates following notes
        for (int j = 0; j < sampleSize; j++) {

            // find the most likeliest output
            double[] outputProbDistribution = new double[track.size()];
            for (int k = 0; k < outputProbDistribution.length; k++) {
                outputProbDistribution[k] = output.getDouble(k);
            }
            int sampledEventIdx = findIndexOfHighestValue(outputProbDistribution);

            // add event to output track
            outputTrack.add(track.get(sampledEventIdx));

            // use the last output as input
            INDArray nextInput = Nd4j.zeros(track.size());
            nextInput.putScalar(sampledEventIdx, 1);
            output = net.rnnTimeStep(nextInput);
        }
        System.out.println(outputTrack.toString());
        return outputTrack;
    }

    /**
     * Finds the index of the highest value from a given distribution.
     *
     * @param distribution  Distribution of values.
     * @return              Index of highest value.
     */
    private static int findIndexOfHighestValue(double[] distribution) {
        int maxValueIndex = 0;
        double maxValue = 0;

        for (int i = 0; i < distribution.length; i++) {
            if(distribution[i] > maxValue) {
                maxValue = distribution[i];
                maxValueIndex = i;
            }
        }
        return maxValueIndex;
    }

    /**
     *
     * @param track
     * @return
     */
    public static DataSet createTrainingsData(List<Integer> VALIED_INTEGER_LIST, ArrayList<Integer> track) {
        // create input and output arrays: SAMPLE_INDEX, INPUT_NEURON,
        // SEQUENCE_POSITION
        INDArray input = Nd4j.zeros(1, VALIED_INTEGER_LIST.size(), track.size());
        INDArray labels = Nd4j.zeros(1, VALIED_INTEGER_LIST.size(), track.size());
        // loop through our sample-track
        int samplePos = 0;

        for(int currentInt : track){
            int nextInt = track.get((samplePos + 1) % track.size() );
            // System.out.println(nextInt);
            // input neuron for current-char is 1 at "samplePos"
            input.putScalar(new int[]{0, VALIED_INTEGER_LIST.indexOf(currentInt) /*track.get(currentInt)*/, samplePos}, 1);
            // output neuron for next-char is 1 at "samplePos"
            labels.putScalar(new int[]{0, VALIED_INTEGER_LIST.indexOf(nextInt)/*track.get(nextInt)*/, samplePos}, 1);
            samplePos++;
        }

        return new DataSet(input, labels);
    }

    public static DataSet createTrainingsData(ArrayList<Integer> track) {
        // create input and output arrays: SAMPLE_INDEX, INPUT_NEURON,
        // SEQUENCE_POSITION
        INDArray input = Nd4j.zeros(1, track.size(), track.size());
        INDArray labels = Nd4j.zeros(1, track.size(), track.size());
        // loop through our sample-track
        int samplePos = 0;

        for(int currentInt : track){
            int nextInt = track.get((samplePos + 1) % track.size() );
           // System.out.println(nextInt);
            // input neuron for current-char is 1 at "samplePos"
            input.putScalar(new int[]{0, track.indexOf(currentInt), samplePos}, 1);
            // output neuron for next-char is 1 at "samplePos"
            labels.putScalar(new int[]{0, track.indexOf(nextInt), samplePos}, 1);
            samplePos++;
        }

        return new DataSet(input, labels);
    }

    /**
     * Print the number of parameters in the network (and for each layer)
     *
     * @param net
     */
    public static void printNetworkParams(MultiLayerNetwork net) {
        Layer[] layers = net.getLayers();
        int totalNumParams = 0;
        for (int i = 0; i < layers.length; i++) {
            int nParams = layers[i].numParams();
            System.out.println("Number of parameters in layer " + i + ": " + nParams);
            totalNumParams += nParams;
        }
        System.out.println("Total number of network parameters: " + totalNumParams);
    }

}
