import java.util.Scanner;

public class Driver {
    Patient patient = new Patient();
    Service service = new Service();
    LabRequest labRequest = new LabRequest();

    public void tests(){
        //patient.generateUID();
        labRequest.searchLabRequest();
    }

    public void mainProgram() {
        char input;
        boolean inputValid = false;
        boolean restartProgram = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println(" ===== MEDICAL LABORATORY INFORMATION SYSTEM ===== ");

        do {
            System.out.print("""
          [1] Manage PATIENT RECORDS
          [2] Manage SERVICES
          [3] Manage LABORATORY RESULTS
          [X] Exit

          Select a transaction:\040""");

            input = scanner.next().charAt(0);
            scanner.nextLine();
            input = Character.toUpperCase(input);

            switch (input) {
                // PATIENT CASE
                case '1' -> {
                    patient.managePatientRecords();
                }

                // SERVICE CASE
                case '2' -> {
                    service.manageServiceRecords();
                }

                // LAB RESULT CASE
                 case '3' -> {
                     labRequest.manageLabRequests();
                 }

                case 'X' -> {
                    System.out.print("Are you sure that you want to exit? Y/N: ");
                    do {
                        input = scanner.next().charAt(0);
                        scanner.nextLine();
                        input = Character.toUpperCase(input);
                        if (input == 'Y')
                            System.exit(0);
                        else if (input == 'N') {
                            inputValid = true;
                        } else {
                            System.out.print("Input unidentified, please try again: ");
                        }
                    } while (!inputValid);
                }
                default -> {
                    System.out.print("INPUT ERROR. Program did not identify. Please try again: ");
                }
            }
        } while (true);
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.mainProgram();
        //driver.tests();
    }
}