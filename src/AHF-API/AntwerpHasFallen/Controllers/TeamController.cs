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

        [Route("{id}/use/{inventoryItemId}")]
        [HttpPost]
        public IActionResult useShopItem(int id, int inventoryItemId, [FromBody] int targetTeamId)
        {
            Inventory inventory = teamService.UseShopItem(inventoryItemId, id, targetTeamId);
            if (inventory != null)
                return Ok(inventory);
            return NotFound();
        }

        [Route("{id}/blackout")]
        [HttpPut]
        public IActionResult StopBlackout(int id)
        {
            Team team = teamService.StopBlackout(id);
            if (team != null)
                return Ok(team);
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

        [Route("{id}/{money}")]
        [HttpPut]
        public IActionResult updateMoney(int id, int money)
        {
            Team team = teamService.UpdateMoney(id, money);
            if(team != null)
                return Ok();
            return NotFound();
        }
        
    }
}
