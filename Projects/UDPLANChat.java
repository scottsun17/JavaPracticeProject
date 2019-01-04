/* Author:Scott Sun
UDP Socket to send chat through local area network(LAN)

Some of my summaries about UDP socket:
	1. data is packaged into a packet and send connection less like a radio or broadcast
	2. because it is connection less application, the data sending is unreliable. Receiver 
		may not receive the data if the receiver did not instantiate ChatReceive. (It is like
		a radio station. the station sends out music 24/7, but if the user does not turn on the
		radio, the user wont receive the music.)
	3. UDP is connection less, so the data transmitting is fast.
	4. UDP does not have client side or server side. It is only sender and receiver. (you have the  
		option to only send message but not receive message, vice versa, or at the same time)
	5. UDP data packet is limited to 64kb

About the port number for UDP data transmission:
	within the computer system, every executed program has it own unique port number. It is like an ID 
	for the program. When data is transmitted, we use the port number to identify which program to send 
	it to or to receive
	
	port number in the system: 0 ~ 65535
	0 ~ 1023 are reserved port number for system program (Do not use)
	1024 ~ 65535 port number for you to use 

About this program:
	It is a demo of UDP LAN chat. Since I don't have anyone to send the string msg to within my LAn, I write 
	it to send and receive myself. You can update the sending IP object to send it to your friend within the 
	LAN - see inline comments for where to update
	
	Also, the chat receiver must be instantiated to receive chat before the msg is sent.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class Chatsender extends Thread {
	@Override
	public void run() {
		try {

			// 1. Instantiated UDP socket, try-catch SocektEception
			DatagramSocket socket = new DatagramSocket();
			
			// use BufferedReader to read from keyboard (System.in)
			BufferedReader bs = new BufferedReader(new InputStreamReader(System.in));
			
			// 2. Prepare data to send
			String msg = null;

			// 3. Packaging data packet
			DatagramPacket packet = null;

			while ((msg = bs.readLine()) != null) {
				packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
						InetAddress.getLocalHost(), // Update here for the receiver IP ex.
													// InetAddress.getByName("169.254.75.104")
						8989); // this program's port number. you can have anything between 1024 ~ 65535

				// 4. send packet/data
				socket.send(packet); // try-catch IOException
			}

			// 5. close resource
			socket.close();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ChatReceiver extends Thread {

	@Override
	public void run() {
		try {
			// 1. Instantiated UDP socket, try-catch SocektEception
			DatagramSocket socket = new DatagramSocket(8989); // this program's port number. must be the same as sender

			// 2. prepare empty data pack to receive information from sender
			byte[] buf = new byte[1024]; // can be up to 64kb
			// set up packet with empty data pack
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			boolean flag = true;
			while (flag) {

				// 3. call UDP socket method to receive data, try-catch IOException
				socket.receive(packet);

				// 4. print out data received
				System.out
						.println(packet.getAddress().getHostAddress() + " : " + new String(buf, 0, packet.getLength()));
			}
			// 5. close resource
			socket.close();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class UDPLANChat {
	public static void main(String[] args) {
		Chatsender sender = new Chatsender();
		ChatReceiver receive = new ChatReceiver();

		sender.start();
		receive.start();
	}
}
