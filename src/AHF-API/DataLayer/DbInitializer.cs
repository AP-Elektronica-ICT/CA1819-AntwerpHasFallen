﻿using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DataLayer
{
    public class DbInitializer
    {
        public static void Initialize(GameContext context)
        {
            context.Database.EnsureCreated();

            if (!context.Games.Any())
            {
                Team t1 = new Team()
                {
                    Name = "testTeam",
                    PlayerCount = 3
                };

                Team t2 = new Team()
                {
                    Name = "otherTeam",
                    PlayerCount = 3
                };

                Team t3 = new Team()
                {
                    Name = "newTeam",
                    PlayerCount = 3
                };

                Game g1 = new Game()
                {
                    Teams = new List<Team>()
                };
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                context.Games.Add(g1);

                context.SaveChanges();
            }
        }
    }
}