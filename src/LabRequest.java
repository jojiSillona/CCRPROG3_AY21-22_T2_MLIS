import java.util.ArrayList;
import java.util.Scanner;

public class LabRequest {
    private String requestUID;
    private String patientUID;
    private int requestDate;
    private int requestTime;
    private String result;

    Scanner scanner = new Scanner(System.in);

    //ADD LAB REQUEST HERE

    public void searchLabRequest(){
        String finalRequest = null;
        boolean requestFound = false;
        int multipleCount = 0;
        ArrayList<String> multipleEntries = new ArrayList<>();

        System.out.print("Type in either Request UID or Patient UID:");

        String input = scanner.nextLine();




    }

    //DELETE LAB REQUEST

    public void editLabRequest(){

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
