using DataLayer.Model;
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
                Team t1 = new Team("testTeam");

                Team t2 = new Team("otherTeam");

                Team t3 = new Team("newTeam");

                Game g1 = new Game();
                g1.Teams.Add(t1);
                g1.Teams.Add(t2);
                g1.Teams.Add(t3);

                context.Teams.Add(t1);
                context.Teams.Add(t2);
                context.Teams.Add(t3);

                context.Games.Add(g1);

                context.SaveChanges();
            }
            if (!context.Locations.Any())
            {
                Location l1 = new Location(51.229023, 4.404622, "MAS");
                Location l2 = new Location(51.216968, 4.409315, "Rubenshuis");
                Location l3 = new Location(51.222759, 4.397382, "Het Steen");
            }
        }
    }
}
