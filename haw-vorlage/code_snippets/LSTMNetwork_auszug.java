    //Version 1
public DataSet createTrainingsData(MidiEvents midiTrack, List<Integer> validKeyList, List<Integer> validVeloList) {
    // create input and output arrays: miniBatchSize, nIn, timeSeriesLength
    INDArray input = Nd4j.zeros(1, validKeyList.size()*validVeloList.size()*2, midiTrack.keys.size());
    INDArray labels = Nd4j.zeros(1, validKeyList.size()*validVeloList.size()*2, midiTrack.keys.size());

    int samplePos = 0;
    for(int currentKey : midiTrack.keys){
        int currentVelo = midiTrack.velocities.get(samplePos);
        int currentType = midiTrack.eventTypes.get(samplePos);
        int nextVelo = midiTrack.velocities.get((samplePos + 1) % midiTrack.velocities.size() );
        int nextKey = midiTrack.keys.get((samplePos + 1) % midiTrack.keys.size() );
        int nextType = midiTrack.eventTypes.get((samplePos + 1) % midiTrack.eventTypes.size() );
        input.putScalar(new int[]{0, validKeyList.indexOf(currentKey)*validVeloList.indexOf(currentVelo)*currentType, samplePos}, 1);
        labels.putScalar(new int[]{0, validKeyList.indexOf(nextKey)*validVeloList.indexOf(nextVelo)*nextType, samplePos}, 1);
        samplePos++;
    }

    return new DataSet(input, labels);
}
	
	
	
	
	// Version 2
public static DataSet createTrainingsData(List<Integer> VALID_INTEGER_LIST, ArrayList<Integer> track) {
    // create input and output arrays: miniBatchSize, nIn, timeSeriesLength
    INDArray input = Nd4j.zeros(1, VALID_INTEGER_LIST.size(), track.size());
    INDArray labels = Nd4j.zeros(1, VALID_INTEGER_LIST.size(), track.size());

    int samplePos = 0;
    for(int currentInt : track){
        int nextInt = track.get((samplePos + 1) % track.size() );
        input.putScalar(new int[]{0, VALID_INTEGER_LIST.indexOf(currentInt), samplePos}, 1);
        labels.putScalar(new int[]{0, VALID_INTEGER_LIST.indexOf(nextInt), samplePos}, 1);
        samplePos++;
    }

    return new DataSet(input, labels);
}






// Version 1
for (int j = 0; j < sampleSize; j++) {
    // find the most likeliest output
    double[] outputProbDistribution = new double[output.length()];
    for (int k = 0; k < outputProbDistribution.length; k++) {
        outputProbDistribution[k] = output.getDouble(k);
    }
    int sampledEventIdx = findIndexOfHighestValue(outputProbDistribution);

    // add event to output track
    outputTrack.keys.add(...);
    outputTrack.velocities.add(...);
    outputTrack.eventTypes.add(...);

    // use the last output as input
	{...}
}
	
//Version 2
for (int j = 0; j < sampleSize; j++) {
    // find the most likeliest output
    double[] outputProbDistribution = new double[VALID_INTEGER_LIST.size()];
    for (int k = 0; k < outputProbDistribution.length; k++) {
        outputProbDistribution[k] = output.getDouble(k);
    }
    int sampledEventIdx = findIndexOfHighestValue(outputProbDistribution);

    // add event to output track
    outputTrack.add(VALID_INTEGER_LIST.get(sampledEventIdx));

    // use the last output as input
    INDArray nextInput = Nd4j.zeros(VALID_INTEGER_LIST.size());
    nextInput.putScalar(sampledEventIdx, 1);
    output = net.rnnTimeStep(nextInput);
}