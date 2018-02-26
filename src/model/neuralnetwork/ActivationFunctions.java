package model.neuralnetwork;

public class ActivationFunctions {
	
	public static float activationFunction(float value, Activation mode) {
		switch (mode) {
		case relu:		
			return ActivationFunctions.relu(value);
		case tanh:
			return ActivationFunctions.tanh(value);
		case logistic:
			return ActivationFunctions.logistic(value);
		case linear:
		default:
			return linear(value);
		}
	}
	
	private static float logistic(float value) {
		double dvalue = value;
		
		double epow = Math.exp(-dvalue);
		
		float result = (float) (1.0f / (1.0f + epow));
		
		return result;
	}

	private static float tanh(float value) {
		double dvalue = value;
		
		double val1 = Math.exp(dvalue);
		double val2 = Math.exp(-dvalue);
		
		double result = (val1 - val2) / (val1 + val2);
		
		return (float) result;
	}

	private static float linear(float value) {
		return value;
	}
	
	private static float relu(float value) {
		return Math.max(0, value);
	}

}
