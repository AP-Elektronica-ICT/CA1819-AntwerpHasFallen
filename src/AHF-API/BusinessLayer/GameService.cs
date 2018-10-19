using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace BusinessLayer
{
    public class GameService
    {
        private readonly GameContext context;

        public GameService(GameContext context)
        {
            this.context = context;
        }

        public List<Game> getGames()
        {
            return context.Games.Include(g => g.Teams).ToList();
        }
        
        public Game newGame(int teams, string[] teamNames)
        {
            Game game = new Game();
            game.Teams = new List<Team>();
            for (int i = 0; i < teams; i++)
            {
                Team team = new Team(teamNames[i]);
                game.Teams.Add(team);
                context.Add(team);
            }
            context.Add(game);
            context.SaveChanges();
            return game;
        }
    }
}
