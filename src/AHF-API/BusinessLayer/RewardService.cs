using DataLayer;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class RewardService
    {
        private readonly GameContext context;

        public RewardService(GameContext context)
        {
            this.context = context;
        }
    }
}
