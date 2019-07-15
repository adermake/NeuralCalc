
public class Neuron {

	double value = 0;
	public static int currentID = 0;
	public int id;
	public int layer;
	public NeuronType nt;
	public boolean calculactionComplete = false;
	public Neuron(NeuronType nt,int layer) {
		this.nt =  nt;
		this.layer = layer;
		id = currentID++;
	}
	public Neuron clone()
	{
		
		
		Neuron n = new Neuron(nt,layer);
		n.id = this.id;
		n.value = value;
		return n;
	} 
	
	
	
}

