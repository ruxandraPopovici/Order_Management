package bll.validators;

import dao.AbstractDAO;
import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that implements the business logic for the Client table / class
 */

public class ClientBLL {
    private AbstractDAO<Client> dao;
    private List<Validator<Client>> validators;

    public ClientBLL(){
        this.validators = new ArrayList<Validator<Client>>();
        this.validators.add(new NameValidator());
        this.validators.add(new EmailValidator());

        this.dao = new ClientDAO();
    }

    public Client findClientByID (int id){
        Client client = dao.findByField(id, "id");
        if(client == null){
            throw new NoSuchElementException("Client with ID: " + id + " was not found!");
        }
        return client;
    }

    public Client findClientByName(String name){
        Client client = dao.findByField(name, "name");

        if(client == null){
            throw new NoSuchElementException("Client by the name of " + name + " was not found!");
        }
        return client;
    }

    public int insertClient(Client client){
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return dao.insert(client);
    }
    public void deleteClient(Client client){
        dao.delete(client.getName(), "name");
    }
    public void deleteClient(String name){
        dao.delete(name, "name");
        (new OrderBLL()).deleteClientOrders(name);
    }

    public List<Client> showClients(){
        return dao.findAll();
    }

    public int getSize(){
        return dao.getSize();
    }
}
