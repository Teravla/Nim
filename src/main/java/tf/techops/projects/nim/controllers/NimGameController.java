package tf.techops.projects.nim.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class NimGameController {

    private int totalMatches; // Initialiser ici
    private int numberOfPlayers;
    private String currentPlayer; // Variable pour suivre le joueur courant
    private int roundNumber;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("totalMatches", totalMatches); // Ajoutez ici
        return "home";  // Page d'accueil
    }

    @GetMapping("/flipCoin")
    public String flipCoin(Model model) {
        Random random = new Random();
        currentPlayer = random.nextBoolean() ? "left" : "right"; // Choisit un joueur aléatoirement
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("totalMatches", totalMatches); // Ajoute le nombre d'allumettes
        model.addAttribute("matchOptions", Arrays.asList(1, 2, 3)); // Ajoute les options d'allumettes

        return "game"; // Reste sur la page de jeu
    }

    @PostMapping("/start")
    public String startGame(@RequestParam int totalMatches, @RequestParam int numberOfPlayers, Model model) {
        this.totalMatches = totalMatches;
        this.numberOfPlayers = numberOfPlayers;
        this.roundNumber = 0;

        List<Integer> matchOptions = Arrays.asList(1, 2, 3);
        model.addAttribute("totalMatches", totalMatches);
        model.addAttribute("numberOfPlayers", numberOfPlayers);
        model.addAttribute("matchOptions", matchOptions);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("roundNumber", roundNumber);

        return "game";  // Page de jeu
    }

    @PostMapping("/play")
    public String playTurn(@RequestParam int matches, Model model) {
        if (matches < 1 || matches > 3) {
            model.addAttribute("error", "Choisissez entre 1 et 3 allumettes.");
            model.addAttribute("totalMatches", totalMatches);
            List<Integer> matchOptions = Arrays.asList(1, 2, 3);
            model.addAttribute("matchOptions", matchOptions);
            model.addAttribute("currentPlayer", currentPlayer); // Ajouter le joueur courant
            return "game";  // Retourne à la page de jeu avec un message d'erreur
        }

        totalMatches -= matches;
        roundNumber++;

        if (totalMatches <= 0) {
            model.addAttribute("message", "Vous avez malheureusement échoué. Vous êtes le maillon faible. Au revoir");
            totalMatches = 0;  // Réinitialiser les allumettes
        }

        model.addAttribute("totalMatches", totalMatches);
        List<Integer> matchOptions = Arrays.asList(1, 2, 3);
        model.addAttribute("matchOptions", matchOptions);
        currentPlayer = currentPlayer.equals("left") ? "right" : "left";
        model.addAttribute("currentPlayer", currentPlayer); // Mettre à jour le joueur courant

        return "game";  // Retourne à la page de jeu
    }
}