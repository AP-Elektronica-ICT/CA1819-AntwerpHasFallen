using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class FinishedGame
    {
        public FinishedGame(int gameId)
        {
            GameId = gameId;
        }

        public int Id { get; set; }
        public int GameId { get; set; }
        public string TeamsLeaderboard { get; set; }
        public string Winner { get; set; }
    }
}
