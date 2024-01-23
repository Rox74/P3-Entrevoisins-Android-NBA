package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyNeighbourGenerator {

    private List<Neighbour> neighbours;

    public static List<Neighbour> DUMMY_NEIGHBOURS = Arrays.asList(
            new Neighbour(1, "Caroline", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Tarteaucitron ; 7km",
                    "+33 6 12 34 56 78", "Salut ! J'adore le yoga et je cherche des partenaires pour pratiquer en plein air. Si ça te dit, contacte-moi !", false),
            new Neighbour(2, "Jack", "https://i.pravatar.cc/150?u=a042581f4e29026704e", "Mont-au-vent ; 8km",
                    "+33 6 23 45 67 89", "Bonjour ! Passionné de cuisine italienne, je recherche des amateurs pour découvrir de nouveaux restaurants. Partants ?", false),
            new Neighbour(3, "Chloé", "https://i.pravatar.cc/150?u=a042581f4e29026704f", "Lac-des-cygnes ; 6km",
                    "+33 6 34 56 78 90", "Salut ! Je suis fan d'escalade et je cherche des partenaires d'escalade. Conquérons ensemble quelques sommets !", false),
            new Neighbour(4, "Vincent", "https://i.pravatar.cc/150?u=a042581f4e29026704a", "Mont-chocolat ; 10km",
                    "+33 6 45 67 89 01", "Salut ! Je suis fan de jeux de société et je cherche des partenaires pour des soirées jeux. Qui est partant ?", false),
            new Neighbour(5, "Elodie", "https://i.pravatar.cc/150?u=a042581f4e29026704b", "Val-de-roses ; 5km",
                    "+33 6 56 78 90 12", "Bonjour ! J'aime la photographie et je recherche des modèles pour des séances photo. Intéressé(e) ?", false),
            new Neighbour(6, "Sylvain", "https://i.pravatar.cc/150?u=a042581f4e29026704c", "Colline-aux-étoiles ; 9km",
                    "+33 6 67 89 01 23", "Salut ! Je suis passionné de cinéma et je cherche des compagnons pour aller voir des films. Ça te dit ?", false),
            new Neighbour(7, "Laetitia", "https://i.pravatar.cc/150?u=a042581f4e29026703d", "Vallée-des-merveilles ; 7km",
                    "+33 6 78 90 12 34", "Salut ! J'adore faire du vélo et je cherche des partenaires de vélo. Explorons de nouvelles routes ensemble !", false),
            new Neighbour(8, "Dan", "https://i.pravatar.cc/150?u=a042581f4e29026703b", "Forêt-enchantée ; 8km",
                    "+33 6 89 01 23 45", "Salut ! Je suis passionné de musique électronique et je cherche des amateurs pour des soirées électro. Partants ?", false),
            new Neighbour(9, "Joseph", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Pic-des-aventuriers ; 6km",
                    "+33 6 90 12 34 56", "Bonjour ! Je suis amateur de randonnée et je cherche des compagnons pour des balades en pleine nature. Intéressés ?", false),
            new Neighbour(10, "Emma", "https://i.pravatar.cc/150?u=a042581f4e29026706d", "Montagne-des-livres ; 10km",
                    "+33 6 01 23 45 67", "Salut ! J'adore lire et je fais partie d'un club de lecture. Rejoins-moi pour notre prochaine discussion et partage tes livres préférés !", false),
            new Neighbour(11, "Patrick", "https://i.pravatar.cc/150?u=a042581f4e29026702d", "Baie-des-aventuriers ; 5km",
                    "+33 6 45 67 89 01", "Salut ! Je suis passionné de plongée sous-marine et je cherche des partenaires pour explorer les fonds marins. Qui est partant ?", false),
            new Neighbour(12, "Ludovic", "https://i.pravatar.cc/150?u=a042581f3e39026702d", "Village-des-artistes ; 9km",
                    "+33 6 23 45 67 89", "Salut ! Je suis amateur d'art et je cherche des créatifs pour collaborer sur des projets. Créons quelque chose d'incroyable ensemble !", false)
    );

    static List<Neighbour> generateNeighbours() {
        return new ArrayList<>(DUMMY_NEIGHBOURS);
    }
}
