package businessLogic;

import dataAcces.ClientDAO;
import model.dto.ClientDTO;
import model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the logic part on a client
 */
public class ClientLogic {
    private ClientDAO clientDAO;

    public ClientLogic() {
        clientDAO = new ClientDAO();
    }

    public List<ClientDTO> findAll() {
        List<Client> clients = clientDAO.findAll();
        List<ClientDTO> result = new ArrayList<>();
        clients.forEach(client -> {
            if (client.getDeleted() == 0) {
                result.add(new ClientDTO(client));
            }
        });
        return result;
    }

    public void add(ClientDTO client) {
        clientDAO.add(new Client(client));
    }

    public void update(ClientDTO client) {
        clientDAO.update(new Client(client));
    }

    public void delete(ClientDTO client) {
        Client deletedClient = new Client(client);
        deletedClient.setDeleted(1);
        clientDAO.update(deletedClient);
    }


}
