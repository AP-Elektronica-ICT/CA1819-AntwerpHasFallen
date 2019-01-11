using BusinessLayer;
using DataLayer.Model;
using DataLayer.Model.InventoryModel;
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
            Team team = teamService.GetTeam(id);
            if (team != null)
                return Ok(team);
            return NotFound();
        }

        [Route("{id}/use/{shopItemId}")]
        [HttpPost]
        public IActionResult useShopItem(int id, int shopItemId, [FromBody] int targetTeamId)
        {
            Inventory inventory = teamService.UseShopItem(shopItemId, id, targetTeamId);
            if (inventory != null)
                return Ok(inventory);
            return NotFound();
        }

        [Route("randomlocation/{id}")]
        [HttpGet]
        public IActionResult getRandomLocation(int id)
        {
            Location locatie = teamService.getRandomLocation(id);
            if(locatie != null)
            {
                return Ok(locatie);
            }
            else
            {
                return NotFound();
            }
        }
            
        
    }
}
