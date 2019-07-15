import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class NeuralNetwork implements Comparator<NeuralNetwork>{

	public static ArrayList<NeuralNetwork> allNetworks = new ArrayList<NeuralNetwork>();
	ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	ArrayList<Neuron> inputNeurons = new ArrayList<Neuron>();
	ArrayList<NeuronConnection> connections = new ArrayList<NeuronConnection>();
	public double failValue = 0;
	public static int nextId = 0;
	public int id;
	public NeuralNetwork() {
		
	}
	
	public NeuralNetwork(int inputs,int layerSize,int layerDepth) {
		id = nextId++;
		for (int i = 0;i<inputs;i++) {
			Neuron n = new Neuron(NeuronType.INPUT,0);
			inputNeurons.add(n);
			neurons.add(n);
		}
		for (int i = 1;i<=layerDepth;i++) {
			
			
			for (int j = 0;j<layerSize;j++) {
				Neuron n =  new Neuron(NeuronType.MIDDLE,i);
				neurons.add(n);
			}
			connectNeurons(i-1,i);
			
			
			
		}
		//for (int i = 0;i<1;i++) {
			Neuron n = new Neuron(NeuronType.OUTPUT,layerDepth+1);
			
			neurons.add(n);
		//}
		connectNeurons(layerDepth,layerDepth+1);
		allNetworks.add(this);
	}
	
	public NeuralNetwork clone() {
		
		NeuralNetwork nw = new NeuralNetwork();
		nw.id = id;
		for (Neuron n : neurons) {
			nw.neurons.add(n.clone());
			if (n.nt == NeuronType.INPUT) {
				nw.inputNeurons.add(n);
			}
		}
		
		//System.out.println("PRESOIZE"+nw.connections.size()+"---"+connections.size());
		ArrayList<NeuronConnection> cloned = new ArrayList<NeuronConnection>();
		for (NeuronConnection nc : connections) {
			cloned.add(nc.clone(nw));
		}
		nw.connections = cloned;
		//System.out.println("SOIZE"+nw.connections.size());
		
		return nw;
		
		
	}
	
	public void connectNeurons(int layer1,int layer2) {
		for (Neuron n1 : neurons) {
			if (n1.layer == layer1) {
				
				for (Neuron n2 : neurons) {
					if (n2.layer == layer2) {
						
						NeuronConnection nc = new NeuronConnection(n1,n2,this,layer1);
						connections.add(nc);
						
						
					}
				}
				
				
			}
		}
	}
	
	public Neuron findNeuron(Neuron ne) {
		if (ne == null)
			return null;
		for (Neuron n : neurons) {
			if (n.id == ne.id)
				return n;
		}
		return null;
	}
	
	public boolean compareNeuron(Neuron n1,Neuron n2) {
		
		if (n1.id == n2.id) {
			return true;
		}
		return false;
	}
	
	
	public void calculate(double[] input) {
		System.out.println("Called Calculate");
		//RESET NEURONS CALC FLAG
		for (Neuron n : neurons) {
			n.calculactionComplete = false;
		}
		
		//SET INPUT VALUES
		int i = 0;
		
		for (Neuron in : inputNeurons) {
			in.value = input[i];
			i++;
			
		}
		//SORT CONNECTIONS
		Collections.sort(connections, new NeuronConnection());
		
		//START CALCUALTION
		for (NeuronConnection nc : connections ) {
			nc.calc();
		}
		/*
		for (Neuron n : neurons) {
			if (n.nt == NeuronType.OUTPUT) {
				System.out.println(""+n.value);
			}
		}
		*/
		calcFail(input);
		
	}
	
	public double getOutput() {
		for (Neuron n : neurons) {
			if (n.nt == NeuronType.OUTPUT) {
				return n.value;
			}
		}
		return 0;
	}
	
	public void calcFail(double[] input) {
		
		
		failValue = Math.abs(getOutput()-realResult(input));
	}
	
	public double realResult(double[] input) {
		//OVERRIDE IN HERE
		return input[0]+input[1];
		
	}
	
	public static void sortNetworks() {
		Collections.sort(allNetworks, new NeuralNetwork());
		
		for (NeuralNetwork n : allNetworks) {
			System.out.println("NID:"+n.id+" - "+n.failValue);
		}
		
		System.out.println("Done");
		
	}

	public static void naturalSelection() {
		sortNetworks();
		for (int i = 0;i<=(allNetworks.size()/2);i++) {
			
				allNetworks.remove(i);
			
			
			
		}
		ArrayList<NeuralNetwork> addAfter = new ArrayList<NeuralNetwork>();
		for (NeuralNetwork survivor : allNetworks) {
				NeuralNetwork n = survivor.clone();
				n.mutate();
				addAfter.add(n);
		}
		for (NeuralNetwork n : addAfter) {
			allNetworks.add(n);
		}
	}
	
	public void mutate() {
		int changes = randInt(1,9);
		if (connections.size()<=1)
			return;
		for (int i = 0;i<changes;i++) {
			int connSel = randInt(0,connections.size()-1);
			connections.get(connSel).mutate();
		}
	}
	@Override
	public int compare(NeuralNetwork arg0, NeuralNetwork arg1) {
		if (arg0.failValue<arg1.failValue)
		return 1;
		
		return -1;
	}
	
	public static double randDouble(double min, double max) {
		  return ThreadLocalRandom.current().nextDouble(min, max);
		}
	public static int randInt(int min, int max) {
		  return ThreadLocalRandom.current().nextInt(min, max);
		}
	
	
}
