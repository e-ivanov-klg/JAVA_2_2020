package Lesson03;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneBook {
    private Set<Person> abonentList;

    PhoneBook () {
        abonentList = new HashSet<>();
    }

    public void add (Person newPerson) {
        abonentList.add(newPerson);
    }

    public List<Person> get (String name){
        List<Person> searchResult = new ArrayList<>();
        for (Person searchName : abonentList){
            if (searchName.getName() == name) {
                searchResult.add(searchName);
            }
        }
        return searchResult;
    }

    @Override
    public String toString() {
        return abonentList.toString();
    }
}
