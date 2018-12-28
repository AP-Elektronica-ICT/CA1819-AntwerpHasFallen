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

        [Route("{id}")]
        [HttpGet]
        public IActionResult getTeam(int id)
        {
            if (teamService.GetTeam(id) != null)
                return Ok(teamService.GetTeam(id));
            return NotFound();
        }

        [Route("randomlocation/{id}")]
        [HttpGet]
        public IActionResult getRandomLocation(int id)
        {
            return Ok(teamService.getRandomLocation(id));
        }
            
        
    }
}
