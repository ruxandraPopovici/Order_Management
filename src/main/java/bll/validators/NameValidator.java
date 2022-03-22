package bll.validators;

import model.Client;

public class NameValidator implements Validator<Client>{
    @Override
    public void validate(Client client) {
        char [] name = client.getName().toCharArray();
        if(!(Character.isUpperCase(name[0]))){
            throw new IllegalArgumentException("Provide with valid name: first letter is not in upper case!");
        }
        for(int i = 0; i < name.length; i++){
            if(!Character.isLetter(name[i])){
                throw new IllegalArgumentException("Provide with valid name: there can only be letters!");
            }
        }
    }
}
