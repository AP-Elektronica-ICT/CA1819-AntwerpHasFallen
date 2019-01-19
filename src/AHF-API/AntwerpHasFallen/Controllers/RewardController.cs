using BusinessLayer;
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
    }
}
