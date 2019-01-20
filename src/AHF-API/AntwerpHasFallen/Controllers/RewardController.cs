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
        public IActionResult Reward(int teamId, [FromHeader] string difficulty, [FromHeader] string gotIngredient, [FromHeader] string answer, [FromBody] List<string> missingIngredients)
        {
            bool b_answer;
            bool b_gotIngredient;
            if (answer == "true")
                b_answer = true;
            else
                b_answer = false;
            if (gotIngredient == "true")
                b_gotIngredient = true;
            else
                b_gotIngredient = false;
            Team team = rewardService.Reward(teamId, difficulty, b_answer, b_gotIngredient, missingIngredients);
            if (team != null)
                return Ok(team);
            return NotFound();
        }
    } 
}
