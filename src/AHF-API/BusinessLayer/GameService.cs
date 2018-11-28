using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using DataLayer.Model.InventoryModel;

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
            Game game = context.Games.Include(g => g.Teams).SingleOrDefault(g => g.Id == id);
            if (game == null)
                return false;
            List<Team> teams = new List<Team>();
            foreach (Team team in game.Teams)
            {
                teams.Add(context.Teams.Include(t => t.Players).Include(t => t.Inventory).SingleOrDefault(t => t.Id == team.Id));
            }

            foreach(Team t in teams)
            {
                if (t == null)
                    return false;
                /*if (t.Inventory != null)                   !!Conflict in db, door mapping EF van list!!
                    context.Inventories.Remove(t.Inventory);*/
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
            if (isDifferent(teamNames))
            {
                for (int i = 0; i < teams; i++)
                {
                    Team team = new Team(teamNames[i]);
                    Inventory inventory = new Inventory();
                    context.Inventories.Add(inventory);
                    team.Inventory = inventory;
                    game.Teams.Add(team);
                    context.Teams.Add(team);
                }
                context.Games.Add(game);
                context.SaveChanges();
                return game;
            }
            else return null;
        }

        public bool isDifferent(string[] checkArray)
        {
            bool different = true;
            for(int i = 0; i < checkArray.Length - 1; i++)
                for(int j = i + 1; j < checkArray.Length; j++)
                    if (RemoveWhiteSpaces(checkArray[i]).Equals(RemoveWhiteSpaces(checkArray[j])))
                    {
                        different = false;
                        break;
                    }
            return different;
        }

        public string RemoveWhiteSpaces(string s)
        {
            return new string(s.ToCharArray().Where(c => !Char.IsWhiteSpace(c)).ToArray());
        }
    }
}
