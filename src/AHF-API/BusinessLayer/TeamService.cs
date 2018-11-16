﻿using DataLayer;
using DataLayer.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
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

        public List<Team> GetTeams()
        {
            return context.Teams.Include(t => t.Players).ToList();
        }

        public Team GetTeam(int id)
        {
            try
            {
                return context.Teams.Include(t => t.Players).Single(t => t.Id == id);
            }catch (ArgumentNullException e)
            {
                return null;
            }catch (InvalidOperationException e)
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
    }
}
