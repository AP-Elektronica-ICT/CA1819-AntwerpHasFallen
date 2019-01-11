using DataLayer;
using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLayer
{
    public class TeamService
    {
        private readonly GameContext context;

        public TeamService(GameContext context)
        {
            this.context = context;
        }

        public List<Team> GetTeams()
        {
            return context.Teams.Include(t => t.Players).ToList();
        }

        public Team GetTeam(int id)
        {
            try
            {
                return context.Teams.Include(p => p.PreviousLocations).Include(t => t.Players).Include(t => t.Inventory).Single(t => t.Id == id);
            }catch (ArgumentNullException)
            {
                return null;
            }catch (InvalidOperationException)
            {
                return null;
            }
        }

        public Player JoinTeam(int gameId, int teamId)
        {
            Game game = context.Games.Include(g => g.Teams).SingleOrDefault(g => g.Id == gameId);
            if (game == null) return null;
            Team team = null;
            foreach (Team t in game.Teams)
                if (t.Id == teamId)
                    team = t;
            if (team == null) return null;
            Player player = new Player() { GameId = game.Id, TeamId = team.Id };
            context.Players.Add(player);
            team.Players.Add(player);
            context.SaveChanges();
            return player;
        }

        public Location getRandomLocation(int teamId)
        {
            bool checking = true;
            Random rand = new Random();
            int id = 0;
            LocationService locationService = new LocationService(context);
            List<Location> locations = locationService.getLocations();
            Team team = GetTeam(teamId);
            List<PreviousLocation> previousLocations = team.PreviousLocations;
            id = rand.Next(locations.Count());

            while (checking)
            {
                if (previousLocations.Count != locations.Count)
                {
                    if (previousLocations != null && previousLocations.Count > 0)
                    {
                        foreach (PreviousLocation prevLoc in previousLocations)
                        {
                            if (prevLoc.Location == locations[id])
                            {
                                checking = true;
                                id = rand.Next(locations.Count());
                                break;
                            }
                            else
                            {
                                checking = false;
                            }
                        }
                    }
                    else
                    {
                        checking = false;
                    }
                }
                else
                {
                    return null;
                }
            }

            PreviousLocation currentLocation = new PreviousLocation();
            currentLocation.LocationId = locations[id].Id;
            currentLocation.Location = locations[id];
            currentLocation.Team = team;
            currentLocation.TeamId = teamId;

            team.PreviousLocations.Add(currentLocation);

            context.SaveChanges();

            return currentLocation.Location;
        }

        public Team StopBlackout(int teamId)
        {
            Team team = GetTeam(teamId);
            team.Blackout = null;
            context.SaveChanges();
            return team;
        }

        public Inventory UseShopItem(int inventoryItemId, int teamId, int targetTeamId)
        {
            Team team = GetTeam(teamId);
            Team target = GetTeam(targetTeamId);
            if (team != null && target != null)
            {
                Inventory inventory = context.Inventories.Include(i => i.Items).ThenInclude(i => i.Item).SingleOrDefault(i => i.Id == team.Inventory.Id);
                if(inventory != null)
                {
                    InventoryItem InventoryItem = context.InventoryItems.Include(i => i.Item).SingleOrDefault(i => i.Id == inventoryItemId);
                    if (InventoryItem != null) {
                        InventoryItem usedItem = null;
                        foreach (InventoryItem item in inventory.Items) //check if item exist in this teams inventory
                            if (item.Item.Id == InventoryItem.Item.Id && item.Quantity >= 1) usedItem = item;
                        if (usedItem != null)
                        {
                            if(usedItem.Item.Name.ToLower() == "blackout")
                            {
                                Blackout(team, target);
                            }
                            inventory.Items.Remove(usedItem);
                        }
                    }
                }
                context.SaveChanges();
                return inventory;
            }
            return null;
        }
        #region shopItem use methods
        private void Blackout(Team team, Team targetTeam)
        {
            targetTeam.Blackout = team.Name;
        }
        #endregion
    }
}
