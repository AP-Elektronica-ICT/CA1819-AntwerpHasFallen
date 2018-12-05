using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class PlayerService
    {
        private readonly GameContext gameContext;

        public PlayerService(GameContext gameContext)
        {
            this.gameContext = gameContext;
        }

        public List<Player> GetPlayers()
        {
            return gameContext.Players.ToList();
        }

        public Player GetPlayer(int id)
        {
            return gameContext.Players.Find(id);
        }
    }
}
