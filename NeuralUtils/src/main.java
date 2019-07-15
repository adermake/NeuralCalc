import java.util.concurrent.ThreadLocalRandom;

public class main {

	
	public static void main(String[] args) {
			
			
			doStuff();
			/*
			NeuralNetwork nw = new NeuralNetwork(2,50,5);
			for (NeuralNetwork n : NeuralNetwork.allNetworks) {
				
				double[] in = new double[] {randDouble(-10,10),randDouble(-10,10)};
				n.calculate(in);
				System.out.println(""+n.id+" Input :" + in+" > output > "+ n.getOutput());
				for (Neuron neu : n.neurons) {
					System.out.println("NeruonNR."+neu.id+" > Value: "+neu.value);
				}
				break;
			}
			NeuralNetwork.naturalSelection();*/
	}
	
	
	public static  void doStuff() {
		for (int i = 0;i<4;i++) {
			NeuralNetwork nw = new NeuralNetwork(2,50,3);
		}
		
		new Runnable(1L, 1L) {
			int t = 0;
			public void run() {
				boolean display = false;
						
					t++;
					for (NeuralNetwork n : NeuralNetwork.allNetworks) {
						//randDouble(-10,10)
						double[] in = new double[] {5D,2D};
						n.calculate(in);
						//System.out.println(""+n.id+" Input :" + in+" > output > "+ n.getOutput());
						
						
					}
					
					NeuralNetwork n = NeuralNetwork.allNetworks.get(NeuralNetwork.allNetworks.size()-1);
					System.out.println("NETWORK NR: "+n.id+" Input> "+n.inputNeurons.get(0).value +" + " +n.inputNeurons.get(1).value+ "> output > "+n.getOutput());
					System.out.println("FailValue "+n.failValue);
					
					if (t>100) {
						
						t = 0;
						this.cancel();
						//System.out.println("Count" + n.neurons.size()+" Connections " + n.connections.size());
					}
					
					
					//System.out.println("Networks" +NeuralNetwork.allNetworks.size());
					//System.out.println("Selecting"+display);
					NeuralNetwork.naturalSelection();
					//System.out.println("Selection Complete");
				
				
				
				
				
				
				
			}
		};
	}
		public static double randDouble(double min, double max) {
			  return ThreadLocalRandom.current().nextDouble(min, max);
			}
}
