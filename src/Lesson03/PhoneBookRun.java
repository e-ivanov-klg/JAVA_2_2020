package Lesson03;

public class PhoneBookRun{
    public static void main(String[] args) {
        PhoneBook newPhoneBook = new PhoneBook();
        newPhoneBook.add(new Person("Ivanov", "123-123"));
        newPhoneBook.add(new Person("Petrov", "456-456"));
        newPhoneBook.add(new Person("Sidorov", "789-789"));
        newPhoneBook.add(new Person("Ivanov", "321-321"));
        newPhoneBook.add(new Person("Petrov", "456-879"));
        newPhoneBook.add(new Person("Ivanov", "365-258"));
        System.out.println(newPhoneBook);
        System.out.println(newPhoneBook.get("Ivanov"));
        System.out.println(newPhoneBook.get("Petrov"));
    }
}
