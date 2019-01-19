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
    [Route("api/rewards")]
    public class RewardController : Controller
    {
        private readonly RewardService rewardService;

        public RewardController(RewardService rewardService)
        {
            this.rewardService = rewardService;
        }

        [Route("{teamId}")]
        [HttpPut]
        public IActionResult Reward(int teamId, [FromBody] string difficulty, [FromBody] bool gotIngredient, [FromBody] bool answer, [FromBody] List<string> missingIngredients)
        {
            Team team = rewardService.Reward(teamId, difficulty, answer, gotIngredient, missingIngredients);
            if (team != null)
                return Ok(team);
            return NotFound();
        }
    } 
}
