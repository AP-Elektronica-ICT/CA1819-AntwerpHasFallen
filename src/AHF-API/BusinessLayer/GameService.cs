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
            return context.Games.Include(g => g.Teams).ThenInclude(t => t.Players).ToList(); //.ThenInclude(t => t.Players)
        }

        public Game GetGame(int id)
        {
            return context.Games.Include(g => g.Teams).SingleOrDefault(g => g.Id == id);
        }

        public bool deleteGame(int id)
        {
            Game game = context.Games.Find(id);
            if (game == null)
                return false;
            List<Team> teams = game.Teams;

            foreach(Team t in teams)
            {
                if (t == null)
                    return false;
                if (t.Players != null)
                {
                    foreach (Player p in t.Players)
                    {
                        if (p == null)
                            return false;
                        context.Players.Remove(p);
                    }
                }
                context.Teams.Remove(t);
            }
            context.Games.Remove(game);
            context.SaveChanges();
            return true;            
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
