package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
	public static void main(String[] args) throws IOException
	{
		Threadrw t = new Threadrw();
		t.start();
	}

	static class Threadrw extends Thread
	{
		public void run()
		{
			Socket echoSocket = null;
			BufferedReader in = null;
			PrintWriter out = null;

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String s;
			try
			{
				echoSocket = new Socket("10.1.6.76", 20000);

				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				out = new PrintWriter(echoSocket.getOutputStream(), true);

				while (true)
				{
					if (in.ready())
					{
						s = in.readLine();

						if (s != null)
						{
							System.out.println(s);

							s = null;
						}

					}
					if (stdIn.ready())
					{
						s = stdIn.readLine();

						if (s != null)
						{
							// System.out.println(s);
							out.println(s);

							s = null;
						}
					}

					try
					{
						Thread.sleep(20);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (UnknownHostException e)
			{
				System.err.println("Host desconocido");
				System.exit(1);
			} catch (IOException e)
			{
				System.err.println("No se puede conectar a localhost");
				System.exit(1);
			}
		}
	}
}
