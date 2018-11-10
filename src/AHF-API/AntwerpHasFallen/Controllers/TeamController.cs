using BusinessLayer;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
    [Produces("application/json")]
    [Route("api/teams")]
    public class TeamController : Controller
    {
        private readonly TeamService teamService;

        public TeamController(TeamService teamService)
        {
            this.teamService = teamService;
        }

        [HttpGet]
        public IActionResult getAllTeams()
        {
            return Ok(teamService.GetTeams());
        }

        [HttpGet("/players")]
        public IActionResult getAllPlayers()
        {
            return Ok(teamService.GetPlayers());
        }
    }
}
