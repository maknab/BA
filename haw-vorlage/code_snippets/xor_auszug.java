	INDArray input = Nd4j.zeros(4, 2);
	INDArray labels = Nd4j.zeros(4, 2);

	// ({Sample Index, Input Neuron}, Value)
	input.putScalar(new int[] { 0, 0 }, 0);
	input.putScalar(new int[] { 0, 1 }, 0);
	// ({Sample Index, Output Neuron}, Value)
	labels.putScalar(new int[] { 0, 0 }, 1);
	labels.putScalar(new int[] { 0, 1 }, 0);

	input.putScalar(new int[] { 1, 0 }, 1);
	input.putScalar(new int[] { 1, 1 }, 0);
	labels.putScalar(new int[] { 1, 0 }, 0);
	labels.putScalar(new int[] { 1, 1 }, 1);

	// sample data 2 and 3
	{...}

	DataSet ds = new DataSet(input, labels);
	
	
	
		// list off input values, 4 training samples with data for 2
		// input-neurons each
		INDArray input = Nd4j.zeros(4, 2);

		// correspondending list with expected output values, 4 training samples
		// with data for 2 output-neurons each
		INDArray labels = Nd4j.zeros(4, 2);

		// create first dataset
		// when first input=0 and second input=0
		input.putScalar(new int[] { 0, 0 }, 0);
		input.putScalar(new int[] { 0, 1 }, 0);
		// then the first output fires for false, and the second is 0 (see class
		// comment)
		labels.putScalar(new int[] { 0, 0 }, 1);
		labels.putScalar(new int[] { 0, 1 }, 0);

		// when first input=1 and second input=0
		input.putScalar(new int[] { 1, 0 }, 1);
		input.putScalar(new int[] { 1, 1 }, 0);
		// then xor is true, therefore the second output neuron fires
		labels.putScalar(new int[] { 1, 0 }, 0);
		labels.putScalar(new int[] { 1, 1 }, 1);

		// same as above
		input.putScalar(new int[] { 2, 0 }, 0);
		input.putScalar(new int[] { 2, 1 }, 1);
		labels.putScalar(new int[] { 2, 0 }, 0);
		labels.putScalar(new int[] { 2, 1 }, 1);

		// when both inputs fire, xor is false again - the first output should
		// fire
		input.putScalar(new int[] { 3, 0 }, 1);
		input.putScalar(new int[] { 3, 1 }, 1);
		labels.putScalar(new int[] { 3, 0 }, 1);
		labels.putScalar(new int[] { 3, 1 }, 0);

		// create dataset object
		DataSet ds = new DataSet(input, labels);