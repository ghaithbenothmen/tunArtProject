package Controllers;

import Entites.Role;
import Entites.User;

import java.util.ArrayList;
import java.util.List;


public class AfficherArtistesController {
    // Méthode pour récupérer la liste des artistes
    public List<User> getListeArtistes(List<User> utilisateurs) {
        List<User> artistes = new ArrayList<>();
        for (User utilisateur : utilisateurs) {
            if (utilisateur.getRole() == Role.ARTISTE) {
                artistes.add(utilisateur);
            }
        }
        return artistes;
    }
}
