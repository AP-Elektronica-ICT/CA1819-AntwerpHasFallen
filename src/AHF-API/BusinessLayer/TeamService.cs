using DataLayer;
using DataLayer.Model;
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
                if(previousLocations != null && previousLocations.Count > 0)
                {
                    foreach (PreviousLocation prevLoc in previousLocations)
                    {
                        if (prevLoc.Location == locations[id])
                        {
                            id = rand.Next(locations.Count());
                        }
                        else
                        {
                            checking = false;
                            break;
                        }
                    }
                }
                else
                {
                    checking = false;
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
    }
}
