import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Service {
    private String serviceCode;
    private String description;
    private int price;

    Scanner scanner = new Scanner(System.in);

    public void addService() {
        boolean Loop;
        boolean inLoop;
        do {
            System.out.println("===== ADD SERVICES =====");
            System.out.println("Please input the following details for the service");

            System.out.print("Service Code: ");
            setServiceCode(scanner.nextLine());

            System.out.print("Description: ");
            setDescription(scanner.nextLine());

            System.out.print("Price: ");
            setPrice(scanner.nextInt());

            System.out.println("Input Details: ");
            System.out.println("Service Code: " + getServiceCode());
            System.out.println(("Description: " + getDescription()));
            System.out.println("Price: " + getPrice());

            do {
                System.out.print("Would you like to save this service? [Y/N]");
                char input = scanner.next().charAt(0);
                scanner.nextLine();
                input = Character.toUpperCase(input);

                if (input == 'Y') {
                    System.out.println("Saving.");
                    try {
                        File serviceFile = new File("Services.txt");
                        if (serviceFile.createNewFile()) {
                            System.out.println("File doesn't exist. Making file.");
                        } else {
                            System.out.println("File exists. Opening");
                        }
                        try {
                            FileWriter writer = new FileWriter(serviceFile, true);
                            PrintWriter pw = new PrintWriter(writer);
                            pw.println(serviceCode + ";" + description + ";" + price + ";");
                            writer.close();
                            System.out.println(serviceCode + " " + description + " has been added");
                        } catch (IOException e) {
                            System.out.println("An error occurred in saving");
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred in creating/opening file");
                        e.printStackTrace();
                    }
                    inLoop = true;
                } else if (input == 'N') {
                    System.out.println("Saving Cancelled");
                    inLoop = true;
                } else {
                    System.out.println("User input unrecognizable");
                    inLoop = false;
                }
            }while(!inLoop);

            System.out.print("Do you want to add another service? [Y/N]");
            char serRepeat = scanner.next().charAt(0);
            if(serRepeat == 'Y' || serRepeat == 'y'){
                Loop = false;
            }else if(serRepeat == 'N' || serRepeat == 'n'){
                Loop = true;
            }else{
                System.out.println("Input either Y or N");
                Loop = false;
            }
        }while(!Loop);
    }

    public String searchService(){

        System.out.print("Search for Services (You may use the Service Code or Keyword): ");
        String input = scanner.nextLine();
        ArrayList<String> multipleService = new ArrayList<>();
        String finalService = null;
        int multipleCount = 0;
        boolean serviceFound = false;
        try {

            Scanner fileScanner = new Scanner(new File("Services.txt"));
            while(fileScanner.hasNext()){

                String line = fileScanner.nextLine();
                if(line.contains(input)){

                    multipleService.add(line);
                    finalService = line;
                    serviceFound = true;
                    ++multipleCount;
                }
            }

            if(multipleCount > 1){

                String[] serviceDetails;
                serviceFound = false;
                System.out.println("Multiple records found!");
                System.out.printf("%10s %30s %10s", "Service Code", "Description", "Price");
                System.out.println();
                for(int i = 0; i < multipleService.size(); i++) {
                    serviceDetails = multipleService.get(i).split(";");
                    System.out.printf("%10s %30s %10s", serviceDetails[0], serviceDetails[1], serviceDetails[2]);
                    System.out.println();
                }
                System.out.print("Please type the Service Code that you want to access:");
                do{

                    String inputService = scanner.nextLine();
                    for(String currentService : multipleService){

                        if(currentService.contains(inputService.toUpperCase(Locale.ROOT))){

                            finalService = currentService;
                            serviceFound = true;
                            break;
                        }
                    }
                    if(!serviceFound)
                        System.out.print("Service not found, please try again: ");

                } while(!serviceFound);
            }

            if(!serviceFound){

                System.out.println("Service does not exist in any records. Do you want to look again?");
                System.out.println("(Y)es - Look again");
                System.out.println("(N)o  - Return to main menu");
                System.out.print("Input: ");

                boolean exitLoop;
                do {
                    char choice = scanner.next().charAt(0);
                    scanner.nextLine();

                    if (Character.toUpperCase(choice) == 'Y') {

                        finalService = searchService();
                        exitLoop = true;
                    }else if (Character.toUpperCase(choice) == 'N') {

                        System.out.println("returning to main menu");
                        return null;

                    }else {

                        System.out.print("INPUT UNIDENTIFIED, please try again: ");
                        exitLoop = false;
                    }
                } while(!exitLoop);
            } else{
                String[] serviceDetails = finalService.split(";");
                System.out.println("SERVICE FOUND!");
                System.out.println("SERVICE UID: " + serviceDetails[0]);
                System.out.println("DESCRIPTION: " + serviceDetails[1]);
                System.out.println("PRICE: " + serviceDetails[2]);
            }
        } catch(IOException e) {

            e.printStackTrace();
        }

        return finalService;
    }

    //DELETE SERVICE
    public void deleteService(){

        char input;

        String deleteReason = null;
        StringBuilder inputBuffer = new StringBuilder();
        String temp;
        boolean inputValid;
        boolean noErrors;

        System.out.println("===== DELETING PATIENT RECORD =====");
        String targetService = searchService();
        if(targetService == null)
            return;

        System.out.print("Do you want to delete the service? Y/N: ");

        boolean check;
        do {

            check = false;

            char deletePrompt = scanner.next().charAt(0);
            scanner.nextLine();
            deletePrompt = Character.toUpperCase(deletePrompt);

            if (deletePrompt == 'Y') {

                System.out.print("Please state the reason for deleting the service: ");
                deleteReason = scanner.nextLine();
                check = true;

            } else if (deletePrompt == 'N') {

                System.out.println("Returning to main menu.");
                check = true;
            } else {

                System.out.print("Input unrecognizable, please try again: ");
            }
        } while (!check);

        try{
            Scanner fileScanner = new Scanner(new File("Services.txt"));
            while (fileScanner.hasNext()) {

                temp = fileScanner.nextLine();
                if (temp.contains(targetService)) {
                    temp = temp + "D;" + deleteReason + ";";
                }
                inputBuffer.append(temp);
                inputBuffer.append('\n');
            }
            String inputString = inputBuffer.toString();

            fileScanner.close();

            try{
                FileWriter fileWriter = new FileWriter("Services.txt");
                fileWriter.write(inputString);
                fileWriter.close();

                System.out.println(targetService.substring(0, 3) + " has been deleted.");
                return;
                
            } catch(IOException e){
                System.out.println("UNEXPECTED ERROR. Returning to first step.");
                e.printStackTrace();
                
            }
        }catch(IOException e){
            System.out.println("UNEXPECTED ERROR. Returning to first step.");
            e.printStackTrace();

        }
        deleteService();
    }

    public void editService(){

        String targetService = null;
        char choice;

        System.out.println("===== EDIT SERVICE =====");
        System.out.println("Note (PLEASE READ):");
        System.out.print("""
                The services cannot be edited. If you would like to edit an existing service,
                the service will be deleted first, and a new service will be created.
                Would you like to continue (Y/N):\s""");

        boolean scannerConfirm;
        do{
            choice = scanner.next().charAt(0);
            scanner.nextLine();
            choice = Character.toUpperCase(choice);
            if(choice == 'Y'){
                deleteService();
                addService();
                scannerConfirm = true;
            } else if (choice == 'N'){
                System.out.println("Returning to main menu.");
                scannerConfirm = true;
            } else {
                System.out.print("INPUT ERROR: not identified. Please try again: ");
                scannerConfirm = false;
            }
        } while(!scannerConfirm);
    }

    public void manageServiceRecords(){

        char input;
        boolean inputValid = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MANAGE SERVICES =====");

        do{
            System.out.print("""
                    [1] ADD NEW Service
                    [2] EDIT Service
                    [3] DELETE Service
                    [4] SEARCH Service
                    [X] RETURN TO MAIN MENU
                                        
                    Select a transaction:\040""");

            input = scanner.next().charAt(0);
            input = Character.toUpperCase(input);
            switch(input){
                //ADD CASE
                case '1' -> {
                    inputValid = true;
                    addService();
                }

                //EDIT CASE
                case '2' -> {
                    inputValid = true;
                    editService();
                }

                //DELETE CASE
                case '3' -> {
                    boolean repeatService = false;
                    do {
                        deleteService();
                        System.out.print("Do you want to delete another service? Y/N: ");
                        do {
                            input = scanner.next().charAt(0);
                            scanner.nextLine();
                            input = Character.toUpperCase(input);
                            if(input == 'Y') {
                                repeatService = true;
                                inputValid = true;
                            }else if(input == 'N')
                                repeatService = false;
                            else{
                                System.out.print("Input error, please try again: ");
                                inputValid = false;
                            }
                        } while(inputValid);
                    } while(repeatService);
                }

                //SEARCH CASE
                case '4' -> {
                    inputValid = true;
                    searchService();
                }

                //EXIT CASE
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

    public String getServiceCode() {
        return serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
