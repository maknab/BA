//App.java
private static final List<Integer> VALID_INTEGER_LIST = new ArrayList<Integer>();

public static void main(String[] args){
	ArrayList<Integer> track;
	
	{...}
	
	LinkedHashSet<Integer> validInputs = new LinkedHashSet<Integer>();
	for (int i : track){
		validInputs.add(i);
	}
	VALID_INTEGER_LIST.addAll(validInputs);	
	
	nIn = VALID_INTEGER_LIST.size();
	nOut = VALID_INTEGER_LIST.size();
	
	{...}
	
	LinkedHashSet<Integer> validKeys = new LinkedHashSet<Integer>();
	for (int i : midiEvents.keys){
		validKeys.add(i);
	}
	VALID_KEY_LIST.addAll(validKeys);

	LinkedHashSet<Integer> validVelos = new LinkedHashSet<Integer>();
	for (int i : midiEvents.velocities){
		validVelos.add(i);
	}
	VALID_VELO_LIST.addAll(validVelos);
	
	nIn = VALID_KEY_LIST.size() * VALID_VELO_LIST.size() * 2;
	nOut = VALID_KEY_LIST.size() * VALID_VELO_LIST.size() * 2;
}
