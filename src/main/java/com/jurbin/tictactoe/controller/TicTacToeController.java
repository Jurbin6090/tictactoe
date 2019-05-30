package com.jurbin.tictactoe.controller;

import com.jurbin.tictactoe.entity.Game;
import com.jurbin.tictactoe.entity.GameState;
import com.jurbin.tictactoe.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController("/ticTacToe")
public class TicTacToeController {
    private GameRepository gameRepository;

    @Autowired
    public TicTacToeController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/getAllGames")
    @ResponseBody
    private Iterable<Game> getGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/getAGame/{id}")
    @ResponseBody
    private Game getGames(@PathVariable(value = "id") Integer id) {
        Optional<Game> optionalGame = gameRepository.findById(id);

        if(optionalGame.isPresent())
            return optionalGame.get();

        return null;
    }

    @PostMapping("/createGame")
    @ResponseBody
    private Game createGame(@RequestParam String playerOneName, @RequestParam String playerTwoName) {
        Game game = new Game(playerOneName, playerTwoName);
        return gameRepository.save(game);
    }

    @PostMapping("/takeTurn/{gameId}/{row}/{column}")
    @ResponseBody
    private Game takeTurn(@PathVariable(value="gameId") Integer gameId, @PathVariable(value="row") Integer row, @PathVariable(value="column") Integer column) {
        Game game;

        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isPresent()) {
            game = optionalGame.get();
        } else {
            return null;
        }

        if (game.getState().equals(GameState.INPROGRESS) || game.getState().equals(GameState.INVALID)) {
            game.takeTurn(row - 1, column - 1);
        }
        return gameRepository.save(game);
    }
}
