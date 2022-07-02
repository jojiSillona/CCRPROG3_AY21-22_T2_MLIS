import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LabRequest {
    private String requestUID;
    private String patientUID;
    private int requestDate;
    private int requestTime;
    private String result;

    Scanner scanner = new Scanner(System.in);

    //ADD LAB REQUEST HERE

    public String searchLabRequest(){
        String finalRequest = null;
        boolean requestFound = false;
        int multipleCount = 0;
        ArrayList<String> multipleEntries = new ArrayList<>();
        String labTestType = "";

        System.out.print("Type in either Request UID or Patient UID:");

        String input = scanner.nextLine();

        ArrayList<String> fileList = new ArrayList<>();

        File folder = new File(".");
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isFile() && file.getName().endsWith(".txt") && file.getName().contains("Requests"))
                fileList.add(file.getName());
        }
        for(String fileName : fileList){
            try{

                Scanner fileScanner = new Scanner(new File(fileName));
                while(fileScanner.hasNext()){

                    String line = fileScanner.nextLine();
                    if(line.contains(input)){

                        multipleEntries.add(line);
                        finalRequest = line;
                        requestFound = true;
                        ++multipleCount;

                    }
                }
                fileScanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(multipleCount > 1){


            String[] requestDetails;

            requestFound = false;
            System.out.println("Multiple Records Found!");
            System.out.printf("%25s %30s %10s %10s", "Request UID", "Lab Test Type", "Request Date", "Results");
            System.out.println();
            for (String multipleEntry : multipleEntries) {
                requestDetails = multipleEntry.split(";");
                labTestType = getServiceDetails(labTestType, requestDetails);
                System.out.printf("%25s %30s %10s %10s", requestDetails[0], labTestType, requestDetails[2], requestDetails[4]);
                System.out.println();
            }
            System.out.print("Please type the Request UID that you want to access: ");
            do{

                String inputRequest = scanner.nextLine();
                for(String currentRequest : multipleEntries){
                    if(currentRequest.contains(inputRequest.toUpperCase(Locale.ROOT))){
                        finalRequest = currentRequest;
                        requestFound = true;
                        break;
                    }
                }
                if(!requestFound)
                    System.out.print("Request not found, please try again: ");
            } while(!requestFound);
        }

        if(!requestFound){
            System.out.println("Request does not exist in any records. Do you want to look again?");
            System.out.println("(Y)es - Look again");
            System.out.println("(N)o  - Return to main menu");
            System.out.print("Input: ");

            boolean exitLoop;

            do {
                char choice = scanner.next().charAt(0);
                scanner.nextLine();

                if (Character.toUpperCase(choice) == 'Y') {

                    finalRequest = searchLabRequest();
                    exitLoop = true;
                }else if (Character.toUpperCase(choice) == 'N') {

                    System.out.println("returning to main menu");
                    return null;

                }else {

                    System.out.print("INPUT UNIDENTIFIED, please try again: ");
                    exitLoop = false;
                }
            } while(!exitLoop);
        } else {
            String[] requestDetails = finalRequest.split(";");
            System.out.println("REQUEST FOUND!");
            System.out.println("REQUEST UID: " + requestDetails[0]);
            labTestType = getServiceDetails(labTestType, requestDetails);
            System.out.println("LAB TEST TYPE: " + labTestType);
            System.out.println("DATE REQUESTED: " + requestDetails[2]);
            System.out.println("RESULTS :" + requestDetails[4]);
        }
        return finalRequest;
    }

    private String getServiceDetails(String labTestType, String[] requestDetails) {
        try{
            Scanner fileScanner = new Scanner(new File("Services.txt"));
            while(fileScanner.hasNext()){

                String line = fileScanner.nextLine();
                if(line.contains(requestDetails[0].substring(0, 3))){
                    String[] serviceDescription = line.split(";");
                    labTestType = serviceDescription[1];
                    break;
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labTestType;
    }

    private String getService(String targetRequest){
        ArrayList<String> fileList = new ArrayList<>();

        File folder = new File(".");
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isFile() && file.getName().endsWith(".txt") && file.getName().contains("Requests"))
                fileList.add(file.getName());
        }
        for(String fileName : fileList){
            try{

                Scanner fileScanner = new Scanner(new File(fileName));
                while(fileScanner.hasNext()){

                    String line = fileScanner.nextLine();
                    if(line.contains(targetRequest)){
                        return fileName;
                    }
                }
                fileScanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    //DELETE LAB REQUEST

    public void editLabRequest(){

        StringBuilder base = new StringBuilder();


        System.out.println("===== EDIT LABORATORY REQUEST =====");
        String targetLabRequest = searchLabRequest();
        String[] info = targetLabRequest.split(";");
        System.out.print("You can only edit RESULTS. Do you want to continue? (Y/N):");
        boolean exitLoop = false;
        do {
            char choice = scanner.next().charAt(0);
            scanner.nextLine();

            if (Character.toUpperCase(choice) == 'Y') {
                System.out.print("Please enter the result: ");
                String newResult = scanner.nextLine();
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(getService(targetLabRequest)));
                    String row = reader.readLine();

                    while(row != null){
                        base.append(row).append(System.lineSeparator());
                        row = reader.readLine();
                    }

                    String newFile = base.toString().replaceAll(
                            "(?i)" + info[0] + ";" + info[1] + ";" + info[2] + ";" +
                                    info[3] + ";" + info[4] + ";", info[0] + ";" + info[1] + ";" + info[2] + ";" + info[3] +
                                    ";" + newResult + ";"
                    );

                    FileWriter writer = new FileWriter(getService(targetLabRequest));
                    writer.write(newFile);
                    reader.close();
                    writer.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                System.out.println("Laboratory Request " + targetLabRequest + "was edited successfully.");

                exitLoop = true;


            }else if (Character.toUpperCase(choice) == 'N') {

                System.out.println("returning to main menu");
                return;

            }else {

                System.out.print("INPUT UNIDENTIFIED, please try again: ");
            }
        } while(!exitLoop);

        System.out.print("Do you want to edit another request? Y/N: ");

        char input;
        boolean inputValid;
        do {
            input = scanner.next().charAt(0);
            scanner.nextLine();
            input = Character.toUpperCase(input);
            if(input == 'Y')
                inputValid = false;
            else if(input == 'N')
                return;
            else{
                System.out.print("Input error, please try again: ");
                inputValid = true;
            }
        } while(inputValid);

        editLabRequest();
    }

    public void manageLabRequests(){

        char input;
        boolean inputValid;
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MANAGE LABORATORY REQUESTS =====");

        do{
            System.out.println("""
                    [1] ADD NEW Laboratory Requests
                    [2] SEARCH Laboratory Request
                    [3] DELETE Laboratory Request
                    [4] EDIT Laboratory Request
                    [X] RETURN TO MAIN MENU
                    """);

            input = scanner.next().charAt(0);
            input = Character.toUpperCase(input);

            switch (input){
                case '1' -> {
                    //ADD LAB REQ
                    inputValid = true;
                }

                case '2' -> {
                    searchLabRequest();
                    inputValid = true;
                }

                case '3' -> {
                    //DELETE LAB REQ
                    inputValid = true;
                }

                case '4' -> {
                    editLabRequest();
                    inputValid = true;
                }

                case 'X' -> {
                    inputValid = true;
                    System.out.println("Returning to main menu.");
                }
                default -> {
                    System.out.println("Input unidentified, please try again: ");
                    inputValid = false;
                }

            }

        } while(!inputValid);
    }
}
