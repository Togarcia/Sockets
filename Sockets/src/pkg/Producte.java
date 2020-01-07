package pkg;

public class Producte
{
	private String nom;
	private long preuSortida;
	private long preuAdjudicacio;
	private String nomAdjudicat;
	
	public Producte(String nom, long preuSortida, long preuAdjudicacio, String nomAdjudicat)
	{
		super();
		this.nom = nom;
		this.preuSortida = preuSortida;
		this.preuAdjudicacio = preuAdjudicacio;
		this.nomAdjudicat = nomAdjudicat;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public long getPreuSortida()
	{
		return preuSortida;
	}

	public void setPreuSortida(long preuSortida)
	{
		this.preuSortida = preuSortida;
	}

	public long getPreuAdjudicacio()
	{
		return preuAdjudicacio;
	}

	public void setPreuAdjudicacio(long preuAdjudicacio)
	{
		this.preuAdjudicacio = preuAdjudicacio;
	}

	public String getNomAdjudicat()
	{
		return nomAdjudicat;
	}

	public void setNomAdjudicat(String nomAdjudicat)
	{
		this.nomAdjudicat = nomAdjudicat;
	}
}
