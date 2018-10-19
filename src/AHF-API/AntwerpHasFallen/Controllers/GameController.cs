using BusinessLayer;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
    public class GameController : Controller
    {
        private readonly GameService service;

        public GameController(GameService service)
        {
            this.service = service;
        }

        [Route("api/games")]
        [HttpGet]
        public IActionResult getAllGames()
        {
            return Ok(service.getGames());
        }
    }
}
