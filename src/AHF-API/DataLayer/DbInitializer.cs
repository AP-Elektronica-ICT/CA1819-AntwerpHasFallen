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
            if (!context.Quizpuzzles.Any())
            {
                Quizpuzzles quiz = new Quizpuzzles();


                quiz.Answers = "1008,1420,1052";
                quiz.Question = "Wanneer heeft Antwerpen zijn Stadszegel gekregen";
                quiz.CorrectAnswer = "1008";
                context.Quizpuzzles.Add(quiz);
                context.SaveChanges();
            }
        }
    }
}
