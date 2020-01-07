package pkg;

public class Usuari
{
	private String nom;
	private String password;
	private long saldo;
	private boolean logged;
	private String address;
	
	//public static int aux;
	
	public Usuari(String nom, String password, long saldo, String address, boolean logged)
	{
		this.nom = nom;
		this.password = password;
		this.saldo = saldo;
		this.address = address;
		this.logged = logged;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public long getSaldo()
	{
		return saldo;
	}

	public void setSaldo(long saldo)
	{
		this.saldo = saldo;
	}

	public boolean isLogged()
	{
		return logged;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public void setLogged(boolean logged)
	{
		/*if(logged == true)
		{
			aux++;
		}*/
		
		this.logged = logged;
	}
}
