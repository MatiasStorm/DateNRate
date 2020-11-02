package easyon.dating.app;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SSHTunnel {
    private String sshUser;
    private String sshHost;
    private int sshPort;
    private String sshPassword;
    private String knownHostKey;
    private int remotePort;
    private int localPort = 3307;
    private String localhost = "127.0.0.1";

    public SSHTunnel(){
        readSecrets();
    }

    private void readSecrets()  {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File secretFile = new File(classLoader.getResource("secrets.txt").getFile());
            Scanner scan = new Scanner(secretFile);
            sshUser = scan.next();
            sshHost = scan.next();
            sshPassword = scan.next();
            sshPort = scan.nextInt();
            scan.nextLine();
            knownHostKey = scan.nextLine();
            remotePort = scan.nextInt();
        }
        catch (FileNotFoundException e){
            System.err.println("Secret File Not Found");
            System.exit(1);
        }
    }

    public void createSshTunnel() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(new ByteArrayInputStream(knownHostKey.getBytes()));
        Session session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);
        System.out.println("Connection to server...");
        session.connect();
        System.out.println("Setting up port forwarding...");
        session.setPortForwardingL(localhost, localPort, localhost, remotePort);
    }
}
