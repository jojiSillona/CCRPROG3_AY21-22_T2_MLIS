import java.io.*;
import java.util.*;

public class LabRequest {
    private String requestUID;
    private int requestDate;
    private int requestTime;
    private String result;

    Scanner scanner = new Scanner(System.in);

    public void addLabRequest(){
        String breakloop = null;
        try {
            do {
                System.out.println("===== ADD LABORATORY REQUEST =====");
                System.out.println("Please enter the following details.");
                Scanner sc = new Scanner(System.in);
                //Enter Patient's UID and Service Code
                System.out.print("Patient's UID:");
                String patientUID = sc.nextLine();
                System.out.print("Service Code:");
                String serviceCode = sc.nextLine();
                boolean flag = false;
                Scanner sc2 = new Scanner(new FileInputStream("Patient.txt"));
                while (sc2.hasNextLine()) {
                    String line = sc2.nextLine();
                    System.out.println(line);
                    if (line.contains(patientUID)) {
                        flag = true;
                    }
                }
                if (flag) {
                    System.out.println("Patient's record exists. Checking Services");
                    boolean flag2 = false;
                    Scanner sc3 = new Scanner(new FileInputStream("Services.txt"));
                    while (sc3.hasNextLine()) {
                        String line2 = sc3.nextLine();
                        System.out.println(line2);
                        if (line2.contains(serviceCode)) {
                            flag2 = true;
                        }
                    }
                    if (flag2) {
                        System.out.println("Service Code also exists.");

                        //DATE RELATED UID ELEMENTS
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        String monthString = String.format("%02d", month);
                        String dayString = String.format("%02d", day);
                        String UID = "";
                        String lastLine = "";

                        //INCREMENTING UID ELEMENTS
                        try {
                            BufferedReader f = new BufferedReader(new FileReader("Patient.txt"));
                            if (f.readLine() == null)
                                UID = "AAA00";
                            else {
                                System.out.println("GENERATING NEW UID");
                                try {
                                    String currentLine;


                                    BufferedReader br = new BufferedReader(new FileReader("Patient.txt"));

                                    while ((currentLine = br.readLine()) != null)
                                        lastLine = currentLine;

                                    UID = lastLine.substring(7, 12);

                                    StringBuilder builder = new StringBuilder(UID);
                                    int i = builder.length() - 1;

                                    char minDigit;
                                    char maxDigit;

                                    while (i >= 0) {

                                        if (i >= 2) {
                                            minDigit = '0';
                                            maxDigit = '9';
                                        } else {
                                            minDigit = 'A';
                                            maxDigit = 'Z';
                                        }

                                        char c = builder.charAt(i);
                                        c++;

                                        if (c > maxDigit) {
                                            builder.setCharAt(i, minDigit);
                                            i--;
                                            continue;
                                        }

                                        builder.setCharAt(i, c);
                                        UID = builder.toString();
                                        break;
                                    }

                                    UID = builder.toString();

                                } catch (IOException e) {

                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Program error. Aborting program.");
                        }

                        if (calendar.get(Calendar.MONTH) > Integer.parseInt(lastLine.substring(5, 7))) {

                            System.out.println("Resetting incrementor");
                            UID = "AAA00";
                        }

                        String LabUID = serviceCode + year + monthString + dayString + UID;
                        System.out.println(LabUID);

                    } else {
                        System.out.println("Error...Input Service Code does not exist.");
                        Scanner sc4 = new Scanner(System.in);
                        System.out.print("Would you like to search again?[Y/N]:");
                        breakloop = sc4.nextLine();
                    }
                } else {
                    System.out.println("Error...Patient's record does not exist");
                    Scanner sc3 = new Scanner(System.in);
                    System.out.print("Would you like to search again?[Y/N]:");
                    breakloop = sc3.nextLine();
                }
            } while (!Objects.equals(breakloop, "N"));
        }catch(IOException e){
            System.out.println("Error...Error");
        }
    }
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

    public void deleteLabRequest(){
        char input;
        String deleteReason = null;
        StringBuilder inputBuffer = new StringBuilder();
        String temp;
        boolean inputValid;
        boolean noErrors;

        System.out.println("===== DELETE LABORATORY REQUEST =====");
        String targetLabRequest = searchLabRequest();
        if(targetLabRequest== null)
            return;

        System.out.print("Do you want to delete the lab request? Y/N: ");

        boolean check;
        do{
            check = false;
            char deletePrompt = scanner.next().charAt(0);
            scanner.nextLine();
            deletePrompt = Character.toUpperCase(deletePrompt);
            if (deletePrompt == 'Y') {

                System.out.print("Please state the reason for deleting the request: ");
                deleteReason = scanner.nextLine();
                check = true;

            } else if (deletePrompt == 'N') {

                System.out.println("Returning to main menu.");
                check = true;
            } else {

                System.out.print("Input unrecognizable, please try again: ");
            }
        }while (!check);

        try {
            Scanner fileScanner = new Scanner(new File("Patient.txt")); //CHANGE FILE NAME
            while (fileScanner.hasNext()) {

                temp = fileScanner.nextLine();
                if (temp.contains(targetLabRequest)) {
                    temp = temp + "D;" + deleteReason + ";";
                }
                inputBuffer.append(temp);
                inputBuffer.append('\n');
            }
            String inputString = inputBuffer.toString();

            fileScanner.close();

            try {
                FileWriter fileWriter = new FileWriter("Patient.txt"); //CHANGE FILE NAME
                fileWriter.write(inputString);
                fileWriter.close();

                System.out.println("Data of Lab Request " + targetLabRequest.substring(0, 12) + " has been deleted."); //CHANGE FILE NAME
                noErrors = true;

            } catch (IOException e) {
                System.out.println("UNEXPECTED ERROR. Returning to first step.");
                e.printStackTrace();
                noErrors = false;
            }
        } catch (IOException e) {
            System.out.println("UNEXPECTED ERROR. Returning to first step.");
            e.printStackTrace();
            noErrors = false;
        }

        System.out.print("Do you want to delete another patient? Y/N: ");

        if(noErrors) do {
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

        deleteLabRequest();
    }

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
                    
                    Select a transaction:
                    """);

            input = scanner.next().charAt(0);
            input = Character.toUpperCase(input);

            switch (input){
                case '1' -> {
                    addLabRequest();
                    inputValid = true;
                }

                case '2' -> {
                    searchLabRequest();
                    inputValid = true;
                }

                case '3' -> {
                    deleteLabRequest();
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