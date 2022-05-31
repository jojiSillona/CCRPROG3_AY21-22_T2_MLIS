package com.mlis.managers;

import java.io.*;
import java.util.*;

public class Patient {

    private String patientUID;
    private String firstName;
    private String lastName;
    private String middleName;
    private int birthday;
    private char gender;
    private String address;
    private String phoneNumber;
    private String nationalIDNum;

    Scanner scanner = new Scanner(System.in);
    /**
     * A SUB-FUNCTION that returns a String containing all the information of target patient
     * @return String if the function finds the target patient successfully or null if unsuccessful or user quit
     */
    public String searchPatient() {

        String finalRecord = null;
        boolean recordFound = false;
        int multipleCount = 0;
        ArrayList<String> multipleEntries = new ArrayList<>();

        System.out.println("Select the preferred choice of search:");
        System.out.println("""
                1. PATIENT UID
                2. LAST NAME, FIRST NAME, BIRTHDAY
                3. NATIONAL ID""");
        System.out.print("Choice: ");

        int choice;
        String[] keyword = {"", "", ""};

        do {

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {

                case 1 -> {

                    System.out.print("Please type the Patient.txt's UID: ");
                    keyword[0] = scanner.nextLine();
                }
                case 2 -> {

                    System.out.print("Please type the Patient.txt's last name: ");
                    keyword[0] = scanner.nextLine();
                    System.out.print("Please type the Patient.txt's first name: ");
                    keyword[1] = scanner.nextLine();
                    System.out.print("Please type the Patient.txt's birthday in format \"YYYYMMDD\": ");
                    keyword[2] = Integer.toString(scanner.nextInt());
                    scanner.nextLine();
                }
                case 3 -> {

                    System.out.print("Please type the Patient.txt's National ID: ");
                    keyword[0] = scanner.nextLine();
                }
                default -> System.out.print("You have inputted the wrong number! Please try again: ");
            }
        } while (choice > 3 || choice < 1);

        try {

            System.out.println("Searching for records");
            Scanner fileScanner = new Scanner(new File("Patient.txt.txt"));

            while (fileScanner.hasNext()) {

                String line = fileScanner.nextLine().toUpperCase(Locale.ROOT);
                if (choice != 2) {

                    if (line.contains(keyword[0].toUpperCase(Locale.ROOT))) {
                        finalRecord = line;
                        recordFound = true;
                        break;
                    }
                } else {

                    for (String item : keyword) {
                        if (line.contains(item)) {
                            finalRecord = line;
                            multipleEntries.add(line);
                            ++multipleCount;
                            recordFound = true;
                            break;
                        }
                    }

                }
            }

            if (multipleCount > 1) {
                String[] patientDetails;
                recordFound = false;
                System.out.println("MULTIPLE RECORDS FOUND");
                System.out.printf("%13s %12s %12s %12s %10s %7s %30s %13s %15s", "Patient.txt's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");
                System.out.println();
                for (String entry : multipleEntries) {
                    patientDetails = entry.split(";");
                    System.out.printf("%13s %12s %12s %12s %10s %7s %30s %13s %15s", patientDetails[0], patientDetails[1], patientDetails[2], patientDetails[3], patientDetails[4], patientDetails[5], patientDetails[6], patientDetails[7], patientDetails[8]);
                    System.out.println();
                }
                System.out.print("Please type the Patient.txt's UID of your desired record: ");
                do {
                    String inputUID = scanner.nextLine();
                    for (String multipleEntry : multipleEntries) {
                        if (multipleEntry.contains(inputUID)) {
                            finalRecord = multipleEntry;
                            recordFound = true;
                            break;
                        }
                    }
                    if (!recordFound)
                        System.out.print("Input not found, please try again: ");
                } while (!recordFound);

            }

            if (!recordFound) {

                System.out.println("No record has been found. Try again?");
                System.out.println("(Y)es - Look for another record.\n" +
                        "(N)o  - Return to main menu.");
                System.out.print("In: ");

                boolean check;

                do {

                    char returnAnswer = scanner.next().charAt(0);
                    scanner.nextLine();
                    returnAnswer = Character.toUpperCase(returnAnswer);

                    if (returnAnswer == 'Y') {

                        finalRecord = searchPatient();
                        check = true;
                    } else if (returnAnswer == 'N') {

                        System.out.println("Returning to main menu");
                        return null;
                    } else {

                        System.out.println("Input unidentified, please try again: ");
                        check = false;
                    }
                } while (!check);
            } else {
                String[] patientDetails = finalRecord.split(";");
                /* ARRAY GUIDELINES
                0 - Patient.txt UID
                1 - Last Name
                2 - First Name
                3 - Middle Name
                4 - Birthday
                5 - Gender
                6 - Address
                7 - Phone Number
                8 - National ID Number
                 */
                System.out.println("RECORD FOUND!");
                System.out.println("PATIENT UID: " + patientDetails[0]);
                System.out.println("NAME: " + patientDetails[1] + ", " + patientDetails[2] + " " + patientDetails[3]);
                System.out.println("BIRTHDAY: " + patientDetails[4]);
                System.out.println("ADDRESS: " + patientDetails[6]);
                System.out.println("PHONE NUMBER: " + patientDetails[7]);
                System.out.println("NATIONAL ID NUMBER: " + patientDetails[8]);
            }
        } catch (IOException e) {
            System.out.println("Program Error. Aborting actions.");
        }

        return finalRecord;
    }

