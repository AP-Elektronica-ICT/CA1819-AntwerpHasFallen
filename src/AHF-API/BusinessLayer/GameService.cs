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
        private readonly ShopService shopService;
        private readonly InventoryService inventoryService;

        public GameService(GameContext context)
        {
            this.context = context;
            this.shopService = new ShopService(context);
            this.inventoryService = new InventoryService(context);
        }

        public List<Game> getGames()
        {
            return context.Games.Include(g => g.Teams).ThenInclude(t => t.Players).ToList(); 
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
                teams.Add(context.Teams.Include(t => t.Players).Include(t => t.Inventory).Include(t => t.PreviousLocations).SingleOrDefault(t => t.Id == team.Id));
            }

            saveGameStats(game, teams);

            foreach(Team t in teams)
            {
                if (t == null)
                    return false;
                if (t.Inventory != null)
                {             // !!Conflict in db, door mapping EF van list!!
                    Inventory inventory = context.Inventories.Include(i => i.Ingredients).Include(i => i.Items).SingleOrDefault(i => i.Id == t.Inventory.Id);
                    foreach (InventoryItem ingredient in inventory.Ingredients)
                        context.InventoryItems.Remove(ingredient);
                    foreach (InventoryItem shopItem in inventory.Items)
                        context.InventoryItems.Remove(shopItem);
                    context.Inventories.Remove(t.Inventory);
                }
                if(t.PreviousLocations != null)
                {
                    t.PreviousLocations = new List<PreviousLocation>();
                }
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

        public FinishedGame saveGameStats(Game game, List<Team> teams)
        {
            FinishedGame finishedGame = new FinishedGame(game.Id);
            finishedGame.TeamsLeaderboard = "";
            List<Item> ingredients = shopService.GetIngredients();
            int winnerCount = 0;

            foreach(Team t in teams)
            {
                Inventory inventory = inventoryService.getInventory(t.Inventory.Id);
                int teamCount = inventory.Ingredients.Count;
                if (teamCount > winnerCount)
                {
                    winnerCount = teamCount;
                    finishedGame.Winner = t.Name;
                    finishedGame.TeamsLeaderboard.Insert(0, t.Name + ":" + teamCount + "/");
                }
                if (!finishedGame.TeamsLeaderboard.Contains(t.Name))
                {
                    int beginSearch = 0;
                    while (finishedGame.TeamsLeaderboard.IndexOf('/', beginSearch) > 0)
                    {
                        int start = finishedGame.TeamsLeaderboard.IndexOf(':', beginSearch);
                        int end = finishedGame.TeamsLeaderboard.IndexOf('/', beginSearch);
                        int count = Convert.ToInt32(finishedGame.TeamsLeaderboard.Substring(start + 1, end - (start + 1)));
                        if (teamCount > count)
                        {
                            finishedGame.TeamsLeaderboard.Insert(beginSearch, t.Name + ":" + teamCount + "/");
                            break;
                        }
                        beginSearch = end;
                    }
                    if (!finishedGame.TeamsLeaderboard.Contains(t.Name)) finishedGame.TeamsLeaderboard += t.Name + ":" + teamCount + "/";
                }
            }
            return finishedGame;     
        }

        public Game newGame(int teams, string[] teamNames)
        {
            Game game = new Game();
            game.Teams = new List<Team>();
            if (isDifferent(teamNames))
            {
                for (int i = 0; i < teams; i++)
                {
                    Team team = new Team(teamNames[i], 100);
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
