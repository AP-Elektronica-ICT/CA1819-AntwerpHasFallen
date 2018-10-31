using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class TeamService
    {
        private readonly GameContext context;

        public TeamService(GameContext context)
        {
            this.context = context;
        }

        public Player JoinTeam(int gameId, int teamId)
        {
            Game game = context.Games.Find(gameId);
            Team team = context.Teams.Find(teamId);
            Player player = new Player() { Game = game, TeamId = team.Id };
            context.Players.Add(player);
            context.SaveChanges();
            return player;
        }
    }
}