    /**
     * Outputs a new UID if user creates a new record
     * @return String new UID
     */
    public String generateUID() {

        //DATE RELATED UID ELEMENTS
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString = String.format("%02d", month);
        String UID = "";

        //INCREMENTING UID ELEMENTS
        try {
            BufferedReader f = new BufferedReader(new FileReader("Patient.txt.txt"));
            if (f.readLine() == null)
                UID = "AAA00";
            else {
                System.out.println("GENERATING NEW UID");
                try {
                    String currentLine;
                    String lastLine = "";

                    BufferedReader br = new BufferedReader(new FileReader("Patient.txt.txt"));

                    while ((currentLine = br.readLine()) != null)
                        lastLine = currentLine;

                    UID = lastLine.substring(7, 12);
                    StringBuilder builder = new StringBuilder(UID);
                    int i = builder.length() - 1;

                    char minDigit;
                    char maxDigit;

                    while (i >= 0) {

                        if (i >= 3) {
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

        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {

            System.out.println("Resetting incrementor");
            UID = "AAA00";
        }

        return "P" + year + monthString + UID;
    }

    /**
     * A MAIN FUNCTION that adds patients to Patient.txt.txt
     */
    public void addNewPatient() {

        boolean correctFormatGiven;

        System.out.println("===== ADD NEW PATIENT =====");
        System.out.println("Please input the following details of the patient.");

        System.out.print("1. FIRST NAME : ");
        setFirstName(scanner.nextLine());

        System.out.print("2. LAST NAME : ");
        setLastName(scanner.nextLine());

        System.out.print("3. MIDDLE NAME (type \"NA\" if you do not have a middle name) : ");
        String midNameIn = scanner.nextLine();
        if (midNameIn.equalsIgnoreCase("na"))
            setMiddleName("");
        else
            setMiddleName(midNameIn);

        System.out.print("4. BIRTHDAY (using format \"YYYYMMDD\"): ");
        do {

            int bDayIn = scanner.nextInt();
            if (Integer.toString(bDayIn).length() == 8) {

                setBirthday(bDayIn);
                correctFormatGiven = true;
            } else {

                System.out.print("""
                        It seems that you didn't follow the format.
                        Please input your birthday again and make sure that you follow
                        the format "YYYYMMDD" :\s""");
                correctFormatGiven = false;
            }
        } while (!correctFormatGiven);

        System.out.print("5. GENDER (M/F) : ");
        do {

            char genderIn = scanner.next().charAt(0);
            scanner.nextLine();
            genderIn = Character.toUpperCase(genderIn);
            if (genderIn == 'M' || genderIn == 'F') {
                setGender(genderIn);
                correctFormatGiven = true;
            } else {
                System.out.print("""
                        It seems that you didn't follow the format.
                        Please make sure that you use either M or F.
                        Please input your gender again:\s""");
                correctFormatGiven = false;
            }

        } while (!correctFormatGiven);

        System.out.print("6. ADDRESS : ");
        setAddress(scanner.nextLine());

        System.out.print("7. PHONE NUMBER (do not include 0 as the first digit) : ");
        do {
            String phoneIn = scanner.nextLine();
            if (phoneIn.length() == 10) {

                setPhoneNumber(phoneIn);
                correctFormatGiven = true;
            } else {

                System.out.print("""
                        It seems that you didn't follow the format.
                        Phone number should not include "0" at the start
                        and therefore, should include only 10 digits.
                        Please try again :\s""");
                correctFormatGiven = false;
            }
        } while (!correctFormatGiven);

        System.out.print("8. NATIONAL ID NUMBER (Any National ID can be used) : ");
        setNationalIDNum(scanner.nextLine());

        System.out.println("Here are the following details: ");
        System.out.println("1. FIRST NAME: " + getFirstName());
        System.out.println("2. LAST NAME: " + getLastName());
        System.out.println("3. MIDDLE NAME: " + getMiddleName());
        System.out.println("4. BIRTHDAY: " + getBirthday());
        System.out.println("5. GENDER: " + getGender());
        System.out.println("6. ADDRESS: " + getAddress());
        System.out.println("7. PHONE NUMBER: " + getPhoneNumber());
        System.out.println("8. NATIONAL ID NUMBER: " + getNationalIDNum());

        System.out.println("Would you like to save thew patient's information? [Y/N] : ");
        char input = scanner.next().charAt(0);
        scanner.nextLine();
        input = Character.toUpperCase(input);

        if (input == 'Y') {

            System.out.println("Saving.");
            try {

                File patientFile = new File("Patient.txt.txt");
                if (patientFile.createNewFile())
                    System.out.println("File doesn't exist. Making file.");
                else
                    System.out.println("File exists. Opening");
                try {

                    FileWriter writer = new FileWriter(patientFile, true);
                    PrintWriter pw = new PrintWriter(writer);
                    patientUID = generateUID();
                    pw.println(patientUID + ";" + lastName + ";" + firstName + ";" + middleName + ";"
                            + birthday + ";" + gender + ";" + address + ";" + phoneNumber + ";" + nationalIDNum + ";");
                    writer.close();
                    System.out.println("Save Successful");
                } catch (IOException e) {

                    System.out.println("An error occurred in saving");
                    e.printStackTrace();
                }
            } catch (IOException e) {

                System.out.println("An error occurred in creating/opening file");
                e.printStackTrace();
            }
        } else if (input == 'N')
            System.out.println("Saving Cancelled");
        else
            System.out.println("User input unrecognizable");
    }

    /**
     * A MAIN FUNCTION that concatenates delete status to an existing line in Patient.txt.txt
     */
    public void deletePatient() {

        char input;

        String deleteReason = null;
        StringBuilder inputBuffer = new StringBuilder();
        String temp;
        boolean inputValid;
        boolean noErrors;

        System.out.println("===== DELETING PATIENT RECORD =====");
        String targetPatient = searchPatient();
        if(targetPatient == null)
            return;


        System.out.print("Do you want to delete the record? Y/N: ");

        boolean check;
        do {

            check = false;

            char deletePrompt = scanner.next().charAt(0);
            scanner.nextLine();
            deletePrompt = Character.toUpperCase(deletePrompt);

            if (deletePrompt == 'Y') {

                System.out.print("Please state the reason for deleting the record: ");
                deleteReason = scanner.nextLine();
                check = true;

            } else if (deletePrompt == 'N') {

                System.out.println("Returning to main menu.");
                check = true;
            } else {

                System.out.print("Input unrecognizable, please try again: ");
            }
        } while (!check);

        try {
            Scanner fileScanner = new Scanner(new File("Patient.txt.txt"));
            while (fileScanner.hasNext()) {

                temp = fileScanner.nextLine();
                if (temp.contains(targetPatient)) {
                    temp = temp + "D;" + deleteReason + ";";
                }
                inputBuffer.append(temp);
                inputBuffer.append('\n');
            }
            String inputString = inputBuffer.toString();

            fileScanner.close();

            try {
                FileWriter fileWriter = new FileWriter("Patient.txt.txt");
                fileWriter.write(inputString);
                fileWriter.close();

                System.out.println("Data of Patient.txt " + targetPatient.substring(0, 12) + " has been deleted.");
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

        deletePatient();
    }

    /* A MAIN FUNCTION that searches for and displays patient details.*/
    /*Details requiring Lab Service functions & PDF printing unavailable*/
    public void searchAndDisplay(){
        boolean printLoop;
        int count = 1;
        String targetDisplay = searchPatient();
        String[] data = targetDisplay.split(";");
        System.out.println("Patient.txt's UID:   " + data[0]);
        System.out.println("Name:            " + data[1] + "," + data[2] + " " + data[3]);
        System.out.println("Birthday:        " + data[4]);
        System.out.println("Address:         " + data[6]);
        System.out.println("Phone Number:    " + data[7]);
        System.out.println("National ID no.: " + data[8]);

        System.out.printf("%-20s %-20s %-20s %-20s %n",
                "Request's UID", "Lab Test Type", "Request Date", "Result");
        System.out.printf("%-20s %-20s %-20s %-20s %n",
                "FUNCTION", "IS", "CURRENTLY", "UNAVAILABLE");
        do {
            if(count == 1) {
                System.out.print("Do you want to print a laboratory test result? [Y/N]");
            }else if(count > 1) {
                System.out.print("Do you want to print another laboratory test result? [Y/N]");
            }
            int printLabRes = scanner.next().charAt(0);
            scanner.nextLine();
            if(printLabRes == 'Y' || printLabRes == 'y'){
                System.out.print("Please input the requested patient's UID:");
                String requestUID = scanner.nextLine();
                System.out.println("PDF Generation Unavailable...");
                printLoop = false;
            }else if(printLabRes == 'N' || printLabRes == 'n'){
                printLoop = true;
                System.out.println("Returning to main menu...");
            }else{
                System.out.println("Please input either Y or N");
                printLoop = false;
            }
            count++;
        }while(!printLoop);
    }

    /*A MAIN FUNCTION that updates a chosen record*/
    public void editPatient() {
        boolean breakloop; // Address or Phone Number Loop
        boolean miniLoop; // Y/N Loop
        boolean megaLoop; //Edit another record? Loop

        do{
            System.out.println("===== EDIT PATIENT RECORD =====");
            StringBuilder base = new StringBuilder();
            BufferedReader reader;
            FileWriter writer;
            String editString = searchPatient();
            String[] info = editString.split(";");


            do{
                System.out.println("""
                        Which record would you like to update?
                        [1] Address
                        [2] Phone Number""");

                System.out.print("Choice: ");
                int editPick = scanner.nextInt();
                scanner.nextLine();

                if (editPick == 1) {
                    System.out.print("Please enter the new address: ");
                    String newAddress = scanner.nextLine();

                    try{
                        reader = new BufferedReader(new FileReader("Patient.txt.txt"));
                        String row = reader.readLine();
                        while (row != null)
                        {
                            base.append(row).append(System.lineSeparator());
                            row = reader.readLine();
                        }

                        String newFile = base.toString().replaceAll("(?i)" + info[0] + ";" + info[1] + ";" + info[2] + ";" +
                                info[3] + ";" + info[4] + ";" + info[5] + ";" + info[6] + ";" + info[7] + ";" + info[8] +
                                ";", info[0] + ";" + info[1] + ";" + info[2] + ";" + info[3] + ";" + info[4] +
                                ";" + info[5] + ";" + newAddress + ";" + info[7] + ";" + info[8] + ";");

                        writer = new FileWriter("Patient.txt.txt");
                        writer.write(newFile);
                        reader.close();
                        writer.close();
                    }catch(IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("The Address of patient " + info[0] + " has been updated.");

                    breakloop = true;
                } else if (editPick == 2) {
                    System.out.print("Please enter the new phone number: ");
                    String newNumber = scanner.nextLine();

                    try{
                        reader = new BufferedReader(new FileReader("Patient.txt.txt"));
                        String row = reader.readLine();
                        while (row != null)
                        {
                            base.append(row).append(System.lineSeparator());
                            row = reader.readLine();
                        }

                        String newFile = base.toString().replaceAll("(?i)" + info[0] + ";" + info[1] + ";" + info[2] + ";" +
                                info[3] + ";" + info[4] + ";" + info[5] + ";" + info[6] + ";" + info[7] + ";" + info[8] +
                                ";", info[0] + ";" + info[1] + ";" + info[2] + ";" + info[3] + ";" + info[4] +
                                ";" + info[5] + ";" + info[6] + ";" + newNumber + ";" + info[8] + ";");

                        writer = new FileWriter("Patient.txt.txt");
                        writer.write(newFile);
                        reader.close();
                        writer.close();
                    }catch(IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("The Phone Number of patient " + info[0] + " has been updated.");

                    breakloop = true;
                } else {
                    System.out.println("Please input valid option");
                    breakloop = false;
                }

                do{
                    System.out.print("Do you want to edit another patient record? [Y/N]");
                    char editRepeat = scanner.next().charAt(0);
                    if(editRepeat == 'Y' || editRepeat == 'y'){
                        miniLoop = true;
                        megaLoop = false;
                    }else if(editRepeat == 'N' || editRepeat == 'n'){
                        miniLoop = true;
                        megaLoop = true;
                    }else{
                        System.out.println("Input either Y or N");
                        miniLoop = false;
                        megaLoop = true;
                    }
                }while(!miniLoop);

            } while (!breakloop);

        }while(!megaLoop);
    }

    /**
     * A MAIN FUNCTION that compiles all functions into one
     */
    public void managePatientRecords(){

        char input;
        boolean inputValid;
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MANAGE PATIENT RECORDS =====");

        do{
            System.out.print("""
                    [1] ADD NEW Patient.txt
                    [2] EDIT Patient.txt  Record
                    [3] DELETE Patient.txt Record
                    [4] SEARCH Patient.txt Record
                    [X] RETURN TO MAIN MENU
                                        
                    Select a transaction:\040""");

            input = scanner.next().charAt(0);
            input = Character.toUpperCase(input);
            switch(input){
                //ADD CASE
                case '1' -> {
                    inputValid = true;
                    addNewPatient();
                }

                //EDIT CASE
                case '2' -> {
                    editPatient();
                    inputValid = true;
                }

                //DELETE CASE
                case '3' -> {

                    deletePatient();
                    inputValid = true;
                }

                //SEARCH CASE
                case '4' -> {
                    searchAndDisplay();
                    inputValid = true;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getBirthday() {
        return birthday;
    }

    public char getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNationalIDNum() {
        return nationalIDNum;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNationalIDNum(String nationalIDNum) {
        this.nationalIDNum = nationalIDNum;
    }
}