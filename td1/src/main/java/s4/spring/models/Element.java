package s4.spring.models;

public class Element {
	
	private String nom;
	private int evaluation;
	
	
	
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		
		if(!(o instanceof Element)) return false;
		
		Element other = (Element) o;
		
		if(nom == null) return other.nom == null; 
		
		return this.nom.equals(other.nom);
	}

}
