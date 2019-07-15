import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class NeuronConnection implements Comparator<NeuronConnection>{

	public Neuron from;
	public Neuron to;
	public double weight;
	public NeuralNetwork nw;
	public int layer = 0;
	
	public void calc() {
		if (from == null)
		System.out.println("Why"+from+to+" "+layer+" "+nw.id);
		to.value += from.value * weight;
	}
	
	public NeuronConnection(Neuron n1,Neuron n2,NeuralNetwork nw,int layer) {
		this.nw = nw;
		this.layer = layer;
		from = n1;
		to = n2;
		weight = randDouble(-2,2);
	}
	public NeuronConnection(NeuralNetwork nw,int layer) {
		this.nw = nw;
		this.layer = layer;
		//DONT USE
		
	}
	public NeuronConnection() {
		//DONT USE
	}
	public NeuronConnection clone(NeuralNetwork nn) {
		//CLONIG TO THE NEW NETWORK NN
		NeuronConnection n = new NeuronConnection(nn,layer);
		n.weight = weight;
		Neuron nFrom = nn.findNeuron(from);
		Neuron nTo = nn.findNeuron(to);
		
		if ( nFrom == null) {
			nFrom = from.clone();
			nn.neurons.add(nFrom);
			if (nFrom.nt == NeuronType.INPUT) {
				nn.inputNeurons.add(nFrom);
			}
		}
			
		if ( nTo == null) {
			nTo = to.clone();	
			nn.neurons.add(nTo);
			
		}
			
			
		n.to = nTo;
		n.from = nFrom;
		
		nn.connections.add(n);
		return n; 
		
	}

	
	public void mutate() {
		weight = weight + randDouble(-1,1);
	}
	

	@Override
	public int compare(NeuronConnection nc1, NeuronConnection nc2) {
		if (nc1.layer<=nc2.layer) {
			return 1;
		}
		return -1;
	}
	public static double randDouble(double min, double max) {
		  return ThreadLocalRandom.current().nextDouble(min, max);
		}
	public static int randInt(int min, int max) {
		  return ThreadLocalRandom.current().nextInt(min, max);
		}
}
