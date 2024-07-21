package service.caops;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import service.exceptions.InvalidFieldException;
import service.ticket.User;
import service.ticketoperations.TicketOperations;

public class CaOps {
    /**
     * @param user,        user credentials
     * @param workingPath, the path to the client
     * @throws IOException
     */
    public static void registerUser(User user, String workingPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        setClientFabricHome(workingPath, processBuilder);
        String commmand = "fabric-ca-client register -d --id.name " + user.getUsername() +
                "--id.secret " + user.getPassword() +
                " -u http://172.24.166.62:30002 --tls.certfiles ./root-cert/ca-cert.pem --mspdir ./admin-msp/";
        processBuilder.command(commmand);
        processBuilder.start();
    }

    /**
     * @param workingPath,the path to the client
     * @param processBuilder, the process that will build this up
     */
    public static void setClientFabricHome(String workingPath, ProcessBuilder processBuilder) {
        Map<String, String> environment = processBuilder.environment();
        String currentDirectory = System.getProperty("user.dir");
        Path fullpath = Paths.get(currentDirectory, workingPath);
        environment.put("FABRIC_CA_CLIENT_HOME", fullpath.toString());
        processBuilder.directory(new File(workingPath));
    }

    /**
     * @param user,        user credentials
     * @param workingPath, the path to the client
     * @throws IOException
     */
    public static void enrollUserMsp(User user, String workingPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        setClientFabricHome(workingPath, processBuilder);
        String command = "fabric-ca-client enroll -d -u http://" + user.getUsername() +
                ":" + user.getPassword() +
                "@172.24.166.62:30002 --csr.cn $CN --csr.names 'C=FR,ST=Antibes,L=Antibes,O=Alten' --tls.certfiles  ./root-cert/ca-cert.pem "
                +
                "--mspdir ./tempMsp/";
        processBuilder.command(command);
        processBuilder.start();
    }

    /**
     * @param workingPath, path of the directory to clean up files
     * @throws IOException
     */
    public static void cleanTempMsp(String workingPath) throws IOException {
        Path directory = Paths.get(workingPath);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    Files.delete(path);
                }
            }
        }
    }

    /**
     * @param workingPath
     * @return
     * @throws IOException
     */
    public static byte[] getFileInSomeDirectory(String workingPath) throws IOException {
        Path directory = Paths.get(workingPath);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    return Files.readAllBytes(path);
                }
            }
        }
        return null;
    }

    /**
     * @param user, user to check
     * @throws InvalidFieldException
     */
    public static void checkUser(User user) throws InvalidFieldException {
        if (!TicketOperations.isValidString(user.getUsername())
                || !TicketOperations.checkLength(user.getUsername(), 3)) {
            throw new InvalidFieldException("username");
        }
        if (!TicketOperations.isValidString(user.getPassword())
                || !TicketOperations.checkLength(user.getPassword(), 3)) {
            throw new InvalidFieldException("password");
        }
    }
}
