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

        [Route("{id}")]
        [HttpGet()]
        public IActionResult getGame(int id)
        {
            Game game = gameService.GetGame(id);
            if (game != null)
                return Ok(gameService.GetGame(id));
            return NotFound();
        }

        [Route("{id}")]
        [HttpDelete()]
        public IActionResult deleteGame(int id)
        {
            if(gameService.deleteGame(id))
                return Ok();
            return NotFound();
        }

        [Route("newgame/{teams}")]
        [HttpPost()]
        public IActionResult startNewGame(int teams, [FromBody] IEnumerable<string> teamNames)
        {
            return Ok(gameService.newGame(teams, teamNames.ToArray<string>()));
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
