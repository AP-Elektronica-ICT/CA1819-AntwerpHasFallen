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
        private readonly GameService service;

        public GameController(GameService service)
        {
            this.service = service;
        }
        
        [HttpGet]
        public IActionResult getAllGames()
        {
            return Ok(service.getGames());
        }

        [HttpGet("{id}")]
        public IActionResult getGame(int id)
        {
            Game game = service.GetGame(id);
            if (game != null)
                return Ok(service.GetGame(id));
            return NotFound();
        }

        [Route("newgame/{teams}")]
        [HttpPost()]
        public IActionResult startNewGame(int teams, [FromBody] IEnumerable<string> teamNames)
        {
            return Ok(service.newGame(teams, teamNames.ToArray<string>()));
        }
    }
}
