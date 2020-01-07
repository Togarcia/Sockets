package pkg;

import java.util.ArrayList;

public class Subasta {
	
	static Subasta subasta = null;
	static int nusuarios = 0;
	static boolean empezada=false;
	
	static ArrayList<Usuari> clients = new ArrayList<>();
	static ArrayList<Producte> productes = new ArrayList<>();
	
	static long startTime;
	static long precioActual;
	
	private Subasta()
	{
		
	}
	
	public static Subasta getSubasta()
	{
		if(subasta == null)
		{
			subasta = new Subasta();
			
			clients.add(new Usuari("marina", "hola", 2500, "none", false));
			clients.add(new Usuari("adri", "hola", 2500, "none", false));
			clients.add(new Usuari("toni", "hola", 2500, "none", false));
			
			productes.add(new Producte("Producte 1", 100, 0, null));
			productes.add(new Producte("Producte 2", 150, 0, null));
			productes.add(new Producte("Producte 3", 200, 0, null));
		}
		
		return subasta;
		
	}
	
	

	public static void newUsuario()
	{
		nusuarios++;
		
		if(nusuarios >= 2)
		{
			empezada=true;
		}
	}

	public static boolean login(String nom, String password, String address)
	{
		for(Usuari u : clients)
		{
			if(u.getNom().equals(nom))
			{
				if(u.getPassword().equals(password) && u.isLogged() == false)
				{
					u.setLogged(true);
					
					u.setAddress(address);
					
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}

	public static String getUsernameByAddress(String address)
	{
		String s = "";
		
		for(Usuari u : clients)
		{
			if(u.getAddress().equals(address))
			{
				return u.getNom();
			}
		}
		
		return s;
	}
	
	public static void updateSaldoByAddress(String address, long precio)
	{
		for(Usuari u : clients)
		{
			if(u.getAddress().equals(address))
			{
				System.out.println(u.getSaldo() + " - " + precio + " = " + (u.getSaldo() - precio));
				
				u.setSaldo(u.getSaldo() - precio);
			}
		}
	}
	
	public static boolean canUserBid(String address, long bid)
	{
		for(Usuari u : clients)
		{
			if(u.getAddress().equals(address))
			{
				if((u.getSaldo() - bid) >= 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}
	
	public static long getUserSaldo(String address)
	{
		for(Usuari u : clients)
		{
			if(u.getAddress().equals(address))
			{
				return u.getSaldo();
			}
		}
		
		return 0;
	}
}
