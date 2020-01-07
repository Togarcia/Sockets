package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Conexion extends Thread {
	Socket id;

	public static ArrayList<Conexion> usuaris;
	//public static String nombre;
	//public static long startTime = System.currentTimeMillis();

	public Conexion(Socket id) {
		this.id = id;
	}

	public void run() {
		BufferedReader in = null;

		String s;

		try {
			in = new BufferedReader(new InputStreamReader(id.getInputStream()));
			PrintWriter out = new PrintWriter(id.getOutputStream(), true);
			
			PrintWriter outAux;

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			Scanner sc = new Scanner(System.in);
			
			//while (System.currentTimeMillis()-startTime < 20000)
			while (Subasta.empezada == false)
			{
				if (in.ready())
				{
					s = in.readLine();

					if (s != null)
					{
						String[] login = s.split(" ");
						//if(s.equals("client"))	//Subasta.login
						if(Subasta.login(login[0], login[1], this.id.getRemoteSocketAddress().toString()) == true)
						{
							Subasta.newUsuario();
							
							System.out.println(login[0] + " connectat");
							
							out.println(login[0] + " connectat " + this.id.getRemoteSocketAddress().toString());
						}
						else
						{
							out.println("Usuari o password incorrecte");
						}
					}
				}
				
				/*if (stdIn.ready()) {
					s = stdIn.readLine();
					//System.out.println("he entrado aqui");
					
					if (s != null) {
						System.out.println(s);
						for(Conexion conexion : usuaris ) {
							//System.out.println(servidor.id.getRemoteSocketAddress());
							//System.out.println(this.id.getRemoteSocketAddress());
							
							if(conexion.id == this.id)  {
								//System.out.println(servidor.id.getRemoteSocketAddress());
								out = new PrintWriter(conexion.id.getOutputStream(), true);
								out.println(s);
								out.flush();
							}
						}
						
						//out.flush();
					}
				}*/
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/*for(Usuari u : Subasta.clients)
			{
				System.out.println(u.getNom() + " " + u.getAddress());
			}*/
			System.out.println(Subasta.getUsernameByAddress(this.id.getRemoteSocketAddress().toString()) + " - " + this.id.getRemoteSocketAddress().toString());
			
			//System.out.println("Empiza la subasta");
			
			/*for(Conexion c : usuaris)
			{
				if(c.id == this.id)
				{
					out.println("Empieza la subasta");
				}
			}*/
			
			out.println("Empieza la subasta");
			
			for(Producte p : Subasta.productes)
			{
				/*for(Conexion c : usuaris)
				{
					if(c.id == this.id)
					{
						out.println(p.getNom() + ", preu inicial " + p.getPreuSortida());
					}
				}*/
				
				out.println(p.getNom() + ", preu inicial " + p.getPreuSortida());
				
				Subasta.precioActual = p.getPreuSortida();
					
				Subasta.startTime = System.currentTimeMillis();
				
				while((System.currentTimeMillis() - Subasta.startTime) <= 20000)
				{
					//System.out.println((System.currentTimeMillis() - Subasta.startTime));
					//out.println((System.currentTimeMillis() - Subasta.startTime));
					
					if (in.ready())
					{
						s = in.readLine();

						if (s != null)
						{
							if((Long.valueOf(s) > Subasta.precioActual) && Subasta.canUserBid(this.id.getRemoteSocketAddress().toString(), Long.valueOf(s)))
							{
								Subasta.precioActual = Long.valueOf(s);
								
								for(Conexion c : usuaris)
								{
									outAux = new PrintWriter(c.id.getOutputStream(), true);
									
									//outAux.println(this.id.getRemoteSocketAddress() + " ofereix " + Long.valueOf(s));
									outAux.println(Subasta.getUsernameByAddress(this.id.getRemoteSocketAddress().toString()) + " ofereix " + Long.valueOf(s));
									
									p.setPreuAdjudicacio(Subasta.precioActual);
									
									//p.setNomAdjudicat(this.id.getRemoteSocketAddress().toString());
									p.setNomAdjudicat(Subasta.getUsernameByAddress(this.id.getRemoteSocketAddress().toString()));
								}
								
								Subasta.startTime = System.currentTimeMillis();
							}
							else
							{
								if(Long.valueOf(s) < Subasta.precioActual)
								{
									out.println("Valor inferior a la licitacio actual (" + Subasta.precioActual + ")");
								}
								
								if(Subasta.canUserBid(this.id.getRemoteSocketAddress().toString(), Long.valueOf(s)) == false)
								{
									out.println("Licitacio superior al saldo actual (" + Subasta.getUserSaldo(this.id.getRemoteSocketAddress().toString()) + ")");
								}
							}
						}
					}
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//TEST
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("acaba subasta de usuario " + Subasta.getUsernameByAddress(this.id.getRemoteSocketAddress().toString()));
				
				/*for(Conexion c : usuaris)
				{
					if(c.id == this.id)
					{
						out.println("Ja no hi ha mes ofertes, adjudicat a " + this.id.getRemoteSocketAddress() + " per " + Subasta.precioActual);
					}
				}*/
				
				if(p.getNomAdjudicat() == null)
				{
					out.println("No hi ha hagut cap oferta, producte sense adjudicar");
				}
				else
				{
					out.println("Ja no hi ha mes ofertes, adjudicat a " + p.getNomAdjudicat() + " per " + Subasta.precioActual);
					
					//Actualiza el dinero actual del usuario que ha comprado el producto
					if(p.getNomAdjudicat().equals(Subasta.getUsernameByAddress(this.id.getRemoteSocketAddress().toString())))
					{
						Subasta.updateSaldoByAddress(this.id.getRemoteSocketAddress().toString(), Subasta.precioActual);
					}
				}
				
				//TEST
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				out.println("Els clients queden:");
				
				for(Usuari u : Subasta.clients)
				{
					out.println(u.getNom() + ", saldo: " + u.getSaldo());
				}
				
				/*p.setPreuAdjudicacio(Subasta.precioActual);
				p.setNomAdjudicat(this.id.getRemoteSocketAddress().toString());*/
			}
			
			out.println("Subasta finalitzada, els productes han sigut adjudicats a:");
			
			for(Producte p : Subasta.productes)
			{
				if(p.getNomAdjudicat() == null)
				{
					out.println(p.getNom() + " sense adjudicar");
				}
				else
				{
					out.println(p.getNom() + " adjudicat a " + p.getNomAdjudicat() + " per un valor de " + p.getPreuAdjudicacio());
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Host desconocido");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("No se puede conectar a localhost");
			System.exit(1);
		}

	}

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(20000);

		usuaris = new ArrayList<>();
		
		Subasta.getSubasta();

		while (true) {
			Conexion s = new Conexion(ss.accept());
			//Subasta.newUsuario();
			usuaris.add(s);
			s.start();
		}
	}
}
