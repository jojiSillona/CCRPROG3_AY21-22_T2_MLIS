import java.util.Scanner;

public class Driver {
    Patient patient = new Patient();
    Service service = new Service();

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
          Laboratory Results Management is not available
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
                // Cut off due to lack of time.
                // case '3' -> {
                // restartProgram = true;
                // }

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
        SoftwareManager softwareManager = new SoftwareManager();
        softwareManager.showScreen();
        Driver driver = new Driver();
        driver.mainProgram();
    }
}