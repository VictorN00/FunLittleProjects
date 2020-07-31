import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MulticastGroupChat {
	
	private volatile static int OFFSET = Long.BYTES, MAX_LEN = 1000 + OFFSET;
	private volatile static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd -- HH:mm:ss.SSS");

	private volatile MulticastSocket socket = null;
	private volatile InetAddress addr = null;
	//private volatile int port;
	private volatile BufferedReader input = null;
	private volatile boolean exit;
	private volatile long prev_id;
	private String name;

	/*class Send extends Thread {
		public void run() {
			while (!exit) {
				try {
					String msg = input.readLine();
					if (msg.equals("exit()")) {
						exit = true;
						socket.leaveGroup(addr);
						socket.close();
						input.close();
					}
					byte[] buffer = msg.trim().getBytes();
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, port);
					socket.send(packet);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
	}*/

	class Receive extends Thread {
		public void run() {
			while (!exit) {
				try {
					byte[] buffer = new byte[MAX_LEN];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String received = dateformat.format(new Date(System.currentTimeMillis()));
					String msg = new String(buffer, OFFSET, MAX_LEN - OFFSET).trim();
					byte[] msgid = new byte[OFFSET];
					System.arraycopy(buffer, 0, msgid, 0, OFFSET);
					long id = ByteBuffer.wrap(msgid).getLong();
					String sent = dateformat.format(new Date(id));
					if (prev_id != id)
						System.out.printf("[%s | %s]: %s\n", sent, received, msg);
					/*if (msg.equals("exit()")) {
						exit = true;
					}*/
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
	}

	public MulticastGroupChat(String address, int port) throws Exception {
		addr = InetAddress.getByName(address);
		//this.port = port;
		socket = new MulticastSocket(port);
		input = new BufferedReader(new InputStreamReader(System.in));
		exit = false;

		System.out.print("Display Name: ");
		name = input.readLine().trim();
		System.out.println();
		socket.joinGroup(addr);
		System.out.println("Connected. You can start sending messages.\n");
		new Receive().start();
		String msg = "";
		while (!exit) {
			msg = input.readLine();
			if (msg.equals("exit()")) {
				exit = true;
				socket.leaveGroup(addr);
				socket.close();
				input.close();
				break;
			}
			else if (msg.matches("change_name\\(.*\\)")) {
				String newname = msg.substring("change_name(".length(), msg.length() - 1).trim();
				name = newname;
				System.out.println("Your name has been changed to " + newname + ".");
				continue;
			}
			msg = name + ": " + msg;
			byte[] msgbuffer = msg.trim().getBytes();
			byte[] buffer = new byte[MAX_LEN];
			long id = System.currentTimeMillis();
			byte[] msgid = ByteBuffer.allocate(OFFSET).putLong(id).array();
			System.arraycopy(msgid, 0, buffer, 0, OFFSET);
			System.arraycopy(msgbuffer, 0, buffer, OFFSET, msgbuffer.length);
			prev_id = id;
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, port);
			socket.send(packet);
		}
		//new Send().start();
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Argument(s): address:port");
			return;
		}
		new MulticastGroupChat(args[0].substring(0, args[0].indexOf(':')), Integer.parseInt(args[0].substring(args[0].indexOf(':') + 1)));
	}

}
