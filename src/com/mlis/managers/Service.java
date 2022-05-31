package com.mlis.managers;

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
                System.out.println("Would you like to save this service?");
                char input = scanner.next().charAt(0);
                scanner.nextLine();
                input = Character.toUpperCase(input);

                if (input == 'Y') {
                    System.out.println("Saving.");
                    try {
                        File serviceFile = new File("file services.txt");
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
                            System.out.println(serviceCode + description + "has been added");
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

                String[] serviceDetails = new String[0];
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
            }
        } catch(IOException e) {

            e.printStackTrace();
        }

        return finalService;
    }

    //DELETE SERVICE
    //TODO: Restore DELETE SERVICES

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
                targetService = searchService();
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
        boolean inputValid;
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
                    inputValid = true;
                    //deleteService();
                    //where's deleteService()?
                }

                //SEARCH CASE
                case '4' -> {
                    inputValid = true;
                    String outputService = searchService();
                    if(outputService != null){
                        String[] displayList = outputService.split(";");

                        System.out.println("SERVICE FOUND!");

                        for(String displayLine : displayList){
                            System.out.println(displayLine);
                        }

                        System.out.println("Returning to main menu.");
                    }
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
