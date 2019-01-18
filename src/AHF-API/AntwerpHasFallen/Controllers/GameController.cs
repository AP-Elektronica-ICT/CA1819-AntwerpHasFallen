using BusinessLayer;
using DataLayer.Model;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
    [Produces("application/json")]
    [Route("api/games")]
    public class GameController : Controller
    {
        private readonly GameService gameService;
        private readonly TeamService teamService;

        public GameController(GameService gameService, TeamService teamService)
        {
            this.gameService = gameService;
            this.teamService = teamService;
        }
        
        [HttpGet]
        public IActionResult getAllGames()
        {
            return Ok(gameService.getGames());
        }

        [Route("history")]
        [HttpGet()]
        public IActionResult GetFinishedGames()
        {
            return Ok(gameService.GetFinishedGames());
        }

        [Route("history/{gameId}")]
        [HttpGet()]
        public IActionResult GetFinishedGames(int gameId)
        {
            return Ok(gameService.GetFinishedGame(gameId));
        }

        [Route("{id}")]
        [HttpGet()]
        public IActionResult getGame(int id)
        {
            Game game = gameService.GetGame(id);
            if (game != null)
                return Ok(game);
            return NotFound();
        }

        [Route("{id}")]
        [HttpDelete()]
        public IActionResult deleteGame(int id)
        {
            if(gameService.deleteGame(id))
                return Ok(true);
            return NotFound();
        }

        [Route("newgame/{teams}")]
        [HttpPost()]
        public IActionResult startNewGame(int teams, [FromBody] IEnumerable<string> teamNames)
        {
            Game newGame = gameService.newGame(teams, teamNames.ToArray<string>());
            if (newGame != null)
                return Ok(newGame);
            else return NotFound();
        }

        [Route("join/{gameId}")]
        [HttpPost()]
        public IActionResult joinGame(int gameId, [FromBody] int teamId)
        {
            Player player = teamService.JoinTeam(gameId, teamId);
            if (player != null)
                return Ok(player);
            return NotFound();
        }
    }
}
