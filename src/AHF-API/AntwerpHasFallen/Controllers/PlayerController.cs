using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BusinessLayer;

namespace AntwerpHasFallen.Controllers
{
    [Produces("application/json")]
    [Route("api/players")]
    public class PlayerController: Controller
    {
        private readonly PlayerService playerService;

        public PlayerController(PlayerService playerService)
        {
            this.playerService = playerService;
        }

        public IActionResult getAllPlayers()
        {
            return Ok(playerService.GetPlayers());
        }

        public IActionResult getPlayer(int id)
        {
            if (playerService.GetPlayer(id) != null)
                return Ok(playerService.GetPlayer(id));
            return NotFound();
        }
    }
}
